## Bonelf Gateway 微服务网关

#### 注意点
如果引入了spring security，在没有配置密码的情况下，启动控制台输出 Using generated security password:XXX 即为密码，用户名：user

这时 在使用Postman调用网关接口时需要复制浏览器登录后的SESSIONID cookie即可；

在REST开发中调用接口鉴权让请求走auth服务处理，所以网关对swagger外的接口不需要进行鉴权。