package com.orderdetails.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private Customer custumer;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Order date cannot be empty")
	private Date orderDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference
	private List<OrderDetails> orderLines;

	private Double total;

	public void addOrderLine(OrderDetails orderLine) {
		orderLine.setOrder(this);
		this.orderLines.add(orderLine);
	}
}
