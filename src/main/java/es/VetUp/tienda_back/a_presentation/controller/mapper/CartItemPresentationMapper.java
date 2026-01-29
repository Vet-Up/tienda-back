package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartItemInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartItemUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CartItemDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.CartItemDto;
import es.VetUp.tienda_back.b_domain.service.dto.ProductDto;

public class CartItemPresentationMapper {
    private static CartItemPresentationMapper INSTANCE;

    private CartItemPresentationMapper() {
    }

    public static CartItemPresentationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartItemPresentationMapper();
        }
        return INSTANCE;
    }

    public CartItemDetailResponse fromCartItemDtoToCartItemDetailResponse(CartItemDto cartItemDto) {
        if (cartItemDto == null) {
            return null;
        }
        return new CartItemDetailResponse(
                cartItemDto.id(),
                cartItemDto.quantity(),
                ProductPresentationMapper.getInstance().fromProductDtoToToProductDetailResponse(cartItemDto.product())
        );
    }

    public CartItemDto fromCartItemInsertRequestToCartItemDto(CartItemInsertRequest cartItemInsertRequest) {
        if (cartItemInsertRequest == null) {
            return null;
        }
        return new CartItemDto(
                null,
                cartItemInsertRequest.quantity(),
                cartItemInsertRequest.cartId(),
                mapProduct(cartItemInsertRequest.productId())
        );
    }

    public CartItemDto fromCartItemUpdateRequestToCartItemDto(CartItemUpdateRequest cartItemUpdateRequest) {
        if (cartItemUpdateRequest == null) {
            return null;
        }
        // Update request only carries id and quantity; preserve cartId and product as null (no change)
        return new CartItemDto(
                cartItemUpdateRequest.id(),
                cartItemUpdateRequest.quantity(),
                null,
                null
        );
    }

    private ProductDto mapProduct(Long productId) {
        if (productId == null) {
            return null;
        }
        // return null here and let controller/service fetch the real ProductDto
        return null;
    }
}
