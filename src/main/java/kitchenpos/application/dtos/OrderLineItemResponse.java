package kitchenpos.application.dtos;

import kitchenpos.domain.OrderLineItem;

public class OrderLineItemResponse {
    private final Long id;
    private final Long orderId;
    private final Long menuId;
    private final Long quantity;

    public OrderLineItemResponse(OrderLineItem orderLineItem) {
        this.id = orderLineItem.getId();
        this.orderId = orderLineItem.getOrder();
        this.menuId = orderLineItem.getMenuId();
        this.quantity = orderLineItem.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
