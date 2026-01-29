package com.booktrading.server.controller;

import com.booktrading.server.common.Result;
import com.booktrading.server.entity.Order;
import com.booktrading.server.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result<?> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return Result.success(createdOrder);
    }

    @GetMapping("/detail")
    public Result<?> getOrderDetail(@RequestParam Long orderId) {
        Order order = orderService.getOrderDetail(orderId);
        return Result.success(order);
    }

    @GetMapping("/user/list")
    public Result<?> getUserOrders(@RequestParam Long userId, 
                                  @RequestParam(defaultValue = "1") Integer page, 
                                  @RequestParam(defaultValue = "10") Integer size, 
                                  @RequestParam(required = false) Integer status) {
        IPage<Order> orderPage = new Page<>(page, size);
        orderPage = orderService.getOrdersByUserId(orderPage, userId, status);
        return Result.success(orderPage);
    }

    @GetMapping("/seller/list")
    public Result<?> getSellerOrders(@RequestParam Long sellerId, 
                                    @RequestParam(defaultValue = "1") Integer page, 
                                    @RequestParam(defaultValue = "10") Integer size, 
                                    @RequestParam(required = false) Integer status) {
        IPage<Order> orderPage = new Page<>(page, size);
        orderPage = orderService.getOrdersBySellerId(orderPage, sellerId, status);
        return Result.success(orderPage);
    }

    @GetMapping("/admin/list")
    public Result<?> getAllOrders(@RequestParam(defaultValue = "1") Integer page, 
                                  @RequestParam(defaultValue = "10") Integer size, 
                                  @RequestParam(required = false) Integer status) {
        IPage<Order> orderPage = new Page<>(page, size);
        orderPage = orderService.getAllOrders(orderPage, status);
        return Result.success(orderPage);
    }

    @PutMapping("/status")
    public Result<?> updateOrderStatus(@RequestParam Long orderId, @RequestParam Integer status) {
        orderService.updateOrderStatus(orderId, status);
        return Result.success();
    }

    @PutMapping("/cancel")
    public Result<?> cancelOrder(@RequestParam Long orderId) {
        orderService.cancelOrder(orderId);
        return Result.success();
    }

    @PutMapping("/confirm")
    public Result<?> confirmReceipt(@RequestParam Long orderId) {
        orderService.confirmReceipt(orderId);
        return Result.success();
    }
}
