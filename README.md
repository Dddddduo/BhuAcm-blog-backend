# BhuAcm-blog-backend 项目文档

## 项目概述

**于本科阶段大二上学期进行构思并进行完成，同样也是本人的毕业设计**

BhuAcm-blog-backend 是一个基于 Spring Boot + Vue3 开发的前后端分离博客系统，采用现代化的技术栈构建，支持多数据源、多存储策略、实时通信等高级功能。

## 开发日志

### 项目启动阶段 (2024年11月)
- **2024/11/28 18:54** - 项目主启动类 <mcsymbol name="BlogApplication" filename="BlogApplication.java" path="/Users/dduo/IdeaProjects/BhuAcm-blog-backend/src/main/java/com/ican/BlogApplication.java" startline="1" type="class">BlogApplication</mcsymbol> 创建完成，标志着项目正式启动
- **2024/11/29 22:09** - 核心实体类 <mcsymbol name="User" filename="User.java" path="/Users/dduo/IdeaProjects/BhuAcm-blog-backend/src/main/java/com/ican/entity/User.java" startline="1" type="class">User</mcsymbol> 设计完成，包含完整的用户信息管理功能

### 核心功能开发阶段 (2024年12月)
- **2024/12/04 21:46** - 文章实体类 <mcsymbol name="Article" filename="Article.java" path="/Users/dduo/IdeaProjects/BhuAcm-blog-backend/src/main/java/com/ican/entity/Article.java" startline="1" type="class">Article</mcsymbol> 设计完成，支持多种文章状态管理
- **2024/12/04 22:31** - 文章服务类 <mcsymbol name="ArticleService" filename="ArticleService.java" path="/Users/dduo/IdeaProjects/BhuAcm-blog-backend/src/main/java/com/ican/service/ArticleService.java" startline="1" type="class">ArticleService</mcsymbol> 实现，包含完整的CRUD操作和业务逻辑
- **2024/12/05 10:03** - 用户控制器 <mcsymbol name="UserController" filename="UserController.java" path="/Users/dduo/IdeaProjects/BhuAcm-blog-backend/src/main/java/com/ican/controller/UserController.java" startline="1" type="class">UserController</mcsymbol> 开发完成，提供用户管理API接口
- **2024/12/05 15:32** - 用户服务类 <mcsymbol name="UserService" filename="UserService.java" path="/Users/dduo/IdeaProjects/BhuAcm-blog-backend/src/main/java/com/ican/service/UserService.java" startline="1" type="class">UserService</mcsymbol> 完善，实现复杂的用户权限和状态管理

### 高级功能集成阶段
- MongoDB 数据源配置和双数据源切换功能
- Redis 缓存和会话管理集成
- WebSocket 实时通信功能
- 文件上传多存储策略（本地、OSS、COS、七牛云）
- 定时任务和异步处理机制
- 完整的权限管理和安全控制

## 技术栈

### 后端技术
- **框架**: Spring Boot 2.6.14
- **语言**: Java 11
- **ORM**: MyBatis-Plus 3.5.3.1
- **安全**: Sa-Token 1.34.0
- **缓存**: Redis
- **数据库**: MySQL + MongoDB（双数据源）
- **消息队列**: RabbitMQ
- **搜索**: Elasticsearch
- **文档**: Knife4j/Swagger

### 前端技术
- Vue3 + TypeScript
- Element Plus UI组件库
- Axios HTTP客户端
- Vue Router 路由管理
- Pinia 状态管理

## 项目结构

```
src/main/java/com/ican/
├── annotation/          # 自定义注解
├── aspect/              # AOP切面
├── config/              # 配置类
├── constant/            # 常量定义
├── controller/          # 控制器层
├── entity/              # 实体类
├── enums/               # 枚举类
├── mapper/              # 数据访问层
├── model/               # 数据传输对象
├── service/             # 业务逻辑层
├── strategy/            # 策略模式实现
├── utils/               # 工具类
└── websocket/           # WebSocket通信
```

## 核心功能模块

### 1. 用户管理模块
- 用户注册、登录、注销
- 角色权限管理（RBAC）
- 在线用户监控
- 密码修改和安全控制

### 2. 文章管理模块
- 文章CRUD操作
- 文章分类和标签管理
- 文章搜索和推荐
- 点赞、评论、收藏功能

### 3. 文件管理模块
- 多存储策略支持
- 图片上传和压缩
- 文件分类管理
- 存储空间监控

### 4. 系统管理模块
- 网站配置管理
- 操作日志记录
- 异常监控和处理
- 定时任务管理

### 5. 实时通信模块
- WebSocket消息推送
- 在线聊天功能
- 实时通知系统
- 访客统计

## 数据库设计

### 主要数据表
- `user` - 用户表
- `article` - 文章表  
- `category` - 分类表
- `tag` - 标签表
- `comment` - 评论表
- `role` - 角色表
- `menu` - 菜单表
- `file` - 文件表

### MongoDB集合
- `article_document` - 文章文档（用于搜索）
- `chat_record` - 聊天记录

## 配置说明

### 多环境配置
- `application.yml` - 主配置文件
- `application-dev.yml` - 开发环境配置
- `application-prod.yml` - 生产环境配置

### 数据源配置
支持MySQL和MongoDB双数据源，可根据业务需求灵活切换。

## API接口文档

项目集成了Knife4j，访问 `/doc.html` 即可查看完整的API文档，包含：
- 接口详细说明
- 请求参数说明
- 响应示例
- 在线测试功能

## 部署要求

### 系统要求
- JDK 11+
- MySQL 8.0+
- Redis 6.0+
- MongoDB 4.4+
- Maven 3.6+

### 部署步骤
1. 克隆项目代码
2. 配置数据库连接信息
3. 导入SQL脚本
4. 配置Redis和MongoDB
5. 使用Maven打包项目
6. 运行Spring Boot应用

## 特色功能

### 1. 双数据源支持
项目同时支持关系型数据库MySQL和文档数据库MongoDB，可根据业务场景选择合适的数据存储方案。

### 2. 多存储策略
支持本地存储、阿里云OSS、腾讯云COS、七牛云等多种文件存储方式，满足不同部署环境的需求。

### 3. 实时WebSocket通信
基于WebSocket实现实时消息推送、在线聊天等功能，提升用户体验。

### 4. 完整的权限管理
基于Sa-Token实现细粒度的权限控制，支持角色权限分离和动态权限分配。

### 5. 策略模式应用
在搜索、文件上传等场景中使用策略模式，提高代码的可扩展性和维护性。

## 开发规范

### 代码规范
- 遵循阿里巴巴Java开发规范
- 使用Lombok减少样板代码
- 统一的异常处理机制
- 规范的日志记录

### 项目结构规范
- 清晰的分层架构
- 统一的包命名规范
- 标准化的DTO/VO设计
- 一致的API响应格式

## 未来规划

### 短期目标
- [ ] 性能优化和缓存策略改进
- [ ] 更多的第三方登录集成
- [ ] 移动端适配和响应式设计

### 长期规划
- [ ] 微服务架构改造
- [ ] 容器化部署支持
- [ ] 智能化内容推荐
- [ ] 多语言国际化支持

## 致谢

### 指导老师
衷心感谢指导老师在项目开发过程中给予的悉心指导和宝贵建议。老师的专业知识和丰富经验为项目的顺利完成提供了重要支持。

### 学校支持
感谢学校提供的良好学习环境和实验条件，为项目的技术研究和实践开发创造了有利条件。

### 开源社区
感谢Spring Boot、MyBatis-Plus、Sa-Token等优秀开源项目的贡献者，这些优秀的开源技术为项目的快速开发提供了坚实基础。

### 同学帮助
感谢在项目开发过程中给予帮助和支持的同学们，大家的讨论和交流为项目的完善提供了很多有益的思路。

## 总结

BhuAcm-blog-backend项目作为本科阶段的毕业设计，不仅实现了博客系统的基本功能，还融入了许多现代化的技术特性。通过这个项目的开发，我深入学习了Spring Boot生态系统的各项技术，掌握了企业级应用开发的完整流程，为未来的职业发展奠定了坚实的基础。

项目代码结构清晰，功能完善，具有良好的可扩展性和维护性，体现了扎实的编程功底和系统设计能力。

---

*文档最后更新：2024年12月*
*项目作者：Dduo*
*联系方式：[请补充]*
