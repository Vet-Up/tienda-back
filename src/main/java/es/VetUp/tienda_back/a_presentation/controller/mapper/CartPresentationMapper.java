package es.VetUp.tienda_back.a_presentation.controller.mapper;

import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartInsertRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.request.CartUpdateRequest;
import es.VetUp.tienda_back.a_presentation.controller.webModel.response.CartDetailResponse;
import es.VetUp.tienda_back.b_domain.service.dto.CartDto;
import es.VetUp.tienda_back.b_domain.service.dto.UserDto;

public class CartPresentationMapper {
    private static CartPresentationMapper INSTANCE;

    private CartPresentationMapper() {
    }

    public static CartPresentationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartPresentationMapper();
        }
        return INSTANCE;
    }

    public CartDetailResponse fromCartDtoToCartDetailResponse(CartDto cartDto) {
        if (cartDto == null) {
            return null;
        }
        return new CartDetailResponse(
                cartDto.id(),
                cartDto.totalProducts(),
                cartDto.totalPrice(),
                UserPresentationMapper.getInstance().fromUserDtoToUserDetailResponse(cartDto.user()),
                cartDto.cartItems() == null ? null :
                        cartDto.cartItems().stream()
                                .map(CartItemPresentationMapper.getInstance()::fromCartItemDtoToCartItemDetailResponse)
                                .toList()
        );
    }

    public CartDto fromCartInsertRequestToCartDto(CartInsertRequest cartInsertRequest) {
        if (cartInsertRequest == null) {
            return null;
        }
        return new CartDto(
                null,
                cartInsertRequest.totalProducts(),
                cartInsertRequest.totalPrice(),
                mapUser(cartInsertRequest.userId()),
                null
        );
    }

    public CartDto fromCartUpdateRequestToCartDto(CartUpdateRequest cartUpdateRequest) {
        if (cartUpdateRequest == null) {
            return null;
        }
        return new CartDto(
                cartUpdateRequest.id(),
                cartUpdateRequest.totalProducts(),
                cartUpdateRequest.totalPrice(),
                mapUser(cartUpdateRequest.userId()),
                null
        );
    }

    private UserDto mapUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return new UserDto(userId, null, null, null, null, null, null, null, null, null, null);
    }
}

