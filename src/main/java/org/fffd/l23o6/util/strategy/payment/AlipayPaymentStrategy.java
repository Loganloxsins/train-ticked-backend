package org.fffd.l23o6.util.strategy.payment;

import ch.qos.logback.core.util.EnvUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
//import com.alipay.api.EnvUtils;

@Component
public class AlipayPaymentStrategy extends PaymentStrategy{

//    AlipayProperties alipayProperties;
    public String payment(BigDecimal amount) throws AlipayApiException {
        System.out.println("使用支付宝支付"+amount);
        AlipayOrder alipayOrder=new AlipayOrder();
        alipayOrder.setOut_trade_no("balabala");
        alipayOrder.setSubject("车票购买订单");
        alipayOrder.setTotal_amount(String.valueOf(amount));
        return alipay(alipayOrder);
    }

    private String alipay(AlipayOrder alipayOrder) throws AlipayApiException {
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
//        AlipayClient alipayClient = new DefaultAlipayClient(
////                alipayProperties.getGatewayUrl(),
////                alipayProperties.getApp_id(),
////                alipayProperties.getPrivateKey(),
////                "json",
////                alipayProperties.getCharset(),
////                alipayProperties.getPublicKey(),
////                alipayProperties.getSign_type()
//               " https://openapi-sandbox.dl.alipaydev.com/gateway.do","9021000122698301","MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCSqov7OsUFoFM0ZhbaYi6xXyHYamJoibJdpvSTnTv8S25pDMLkP9cMXfWOM9TL/NiGbAFsqhqTPiHmdc7Hr0ugz5+sd/mkZzod9wY6kZjgShKNsbIE4AbGspnDUZqewp1wI3N+dT2V1399QpDzSU8/2Wajz1Tl/E5W1EQvRKLEA57etncxRzDRULr61NsCJ+XsrbmNxNm9jj/sbDF5anHONxy6adn/8bSmgHv2gRad2jZ5i++tWPr3kNyS2D6A86455y8GUrTHCSLOAvkCww/y/qq5E4TRVF4dKrKubdxXlRxFkfTsNznJzeKahxpCLFrF/yrmzppfJ6NMUfMtIdzVAgMBAAECggEAYspd36uGanPDl9fsLM1VhkP+GDgQcTnO2yZKN+QBM7/bbwcZf4VA5SOk81QWUhDOCD1X+Enb/LItQ362+eoplvhDJXjqvsKJcwWhPHI1913tgWPf9UixR8WlrNqau7V7Nr2qXdO61+OADG96E2Wd6/QUHuLY3NFGM1ZnVy90M3L3OyxUkNAgb5i/h3g80HJDMQ8sNlh+44XuUkjMRRVvpi/bQIAYcvDOohV/hRl4TFq/WqAK2s8ZHO0/WAXwW/Jb8fNRovzYYJy/6+8OGms2KaEKV1xspEU2untLh/8Vu394v19FltPYOF1RKtRYGNwnnP41Xhk4yE4t/3hE1ZSTQQKBgQDPak7YOuLKfXZL+YDPJDrg41/cAHY5IvCCsifwq1QLHPHpvhIRhi65uUAecoVcvk7D+FXYzHbAcGyO/JxMtMwQ4Tdhmkl47W3aIwD/ZUJfq50xEdx0cgGj3CfgdkkfbTxXgcz2Z/RmmSdwOeXZhM7uCK1fMHQMeqq389f90E2nXwKBgQC1BWkbaB9EFzW9x8FTK4BYP0rpfVIHtrPnlW9oWDzhYfCpYVKI/dZFU0sei1qpmgeHWce7yo6axXU8NH+hbsuGH+e7ej3HOzBjzebSrrUEaKZM4Yn+Fth2hWIr7OydZjACqRfTDo1WsQkd/JLC1/hgcEW4P5ggBglc1om1EtKsSwKBgQCzARxqoOd5ui2OBBaWrr3huFnSlNNzHCRVp0uw+SvfK2vcPp15YkSRJL+hh1RxZgy0NG5iXJNgIaaPAJQj3yT+rGAPbAhcxQw5ZlxGDi3qQ0G2R6PrGzkvIaGIpo0VAkFBhFRPlAfpQ+Q1hsJHp8aux+5YaIi+/F65w0h1VICwIQKBgQCh6xYKMseMw2oJuGzftRCXXv3l/nfqeG1Rn6RIZG0IeO+owmnuKYeI+Sk/SA0vmZxUYGU9P8DxBeCImrjzMESSU2WXl48871+oVlu6ZhV6vRTLvg4Nnme/FC4s9j7rx6T4LEIoQmMAgViTXwHzkPUVZjemyBONyXiEd040DoMtIwKBgQCgMa35MfFAY6VhJhysZmfzo8BXs6N4gh7RGUMKnhEb/0lSD/z1V7pqEX8+D9zj61mQvLk0h81gzDQ+WcZ6qIRz77f4sOtPoSHx8W8czRWLURhJJ2Bj5iLoOsorm5CgUZYn/CJ4oq+O+CjKIHksVOlFQxZj/lyITqLIJHGFKnz/pw==",
//                "json","utf-8","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkfxIAoLtvhF7WNy7f7qKZmGNc57hkKl5DDNI3cqNwaufUChqkwiAd4UXlAsW5MfgdyUsH0P2RvxP9wbOn++JK3eF2C97K1m3Vn8r67ZGgEwsW3mtzUBqgF9NwCIqohvR1kzZENk5zF4cjg2NWIcRihuQWwD9qVexcMrelOd7rCZFOP1aCx0eOpG60YInNPGweeiqM5HBI6l3wSYfOsYwNTi0cewbuz2WjvPX8IIooi1kR7PVXAUBVyv/VKu2P8SGRXN+kpztkkPBw43TfX+GItNZoDsEIwhskZlukKfKjhaqO373yDtbFjBy49WpsiEO8syyztQ4nTKFOzluyov62QIDAQAB", "RSA2");
//        // 2、设置请求参数
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        // 页面跳转同步通知页面路径(就是支付成功之后跳转的页面)
////        alipayRequest.setReturnUrl(alipayProperties.getReturn_url());
//        alipayRequest.setReturnUrl("https://www.baidu.com");
//        // 服务器异步通知页面路径
//        alipayRequest.setNotifyUrl("https://www.baidu.com");
//        //账户订单号
//        String out_trade_no = System.currentTimeMillis()+ UUID.randomUUID().toString();
//        alipayOrder.setOut_trade_no(out_trade_no);
//        // 封装参数
//        alipayRequest.setBizContent(JSON.toJSONString(alipayOrder));
//        // 3、请求支付宝进行付款，并获取支付结果
//        String result = alipayClient.pageExecute(alipayRequest).getBody();
//        System.out.println("result="+result);
//        // 返回付款信息
//        return result;


//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","9021000122698301","MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCe31XgtwKrkwkyaKmnD8po6EgX3bYMxeb4oncPa+GJKXOhvlW6SEbkljBDomsAzFvzA8ZL2GTVuRHT4tzRO5WcqP02VGWeLMb0fqbebzTsnuV6BPOX1moT/zMPX8Vkntedr84vGC9TD1TL6fRsvCiJ1RWfPmsVYU/RQAsg3V+jJc9TOatvLX1hBeY5OJA1QeteWoBPUGsQo21zsS0kIQ14p6qK47zBRFDrBPkHCydYfhunRacOWgmN9F2I9FQ/fpiq68Fv8Goy+93jsoACso9Gmv/uN2/lrNNQUB0f314WUDH6w3WWThbbu+uKtroXd4sjmrz5HpZFdbEiqhV2TfWZAgMBAAECggEAKa+YLPpYLQTLqPplaXxvM8+e+wQlvHxA+oRGkTlRWFgUCyCSBerpDxmJWHXJddApYD5hTY1eB+Yjk2E3Z38m5pBAwHysnTBKk+SXjE3JqTyYIcgi0nhkJRMVJreOZ7R2Bt4WT60c3cJsEYU1qUJFsMTEg6AxIM8Dznfrc5gifAAJ5sWmAvB5K5ysMq16sevi1JlHeubFsE31A48kffZ4xaIdBjpuHPmp+LmuOmL9shbYHta73lEA578dlrEMp5DIKOJj2s6Mt/8p4nY9pHOlWl1rKeH/adYZ21o7++fe3QEPupZzzR4VnXuMQ+nzCV/j3NuSd2nsyq/a1TE328kewQKBgQDOEv9J4qY79JyqOJHuKPbpHAP5QC+xvqlbGhEkxE+YAc5z/V19oSY1X4IjGe58ywqc60S1bTK1ajVP5ZWTEUT7kB67yN3ohAquuXpuk8coA3NHEGsV1uTSiOMlvAEOpcJs2LhkzHCSI+6OIW8eMSDrxK5hb8tThOfhdZEwM3HftQKBgQDFXNFxzAekGC6FrIsKn+A6DJBZJPLc0zPu9KDOW3yPqgR3KHpzawiB0GoE9/3CWx/bEcVa1iOWtDYSQY1Fm5g9n458IwRXTmpWgJ9S9NNRnb9AOydDboNZEXGAf71HcnXhTgRrjBXPk8IGTKnSCiSkile75sBZ2RQSLS+RZtAE1QKBgQCwhFNKraspBjHAGS7fKxW8vbOlqFZyUVft4lk2/jUThtCaSDfx6by50TK+8HJ5UM6pdVlocr4R6YT7CDRkQlGtjljVD40NozUnT1dRKqJYYwQ2phikH2vn7ztq0iyxqEjvRYBFT11JwR+RfZAZ84k4i0O30LQAuYKKfFM+T4LGVQKBgQCQtcbobpZnrkdmQiZM6v7o8U9h3aKpYb7hhcLmq9QALsRJboeiWyeIr30dGYyZ1mDcKwKO4RaFKKvA5QmlsTmGbZh20uIa9ecod/TaSaMsY8LYYMcgPUbXpt+XPZJWccmXIrJA/Rz877VDEzEtTbyiXMGJh98fwfhif3kyXrVF5QKBgQCFrEYVRNyzBgJgs/MtaKURu7dtKQx/reEs4bMWza7l8DDZLSbZFnVH9xX8tIpcKgtDJSwfUytiR/aWe6GVWdKJ1sxU+lvNdlV9qg2uObmM1pHQvgBlrKnEYQFqyeMjcc+3LAR87u1e9yqMeuG5tdvMR39zx50QChP//02ZJdvxmQ==",
//                "json","GBK","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkfxIAoLtvhF7WNy7f7qKZmGNc57hkKl5DDNI3cqNwaufUChqkwiAd4UXlAsW5MfgdyUsH0P2RvxP9wbOn++JK3eF2C97K1m3Vn8r67ZGgEwsW3mtzUBqgF9NwCIqohvR1kzZENk5zF4cjg2NWIcRihuQWwD9qVexcMrelOd7rCZFOP1aCx0eOpG60YInNPGweeiqM5HBI6l3wSYfOsYwNTi0cewbuz2WjvPX8IIooi1kR7PVXAUBVyv/VKu2P8SGRXN+kpztkkPBw43TfX+GItNZoDsEIwhskZlukKfKjhaqO373yDtbFjBy49WpsiEO8syyztQ4nTKFOzluyov62QIDAQAB","RSA2");
//        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
////异步接收地址，仅支持http/https，公网可访问
////        request.setNotifyUrl("http://localhost:5173");
////同步跳转地址，仅支持http/https
////        request.setReturnUrl("http://localhost:5173");
///******必传参数******/
//        JSONObject bizContent = new JSONObject();
////商户订单号，商家自定义，保持唯一性
//        bizContent.put("out_trade_no", "20210817010101004");
//        bizContent.put("trade_no","123");
////支付金额，最小值0.01元
//        bizContent.put("total_amount", 0.01);
////订单标题，不可使用特殊符号
//        bizContent.put("subject", "测试商品");
////电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
//        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

/******可选参数******/
//bizContent.put("time_expire", "2022-08-01 22:00:00");

//// 商品明细信息，按需传入
//JSONArray goodsDetail = new JSONArray();
//JSONObject goods1 = new JSONObject();
//goods1.put("goods_id", "goodsNo1");
//goods1.put("goods_name", "子商品1");
//goods1.put("quantity", 1);
//goods1.put("price", 0.01);
//goodsDetail.add(goods1);
//bizContent.put("goods_detail", goodsDetail);

//// 扩展信息，按需传入
//JSONObject extendParams = new JSONObject();
//extendParams.put("sys_service_provider_id", "2088511833207846");
//bizContent.put("extend_params", extendParams);
//
//        request.setBizContent(bizContent.toString());
//        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
//        System.out.println(response.getBody());
//        if(response.isSuccess()){
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
//        return null;
//    }
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

        //获得初始化的AlipayClient对象，这个时候就把我们在AlipayConfig中设置的收款方商户信息设置进去了
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayProperties.gatewayUrl, AlipayProperties.app_id, AlipayProperties.merchant_private_key, "json", AlipayProperties.charset, AlipayProperties.alipay_public_key, AlipayProperties.sign_type);

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        // 设置两个网页，一个异步通知网页，一个支付结束跳转页面，初学者若没有部署在服务器上的网页，可以把这两行注释掉
        // alipayRequest.setReturnUrl(AlipayConfig.return_url);
        // alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        try {
            alipayRequest.setBizContent("{\"out_trade_no\":\"" + "123456" + "\","   // 设置订单号
                    + "\"total_amount\":\"" + "100" + "\","     // 设置订单总额
                    + "\"subject\":\"" + "车票" + "\","      // 设置订单主题
                    + "\"timeout_express\":\"10m\","        // 设置该订单最晚付款时间，这里设置的是10分钟
                    + "\"body\":\"" + "无" + "\","        // 设置该订单商品的描述
                    // + "\"qr_pay_mode\":\""+ Constants.QR_PAY_MODE +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            // 发起生成订单请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


}
