package com.orderdetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orderdetails.entity.FinalResponse;
import com.orderdetails.entity.Order;
import com.orderdetails.service.OrderService;
import com.orderdetails.service.ProductService;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	
	private final ProductService productService;
	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService, ProductService productService) {
		this.orderService = orderService;
		this.productService = productService;
	}

	@GetMapping("/products")
	public ResponseEntity<FinalResponse> populateProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<FinalResponse> getById(@PathVariable("id") Long id) {
		return orderService.getById(id);
	}

	
	@DeleteMapping("/{id}")
	private ResponseEntity<FinalResponse> deleteById(@PathVariable("id") Long id) {
		return orderService.deleteById(id);
	}

	@PostMapping("/create")
	public String saveOrder(@RequestBody Order order) {
		return orderService.saveOrder(order);
	}
	
	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<FinalResponse> listOrders() {
		return orderService.listOrders();
	}
	
	

}
