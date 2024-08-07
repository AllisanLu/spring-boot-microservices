package com.example.springbootmicroservices.api;
import com.example.springbootmicroservices.data.Customer;
import com.example.springbootmicroservices.data.CustomerRespository;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Iterable<Customer> getAll() {
		return repo.findAll();
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
	public Optional<Customer> getCustomerByName(@PathVariable("name") String name) {
		return repo.findFirstByName(name);
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
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/customers/{customerId}")
	public Optional<?> updateCustomer(@PathVariable("customerId") Long id, 
			@RequestBody Customer customerDetails) {
		Optional<Customer> customerOpt = repo.findById(id);
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			customer.setName(customerDetails.getName());
			customer.setEmail(customerDetails.getEmail());
			
			return Optional.of(repo.save(customer));
		} 
		
		if (customerDetails.getName() == null || customerDetails.getEmail() == null) {
			return Optional.of(ResponseEntity.badRequest().build());
		}
		customerDetails = repo.save(customerDetails);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(customerDetails.getId())
				.toUri();
		
		return Optional.of(ResponseEntity.created(location).build());
	}
	
	@DeleteMapping("/customers/{customerId}")
	public void deleteCustomer(@PathVariable("customerId") Long id) {
		repo.deleteById(id);
	}
}
