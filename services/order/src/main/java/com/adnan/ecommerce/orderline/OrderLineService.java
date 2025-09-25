package com.adnan.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer SaveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrder(request);
        return repository.save(order).getId();
    }
}
