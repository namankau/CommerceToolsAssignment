package com.importer.application;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.importer.application.config.RabbitConfiguration;

@SpringBootApplication
public class CommerceToolsImporterApplication {
	@Autowired
	private RabbitConfiguration applicationConfig;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CommerceToolsImporterApplication.class, args);
	}
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CommerceToolsImporterApplication.class);
	}
	
	/* This bean is to read the properties file configs */	
	
	
	/* Creating a bean for the Message queue Exchange */
	@Bean
	public TopicExchange getQueueExchange() {
		return new TopicExchange(applicationConfig.getCommerceToolsExchange());
	}

	/* Creating a bean for the Message queue */
	@Bean
	public Queue getQueueName() {
		return new Queue(applicationConfig.getCommerceToolsQueue());
	}
	
	/* Binding between Exchange and Queue using routing key */
	@Bean
	public Binding declareBindingApp1() {
		return BindingBuilder.bind(getQueueName()).to(getQueueExchange()).with(applicationConfig.getCommerceToolsRoutingKey());
	}
	
	/* Creating a bean for the Message queue Exchange */
	

	/* Bean for rabbitTemplate */
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}


	
}
