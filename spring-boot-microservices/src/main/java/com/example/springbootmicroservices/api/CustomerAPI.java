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
	public Optional<Customer> getCustomerById(@PathVariable("customerId") long id) {
		return repo.findById(id);
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
	public Optional<Customer> updateCustomer(@PathVariable("customerId") Long id, 
			@RequestBody Customer customerDetails) {
		Optional<Customer> customerOpt = repo.findById(id);
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			customer.setName(customerDetails.getName());
			customer.setEmail(customerDetails.getEmail());
			return Optional.of(repo.save(customer));
		} 
		return Optional.empty();
	}
	
	@DeleteMapping("/customers/{customerId}")
	public void deleteCustomer(@PathVariable("customerId") Long id) {
		repo.deleteById(id);
	}
}
