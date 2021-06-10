### 背景

由于业务需要，项目需要接入AWS的MQ中间件。综合考虑使用业界比较流行的RabbitMQ。本例讲演示springboot-amqp与RabbitMQ的集成。

###基本概念
RabbitMQ由以下几个核心模块组成：
* Message
  > 消息由消息头和消息体组成。消息体是不透明的，而消息头则由一系列的可选属性组成，这些属性包括routing-key（路由键）、priority（相对于其他消息的优先权）、delivery-
   该消息可能需要持久性存储）等。
* Publisher
  > 消息的生产者，也是一个向交换器发布消息的客户端应用程序。
* Exchange
  > 交换机，用来接收生产者发送的消息并将这些消息路由给服务器中的队列。
  Exchange有4种类型：direct(默认，直连模式，根据路由键精确匹配路由到队列)，fanout（广播模式，不需要路由键直接路由到所有队列）, topic（主题模式，根据路由键模糊匹配路由到队列）, 和headers，不同类型的Exchange转发消息的策略有所区别。
* Queue
  > 消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息可投入一个或多个队列。消息一直在队列里面，等待消费者连接到这个队列将其取走
* Binding
  > 绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。Exchange 和Queue的绑定可以是多对多的关系。
* Connection
  > 网络连接，比如一个TCP连接。
* Channel
  > 信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真实的TCP连接内的虚拟连接，AMQP 命令都是通过信道发出去的，不管是发布消息、订阅队列还是接收消息，这些动作都是通过信道完成。因为对于操作系统来说建立和销毁 TCP 都是非常昂贵的开销，所以引入了信道的概念，以复用一条 TCP 连接
* Consumer
  > 消息的消费者，表示一个从消息队列中取得消息的客户端应用程序。
* Virtual Host
  > 虚拟主机，表示一批交换器、消息队列和相关对象。虚拟主机是共享相同的身份认证和加
  密环境的独立服务器域。每个 vhost 本质上就是一个 mini 版的 RabbitMQ 服务器，拥有
  自己的队列、交换器、绑定和权限机制。vhost 是 AMQP 概念的基础，必须在连接时指定，
  RabbitMQ 默认的 vhost 是 / 。
* Broker
  > 表示消息队列服务器实体


### Getting Started

#### 最简单的收发消息

* 添加依赖
```xml
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
 </dependency>
```
* 编写配置
> 需要在 `application.yaml` 文件中加入以下配置
```yaml
spring:
    rabbitmq:
        username: xxx
        password: xxxx
        addresses: amqps://xxxx:5671
```
* 编写代码
 1. 声明 Queue
 ```java
@Configuration
public class MqConfig {
    
    @Bean("myQueue")
    public Queue emailQueue() {
      return new Queue(QUEUE_FOR_EMAIL);
    }
}
```
 2. 声明 Exchange <br/>
  Exchange分为 Direct（精确匹配）、Topic（模糊匹配）、Fanout（广播）、Headers。可根据业务需要声明不同的Exchange。需要注意的是模糊匹配对于性能会有损耗，所以如果对于确定的routingkey，建议使用Direct类型的交换机。
```java
@Configuration
public class MqConfig {
  /**
   * 声明Topics工作模式的交换机
   */
  @Bean("topicExchange")
  public Exchange topicExchange() {
    // durable(true) 表示重启之后交换机还在
    return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME).durable(true).build();
  }

  /**
   * 声明Direct工作模式的交换机
   */
  @Bean("directExchange")
  public Exchange directExchange() {
    // durable(true) 表示重启之后交换机还在
    return ExchangeBuilder.topicExchange(DIRECT_EXCHANGE_NAME).durable(true).build();
  }

  /**
   * 声明Fanout工作模式的交换机
   */
  @Bean("fanoutExchange")
  public FanoutExchange fanoutExchange() {
    // durable(true) 表示重启之后交换机还在
    return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_NAME).durable(true).build();
  }
}
```
  3. 声明 Exchange和Queue的绑定关系
  想要收发消息，只需要将交换机和队列进行绑定，绑定关系由路由键(Routing key)来提现。由于Fanout模式不要路由键即可发送消息至所有与之绑定的队列，绑定时方法略有不同。
     
```java

@Configuration
public class MqConfig {
  /**
   * direct交换机与队列绑定
   */
  @Bean
  public Binding bindingEmailQueue(@Qualifier("myQueue") Queue queue,
                                   @Qualifier("directExchange") Exchange exchange) {
      return BindingBuilder.bind(queue).to(exchange).with("queue.order").noargs();
  }

  /**
   * topic交换机与队列绑定
   */
  @Bean
  public Binding bindingEmailQueue(@Qualifier("myQueue") Queue queue,
                                   @Qualifier("topicExchange") Exchange exchange) {
    //routing key 中的#表示匹配0个或者任意个单词
    return BindingBuilder.bind(queue).to(exchange).with("queue.#.order").noargs();
  }

  /**
   * fanout交换机绑定队列
   *
   * @param queueA
   * @param exchange
   * @return
   */
  @Bean
  public Binding bindingFanoutQueueA(@Qualifier("myQueue") Queue queueA,
                                     @Qualifier("fanoutExchange") FanoutExchange exchange) {
    return BindingBuilder.bind(queueA).to(exchange);
  }
}
```
  4. 声明消费者 <br>
  这里注意 receive里面的参数，如果发送消息时传入的是对象，则这里的String替换成相应的对象即可。
```java

@Component
public class Consumer {
    
  @RabbitListener(queues = {"myQueue"})
  public void receive(String msg, Message message, Channel channel){
    //do something when you receive the message
  }
}
```
  5. 发送消息 <br>
```java
public class Producer {
    
  @Autowired
  RabbitTemplate rabbitTemplate;

  /**
   * convertAndSend 的第一个参数传入交换机名称，第二个参数传入 Routing key，第三个参数传入消息对象
   */
  public void send(){
    rabbitTemplate.convertAndSend("topicExchange","queue.everything.email","Hello World !");
  }
}
```

