-- 旧书交易管理系统数据库表结构

-- 创建数据库
CREATE DATABASE IF NOT EXISTS book_trading_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE book_trading_system;

-- 1. 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
  `openid` VARCHAR(100) UNIQUE COMMENT '微信开放ID',
  `nickname` VARCHAR(50) COMMENT '用户昵称',
  `avatar` VARCHAR(255) COMMENT '用户头像',
  `phone` VARCHAR(20) UNIQUE COMMENT '手机号',
  `password` VARCHAR(100) COMMENT '密码（加密存储）',
  `role` TINYINT DEFAULT 0 COMMENT '角色：0-普通用户，1-管理员',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 书籍分类表
CREATE TABLE IF NOT EXISTS `book_category` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID，0表示一级分类',
  `sort` INT DEFAULT 0 COMMENT '排序权重',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='书籍分类表';

-- 3. 书籍表
CREATE TABLE IF NOT EXISTS `book` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '书籍ID',
  `user_id` BIGINT NOT NULL COMMENT '卖家ID',
  `category_id` BIGINT NOT NULL COMMENT '分类ID',
  `title` VARCHAR(100) NOT NULL COMMENT '书名',
  `author` VARCHAR(50) COMMENT '作者',
  `isbn` VARCHAR(20) COMMENT 'ISBN码',
  `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
  `original_price` DECIMAL(10,2) COMMENT '原价',
  `condition` TINYINT NOT NULL COMMENT '新旧程度：1-九成新，2-八成新，3-七成新，4-六成新，5-五成新及以下',
  `description` TEXT COMMENT '书籍描述',
  `cover_image` VARCHAR(255) COMMENT '封面图片',
  `status` TINYINT DEFAULT 0 COMMENT '状态：0-待审核，1-已上架，2-已下架，3-已售出',
  `view_count` INT DEFAULT 0 COMMENT '浏览量',
  `comment_count` INT DEFAULT 0 COMMENT '评论数',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`category_id`) REFERENCES `book_category`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='书籍表';

-- 4. 书籍图片表
CREATE TABLE IF NOT EXISTS `book_image` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '图片ID',
  `book_id` BIGINT NOT NULL COMMENT '书籍ID',
  `image_url` VARCHAR(255) NOT NULL COMMENT '图片URL',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (`book_id`) REFERENCES `book`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='书籍图片表';

-- 5. 购物车表
CREATE TABLE IF NOT EXISTS `shopping_cart` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `book_id` BIGINT NOT NULL COMMENT '书籍ID',
  `quantity` INT DEFAULT 1 COMMENT '数量',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY `uk_user_book` (`user_id`, `book_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`book_id`) REFERENCES `book`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表';

-- 6. 地址表
CREATE TABLE IF NOT EXISTS `address` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '地址ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
  `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
  `province` VARCHAR(50) NOT NULL COMMENT '省份',
  `city` VARCHAR(50) NOT NULL COMMENT '城市',
  `district` VARCHAR(50) NOT NULL COMMENT '区县',
  `detail_address` VARCHAR(255) NOT NULL COMMENT '详细地址',
  `is_default` TINYINT DEFAULT 0 COMMENT '是否默认地址：0-否，1-是',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地址表';

-- 7. 订单表
CREATE TABLE IF NOT EXISTS `order` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
  `order_no` VARCHAR(32) UNIQUE NOT NULL COMMENT '订单号',
  `user_id` BIGINT NOT NULL COMMENT '买家ID',
  `seller_id` BIGINT NOT NULL COMMENT '卖家ID',
  `address_id` BIGINT NOT NULL COMMENT '收货地址ID',
  `total_amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
  `actual_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
  `status` TINYINT DEFAULT 0 COMMENT '订单状态：0-待支付，1-待发货，2-待收货，3-已完成，4-已取消，5-退款中，6-已退款',
  `payment_time` DATETIME COMMENT '支付时间',
  `shipping_time` DATETIME COMMENT '发货时间',
  `confirm_time` DATETIME COMMENT '确认收货时间',
  `cancel_time` DATETIME COMMENT '取消时间',
  `refund_time` DATETIME COMMENT '退款时间',
  `remark` VARCHAR(255) COMMENT '订单备注',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`seller_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`address_id`) REFERENCES `address`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 8. 订单详情表
CREATE TABLE IF NOT EXISTS `order_item` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单详情ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `book_id` BIGINT NOT NULL COMMENT '书籍ID',
  `book_title` VARCHAR(100) NOT NULL COMMENT '书籍标题',
  `book_author` VARCHAR(50) COMMENT '书籍作者',
  `book_cover` VARCHAR(255) COMMENT '书籍封面',
  `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
  `quantity` INT DEFAULT 1 COMMENT '数量',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (`order_id`) REFERENCES `order`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`book_id`) REFERENCES `book`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单详情表';

-- 9. 评论表
CREATE TABLE IF NOT EXISTS `comment` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
  `user_id` BIGINT NOT NULL COMMENT '评论用户ID',
  `book_id` BIGINT NOT NULL COMMENT '书籍ID',
  `order_item_id` BIGINT COMMENT '订单详情ID',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `rating` TINYINT DEFAULT 5 COMMENT '评分：1-5星',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`book_id`) REFERENCES `book`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`order_item_id`) REFERENCES `order_item`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- 10. 轮播图表
CREATE TABLE IF NOT EXISTS `banner` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '轮播图ID',
  `title` VARCHAR(100) COMMENT '轮播图标题',
  `image_url` VARCHAR(255) NOT NULL COMMENT '图片URL',
  `link_url` VARCHAR(255) COMMENT '链接地址',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='轮播图表';

-- 插入初始数据
-- 1. 插入书籍分类
INSERT INTO `book_category` (`name`, `parent_id`, `sort`, `status`) VALUES
('教材', 0, 1, 1),
('小说', 0, 2, 1),
('社科', 0, 3, 1),
('计算机', 0, 4, 1),
('文学', 0, 5, 1),
('历史', 0, 6, 1),
('艺术', 0, 7, 1),
('经管', 0, 8, 1);

-- 2. 插入管理员账户（密码：admin123）
INSERT INTO `user` (`nickname`, `phone`, `password`, `role`, `status`) VALUES
('管理员', '13800138000', '$2a$10$e3H5x7s3Bz5y6D4f7G8H9I7J6K5L4M3N2O1P0Q9R8S7T6U5V4W3X2Y1Z', 1, 1);

-- 3. 插入轮播图
INSERT INTO `banner` (`title`, `image_url`, `link_url`, `sort`, `status`) VALUES
('开学季特惠', 'https://example.com/banner1.jpg', '/books?category=1', 1, 1),
('新书推荐', 'https://example.com/banner2.jpg', '/books?sort=newest', 2, 1),
('热门小说', 'https://example.com/banner3.jpg', '/books?category=2', 3, 1);
