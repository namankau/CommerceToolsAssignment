package com.aggregator.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aggregator.dto.ProductDTO;
import com.aggregator.model.Product;
import com.aggregator.service.ProductService;
import com.aggregator.util.ObjectMapperUtils;

/**
 * Controller to aggregator MS to provide API endpoints to fetch records from db 
 * @author nakaushik
 *
 */
@RestController
@RequestMapping(path = "/aggregator")
public class AggregatorController {

	private static final Logger log = LoggerFactory.getLogger(AggregatorController.class);

	@Autowired
	private ProductService aggService;
	/**
	 * Gets all the product stored in db and provides a list of them in json format 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/readProducts", produces = "application/json")
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<ProductDTO> list=new ArrayList<>();
		List<Product> listProducts;
		try {
			listProducts=aggService.getAllProducts();
		    list = ObjectMapperUtils.mapAll(listProducts, ProductDTO.class);
		}
		catch(Exception e) {
			log.error("Unable to read the response");
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok(list);
	}
	/**
	 * 
	 * @param date
	 * @return
	 * List of products that were either updated or inserted on the given date
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value="/getProductsUpdatedByDate", produces = "application/json")
	public ResponseEntity<List<ProductDTO>> getStatusByDate(@RequestParam(name="date") String date){
		
		List<ProductDTO> list=new ArrayList<>();
		List<Product> listProducts;

		try {
			listProducts=aggService.getAllProducts();
		    for(Product p:listProducts) {
		    	if(p.getCreateDate().toString().equals(date)|| p.getModifyDate().toString().equals(date))
		    	{
		    		ProductDTO ob=ProductDTO.convertFromProduct(p);
			    	list.add(ob);
		    	}
		    	
		    }
		}
		catch(Exception e) {
			log.error("Unable to fetch daily stats");
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(list);
	}


	
}
