# bonelf 个人项目经验总结

## 介绍
## 工程结构
``` 
Bonelf
├── bonelf-frame -- 运维中心
├    ├── bonelf-base -- 通用配置
├    ├── bonelf-web -- web服务通用配置
├    ├── bonelf-mq -- mq配置
├    ├── bonelf-cloud -- 微服务配置
├    ├── bonelf-gateway -- 网关通用配置
├    ├── bonelf-core -- 核心功能
├    ├── bonelf-cicada -- 私用工具
├    ├── bonelf-support -- 通用工具服务
├    ├    ├── bonelf-support-fegin -- 
├    ├    ├── bonelf-support-fegin-domain -- 
├    ├    ├── bonelf-support-provider -- 
├    ├    ├── bonelf-support-provider-domain -- 
├    ├    ├── bonelf-support-consumer -- 
├    ├    ├── bonelf-support-web -- 
├    ├    ├── bonelf-support-base -- 
├── bonelf-gateway -- 网关
├    ├── bonelf-gateway-main -- 网关逻辑
├    ├── bonelf-gateway-app -- 启动模块
├── bonelf-support-app -- 工具服务启动模块
├── bonelf-auth -- 授权服务提供
├── bonelf-service-demo -- demo服务
├    ├── bonelf-demo-base -- 配置
├    ├── bonelf-demo-provider -- 提供MQ消息发送方法 （可选） 
├    ├── bonelf-demo-provider-domain -- provider 参数
├    ├── bonelf-demo-feign -- 提供feign发送方法 （可选） 
├    ├── bonelf-demo-feign-domain -- feign 参数
├    ├── bonelf-demo-consumer -- MQ消息处理（可选）（处理定时器消息）（entity、consumer domain、service）
├    ├── bonelf-demo-web -- 服务
└──  └── bonelf-demo-app -- 启动模块 （web）
```
## 项目结构

| 模块 | 名称 | 端口 | |
| --- | --- | --- | --- |
| `bonelf-common` | 公用模块 |  | |
| `bonelf-gateway` | 网关 | HTTP 9999 | |
| `bonelf-support-app` | Netty Websocket服务、定时器等 | HTTP 8800 & WS 8802 | |
| `bonelf-auth` | 鉴权服务 | HTTP 8809 | [接口文档]() |
| `bonelf-service-order` | 订单服务 | HTTP 8801 | [接口文档]() |
| `bonelf-service-pay` | 支付服务 | HTTP 8806 | [接口文档]() |
| `bonelf-service-product` | 商品服务 | HTTP 8803 | [接口文档]() |
| `bonelf-service-promotion` | 营销服务 | HTTP 8805 | [接口文档]() |
| `bonelf-service-search` | 搜索服务 | HTTP 8807| [接口文档]() |
| `bonelf-service-system` | 管理系统服务 | HTTP 8808 | [接口文档]() |
| `bonelf-service-user` | 用户服务 | HTTP 8804 | [接口文档]() |
| `bonelf-service-test` | 测试服务 | HTTP 8080 | [接口文档]() |

## Q&A
Q：关于为什么不把实体类和VO拆分出单独的模块从而避免模块间Feign请求需要再建实体接受的问题：

A：抽离出来确实不用再担心解耦在调用时出现因为实体类不一致导致的无法解析，导致接口调用失败。但我个人认为既然使用了微服务，那么各模块之间应尽可能避免耦合，各模块用各模块需要的实体，不应同意实体。

Q：关于项目细分的问题：

A:

1.普通项目细分为config、service、web、domain、dao；作用：多项目互相调用只需要引用需要的包

2.微服务再细分feign接口、consumer(mq)、provider(mq)、resource(oauth)、具体项目；作用：主要是多微服务项目知道项目的feign、producer接口，主要功能在web包中

## Nacos
下载地址：
[点击下载ZIP（镜像）](https://github-production-release-asset-2e65be.s3.amazonaws.com/137451403/90b68b00-d688-11ea-8e5b-0126ff25179c?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20201004%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20201004T151437Z&X-Amz-Expires=300&X-Amz-Signature=4f9425db68308988d24ba947031ddbac59ceda8a8494a306bff5132fcd1c55c5&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=137451403&response-content-disposition=attachment%3B%20filename%3Dnacos-server-1.3.2.zip&response-content-type=application%2Foctet-stream)

#### 启动用法
先将/conf 中的nacos-mysql.sql添加到自己项目的数据库中并修改application.properties 31行(Config Module Related Configurations)的配置

Windows启动：切换到/bin目录 -> .\startup.cmd -m standalone

## 开源项目参考
Jeecg-boot、ruoyi-cloud、Guns、litemall、best-pay

## 开发计划

单点项目版本（分离auth服务 类似support）

maven一键部署、使用docker部署（depoly）

jenkins 部署 nexus、springcloud（DockerFile/docker-compose.yml-docker方式）

filterRules field、operate、value 三个属性的list 来形成查询条件 Map<Key,(operate、value)>

mybatis-plus 分页可能与seata不兼容；令使用adapter适应分页参数，增加 start、end 的传参方式 而不是 page size

数据库异常封装，controller封装

处理文件上传 aop or 拦截器，处理后返回url填充入对应表单字段中，这样controller只需要处理保存链接地址即可

RocketMQ（应用订单服务通知商品修改销售数目、定时器发送消息，其他服务处理）

点击量排序问题（需rocketmq）

销售量排序问题（需rocketmq）

微信支付宝 支付、退款

库存问题

规格问题（下单）

经纬度排序问题

三方登录

二维码登录

liquibase 数据库版本更新

zipkin 监控 压力测试

grafana 度量分析和可视化工具

Kafka 分布式日志(类似MQ)(新建项目熟悉)

activiti/flowable 工作流

## 不完善

接口耗时aop （只能适用标准的请求-Controller结尾的，例如Oauth等不符合定义的没等统一配置）

## 已完成

基本微服务框架功能配置（swagger、多数据源、druid、nacos、feign、redis、mybatisplus、消息转化器、异常处理advice、spring cache使用redis实现等）

quartz数据库定时器

数据库字典、枚举字典注解

文件上传至服务器、OSS（七牛云一般前端传，后端维护refreshToken）

swagger-ui 微服务下整合

小程序、账号密码登录

文件上传

规格问题（增删改查）

空字符串转null 注解（并没有实现注解，而是全局设置）

OAuth2

string 替换文本注解

支持表情注解（valid）

过滤表情 过滤HTML表情（XSS）注解方式实现

加密传输注解

枚举校验注解（valid clazz or enum value）

验证码 图形验证码

二维码

RocketMQ 实现 redis websocket发布订阅

Websocket:stomp netty origin

更换为标准的Aibaba架构，备份后改用Dubbo、sentinel、streamer

sentinel 流量控制

minio 对象存储

seata 分布式事务中间件

elasticsearch 搜索服务 （elk）

## 参考中

继承Webserver是否可以替换springboot的web服务？

## 放弃

CAS单点登录（使用OAuth2.0解决、SpringSecurity也有支持CAS）

shiro （不支持分布式，替换成OAuth2)