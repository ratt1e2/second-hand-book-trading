package com.booktrading.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("book_image")
public class BookImage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long bookId;
    private String imageUrl;
    private Integer sort;
    private LocalDateTime createdAt;
}
