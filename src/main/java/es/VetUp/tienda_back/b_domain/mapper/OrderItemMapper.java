package es.VetUp.tienda_back.b_domain.mapper;

import es.VetUp.tienda_back.b_domain.model.OrderItem;
import es.VetUp.tienda_back.b_domain.repository.entity.OrderItemEntity;
import es.VetUp.tienda_back.b_domain.service.dto.OrderItemDto;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapper {
    private static OrderItemMapper INSTANCE;

    private OrderItemMapper() {
    }

    public static OrderItemMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderItemMapper();
        }
        return INSTANCE;
    }

    public OrderItem fromOrderItemDtoToOrderItem(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }
        // No mapeamos order aquí para evitar referencias circulares
        return new OrderItem(
            orderItemDto.id(),
            orderItemDto.quantity(),
            null, // order se establece en otro lugar
            ProductMapper.getInstance().fromProductDtotoProduct(orderItemDto.product())
        );
    }

    public OrderItemDto fromOrderItemToOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return new OrderItemDto(
            orderItem.getId(),
            orderItem.getQuantity(),
            orderItem.getOrder() != null ? orderItem.getOrder().getId() : null,
            ProductMapper.getInstance().fromProducttoProductDto(orderItem.getProduct())
        );
    }

    public OrderItem fromOrderItemEntityToOrderItem(OrderItemEntity orderItemEntity) {
        if (orderItemEntity == null) {
            return null;
        }
        return new OrderItem(
            orderItemEntity.id(),
            orderItemEntity.quantity(),
            null, // order se establece en otro lugar
            ProductMapper.getInstance().fromProductEntitytoProduct(orderItemEntity.product())
        );
    }

    public OrderItemEntity fromOrderItemToOrderItemEntity(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return new OrderItemEntity(
            orderItem.getId(),
            orderItem.getQuantity(),
            orderItem.getOrder() != null ? orderItem.getOrder().getId() : null,
            ProductMapper.getInstance().fromProducttoProductEntity(orderItem.getProduct())
        );
    }

    public List<OrderItemDto> fromOrderItemListToOrderItemDtoList(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return null;
        }
        return orderItems.stream()
                .map(this::fromOrderItemToOrderItemDto)
                .collect(Collectors.toList());
    }

    public List<OrderItem> fromOrderItemDtoListToOrderItemList(List<OrderItemDto> orderItemDtos) {
        if (orderItemDtos == null) {
            return null;
        }
        return orderItemDtos.stream()
                .map(this::fromOrderItemDtoToOrderItem)
                .collect(Collectors.toList());
    }
}

