package es.VetUp.tienda_back.filter;

import es.VetUp.tienda_back.b_domain.model.enums.UserRole;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import es.VetUp.tienda_back.config.annotation.RequireAdmin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtService jwtService;
    private final HandlerMapping handlerMapping;

    public JwtFilter(JwtService jwtService, RequestMappingHandlerMapping handlerMapping) {
        this.jwtService = jwtService;
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        log.info("JwtFilter - Processing request: {} {}", method, path);

        if ("OPTIONS".equalsIgnoreCase(method)
                || path.startsWith("/api/auth")
                || (path.startsWith("/api/users") && "POST".equalsIgnoreCase(method))
                || (path.startsWith("/api/products") && "GET".equalsIgnoreCase(method))
                || (path.startsWith("/api/categories") && "GET".equalsIgnoreCase(method))
                || (path.startsWith("/api/reviews") && ("GET".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)))) {
            log.info("JwtFilter - Bypassing authentication for: {} {}", method, path);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("JwtFilter - Missing or invalid Authorization header");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);

        Claims claims;
        UserDto user;
        try {
            claims = jwtService.validateToken(token);
            user = jwtService.getUserFromToken(token);
            log.info("JwtFilter - Token validated successfully for user: {}", user.username());
        } catch (Exception e) {
            log.error("JwtFilter - Token validation failed: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\":\"Invalid token or user not found\",\"details\":\"" + e.getMessage() + "\"}");
            response.getWriter().flush();
            return;
        }

        boolean isAdmin = user.isAdmin() == UserRole.ADMIN;

        try {
            HandlerExecutionChain handlerChain = handlerMapping.getHandler(httpRequest);

            if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod handlerMethod) {

                RequireAdmin annotation = handlerMethod.getMethodAnnotation(RequireAdmin.class);

                if (annotation != null && !isAdmin) {
                    log.warn("JwtFilter - User {} attempted to access admin-only endpoint", user.username());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
        } catch (Exception e) {
            log.error("JwtFilter - Error getting handler: {}", e.getMessage());
            // Error al obtener el handler, continuar con la petición
        }

        httpRequest.setAttribute("isAdmin", isAdmin);
        httpRequest.setAttribute("userId", user.id());
        log.info("JwtFilter - Authentication successful for user: {}, proceeding with request", user.username());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
