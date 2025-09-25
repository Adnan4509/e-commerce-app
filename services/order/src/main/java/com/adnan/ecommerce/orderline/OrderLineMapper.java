package com.adnan.ecommerce.orderline;

import com.adnan.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrder(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(Order.builder()
                        .id(request.orderId())
                        .build())
                .productId(request.productId())
                .build();
    }
}
