package github.silverlight.mqconsumer;

import com.rabbitmq.client.Channel;
import github.silverlight.mqconfig.MqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Guang.Yang
 * @version 1.0
 * @date 2021-06-10
 */
@Slf4j
@Component
public class ConsumerWithAck {

//    @RabbitListener(queues = {MqConfig.QUEUE_FOR_ACK}, ackMode = "MANUAL")
//    public void receiveEmail(String msg, Message message, Channel channel) throws IOException {
//        log.info("consumer1 接收到队列：{}，信息：{},开始处理...",MqConfig.QUEUE_FOR_ACK,msg);
//        try {
//            //处理业务逻辑
//        } catch (Exception e) {
//            //进异常直接拒收消息，这样消息会被下一个消费者消费。否则将变为unacked
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
//            log.error("Ack failed",e);
//        }
//
//        //成功后手动删除消息
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//    }

}
