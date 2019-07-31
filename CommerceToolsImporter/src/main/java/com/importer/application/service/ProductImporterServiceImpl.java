package com.importer.application.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.importer.application.dto.ProductDTO;
import com.opencsv.bean.CsvToBeanBuilder;
/**
 * Product service class to read csv and create JSON for each object
 * @author nakaushik
 *
 */
@Service
public class ProductImporterServiceImpl implements IProductImporterService {

	/**
	 * Reads the products from csv and bind them to JSON
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ProductDTO> readProducts() throws IOException {
		// TODO Auto-generated method stub
		FileReader productsCSVReader= new FileReader(new File("C:\\Users\\nakaushik\\Documents\\CommerceToolsAssignment\\CommerceToolsImporter\\src\\main\\resources\\dataExample.csv"));
		List<ProductDTO> products = new CsvToBeanBuilder(productsCSVReader).withType(ProductDTO.class).build().parse();
		return products;
	}

}
