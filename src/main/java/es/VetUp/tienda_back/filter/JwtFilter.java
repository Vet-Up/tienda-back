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
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private final JwtService jwtService;
    private final HandlerMapping handlerMapping;

    public JwtFilter(JwtService jwtService, RequestMappingHandlerMapping handlerMapping) {
        this.jwtService = jwtService;
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        if ("OPTIONS".equalsIgnoreCase(method) || path.startsWith("/api/auth")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);

        Claims claims;
        UserDto user;
        try {
            claims = jwtService.validateToken(token);
            user = jwtService.getUserFromToken(token);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        boolean isAdmin = user.isAdmin() == UserRole.ADMIN;

        try {
            HandlerExecutionChain handlerChain = handlerMapping.getHandler(httpRequest);

            if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod handlerMethod) {

                RequireAdmin annotation = handlerMethod.getMethodAnnotation(RequireAdmin.class);

                if (annotation != null && !isAdmin) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
        } catch (Exception e) {
            // Error al obtener el handler, continuar con la petición
        }

        httpRequest.setAttribute("isAdmin", isAdmin);
        httpRequest.setAttribute("userId", user.id());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
