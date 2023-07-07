package org.fffd.l23o6;

import com.alipay.api.AlipayApiException;
import jakarta.servlet.ServletException;
import org.fffd.l23o6.dao.OrderDao;
import org.fffd.l23o6.dao.RouteDao;
import org.fffd.l23o6.dao.TrainDao;
import org.fffd.l23o6.dao.UserDao;
import org.fffd.l23o6.pojo.entity.OrderEntity;
import org.fffd.l23o6.pojo.entity.RouteEntity;
import org.fffd.l23o6.pojo.entity.TrainEntity;
import org.fffd.l23o6.pojo.entity.UserEntity;
import org.fffd.l23o6.pojo.enum_.OrderStatus;
import org.fffd.l23o6.pojo.enum_.TrainType;
import org.fffd.l23o6.pojo.vo.order.OrderVO;
import org.fffd.l23o6.service.impl.OrderServiceImpl;
import org.fffd.l23o6.util.strategy.train.GSeriesSeatStrategy;
import org.fffd.l23o6.util.strategy.train.KSeriesSeatStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @MockBean
    private UserDao userDao;

    @MockBean
    private TrainDao trainDao;

    @MockBean
    private RouteDao routeDao;

    @MockBean
    private OrderDao orderDao;

    @Test
    public void testCreateOrder() throws IOException, ServletException, ParseException, AlipayApiException {
        // 准备测试数据
        String username = "testuser";
        Long trainId = 1L;
        Long fromStationId = 2L;
        Long toStationId = 3L;
        String seatType = "GSeriesSeat";
        Long seatNumber = 1L;
        Integer price = 100;

        UserEntity user = new UserEntity();
        // 设置user的属性
        when(userDao.findByUsername(username)).thenReturn(user);

        TrainEntity train = new TrainEntity();
        train.setTrainType(TrainType.HIGH_SPEED);
        // 设置train的属性
        when(trainDao.findById(trainId)).thenReturn(java.util.Optional.of(train));

        RouteEntity route = new RouteEntity();
        // 设置route的属性
        when(routeDao.findById(train.getRouteId())).thenReturn(java.util.Optional.of(route));

        String expectedSeat = "Seat 1";
        switch (train.getTrainType()) {
            case HIGH_SPEED:
                when(GSeriesSeatStrategy.INSTANCE.allocSeat(anyInt(), anyInt(), any(), any())).thenReturn(expectedSeat);
                break;
            case NORMAL_SPEED:
                when(KSeriesSeatStrategy.INSTANCE.allocSeat(anyInt(), anyInt(), any(), any())).thenReturn(expectedSeat);
                break;
        }

        OrderEntity savedOrder = new OrderEntity();
        // 设置savedOrder的属性
        when(orderDao.save(any())).thenReturn(savedOrder);

        // 调用被测试方法
        Long orderId = orderService.createOrder(username, trainId, fromStationId, toStationId, seatType, seatNumber, price);

        // 验证返回结果是否符合预期
        assertEquals(savedOrder.getId(), orderId);

        // 验证DAO方法是否被调用
        verify(userDao, times(1)).findByUsername(username);
        verify(trainDao, times(1)).findById(trainId);
        verify(routeDao, times(1)).findById(train.getRouteId());
        verify(orderDao, times(1)).save(any());
    }

    @Test
    public void testListOrders() {
        // 准备测试数据
        String username = "testuser";
        Long userId = 1L;

        UserEntity user = new UserEntity();
        // 设置user的属性
        when(userDao.findByUsername(username)).thenReturn(user);

        List<OrderEntity> orders = new ArrayList<>();
        // 设置orders列表
        when(orderDao.findByUserId(userId)).thenReturn(orders);

        // 调用被测试方法
        List<OrderVO> result = orderService.listOrders(username);

        // 验证返回结果是否符合预期
        assertEquals(orders.size(), result.size());

        // 验证DAO方法是否被调用
        verify(userDao, times(1)).findByUsername(username);
        verify(orderDao, times(1)).findByUserId(userId);
    }

    // 其他测试方法...

}

