
package com.aggregator.dto;
import java.io.Serializable;

import com.aggregator.model.Product;
import com.opencsv.bean.CsvBindByName;
/**
 * DTO object to send the Product JSON response
 * @author nakaushik
 *
 */
public class ProductDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@CsvBindByName(column="UUID")
	private String UUID;
	
	@CsvBindByName(column="Name")
	private String name;
	
	@CsvBindByName(column="Description")
	private String description;
	
	@CsvBindByName(column="provider")
	private String provider;
	
	@CsvBindByName(column="available")
	private String available;
	
	@CsvBindByName(column="MeasurementUnits")
	private String measurementUnits;
	
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getMeasurementUnits() {
		return measurementUnits;
	}
	public void setMeasurementUnits(String measurementUnits) {
		this.measurementUnits = measurementUnits;
	}
	@Override
	public String toString() {
		return "Product Details [UUID=" + UUID + ", Name=" + name + ", Description=" + description + ",Provider=" +provider+ ",Available=" +available+ ",MeasurementUnits="+measurementUnits+"]";
	}
	public ProductDTO(){}
	public ProductDTO(String UUID, String name,String description,String provider, String available, String measurementUnits){
		this.available=available;
		this.name=name;
		this.description=description;
		this.measurementUnits=measurementUnits;
		this.provider=provider;
		this.UUID=UUID;
	}
	public static ProductDTO convertFromProduct(Product p) {
		return new ProductDTO(p.getUUID(),p.getName(),p.getDescription(),p.getProvider(),p.getAvailable(),p.getMeasurementUnits());
	}
}
