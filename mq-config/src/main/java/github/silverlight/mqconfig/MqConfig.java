package github.silverlight.mqconfig;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Guang.Yang
 * @version 1.0
 * @date 2021-06-08
 */
@Configuration
public class MqConfig {

    public static final String QUEUE_FOR_EMAIL = "queue_for_email";
    public static final String QUEUE_FOR_SMS = "queue_for_sms";
    public static final String QUEUE_FOR_MESSAGE_MODEL = "queue_for_message_model";
    public static final String QUEUE_FOR_FANOUT_A = "queue_for_fanout_A";
    public static final String QUEUE_FOR_FANOUT_B = "queue_for_fanout_B";

    public static final String TOPIC_EXCHANGE_NAME = "topicExchange";
    public static final String DIRECT_EXCHANGE_NAME = "directExchange";
    public static final String FANOUT_EXCHANGE_NAME = "fanoutExchange";

    public static final String ROUTING_KEY_EMAIL = "queue.#.email.#";
    public static final String ROUTING_KEY_SMS = "queue.#.sms.#";
    public static final String ROUTING_KEY_MESSAGE_MODEL = "queue.message-model";

    /**
     * 声明Topics工作模式的交换机
     */
    @Bean(TOPIC_EXCHANGE_NAME)
    public Exchange topicExchange() {
        // durable(true) 表示重启之后交换机还在
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明Direct工作模式的交换机
     */
    @Bean(DIRECT_EXCHANGE_NAME)
    public Exchange directExchange() {
        // durable(true) 表示重启之后交换机还在
        return ExchangeBuilder.topicExchange(DIRECT_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明Fanout工作模式的交换机
     */
    @Bean(FANOUT_EXCHANGE_NAME)
    public FanoutExchange fanoutExchange() {
        // durable(true) 表示重启之后交换机还在
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明email队列
     */
    @Bean(QUEUE_FOR_EMAIL)
    public Queue emailQueue() {
        return new Queue(QUEUE_FOR_EMAIL);
    }

    /**
     * 声明sms队列
     */
    @Bean(QUEUE_FOR_SMS)
    public Queue smsQueue() {
        return new Queue(QUEUE_FOR_SMS);
    }

    /**
     * 声明用于存放给定对象的队列
     */
    @Bean(QUEUE_FOR_MESSAGE_MODEL)
    public Queue messageModelQueue() {
        return new Queue(QUEUE_FOR_MESSAGE_MODEL);
    }

    @Bean(QUEUE_FOR_FANOUT_A)
    public Queue fanoutQueueA() {
        return new Queue(QUEUE_FOR_FANOUT_A);
    }

    @Bean(QUEUE_FOR_FANOUT_B)
    public Queue fanoutQueueB() {
        return new Queue(QUEUE_FOR_FANOUT_B);
    }


    /**
     * 交换机与email队列绑定
     */
    @Bean
    public Binding bindingEmailQueue(@Qualifier(QUEUE_FOR_EMAIL) Queue queue,
                                     @Qualifier(TOPIC_EXCHANGE_NAME) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
    }

    /**
     * 交换机与sms队列绑定
     */
    @Bean
    public Binding bindingSmsQueue(@Qualifier(QUEUE_FOR_SMS) Queue queue,
                                   @Qualifier(TOPIC_EXCHANGE_NAME) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SMS).noargs();
    }

    @Bean
    public Binding bindingMessageModelQueue(@Qualifier(QUEUE_FOR_MESSAGE_MODEL) Queue queue,
                                            @Qualifier(DIRECT_EXCHANGE_NAME) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_MESSAGE_MODEL).noargs();
    }

    /**
     * 交换机绑定多个队列
     *
     * @param queueA
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingFanoutQueueA(@Qualifier(QUEUE_FOR_FANOUT_A) Queue queueA,
                                       @Qualifier(FANOUT_EXCHANGE_NAME) FanoutExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange);
    }

    @Bean
    public Binding bindingFanoutQueueB(@Qualifier(QUEUE_FOR_FANOUT_B) Queue queueB,
                                       @Qualifier(FANOUT_EXCHANGE_NAME) FanoutExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange);
    }
}
