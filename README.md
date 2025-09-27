## 北华大学程序设计工作室实验室官网  
基于SpringBoot2.6 + Vue3开发的前后端分离的博客官网, 于本科阶段大二上学期进行构思并进行完成, 同样也是本人的毕业设计


### 技术架构  
<p align="center">  
  <a target="_blank" href="https://github.com/YourRepository">  
    <img src="https://img.shields.io/badge/JDK-17-green"/>  
    <img src="https://img.shields.io/badge/SpringBoot-2.6.4-green"/>  
    <img src="https://img.shields.io/badge/Vue-3.3.4-green"/>  
    <img src="https://img.shields.io/badge/MySQL-8.0.34-green"/>  
    <img src="https://img.shields.io/badge/MyBatisPlus-4.4.3-green"/>  
     <img src="https://img.shields.io/badge/MongoDb-5.0.0-green"/>  
    <img src="https://img.shields.io/badge/Redis-7.2.1-green"/>  
    <img src="https://img.shields.io/badge/Elasticsearch-8.11.3-green"/>  
  </a>  
</p>  


### 本地运行指南  
1. **环境依赖**  
   - JDK 11+  
   - MySQL 8.0+（推荐8.0.34）  
   - Node.js 18.x（npm 10+）  
   - Redis 7.0+  
   - Elasticsearch 8.0+（可选，用于搜索功能）  

2. **数据库配置**  
   - 导入根目录下的`acm_lab.sql`至本地数据库  
   - 修改后端`application.yml`中的数据库连接信息：  
     ```yaml  
     spring:  
       datasource:  
         url: jdbc:mysql://localhost:3306/acm_lab?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai  
         username: root  
         password: your_password  
     ```  

3. **前端初始化**  
   ```bash  
   # 进入前端目录  
   cd frontend  
   # 移除token.ts中的domain配置（如有）  
   # 安装依赖  
   npm install  
   # 启动开发环境  
   npm run dev  
   ```  

4. **后端启动**  
   - 使用IDEA直接运行`Application.java`  
   - 首次启动会自动创建管理员账号：  
     **用户名**：admin@beihua.edu.cn  **密码**：acm2025  


### 功能特性  
#### 核心模块  
- **竞赛管理**  
  - 实时同步ACM/ICPC、CCPC等赛事信息  
  - 赛事报名、队伍管理、成绩公示  
  - 训练赛题库集成（支持Python/Java/C++在线评测）  

- **成员管理**  
  - 队员档案系统（含算法等级、竞赛履历）  
  - 动态权限控制（教练/队员/访客三级权限）  
  - 训练打卡记录与积分统计  

- **资源中心**  
  - 算法教程（动态规划/图论/数据结构等专题）  
  - 历年真题下载（附题解与代码示例）  
  - 开源项目仓库（ACM模板库、工具类）  

- **社区互动**  
  - 技术博客（支持Markdown与代码高亮）  
  - 讨论区（问题标签分类、精华帖置顶）  
  - 通知公告（含邮件/微信推送）  

#### 技术亮点  
- **前后端分离架构**  
  - 前端：Vue3 + TypeScript + Vite + Element Plus  
  - 后端：SpringBoot + MyBatisPlus + Sa-Token权限体系  
  - 接口遵循RESTful规范，Swagger文档全覆盖  

- **高性能支持**  
  - 缓存层：Redis实现热点数据缓存（如赛事排行榜）  
  - 搜索服务：Elasticsearch实现毫秒级全文检索  
  - 异步处理：RabbitMQ实现通知消息队列  

- **扩展能力**  
  - 支持OJ平台对接（如洛谷、Codeforces）  
  - 自定义评测引擎（可扩展新编程语言）  
  - 数据可视化：ECharts展示竞赛成绩趋势  


### 开发环境配置  
| 工具/组件        | 版本          | 说明                     |  
|------------------|---------------|--------------------------|  
| IDEA             | 2023.3+       | Java后端开发             |  
| VSCode           | 1.85+         | Vue前端开发              |  
| Navicat          | 16+           | 数据库管理               |  
| RedisDesktop     | 10+           | Redis可视化工具          |  
| Xshell/Xftp      | 7+            | 服务器远程管理           |  

| 运行环境         | 版本          | 安装建议                 |  
|------------------|---------------|--------------------------|  
| OpenJDK          | 17            | 推荐采用LTS版本          |  
| MySQL            | 8.0.34        | 启用InnoDB存储引擎       |  
| Elasticsearch    | 8.11.3        | 配置IK分词器             |  
| Redis            | 7.2.1         | 开启持久化（AOF模式）    |  


### 服务器部署方案  
#### 推荐配置  
- 服务器：腾讯云/阿里云 4核8G CentOS 8  
- 存储：OSS对象存储（用于静态资源）  
- 容器化：Docker + Docker Compose一键部署  
- 代理：Nginx实现负载均衡与HTTPS加速  

#### 部署步骤  
```bash  
# 拉取镜像  
docker-compose pull backend frontend redis elasticsearch  

# 启动服务  
docker-compose up -d  

# 初始化数据（首次部署）  
docker exec -it backend java -jar /app/init-data.jar  
```  


### 团队协作规范  
1. **代码管理**  
   - 分支策略：主分支`main`（生产环境）、开发分支`dev`（功能迭代）  
   - 提交规范：采用Angular Commit格式（feat/fix/docs等前缀）  

2. **文档协作**  
   - 接口文档：Swagger在线文档 + Postman测试用例  
   - 技术wiki：使用语雀/Notion维护开发手册  


### 联系我们  
- **邮箱**：acm@beihua.edu.cn  
- **地址**：北华大学计算机科学技术学院第一教学楼1409室  


### 贡献指南  
欢迎Star/Fork本项目！如需贡献代码，请提交PR至`dev`分支，并注明：  
- 功能模块（如：feat: 添加训练赛模块）  
- 问题修复（如：fix: 解决评测超时问题）  

 

需要调整或补充其他内容（如实验室特色功能、导师介绍等），可以随时告知！
