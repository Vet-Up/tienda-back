package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.CartPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.AddProductToCartRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CartDetailResponse;
import es.VetUp.tienda_back.b_domain.service.CartService;
import es.VetUp.tienda_back.b_domain.service.JwtService;
import es.VetUp.tienda_back.b_domain.service.dto.CartDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;
import es.VetUp.tienda_back.config.annotation.RequireAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;
    private final JwtService jwtService;

    public CartController(CartService cartService, JwtService jwtService) {
        this.cartService = cartService;
        this.jwtService = jwtService;
    }

    @RequireAdmin
    @GetMapping
    public ResponseEntity<List<CartDetailResponse>> findAllCarts() {
        List<CartDto> carts = cartService.getAllCarts();
        List<CartDetailResponse> cartResponses = carts.stream()
                .map(CartPresentationMapper.getInstance()::fromCartDtoToCartDetailResponse)
                .toList();
        return new ResponseEntity<>(cartResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDetailResponse> getCartById(
            @PathVariable Long id,
            @RequestAttribute("userId") Long authenticatedUserId,
            @RequestAttribute("isAdmin") Boolean isAdmin) {

        CartDto cartDto = cartService.getCartById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!isAdmin && !cartDto.user().id().equals(authenticatedUserId)) {
            throw new RuntimeException("Unauthorized: You can only access your own cart");
        }

        CartDetailResponse cartDetailResponse =
                CartPresentationMapper.getInstance().fromCartDtoToCartDetailResponse(cartDto);
        return new ResponseEntity<>(cartDetailResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDetailResponse> getCartByUserId(
            @PathVariable Long userId,
            @RequestAttribute("userId") Long authenticatedUserId,
            @RequestAttribute("isAdmin") Boolean isAdmin) {

        if (!isAdmin && !userId.equals(authenticatedUserId)) {
            throw new RuntimeException("Unauthorized: You can only access your own cart");
        }

        CartDto cartDto = cartService.getCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));
        CartDetailResponse cartDetailResponse =
                CartPresentationMapper.getInstance().fromCartDtoToCartDetailResponse(cartDto);
        return new ResponseEntity<>(cartDetailResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartDetailResponse> createCart(@RequestBody CartInsertRequest cartInsertRequest) {
        CartDto cartDto = CartPresentationMapper.getInstance().fromCartInsertRequestToCartDto(cartInsertRequest);
        CartDto createdCart = cartService.createCart(cartDto);
        CartDetailResponse response = CartPresentationMapper.getInstance().fromCartDtoToCartDetailResponse(createdCart);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDetailResponse> updateCart(
            @PathVariable Long id,
            @RequestBody CartUpdateRequest cartUpdateRequest,
            @RequestAttribute("userId") Long authenticatedUserId,
            @RequestAttribute("isAdmin") Boolean isAdmin) {

        if (!id.equals(cartUpdateRequest.id())) {
            throw new RuntimeException("ID mismatch");
        }

        CartDto existingCart = cartService.getCartById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!isAdmin && !existingCart.user().id().equals(authenticatedUserId)) {
            throw new RuntimeException("Unauthorized: You can only update your own cart");
        }

        CartDto cartDto = CartPresentationMapper.getInstance().fromCartUpdateRequestToCartDto(cartUpdateRequest);
        CartDto updatedCart = cartService.updateCart(cartDto);
        CartDetailResponse response = CartPresentationMapper.getInstance().fromCartDtoToCartDetailResponse(updatedCart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(
            @PathVariable Long id,
            @RequestAttribute("userId") Long authenticatedUserId,
            @RequestAttribute("isAdmin") Boolean isAdmin) {

        CartDto existingCart = cartService.getCartById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!isAdmin && !existingCart.user().id().equals(authenticatedUserId)) {
            throw new RuntimeException("Unauthorized: You can only delete your own cart");
        }

        cartService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-product")
    public ResponseEntity<CartDetailResponse> addProductToCar(
            @RequestHeader("Authorization") String token,
            @RequestBody AddProductToCartRequest request) {

        String jwtToken = token.replace("Bearer ", "");

        UserDto user = jwtService.getUserFromToken(jwtToken);

        CartDto updatedCart = cartService.addProductToCart(
                user.id(),
                request.productId(),
                request.quantity()
        );

        CartDetailResponse response = CartPresentationMapper.getInstance()
                .fromCartDtoToCartDetailResponse(updatedCart);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

