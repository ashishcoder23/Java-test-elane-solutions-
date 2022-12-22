package com.orderdetails.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String houseNo;
	
	private String streetNo;
	
	private String nearByLocation;
	
	private String place;

	private String province;

	private String country;

}
