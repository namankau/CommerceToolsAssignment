package com.aggregator.model;


import java.util.Date;
/**
 * Product bean object for DB operations
 * @author nakaushik
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.TemporalType;
@Entity
@Table(name="Product")
public class Product{
	
		@SuppressWarnings("unused")
		private static final long serialVersionUID = 1L;
		@Id
		private String UUID;
		private String name;
		private String description;
		private String provider;
		private String available;
		private String measurementUnits;
		@CreatedDate
		@Temporal(TemporalType.DATE)
		@Column(name = "create_date")
		private Date createDate=new Date();

		public Date getCreateDate() {
			return createDate;
		}
		public void setCreatedDate(Date d) {
			this.createDate=d;
		}
		
		
		@LastModifiedDate
		@Temporal(TemporalType.DATE)
		@Column(name = "modify_date")
		private Date modifyDate =  new Date();
		public Date getModifyDate() {
			return modifyDate;
		}

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

		
		
	

}
