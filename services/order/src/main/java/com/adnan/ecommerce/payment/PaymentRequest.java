package com.adnan.ecommerce.payment;

import com.adnan.ecommerce.customer.CustomerResponse;
import com.adnan.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
