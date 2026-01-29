package com.booktrading.server.service;

import com.booktrading.server.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

public interface OrderService extends IService<Order> {
    Order createOrder(Order order);
    void updateOrderStatus(Long orderId, Integer status);
    IPage<Order> getOrdersByUserId(IPage<Order> page, Long userId, Integer status);
    IPage<Order> getOrdersBySellerId(IPage<Order> page, Long sellerId, Integer status);
    IPage<Order> getAllOrders(IPage<Order> page, Integer status);
    Order getOrderDetail(Long orderId);
    void cancelOrder(Long orderId);
    void confirmReceipt(Long orderId);
}
