package com.orderdetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderdetails.entity.FinalResponse;
import com.orderdetails.entity.Product;
import com.orderdetails.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@PostMapping("/create")
	private ResponseEntity<FinalResponse> create(@RequestBody Product product) {
		return service.create(product);
	}
}
