package com.orderdetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.orderdetails.entity.OrderDetails;

@Repository
@EnableJpaRepositories
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long>{

	@Query(value="select * from order_details",nativeQuery = true)
	List<OrderDetails> getAll();
}
