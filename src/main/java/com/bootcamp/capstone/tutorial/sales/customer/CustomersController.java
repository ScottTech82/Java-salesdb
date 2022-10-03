package com.bootcamp.capstone.tutorial.sales.customer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomersController {

	@Autowired
	private CustomerRepository custRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Customer>> getCustomers() {
		Iterable<Customer> customers = custRepo.findAll(); /*to find all and put in customers var.
		 using the custRepo that is customer repository that inherits the crud repository. */
			//then return the new collection customers, status ok which is error result 200.
		return new ResponseEntity<Iterable<Customer>>(customers, HttpStatus.OK);
	}
	
	@GetMapping("{id}") /* /api/customers/id */
	public ResponseEntity<Customer> getCustomerByPK(@PathVariable int id) {
								//need to use the PathVariable to read id
		Optional<Customer> customer = custRepo.findById(id);
		if(customer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
					//return type is Optional, so have to use customer.get to return it
		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
	}
	
	@GetMapping("code/{code}") /* /api/customers/code/{code} */
	public ResponseEntity<Customer> getCustomerByCode(@PathVariable String code) {
		Optional<Customer> customer = custRepo.findByCode(code);
		if(customer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Customer> postCustomer(@RequestBody Customer customer) {
									//saying where the pass in data is coming from
									//the @ReqestBody being passed into Customer customer.
		if(customer.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Customer newCustomer = custRepo.save(customer);
		return new ResponseEntity<Customer>(newCustomer, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes") //or just leave the yellow squig warning.
	@PutMapping("{id}")
	      //not going to return any data, so dont have to put a type in response entity.
	public ResponseEntity putCustomer(@PathVariable int id, @RequestBody Customer customer) {
		if(id != customer.getId()) { //if the id does not exist return bad request
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Customer> cust = custRepo.findById(customer.getId());
		if(cust.isEmpty()) { //if no customer found, return not found
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		custRepo.save(customer); //save update
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteCustomer(@PathVariable int id) {
		Optional<Customer> customer = custRepo.findById(id);
		if(customer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		custRepo.delete(customer.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
}
