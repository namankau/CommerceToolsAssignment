package com.aggregator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aggregator.model.Product;
import com.aggregator.repository.ProductRepository;
/**
 * Service class to interact with DB
 * @author nakaushik
 *
 */
@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	public List<Product> getAllProducts(){
		return repo.findAll();
	}
	public void insertProducts(List<Product> listOfProducts) {
		repo.saveAll(listOfProducts);
	}
	
}
