package com.booktrading.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("`order`")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long sellerId;
    private Long addressId;
    private BigDecimal totalAmount;
    private BigDecimal actualAmount;
    private Integer status;
    private LocalDateTime paymentTime;
    private LocalDateTime shippingTime;
    private LocalDateTime confirmTime;
    private LocalDateTime cancelTime;
    private LocalDateTime refundTime;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
