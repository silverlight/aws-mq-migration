package com.example.mqconsumer2;

import com.rabbitmq.client.Channel;
import github.silverlight.mqconfig.MessageModel;
import github.silverlight.mqconfig.MqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Guang.Yang
 * @version 1.0
 * @date 2021-06-08
 */
@Slf4j
@Component
public class Consumer {
    /**
     * 监听email队列
     * @param msg
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {MqConfig.QUEUE_FOR_EMAIL})
    public void receiveEmail(String msg, Message message, Channel channel){
        log.info("接收到队列：{}，信息：{}",MqConfig.QUEUE_FOR_EMAIL,msg);
    }

    /**
     * 监听sms队列
     * @param msg
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {MqConfig.QUEUE_FOR_SMS})
    public void receiveSms(String msg, Message message, Channel channel){
        log.info("接收到队列：{}，信息：{}",MqConfig.QUEUE_FOR_SMS,msg);
    }

    @RabbitListener(queues = {MqConfig.QUEUE_FOR_MESSAGE_MODEL})
    public void receiveMessageModel(MessageModel msg, Message message, Channel channel){
        log.info("接收到队列：{}，信息：{},Message:{}",MqConfig.QUEUE_FOR_MESSAGE_MODEL, msg,message);
    }

    @RabbitListener(queues = {MqConfig.QUEUE_FOR_FANOUT_A})
    public void receiveFanoutQueueMessages(MessageModel msg, Message message, Channel channel) {
        log.info("接收到队列：{}，信息：{},Message:{}", MqConfig.QUEUE_FOR_FANOUT_A, msg, message);
    }

    @RabbitListener(queues = {MqConfig.QUEUE_FOR_FANOUT_B})
    public void receiveFanoutQueueMessagesB(MessageModel msg, Message message, Channel channel) {
        log.info("接收到队列：{}，信息：{},Message:{}", MqConfig.QUEUE_FOR_FANOUT_B, msg, message);
    }
}
