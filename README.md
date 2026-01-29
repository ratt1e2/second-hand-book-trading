# 旧书交易管理系统

## 项目介绍

本项目是一个旧书交易管理系统，包含以下模块：

- **admin**：后台管理系统，基于Vue.js开发
- **client**：微信小程序前端
- **server**：Java后端服务，基于Spring Boot开发

### 主要功能

- 用户认证与授权
- 书籍管理与审核
- 分类管理
- 订单管理
- 用户管理

## 技术栈

### 前端
- **admin**：Vue.js + Vite + Vue Router
- **client**：微信小程序原生开发

### 后端
- Java + Spring Boot
- MyBatis Plus（数据访问）
- JWT（认证）

### 数据库
- MySQL（根据database.sql文件）

## 部署方式

### 1. 环境准备

- JDK 1.8+（后端）
- Node.js 14+（前端）
- MySQL 5.7+（数据库）
- Maven 3.6+（后端构建）
- 微信开发者工具（小程序开发）

### 2. 数据库部署

1. 创建数据库
2. 执行 `database.sql` 文件导入表结构和初始数据

### 3. 后端部署

1. 进入 `server` 目录
2. 修改 `src/main/resources/application.yml` 中的数据库连接配置
3. 执行构建命令：
   ```bash
   mvn clean package
   ```
4. 运行jar包：
   ```bash
   java -jar target/server-1.0-SNAPSHOT.jar
   ```

### 4. 后台管理系统部署

1. 进入 `admin` 目录
2. 安装依赖：
   ```bash
   npm install
   ```
3. 开发环境运行：
   ```bash
   npm run dev
   ```
4. 生产环境构建：
   ```bash
   npm run build
   ```
   构建产物将生成在 `dist` 目录，可部署到任意静态文件服务器

### 5. 微信小程序部署

1. 打开微信开发者工具
2. 导入 `client` 目录
3. 配置小程序AppID
4. 编译并预览
5. 提交审核并发布

## 项目结构

```
trae/
├── admin/            # 后台管理系统
│   ├── src/          # 源代码
│   ├── package.json  # 依赖配置
│   └── vite.config.js # Vite配置
├── client/           # 微信小程序
│   ├── pages/        # 页面
│   ├── app.js        # 小程序入口
│   └── app.json      # 小程序配置
├── server/           # 后端服务
│   ├── src/          # 源代码
│   └── pom.xml       # Maven配置
├── database.sql      # 数据库脚本
└── README.md         # 项目说明
```

## 注意事项

1. 确保数据库连接配置正确
2. 后端服务默认端口为8080
3. 后台管理系统默认端口为3000
4. 微信小程序需要在开发者工具中配置合法的AppID

## 开发指南

### 后端开发
- 代码结构遵循Spring Boot标准结构
- 控制器位于 `src/main/java/com/booktrading/server/controller/`
- 服务层位于 `src/main/java/com/booktrading/server/service/`
- 实体类位于 `src/main/java/com/booktrading/server/entity/`

### 前端开发
- 后台管理系统使用Vue 3
- 页面组件位于 `admin/src/views/`
- 路由配置位于 `admin/src/router/index.js`

### 小程序开发
- 页面位于 `client/pages/`
- 遵循微信小程序开发规范

## 联系方式

如有问题，请联系项目维护者。