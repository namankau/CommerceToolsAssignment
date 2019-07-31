package com.importer.application.service;

import java.io.IOException;
import java.util.List;

import com.importer.application.dto.ProductDTO;
/**
 * 
 * @author nakaushik
 *
 */
public interface IProductImporterService {
List<ProductDTO> readProducts() throws IOException;
}
