package com.orderdetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orderdetails.entity.Customer;

@Repository
@EnableJpaRepositories
public interface CustomerRepo extends JpaRepository<Customer, Long> {

	Customer findCustomerByUserName(@Param(value = "userName") String userName);

	List<Customer> findByEmail(String email);
	
}
