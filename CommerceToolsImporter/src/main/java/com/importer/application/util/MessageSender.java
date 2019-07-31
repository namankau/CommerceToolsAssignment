package com.importer.application.util;
/**
 * Publishes the message to the queue for the recievers to receive
 * @author nakaushik
 */
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.importer.application.dto.ProductDTO;
@Component
public class MessageSender {private static final Logger log = LoggerFactory.getLogger(MessageSender.class);

/**
 * 
 * @param rabbitTemplate
 * @param exchange
 * @param routingKey
 * @param data
 * Posts the data on messagequeue
 */
public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, List<ProductDTO> data) {
	log.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
	rabbitTemplate.convertAndSend(exchange, routingKey, data);
	log.info("The message has been sent to the queue.");
}
}
