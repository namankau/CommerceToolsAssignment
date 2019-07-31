package com.aggregator.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.aggregator.config.ApplicationConfigReader;
import com.aggregator.model.Product;
import com.aggregator.service.ProductService;
/**
 * Listener for the message received in MQ and process the message accordingly
 * @author nakaushik
 *
 */

@Service
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    
    @Autowired
    ApplicationConfigReader applicationConfigReader;
    @Autowired
    private ProductService service;
    
    /**
     * Message listener for CommerceTools Application
     * @param List of Products i.e. a user defined object used for deserialization of message
     */
    @RabbitListener(queues = "${commercetools.queue.name}")
    public void receiveMessage(List<Product> data) {
    	log.info("Received message: {} from commercetools queue.", data);
    	try {
    		log.info("Making call to servie class to fetch existing products");
    		List<Product> listExistingProducts = service.getAllProducts();
    		log.info("Segregating existing products in DB from the new list of products coming across queue");
    		List<Product> listTobeUpdated=getExistingProductsInList(data,listExistingProducts);
    		log.info("Segregating freshly coming products in the new list of products coming across queue");
    		List<Product> listOfBrandNewProducts = getNewProductsInList(data,listTobeUpdated);
    		List<Product> listToBeInserted=new ArrayList<>();
    		if(listOfBrandNewProducts.size()==0 && listTobeUpdated.size()==0)
    			listToBeInserted.addAll(data);
    		else {
    			listToBeInserted.addAll(listTobeUpdated);
    			listToBeInserted.addAll(listOfBrandNewProducts);
    		}

    		log.info("Inserting new data to db");
    		service.insertProducts(listToBeInserted);
    		
        	log.info("<< Exiting receiveMessage after inserting new and updated products in db.");
    	} catch(HttpClientErrorException  ex) {
    		
    		if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
        		log.info("Delay...");
        		try {
    				Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
    			} catch (InterruptedException e) { }
    			
    			log.info("Throwing exception so that message will be requed in the queue.");
    			// Note: Typically Application specific exception is thrown below
    			throw new RuntimeException();
    		} else {
    			throw new AmqpRejectAndDontRequeueException(ex); 
    		}
    		
    	} catch(Exception e) {
    		log.error("Internal server error occurred in API call. Bypassing message requeue {}", e);
    		throw new AmqpRejectAndDontRequeueException(e); 
    	}

    }

    /**
     * Method  to get the products that are already stored in database, so as to capture if the existing products are
     *  again coming into the queue
     * @param newProduct
     * @param existingProduct
     * @return
     * Products that were existing in DB
     */
    private static List<Product> getExistingProductsInList(final List<Product> newProduct, List<Product> existingProduct) {

        List<String> existingUUID = 
        		existingProduct.stream()
        	              .map(Product::getUUID)
        	              .collect(Collectors.toList());
        List<Product> listOutput =
        		newProduct.stream()
        		.filter(e -> existingUUID.contains(e.getUUID()))
                .collect(Collectors.toList());
        List<Date> existingCreatedDateList = 
        		existingProduct.stream()
        	              .map(Product::getCreateDate)
        	              .collect(Collectors.toList());
        int i=0;
        for(Product p:listOutput) {
        	p.setCreatedDate(existingCreatedDateList.get(i));
        	i++;
        }
        return listOutput;
    }
    /*
     * 
     */
    /**
     * 
     * @param totalNewProducts
     * @param existingProducts
     * @return 
     *  freshly coming products in the queue
     */
    private static List<Product> getNewProductsInList(final List<Product> totalNewProducts, List<Product> existingProducts) {
    	List<String> newUUID = 
    			totalNewProducts.stream()
        	              .map(Product::getUUID)
        	              .collect(Collectors.toList());
        List<String> existingUUID = 
        		existingProducts.stream()
        	              .map(Product::getUUID)
        	              .collect(Collectors.toList());
       
        newUUID.removeAll(existingUUID);
        List<Product> listOutput =
        		totalNewProducts.stream()
        		.filter(e -> newUUID.contains(e.getUUID()))
                .collect(Collectors.toList());
        return listOutput;
    }
    
    

}
