package com.orderdetails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderdetails.entity.Customer;
import com.orderdetails.repository.CustomerRepo;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	public Customer findByUsername(String userName) {
		Customer findCustomerByUserName = customerRepo.findCustomerByUserName(userName);
		return findCustomerByUserName;
	}

	public Customer saveCustomer(Customer customer) {
		return customerRepo.save(customer);
	}
}
