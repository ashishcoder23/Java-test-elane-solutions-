package com.orderdetails.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderdetails.entity.FinalResponse;
import com.orderdetails.entity.Order;
import com.orderdetails.repository.OrderDetailsRepo;
import com.orderdetails.repository.OrderRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo repo;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;
	
	public Order save(Order order) {
		return repo.save(order);
	}

	@javax.transaction.Transactional
	public ResponseEntity<FinalResponse> findAll() {
		FinalResponse finalResponse = new FinalResponse();
		try {
		List<Order> findAll = repo.findAll();
		findAll =findAll.stream().collect(Collectors.toList());
		if (findAll.size() <= 0) {
			finalResponse.setMessage("Data not exists");
			finalResponse.setStatus(false);
			return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.NOT_FOUND);
		} else {
			finalResponse.setData(findAll);
			finalResponse.setMessage("Success");
			finalResponse.setStatus(true);
			return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.OK);
		}
		}catch (Exception e) {
			finalResponse.setMessage("findAll getting Exception");
			finalResponse.setStatus(false);
			return new ResponseEntity<FinalResponse>(finalResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public String deleteById(Long id) {
		repo.deleteById(id);
		return "Order is deleted";
	}

	public ResponseEntity<FinalResponse> getById(Long id) {
		FinalResponse finalResponse = new FinalResponse();
		try {
		Order order = repo.findById(id).get();
		if (!repo.existsById(id)) {
			finalResponse.setMessage("Id is not exists");
			finalResponse.setStatus(false);
			return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.NOT_FOUND);
		} else {
			finalResponse.setData(order);
			finalResponse.setMessage("Success");
			finalResponse.setStatus(true);
			return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.OK);
		}
		}catch (Exception e) {
			finalResponse.setMessage("getById() Exception");
			finalResponse.setStatus(false);
			return new ResponseEntity<FinalResponse>(finalResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
