# 概述
### 官网
https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.2.RELEASE/reference/html/
### 结构

![RUNOOB 图标](imgs/springcloud结构.png)

![RUNOOB 图标](imgs/网关作用.png)


### 三大核心概念
1. Route（路由）
网关的基本构建块。它由ID，目标URI，谓词集合和过滤器集合定义。如果断言为true，则匹配路由。
2. Predicate（断言）
这是Java 8 Function谓词。输入类型是Spring FrameworkServerWebExchange。这使您可以匹配HTTP请求中的所有内容，例如标头或参数。
3. Filter（过滤器）
这些是使用特定工厂构造的Spring FrameworkGatewayFilter实例。在这里，您可以在发送下游请求之前或之后修改请求和响应。

### 工作流程
![RUNOOB 图标](imgs/gateway工作流程.png)


客户端向Spring Cloud Gateway发出请求。如果网关处理程序映射确定请求与路由匹配，则将其发送到网关Web处理程序。该处理程序通过特定于请求的过滤器链来运行请求。筛选器由虚线分隔的原因是，筛选器可以在发送代理请求之前和之后运行逻辑。所有“前置”过滤器逻辑均被执行。然后发出代理请求。发出代理请求后，将运行“后”过滤器逻辑。

# 项目集成
### gateway 8080
1. pom
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<!-- 注意不要添加 web的依赖，与gateway里的web flux冲突 -->
```
2. yml
```yml
spring:
  profiles:
    active: dev
  lifecycle:
    timeout-per-shutdown-phase: 60s #设置缓冲期，最大等待时间
server:
  port: 8080
  shutdown: graceful #开启优雅停机，默认立即关机

---
spring:
  profiles: dev
  application:
    name: xxqa-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 121.196.145.48:8848
#      config:
#        server-addr: 121.196.145.48:8848
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: xxqa-usercenter
          uri: lb://usercenter
          predicates:
            - Path=/user/**
        - id: xxqa-rentcenter
          uri: lb://rentcenter
          predicates:
            - Path=/rent/**
```
3. 启动类
```java
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
```
### 测试
1. gateway中配置动态路由
```yml
spring:
  profiles: dev
  lifecycle:
    timeout-per-shutdown-phase: 60s #设置缓冲期，最大等待时间
  cloud:
    gateway:
      routes:
        - id: xxqa-usercenter
          uri: lb://usercenter
          predicates:
            - Path=/uc/**
          filters:
            - SwaggerHeaderFilter
            - StripPrefix=1 #去除 path 第一个前缀
        - id: xxqa-rentcenter
          uri: lb://rentcenter
          predicates:
            - Path=/rc/**
          filters:
            - StripPrefix=1 #去除 path 第一个前缀
```
2. 配置后可以通过以下路径访问 usercenter 服务中的信息
http://localhost:8080/uc/im/user/15555555555
不再暴露用户中心 10080 的端口

# Predicate的使用
https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.2.RELEASE/reference/html/#the-between-route-predicate-factory
全部在 yml的Predicate之下
1. After
```yml
# 在该时间之后可以使用
- After=2020-05-26T17:07:03.043+08:00[Asia/Shanghai]
```
获取当前时区的时间
```java
ZonedDateTime z = ZonedDateTime.now();// 默认时区
```
2. Before
```yml
# 之前
- Before=2017-01-20T17:42:47.789-07:00[America/Denver]
```
3. Between
```yml
# 之间
- Between=2017-01-20T17:42:47.789-07:00[America/Denver], 2017-01-21T17:42:47.789-07:00[America/Denver]
```
4. Cookie
```yml
# 查看有没有指定kv的cookie
- Cookie=username,wxh
```
5. Header
```yml
# 请求头，跟cookie一样指定kv键值对
```
6. Host
```yml
# 
```
7. Method
```yml
# 
```
8. Path
```yml
# 
```
9. Query
```yml
# 
```
10. ReadBodyPredicateFactory
```yml
# 
```
11. RemoteAddr
```yml
# 
```
12. Weight
```yml
# 
```
13. CloudFoundryRouteService
```yml
# 
```




# 过滤器 Filter
### 单一过滤器
https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.2.RELEASE/reference/html/#gatewayfilter-factories
### 全局过滤器
### 请求头过滤器
### 自定义过滤器
1. 实现接口GlobalFilter,Ordered
2. 能干嘛
    1. 全局日志记录
    2. 统一网关鉴权
3. 案例
```java
@Component
@Slf4j
public class MyLogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 判断有没有 uname 这个参数
        log.info("自定义全局日志过滤器");
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname==null){
            log.info("用户名非法");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
    /*
    *     int HIGHEST_PRECEDENCE = -2147483648;
            int LOWEST_PRECEDENCE = 2147483647;
            * 加载过滤器顺序
            * 数字越小优先级越高
    * */
    @Override
    public int getOrder() {
        return 0;
    }
}
```
###  Spring Cloud Gateway 熔断、降级、限流

**服务降级**

程序超时，熔断，异常时，不让客户端等待并返回一个友好提示，兜底方案。

**服务熔断**：

在分布式系统中，网关作为流量的入口，大量请求进入网关，向后端远程系统或服务发起调用，后端服务不可避免的会产生调用失败（超时或者异常），失败时不能让请求堆积在网关上，需要快速失败并返回回去，这就需要在网关上做熔断、降级操作。

**服务限流**：

网关上有大量请求，对指定服务进行限流，可以很大程度上提高服务的可用性与稳定性，限流的目的是通过对并发访问/请求进行限速，或对一个时间窗口内的请求进行限速来保护系统。一旦达到限制速率则可以拒绝服务、排队或等待、降级。

### 具体步骤参考 Sentinel 文档
