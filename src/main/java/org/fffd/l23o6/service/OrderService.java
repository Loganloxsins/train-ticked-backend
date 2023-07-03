package org.fffd.l23o6.service;

import java.util.List;

import com.alipay.api.AlipayApiException;
import org.fffd.l23o6.pojo.vo.order.OrderVO;

public interface OrderService {
    Long createOrder(String username, Long trainId, Long fromStationId, Long toStationId, String seatType, Long seatNumber, Integer price);
    List<OrderVO> listOrders(String username);
    OrderVO getOrder(Long id);

    void cancelOrder(Long id);
    void payOrder(Long id, String type) throws AlipayApiException;
    Integer usePoints(Long orderId);
    Integer cancelUsePoints(Long orderId);
}
