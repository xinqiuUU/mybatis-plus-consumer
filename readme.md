生产端:
1. jms+activemq   消息部分
2. 异步编程:   线程池+异步调用
   spring.task:
   execution:
   pool:
   max-size: 16
   queue-capacity: 100
   keep-alive: "10s"

   @EnableAsync

   @Async("taskExecutor")
3. 启用 druid数据库联接池
4. 启用 mybatis-plus
5. 在业务层上启用了AOP机制.
   <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-aop</artifactId>
   </dependency>
   使用@AspectJ定义切面等.
6. 测试框架junit4   , 测试套件

<hr />
消费端  <br />
1. 使用jms的消费端读取消息.
2. 使用velocity读取邮件模板,并从消息中取出数据，并渲染到 模板中. 
3. 使用javax.mail发送邮件. 
4. 发送邮件时使用异步任务. 
5. 提供一个数据中台页面，显示实时发送情况.    websocket
      发送量
      发送方和接收方地址.

**策略模式**:策略上下文（`StrategyContext`），用于根据操作类型 (`opType`) 动态选择对应的电子邮件内容生成策略。具体说明如下：

1. **依赖注入**：通过构造函数注入一个包含所有策略实现的 `Map`，`Map` 的键是策略的操作类型，值是对应的 `EmailContentStrategyService` 实现。
2. **获取策略**：在 `getEmailContent` 方法中，根据 `opType` 从 `strategyMap` 中获取对应的策略。如果找不到对应的策略，会记录错误日志并抛出异常。
3. **生成邮件内容**：找到对应的策略后，调用其 `getEmailContent` 方法，传入账户信息、金额和目标账户ID，生成邮件内容。
