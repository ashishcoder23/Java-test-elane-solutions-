package com.orderdetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderdetails.entity.Customer;
import com.orderdetails.service.CustomerService;

@RestController
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	private CustomerService customerService;

	@PostMapping("/create")
	public Customer saveCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}
}