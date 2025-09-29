package com.adnan.ecommerce.order;

import com.adnan.ecommerce.customer.CustomerClient;
import com.adnan.ecommerce.exception.BusinessException;
import com.adnan.ecommerce.kafka.OrderConfirmation;
import com.adnan.ecommerce.kafka.OrderProducer;
import com.adnan.ecommerce.orderline.OrderLineRequest;
import com.adnan.ecommerce.orderline.OrderLineService;
import com.adnan.ecommerce.payment.PaymentClient;
import com.adnan.ecommerce.payment.PaymentRequest;
import com.adnan.ecommerce.product.ProductClient;
import com.adnan.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createdOrder(OrderRequest request) {

//        step-1: getting customer details -> customer microservice
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the given Id"));

//        step-2: purchase products -> product microservice
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

//        step-3: persist order
        var order = this.repo.save(mapper.toOrder(request));

//        step-4: persist order lines
        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.SaveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
//        step-5: start payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

//        step-6: send the order confirmation -> notification microservice(kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
    return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repo.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));
    }
}
