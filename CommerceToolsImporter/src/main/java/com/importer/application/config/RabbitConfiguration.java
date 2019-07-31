package com.importer.application.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Class to read application.properties file
 * @author nakaushik
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
public class RabbitConfiguration {
	
	@Value("${commercetools.exchange.name}")
	private String commerceToolsExchange;
	
	@Value("${commercetools.queue.name}")
	private String commerceToolsQueue;
	
	@Value("${commercetools.routing.key}")
	private String commerceToolsRoutingKey;

	public String getCommerceToolsExchange() {
		return commerceToolsExchange;
	}

	public void setCommerceToolsExchange(String commerceToolsExchange) {
		this.commerceToolsExchange = commerceToolsExchange;
	}

	public String getCommerceToolsQueue() {
		return commerceToolsQueue;
	}

	public void setCommerceToolsQueue(String commerceToolsQueue) {
		this.commerceToolsQueue = commerceToolsQueue;
	}

	public String getCommerceToolsRoutingKey() {
		return commerceToolsRoutingKey;
	}

	public void setCommerceToolsRoutingKey(String commerceToolsRoutingKey) {
		this.commerceToolsRoutingKey = commerceToolsRoutingKey;
	}

	
}
