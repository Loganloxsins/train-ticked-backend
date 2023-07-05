package org.fffd.l23o6.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import io.github.lyc8503.spring.starter.incantation.exception.BizException;
import io.github.lyc8503.spring.starter.incantation.exception.CommonErrorType;
import io.github.lyc8503.spring.starter.incantation.pojo.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fffd.l23o6.pojo.vo.order.CreateOrderRequest;
import org.fffd.l23o6.pojo.vo.order.OrderIdVO;
import org.fffd.l23o6.pojo.vo.order.OrderVO;
import org.fffd.l23o6.pojo.vo.order.PatchOrderRequest;
import org.fffd.l23o6.service.OrderService;
import org.springframework.web.bind.annotation.*;

import cn.dev33.satoken.stp.StpUtil;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("order")
    public CommonResponse<OrderIdVO> createOrder(@Valid @RequestBody CreateOrderRequest request) throws AlipayApiException, ServletException, IOException {
        StpUtil.checkLogin();
        return CommonResponse.success(new OrderIdVO(orderService.createOrder(StpUtil.getLoginIdAsString(), request.getTrainId(), request.getStartStationId(), request.getEndStationId(), request.getSeatType(), null,request.getPrice())));
    }


    @GetMapping("order")
    public CommonResponse<List<OrderVO>> listOrders(){
        StpUtil.checkLogin();
        return CommonResponse.success(orderService.listOrders(StpUtil.getLoginIdAsString()));
    }

    @GetMapping("order/{orderId}")
    public CommonResponse<OrderVO> getOrder(@PathVariable("orderId") Long orderId) {
        return CommonResponse.success(orderService.getOrder(orderId));
    }

    @PatchMapping("order/{orderId}")
    public CommonResponse<String> patchOrder(@PathVariable("orderId") Long orderId, @Valid @RequestBody PatchOrderRequest request) throws AlipayApiException, ServletException, IOException, ParseException {

        switch (request.getStatus()) {
            case PAID:
                return CommonResponse.success(orderService.payOrder(orderId,request.getType()));
            case CANCELLED:
                orderService.cancelOrder(orderId);
                break;
            default:
                throw new BizException(CommonErrorType.ILLEGAL_ARGUMENTS, "Invalid order status.");
        }

        return CommonResponse.success();
    }



    @GetMapping("order/usePoints/{orderId}")
    public CommonResponse<Integer> usePoints(@PathVariable("orderId") Long orderId){
        return CommonResponse.success(orderService.usePoints(orderId));
    }

    @GetMapping("order/cancelUsePoints/{orderId}")
    public CommonResponse<Integer> cancelUsePoints(@PathVariable("orderId") Long orderId){
        return CommonResponse.success(orderService.cancelUsePoints(orderId));
    }


    /**
     * 处理支付成功后的订单更新
     * @param id 订单ID
     */
    @PostMapping("order/{orderId}/success")
    public void handlePaymentSuccess(@PathVariable("id") Long id) {
        orderService.updateOrderAfterPaymentSuccess(id);
    }

    /**
     * 支付宝支付回调处理方法
     * @param request HttpServletRequest对象，用于获取请求参数
     * @return 返回给支付宝的响应字符串
     */
    @ResponseBody
    @PostMapping("notify")
    public String alipayNotify(HttpServletRequest request) throws AlipayApiException {

        String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkfxIAoLtvhF7WNy7f7qKZmGNc57hkKl5DDNI3cqNwaufUChqkwiAd4UXlAsW5MfgdyUsH0P2RvxP9wbOn++JK3eF2C97K1m3Vn8r67ZGgEwsW3mtzUBqgF9NwCIqohvR1kzZENk5zF4cjg2NWIcRihuQWwD9qVexcMrelOd7rCZFOP1aCx0eOpG60YInNPGweeiqM5HBI6l3wSYfOsYwNTi0cewbuz2WjvPX8IIooi1kR7PVXAUBVyv/VKu2P8SGRXN+kpztkkPBw43TfX+GItNZoDsEIwhskZlukKfKjhaqO373yDtbFjBy49WpsiEO8syyztQ4nTKFOzluyov62QIDAQAB";
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String key : requestParams.keySet()) {
            String[] values = requestParams.get(key);
            StringBuilder valueStr = new StringBuilder();
            for (String value : values) {
                valueStr.append(value).append(",");
            }
            params.put(key, valueStr.substring(0, valueStr.length() - 1));
        }

        // 调用SDK验证通知的合法性
        boolean verifyResult = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8", "RSA2");
        if (verifyResult) {
            // 验证通过，处理支付成功逻辑
            String tradeStatus = params.get("trade_status");
            if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                // 支付成功，更新订单状态等操作
                String tradeNo = params.get("out_trade_no"); // 商户订单号
                String tradeAmount = params.get("total_amount"); // 支付金额
                // 执行订单更新逻辑
                // ...
                return "success"; // 返回给支付宝的响应字符串，表示通知已成功接收
            }
        }

        return "failure"; // 返回给支付宝的响应字符串，表示通知接收失败
    }
}