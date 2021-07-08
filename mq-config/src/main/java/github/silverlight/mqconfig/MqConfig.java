package github.silverlight.mqconfig;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Guang.Yang
 * @version 1.0
 * @date 2021-06-08
 */
@Configuration
public class MqConfig {

    public static final String QUEUE_FOR_EMAIL = "queue_for_email";
    public static final String QUEUE_FOR_SMS = "queue_for_sms";
    public static final String QUEUE_FOR_ACK = "queue_for_ack";
    public static final String QUEUE_FOR_MESSAGE_MODEL = "queue_for_message_model";
    public static final String QUEUE_FOR_FANOUT_A = "queue_for_fanout_A";
    public static final String QUEUE_FOR_FANOUT_B = "queue_for_fanout_B";

    public static final String CONSISTENT_1 = "consistent-1";
    public static final String CONSISTENT_2 = "consistent-2";
    public static final String CONSISTENT_3 = "consistent-3";
    public static final String CONSISTENT_4 = "consistent-4";

    public static final String TOPIC_EXCHANGE_NAME = "topicExchange";
    public static final String DIRECT_EXCHANGE_NAME = "directExchange";
    public static final String FANOUT_EXCHANGE_NAME = "fanoutExchange";
    public static final String CONSISTENT_EXCHANGE_NAME = "consistent";

    public static final String ROUTING_KEY_EMAIL = "queue.#.email.#";
    public static final String ROUTING_KEY_SMS = "queue.#.sms.#";
    public static final String ROUTING_KEY_MESSAGE_MODEL = "queue.message-model";
    public static final String ROUTING_KEY_MESSAGE_ACK = "queue.ack";


//    /**
//     * 声明Topics工作模式的交换机
//     */
//    @Bean(TOPIC_EXCHANGE_NAME)
//    public Exchange topicExchange() {
//        // durable(true) 表示重启之后交换机还在
//        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME).durable(true).build();
//    }
//
//    /**
//     * 声明Direct工作模式的交换机
//     */
//    @Bean(DIRECT_EXCHANGE_NAME)
//    public Exchange directExchange() {
//        // durable(true) 表示重启之后交换机还在
//        return ExchangeBuilder.topicExchange(DIRECT_EXCHANGE_NAME).durable(true).build();
//    }
//
//    /**
//     * 声明Fanout工作模式的交换机
//     */
//    @Bean(FANOUT_EXCHANGE_NAME)
//    public FanoutExchange fanoutExchange() {
//        // durable(true) 表示重启之后交换机还在
//        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_NAME).durable(true).build();
//    }

    @Bean(CONSISTENT_EXCHANGE_NAME)
    public CustomExchange consistentExchange() {
        return new CustomExchange("consistent", "x-consistent-hash", true, false);
    }

//    /**
//     * 声明email队列
//     */
//    @Bean(QUEUE_FOR_EMAIL)
//    public Queue emailQueue() {
//        return new Queue(QUEUE_FOR_EMAIL);
//    }
//
//    /**
//     * 声明sms队列
//     */
//    @Bean(QUEUE_FOR_SMS)
//    public Queue smsQueue() {
//        return new Queue(QUEUE_FOR_SMS);
//    }
//
//    /**
//     * 声明ack队列
//     */
//    @Bean(QUEUE_FOR_ACK)
//    public Queue ackQueue() {
//        return new Queue(QUEUE_FOR_ACK);
//    }
//
//    /**
//     * 声明用于存放给定对象的队列
//     */
//    @Bean(QUEUE_FOR_MESSAGE_MODEL)
//    public Queue messageModelQueue() {
//        return new Queue(QUEUE_FOR_MESSAGE_MODEL);
//    }
//
//    @Bean(QUEUE_FOR_FANOUT_A)
//    public Queue fanoutQueueA() {
//        return new Queue(QUEUE_FOR_FANOUT_A);
//    }
//
//    @Bean(QUEUE_FOR_FANOUT_B)
//    public Queue fanoutQueueB() {
//        return new Queue(QUEUE_FOR_FANOUT_B);
//    }

    @Bean(CONSISTENT_1)
    public Queue consistent1() {
        return new Queue(CONSISTENT_1);
    }

    @Bean(CONSISTENT_2)
    public Queue consistent2() {
        Queue queue = new Queue(CONSISTENT_2);
        return new Queue(CONSISTENT_2);
    }

    @Bean(CONSISTENT_3)
    public Queue consistent3() {
        return new Queue(CONSISTENT_3);
    }

    @Bean(CONSISTENT_4)
    public Queue consistent4() {
        return new Queue(CONSISTENT_4);
    }


//    /**
//     * 交换机与email队列绑定
//     */
//    @Bean
//    public Binding bindingEmailQueue(@Qualifier(QUEUE_FOR_EMAIL) Queue queue,
//                                     @Qualifier(TOPIC_EXCHANGE_NAME) Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
//    }
//
//    /**
//     * 交换机与sms队列绑定
//     */
//    @Bean
//    public Binding bindingSmsQueue(@Qualifier(QUEUE_FOR_SMS) Queue queue,
//                                   @Qualifier(TOPIC_EXCHANGE_NAME) Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SMS).noargs();
//    }
//
//    @Bean
//    public Binding bindingMessageModelQueue(@Qualifier(QUEUE_FOR_MESSAGE_MODEL) Queue queue,
//                                            @Qualifier(DIRECT_EXCHANGE_NAME) Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_MESSAGE_MODEL).noargs();
//    }
//
//    @Bean
//    public Binding bindingMessageAckQueue(@Qualifier(QUEUE_FOR_ACK) Queue queue,
//                                          @Qualifier(DIRECT_EXCHANGE_NAME) Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_MESSAGE_ACK).noargs();
//    }

//    /**
//     * 交换机绑定多个队列
//     *
//     * @param queueA
//     * @param exchange
//     * @return
//     */
//    @Bean
//    public Binding bindingFanoutQueueA(@Qualifier(QUEUE_FOR_FANOUT_A) Queue queueA,
//                                       @Qualifier(FANOUT_EXCHANGE_NAME) FanoutExchange exchange) {
//        return BindingBuilder.bind(queueA).to(exchange);
//    }
//
//    @Bean
//    public Binding bindingFanoutQueueB(@Qualifier(QUEUE_FOR_FANOUT_B) Queue queueB,
//                                       @Qualifier(FANOUT_EXCHANGE_NAME) FanoutExchange exchange) {
//        return BindingBuilder.bind(queueB).to(exchange);
//    }

    @Bean
    public Binding bindingCons1(@Qualifier(CONSISTENT_1) Queue con1,
                                       @Qualifier(CONSISTENT_EXCHANGE_NAME) CustomExchange customExchange) {
        return BindingBuilder.bind(con1).to(customExchange).with("1").noargs();
    }

    @Bean
    public Binding bindingCons2(@Qualifier(CONSISTENT_2) Queue con2,
                                @Qualifier(CONSISTENT_EXCHANGE_NAME) CustomExchange customExchange) {
        return BindingBuilder.bind(con2).to(customExchange).with("2").noargs();
    }

    @Bean
    public Binding bindingCons3(@Qualifier(CONSISTENT_3) Queue con3,
                                @Qualifier(CONSISTENT_EXCHANGE_NAME) CustomExchange customExchange) {
        return BindingBuilder.bind(con3).to(customExchange).with("1").noargs();
    }

    @Bean
    public Binding bindingCons4(@Qualifier(CONSISTENT_4) Queue con4,
            @Qualifier(CONSISTENT_EXCHANGE_NAME) CustomExchange customExchange) {
        return BindingBuilder.bind(con4).to(customExchange).with("2").noargs();
    }
}
