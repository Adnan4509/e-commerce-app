package com.adnan.ecommerce.kafka;

import com.adnan.ecommerce.customer.CustomerResponse;
import com.adnan.ecommerce.order.PaymentMethod;
import com.adnan.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
