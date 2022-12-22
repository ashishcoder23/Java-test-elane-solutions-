package com.orderdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.orderdetails.entity.Address;

@Repository
@EnableJpaRepositories
public interface LocationRepo extends JpaRepository<Address, Long>{

}
