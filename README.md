# spring-ai-mcp-nacos
## 1. 安装nacos：
docker 安装nacos：
1. docker pull nacos/nacos-server:v3.0.0
2. 然后执行：
   docker run --name nacos-standalone-derby \
   -e MODE=standalone \
   -e NACOS_AUTH_TOKEN=c2stMDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg= \
   -e NACOS_AUTH_IDENTITY_KEY=nacos_server \
   -e NACOS_AUTH_IDENTITY_VALUE=nacos_2025 \
   -p 8080:8080 \
   -p 8848:8848 \
   -p 9848:9848 \
   -d nacos/nacos-server:v3.0.0

   备注：NACOS_AUTH_TOKEN：是要用来解密的密钥。相关的可以看：https://nacos.io/en/docs/v3.0/quickstart/quick-start-docker/
## 2. 项目配置
````
spring:
  ai:
    mcp:
      server:
        name: spring-ai-alibaba-demo
        version: 1.0.0
        type: SYNC
        sse-message-endpoint: /spring-ai-alibaba-demo/message
    alibaba:
      mcp:
        nacos:
          enabled: true
          server-addr: 127.0.0.1:8848  # Nacos 地址
          service-namespace: public    # Nacos 命名空间 ID
          service-group: DEFAULT_GROUP
          username: nacos  # nacos用户
          password: nacos  # nacos密码

````


