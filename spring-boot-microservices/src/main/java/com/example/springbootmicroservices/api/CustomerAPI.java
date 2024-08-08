package com.example.springbootmicroservices.api;
import com.example.springbootmicroservices.data.Customer;
import com.example.springbootmicroservices.data.CustomerRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerAPI {
	@Autowired
	private CustomerRespository repo;
	
	@GetMapping("/customers")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(repo.findAll());
	}
	
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") long id) {
		Optional<Customer> customer = repo.findById(id);
		
		if (customer.isPresent()) {
			return ResponseEntity.ok().body(customer.get());
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/customers/byname/{name}")
	public ResponseEntity<?> getCustomerByName(@PathVariable("name") String name) {
		return ResponseEntity.ok(repo.findFirstByName(name));
	}
	
	@PostMapping("/customers")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
		if (customer.getName() == null || customer.getEmail() == null) {
			return ResponseEntity.badRequest().build();
		}
		customer = repo.save(customer);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(customer.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(customer);
	}
	
	@PutMapping("/customers/{customerId}")
	public ResponseEntity<?> updateCustomer(@PathVariable("customerId") Long id, 
			@RequestBody Customer customerDetails) {
		Optional<Customer> customerOpt = repo.findById(id);
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			customer.setName(customerDetails.getName());
			customer.setEmail(customerDetails.getEmail());
			
			repo.save(customer);
			
			return ResponseEntity.ok(customer);
		} 
		
		if (customerDetails.getName() == null || customerDetails.getEmail() == null) {
			return ResponseEntity.badRequest().build();
		}
		customerDetails = repo.save(customerDetails);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(customerDetails.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(customerDetails);
	}
	
	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Long id) {
		repo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
