# rayrpc
very simple rpc project with spring 

please checkout master branch

- rpc-provider: the provider with netty server, run RpcProviderApplication to start
- rpc-consumer: the consumer with netty client, run RpcConsumerApplication to start, then access http://localhost:8080/hello?name=world with chrome
- rpc-core: core

# 注释
RPC一个非常好练手项目

项目包括:
1. rpc-provider：服务提供者
2. rpc-consumer：服务消费者
3. rpc-core：核心服务

主要实现功能：
1. 集成spring，根据@EnableProvider和@EnableReference自动发现生产者和消费者
2. 使用netty作为server和client
3. 基于JDK实现动态代理
4. 基于反射实现服务发现
5. 请求异步处理
6. 心跳检测以及服务重连
7. 基于SPI扩展Handler
