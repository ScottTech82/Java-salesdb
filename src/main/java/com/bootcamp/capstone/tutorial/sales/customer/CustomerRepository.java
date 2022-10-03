package com.bootcamp.capstone.tutorial.sales.customer;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	Optional<Customer> findByCode(String code);
	  //read for one specific column for the specific code passed in.
}
