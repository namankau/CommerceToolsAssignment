package com.importer.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.importer.application.config.RabbitConfiguration;
import com.importer.application.dto.ProductDTO;
import com.importer.application.service.ProductImporterServiceImpl;
import com.importer.application.util.AppConstants;
import com.importer.application.util.MessageSender;
/**
 * Importer controller providing endpoint to send message to queue
 * @author nakaushik
 *
 */
@RestController
@RequestMapping("/importer")
public class ImporterController {


	private static final Logger log = LoggerFactory.getLogger(ImporterController.class);


	@Autowired
	private MessageSender messageSender;
	@Autowired
	private ProductImporterServiceImpl importerService;
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	RabbitConfiguration config;
	/**
	 * posting method to Rabbit Message Queue that will be received by Listener
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(path = "/importProducts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendMessage() {
		
		
		List<ProductDTO> products;
		String exchange = config.getCommerceToolsExchange();
		String routingKey = config.getCommerceToolsRoutingKey();
		try {
			products = importerService.readProducts();
			messageSender.sendMessage(rabbitTemplate, exchange, routingKey, products);
			return new ResponseEntity<String>(AppConstants.IN_QUEUE, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
			return new ResponseEntity(AppConstants.MESSAGE_QUEUE_SEND_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	

}
