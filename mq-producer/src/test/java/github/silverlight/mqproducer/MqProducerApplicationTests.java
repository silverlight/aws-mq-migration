package github.silverlight.mqproducer;

import github.silverlight.mqconfig.MessageModel;
import github.silverlight.mqconfig.MqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MqProducerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSendEmail(){
        String messageFuzzyRouting = "这是一条模糊匹配路由到队列中的消息";
        String messageDirectRouting = "这是一条精确匹配路由到队列中的消息";
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage("消息体消息");
        /**
         * 参数
         * 1。交换机名称
         * 2。routingKey
         * 3。消息内容
         */
        rabbitTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE_NAME,"queue.everything.email",messageFuzzyRouting);
        rabbitTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE_NAME,"queue.email",messageDirectRouting);
        rabbitTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE_NAME,"queue.so-longgggg.sms",messageFuzzyRouting);
        rabbitTemplate.convertAndSend(MqConfig.DIRECT_EXCHANGE_NAME,"queue.message-model",messageModel);

    }

    /**
     * 测试是否广播到所有队列
     */
    @Test
    public void testSendFanout(){
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage("消息体消息");
        /**
         * 参数
         * 1。交换机名称
         * 2。routingKey
         * 3。消息内容
         */
        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE_NAME,"",messageModel);

    }

    /**
     * 启动两个消费者队列，发送两条消息测试是否公平消费
     */
    @Test
    public void testFairConsume(){
        rabbitTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE_NAME,"queue.everything.email","公平消费消息1");
        rabbitTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE_NAME,"queue.everything.email","公平消费消息2");
    }

    /**
     * 启动两个消费者队列，发送两条消息测试是否公平消费
     */
    @Test
    public void testAckConsume(){
        rabbitTemplate.convertAndSend(MqConfig.DIRECT_EXCHANGE_NAME,"queue.ack","公平消费消息1");
    }

    @Test
    public void testConsistent(){
        for(int i=0;i<10;i++){
            MessageModel messageModel = new MessageModel();
            messageModel.setMessage("consistent-content:"+i);
            rabbitTemplate.convertAndSend(MqConfig.CONSISTENT_EXCHANGE_NAME,String.valueOf(i),messageModel);
        }
    }

}
