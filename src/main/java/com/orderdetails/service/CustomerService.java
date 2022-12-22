package com.orderdetails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderdetails.entity.Customer;
import com.orderdetails.entity.FinalResponse;
import com.orderdetails.repository.CustomerRepo;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	public Customer findByUsername(String userName) {
		Customer findCustomerByUserName = customerRepo.findCustomerByUserName(userName);
		return findCustomerByUserName;
	}

	public ResponseEntity<FinalResponse> saveCustomer(Customer customer) {
		FinalResponse finalResponse = new FinalResponse();
		try {
			Customer findCustomerByUserName = customerRepo.findCustomerByUserName(customer.getUserName());
			if (findCustomerByUserName==null ) {
				Customer save = customerRepo.save(customer);
				finalResponse.setData(save);
				finalResponse.setMessage("Register Successfully");
				finalResponse.setStatus(true);
				return new ResponseEntity<>(finalResponse, HttpStatus.CREATED);
			} else if(customer.getUserName().equals(findCustomerByUserName.getUserName())){
				finalResponse.setMessage(customer.getUserName() + " Already exists!");
				finalResponse.setStatus(false);
				return new ResponseEntity<>(finalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				finalResponse.setMessage("Register not successfully");
				finalResponse.setStatus(true);
				return new ResponseEntity<>(finalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			finalResponse.setMessage("Customer Register facing some Exception");
			finalResponse.setStatus(false);
			return new ResponseEntity<>(finalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
