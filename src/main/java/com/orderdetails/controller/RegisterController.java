package com.orderdetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderdetails.entity.Customer;
import com.orderdetails.entity.FinalResponse;
import com.orderdetails.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class RegisterController {
	@Autowired
	private CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<FinalResponse> saveCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}
}