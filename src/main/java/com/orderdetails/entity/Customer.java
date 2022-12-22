package com.orderdetails.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String lastName;

	private String firstName;

	@NotEmpty(message = "Email cannot be empty")
	@Email
	@Column(unique = true)
	private String email;

	@Column(unique = true)
	@Min(value = 10)
	@Max(value = 10)
	private String phoneNo;

	@NotEmpty(message = "Username cannot be empty")
	@Column(unique = true)
	private String userName;

	@OneToOne(cascade = CascadeType.ALL)
	private Address location;

	private Boolean enable = true;

}
