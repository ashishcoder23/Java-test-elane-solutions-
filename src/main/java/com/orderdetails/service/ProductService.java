package com.orderdetails.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderdetails.entity.FinalResponse;
import com.orderdetails.entity.Product;
import com.orderdetails.repository.ProductRepo;

@Service
public class ProductService {

	@Autowired(required = true)
	private ProductRepo repo;

	public ResponseEntity<FinalResponse> createProduct(Product product) {
		FinalResponse finalResponse = new FinalResponse();
		try {
			Product save = repo.save(product);
			finalResponse.setData(save);
			finalResponse.setMessage("Product Created");
			finalResponse.setStatus(true);
			return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			finalResponse.setMessage("Product create facing some Exception");
			finalResponse.setStatus(false);
			return new ResponseEntity<FinalResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Product getById(Long id) {
		return repo.findById(id).get();
	}

	public ResponseEntity<FinalResponse> getAllProducts() {
		List<Product> findAll = repo.findAll();
		findAll = findAll.stream().collect(Collectors.toList());
		FinalResponse finalResponse = new FinalResponse();
		try {
			if (findAll == null) {
				finalResponse.setData(findAll);
				finalResponse.setMessage("Product Don't exists!");
				finalResponse.setStatus(true);
				return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.NOT_FOUND);
			} else {
				finalResponse.setData(findAll);
				finalResponse.setMessage("Success Getting Data");
				finalResponse.setStatus(true);
				return new ResponseEntity<FinalResponse>(finalResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			finalResponse.setMessage("Product finding facing some Exception");
			finalResponse.setStatus(false);
			return new ResponseEntity<FinalResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
