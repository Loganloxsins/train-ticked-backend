package org.fffd.l23o6.util.strategy.payment;

import com.alipay.api.AlipayApiException;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.math.BigDecimal;

public class PaymentService {
    public void payment(PaymentStrategy strategy, BigDecimal amount) throws AlipayApiException, ServletException, IOException {
        strategy.payment(amount);
    }
}
