package com.booktrading.server.service.impl;

import com.booktrading.server.entity.Order;
import com.booktrading.server.mapper.OrderMapper;
import com.booktrading.server.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Order createOrder(Order order) {
        // 生成订单号
        String orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(0); // 待支付
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        save(order);
        return order;
    }

    @Override
    public void updateOrderStatus(Long orderId, Integer status) {
        Order order = getById(orderId);
        if (order != null) {
            order.setStatus(status);
            // 根据状态更新对应时间
            switch (status) {
                case 1: // 待发货
                    order.setPaymentTime(LocalDateTime.now());
                    break;
                case 2: // 待收货
                    order.setShippingTime(LocalDateTime.now());
                    break;
                case 3: // 已完成
                    order.setConfirmTime(LocalDateTime.now());
                    break;
                case 4: // 已取消
                    order.setCancelTime(LocalDateTime.now());
                    break;
                case 6: // 已退款
                    order.setRefundTime(LocalDateTime.now());
                    break;
            }
            order.setUpdatedAt(LocalDateTime.now());
            updateById(order);
        }
    }

    @Override
    public IPage<Order> getOrdersByUserId(IPage<Order> page, Long userId, Integer status) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId);
        if (status != null) {
            queryWrapper.eq(Order::getStatus, status);
        }
        queryWrapper.orderByDesc(Order::getCreatedAt);
        return page(page, queryWrapper);
    }

    @Override
    public IPage<Order> getOrdersBySellerId(IPage<Order> page, Long sellerId, Integer status) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getSellerId, sellerId);
        if (status != null) {
            queryWrapper.eq(Order::getStatus, status);
        }
        queryWrapper.orderByDesc(Order::getCreatedAt);
        return page(page, queryWrapper);
    }

    @Override
    public IPage<Order> getAllOrders(IPage<Order> page, Integer status) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            queryWrapper.eq(Order::getStatus, status);
        }
        queryWrapper.orderByDesc(Order::getCreatedAt);
        return page(page, queryWrapper);
    }

    @Override
    public Order getOrderDetail(Long orderId) {
        return getById(orderId);
    }

    @Override
    public void cancelOrder(Long orderId) {
        updateOrderStatus(orderId, 4); // 已取消
    }

    @Override
    public void confirmReceipt(Long orderId) {
        updateOrderStatus(orderId, 3); // 已完成
    }

    // 生成订单号
    private String generateOrderNo() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return timestamp + random;
    }
}
