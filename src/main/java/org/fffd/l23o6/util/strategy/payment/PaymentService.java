package org.fffd.l23o6.util.strategy.payment;

import com.alipay.api.AlipayApiException;

import java.math.BigDecimal;

public class PaymentService {
    public void payment(PaymentStrategy strategy, BigDecimal amount) throws AlipayApiException {
        strategy.payment(amount);
    }
}
