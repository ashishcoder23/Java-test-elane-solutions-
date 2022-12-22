package com.orderdetails.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orderdetails.entity.FinalResponse;
import com.orderdetails.entity.Order;
import com.orderdetails.entity.OrderDetails;
import com.orderdetails.entity.Product;
import com.orderdetails.service.CustomerService;
import com.orderdetails.service.OrderService;
import com.orderdetails.service.ProductService;


@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private ProductService productService;
	
	private final OrderService orderService;
	private final CustomerService custumerservice;

	@Autowired
	public OrderController(OrderService orderService, CustomerService custumerservice) {
		this.orderService = orderService;
		this.custumerservice = custumerservice;
	}

	@GetMapping("/products")
	public ResponseEntity<FinalResponse> populateProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/getById")
	private ResponseEntity<FinalResponse> getById(@RequestParam Long id) {
		return orderService.getById(id);
	}

	
	@DeleteMapping("/deleteById")
	private String deleteById(@RequestParam Long id) {
		return orderService.deleteById(id);
	}

	@PostMapping("/create")
	public String saveOrder(@RequestBody Order order) {
	
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
				order.setCustumer(custumerservice.findByUsername(order.getCustumer().getUserName()));
				for(OrderDetails orderLine:orderLines) {
					Product price = productService.getById(orderLine.getProduct().getId());
					orderLine.setOrder(order);
					orderLine.setSubTotal(orderLine.getQuantity() * price.getUnitPrice());
					totalAmount += orderLine.getQuantity() * price.getUnitPrice();
				}
				order.setTotal(totalAmount);
				orderService.save(order);
				return "Your order has been placed successfully.";
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		
	}
	
	@GetMapping("/listOrders")
	@ResponseBody
	public ResponseEntity<FinalResponse> listOrders() {
		return orderService.findAll();
	}
	
	

}
