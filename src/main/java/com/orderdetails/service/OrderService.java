package com.orderdetails.service;

import java.util.List;
import java.util.stream.Collectors;

import com.orderdetails.entity.OrderDetails;
import com.orderdetails.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderdetails.entity.FinalResponse;
import com.orderdetails.entity.Order;
import com.orderdetails.repository.OrderRepo;

@Service
public class OrderService {

	private final OrderRepo orderRepository;
	private final ProductService productService;
	private final CustomerService custumerService;

	@Autowired
	public OrderService(OrderRepo orderRepository, ProductService productService, CustomerService custumerService) {
		this.orderRepository = orderRepository;
		this.productService = productService;
		this.custumerService = custumerService;
	}

	public String saveOrder(Order order) {
		try {
			List<OrderDetails> orderLines = order.getOrderLines();
			int lastOrderLineIndex = orderLines.size() - 1;

			if (orderLines.get(lastOrderLineIndex).getProduct() == null) {
				orderLines.remove(lastOrderLineIndex);
			}

			if (orderLines.size() == 0) {
				order.addOrderLine(new OrderDetails());
				throw new Exception("At least one product must be added to the Order");
			}

			for (OrderDetails orderLine : orderLines) {
				if (orderLine.getQuantity() == 0) {
					throw new Exception(
							"Quantity of product " + orderLine.getProduct().getProductName() + " must be positive");
				}
			}
			double totalAmount = 0;
			order.setCustumer(custumerService.findByUsername(order.getCustumer().getUserName()));
			for (OrderDetails orderLine : orderLines) {
				Product price = productService.getById(orderLine.getProduct().getId());
				orderLine.setOrder(order);
				orderLine.setSubTotal(orderLine.getQuantity() * price.getUnitPrice());
				totalAmount += orderLine.getQuantity() * price.getUnitPrice();
			}
			order.setTotal(totalAmount);
			orderRepository.save(order);
			return "Your order has been placed successfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@javax.transaction.Transactional
	public ResponseEntity<FinalResponse> listOrders() {
		FinalResponse finalResponse = new FinalResponse();
		try {
			List<Order> findAll = orderRepository.findAll();
			findAll = findAll.stream().collect(Collectors.toList());
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
		} catch (Exception e) {
			finalResponse.setMessage("All Order facing some Exception");
			finalResponse.setStatus(false);
			return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<FinalResponse> deleteById(Long id) {
		FinalResponse finalResponse = new FinalResponse();
		try {
			
			if (!orderRepository.existsById(id)) {
				finalResponse.setMessage("Order id is not exists!");
				finalResponse.setStatus(false);
				return new ResponseEntity<>(finalResponse, HttpStatus.NOT_FOUND);
			} else {
				orderRepository.deleteById(id);
				finalResponse.setMessage("Order is deleted.");
				finalResponse.setStatus(true);
				return new ResponseEntity<>(finalResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			finalResponse.setMessage("Order Delete facing some Exception!");
			finalResponse.setStatus(false);
			return new ResponseEntity<>(finalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<FinalResponse> getById(Long id) {
		FinalResponse finalResponse = new FinalResponse();
		try {
			if (!orderRepository.existsById(id)) {
				finalResponse.setMessage("Id is not exists!");
				finalResponse.setStatus(false);
				return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.NOT_FOUND);
			} else {
				Order order = orderRepository.findById(id).get();
				finalResponse.setData(order);
				finalResponse.setMessage("Success");
				finalResponse.setStatus(true);
				return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			finalResponse.setMessage("getById() Exception");
			finalResponse.setStatus(false);
			return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
