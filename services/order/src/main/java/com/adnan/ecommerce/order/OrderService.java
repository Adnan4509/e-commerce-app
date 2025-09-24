package com.adnan.ecommerce.order;

import com.adnan.ecommerce.customer.CustomerClient;
import com.adnan.ecommerce.exception.BusinessException;
import com.adnan.ecommerce.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
//    private final OrderRepository repo;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public Integer createdOrder(OrderRequest request) {

//        step-1: getting customer details -> customer microservice
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the given Id"));

//        step-2: purchase products -> product microservice
        this.productClient.purchaseProducts(request.products());

//        step-3:

    return null;
    }
}
