package com.example.mqconsumer2;

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

    @RabbitListener(queues = {MqConfig.QUEUE_FOR_ACK}, ackMode = "MANUAL")
    public void receiveEmail(String msg, Message message, Channel channel) throws IOException {
        log.info("consumer2 接收到队列：{}，信息：{},开始处理...",MqConfig.QUEUE_FOR_ACK,msg);
        try {
            //处理业务逻辑
        } catch (Exception e) {
            log.error("Ack failed",e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
