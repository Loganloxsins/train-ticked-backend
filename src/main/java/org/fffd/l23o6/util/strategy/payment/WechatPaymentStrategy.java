package org.fffd.l23o6.util.strategy.payment;

import java.math.BigDecimal;

public class WechatPaymentStrategy extends PaymentStrategy{
    public String payment(BigDecimal amount){
        System.out.println("使用微信支付"+amount);
        return "支付成功";
    }
}
