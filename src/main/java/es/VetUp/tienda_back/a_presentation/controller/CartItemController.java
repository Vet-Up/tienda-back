package es.VetUp.tienda_back.a_presentation.controller;

import es.VetUp.tienda_back.a_presentation.controller.mapper.CartItemPresentationMapper;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartItemInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartItemUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CartItemDetailResponse;
import es.VetUp.tienda_back.b_domain.service.CartItemService;
import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@CrossOrigin(origins = "*")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDetailResponse>> findAllCartItems() {
        List<CartItemDto> cartItems = cartItemService.getAllCartItems();
        List<CartItemDetailResponse> cartItemResponses = cartItems.stream()
                .map(CartItemPresentationMapper.getInstance()::fromCartItemDtoToCartItemDetailResponse)
                .toList();
        return new ResponseEntity<>(cartItemResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDetailResponse> getCartItemById(@PathVariable Long id) {
        CartItemDto cartItemDto = cartItemService.getCartItemById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        CartItemDetailResponse cartItemDetailResponse =
                CartItemPresentationMapper.getInstance().fromCartItemDtoToCartItemDetailResponse(cartItemDto);
        return new ResponseEntity<>(cartItemDetailResponse, HttpStatus.OK);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItemDetailResponse>> getCartItemsByCartId(@PathVariable Long cartId) {
        List<CartItemDto> cartItems = cartItemService.getCartItemsByCartId(cartId);
        List<CartItemDetailResponse> cartItemResponses = cartItems.stream()
                .map(CartItemPresentationMapper.getInstance()::fromCartItemDtoToCartItemDetailResponse)
                .toList();
        return new ResponseEntity<>(cartItemResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItemDetailResponse> createCartItem(@RequestBody CartItemInsertRequest cartItemInsertRequest) {
        CartItemDto cartItemDto = CartItemPresentationMapper.getInstance().fromCartItemInsertRequestToCartItemDto(cartItemInsertRequest);
        CartItemDto createdCartItem = cartItemService.createCartItem(cartItemDto);
        CartItemDetailResponse response = CartItemPresentationMapper.getInstance().fromCartItemDtoToCartItemDetailResponse(createdCartItem);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemDetailResponse> updateCartItem(@PathVariable Long id, @RequestBody CartItemUpdateRequest cartItemUpdateRequest) {
        CartItemUpdateRequest req = new CartItemUpdateRequest(id, cartItemUpdateRequest.quantity());
        CartItemDto cartItemDto = CartItemPresentationMapper.getInstance().fromCartItemUpdateRequestToCartItemDto(req);
        CartItemDto updatedCartItem = cartItemService.updateCartItem(cartItemDto);
        CartItemDetailResponse response = CartItemPresentationMapper.getInstance().fromCartItemDtoToCartItemDetailResponse(updatedCartItem);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
