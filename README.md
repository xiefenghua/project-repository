## 强安二期系统架构

## 项目介绍

`强安二期` 是基于 Spring Boot 2.3.2、Spring Cloud Hoxton.SR8 & Cloud Alibaba 2.2.5.RELEASE 快速构建的一套**全栈**IM服务平台，包括微服务应用、管理平台、微信小程序及APP应用

spring cloud 版本依赖官方说明
https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
## 项目特色

- 项目使用都是主流的**开源**框架，无过度自定义封装的逻辑，易理解上手和方便扩展

- 基于Spring Boot 2.3.2、Spring Cloud Hoxton.SR8 & Alibaba 一站式微服务解决方案快速开发分布式服务

- 实现Spring Cloud OAuth2、Spring Cloud Gateway、JWT分布式统一认证鉴权和`RBAC` 权限系统设计

- 使用Docker快速构建项目环境和一键打包部署微服务项目

## 项目地址
#### 服务端源码
http://114.55.41.187:8082/group-xxqa/xxqa-server-v2.git
### 工程结构

```xml
xxqa
├── xxqa-common -- 工具类及通用代码
├── xxqa-common-es -- 公用es模块
├── xxqa-common-redis -- 公用redis模块
├── xxqa-common-sms -- 公用短信验证码模块
├── xxqa-codemachine -- 代码生成工具
├── xxqa-leaf -- 分布式ID生成服务
├── xxqa-gateway -- 网关服务，实现请求统一拦截鉴权及路由
├── xxqa-usercenter -- 5A统一认证中心
├── xxqa-usercenter-api -- 5A统一认证中心开放接口
├── xxqa-usercenter-common -- 5A统一认证中心开放接口公用对象
├── xxqa-rentcenter -- 租户中心
├── xxqa-rentcenter-api -- 租户中心开放接口
├── xxqa-rentcenter-common -- 租户中心开放接口公用对象
├── xxqa-operationcenter-api -- 运营中心开放接口
├── xxqa-operationcenter-common -- 运营中心开放接口公用对象
└── xxqa-operationcenter -- 运营中心
```

### 技术栈

| 技术                          | 说明           | 官网                                                         |
| ----------------------------- | -------------- | ------------------------------------------------------------ |
| Spring Cloud                  | 微服务框架     | https://spring.io/projects/spring-cloud                      |
| Nacos                         | 注册中心       | https://nacos.io/zh-cn/docs/quick-start.html                 |
| MyBatis Plus                  | ORM框架        | https://baomidou.com/                                        |
| Seata                         | 分布式事务     | http://seata.io/zh-cn/docs/overview/what-is-seata.html       |
| spring cloud gateway          | 微服务网关     | https://spring.io/projects/spring-cloud-gateway#learn        |
| zipkin                        | 微服务链路追踪 | https://zipkin.io/                                           |
| sharding-shpere               | 分库分表框架   | http://shardingsphere.apache.org/index_zh.html               |
| leaf                          | 分布式ID生成   | https://tech.meituan.com/2017/04/21/mt-leaf.html             |
| Redisson                      | redis 框架     | [https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95](https://github.com/redisson/redisson/wiki/目录) |
| Docker                        | 应用容器引擎   | https://www.docker.com/                                      |
| spring security oauth2.0 集成 jwt     |    统一鉴权认证            |      https://projects.spring.io/spring-security-oauth/docs/oauth2.html                                                        |
| xxl-job         |      xxl开源分布式调度系统          |      https://www.xuxueli.com/xxl-job/                                                        |                                                            |

## 项目启动
### 环境准备
1. jdk 1.8
2. mysql 5.6
3. seata server
4. nacos
5. redis
6. zipkin
7. elasticsearch 7.6.2
8. mongoDB


## 应用部署

1. 部署nacos，参考doc\ 下 nacos 高可用集群搭建，搭建完成导入项目配置 xxqa-sercer-v2\doc\config\nacos_config.zip
2. 部署 seata server 和 zipkin，参考 doc\ Seata调研，启动seata server，zipkin。
3. 部署 elasticsearch7.6.2，参考 doc/Elasticsearch 6.4.2 升级到 7.6.2.md
4. 在 nacos 控制台调整各个微服务的配置，例如：mysql，redis，zipkin，seata，es，mongo 配置，调整为自己部署的对应服务地址。
5. 依次启动 xxqa-gateway，xxqa-usercenter 等服务。

