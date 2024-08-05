package com.example.springbootmicroservices.api;
import com.example.springbootmicroservices.data.Customer;
import com.example.springbootmicroservices.data.CustomerRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class CustomerAPI {
	@Autowired
	private CustomerRespository customerRepository;
	
	@GetMapping("/customers")
	public Iterable<Customer> getAll() {
		return customerRepository.findAll();
	}
	
	@GetMapping("/customers/{customerId}")
	public Optional<Customer> getCustomerById(@PathVariable("customerId") Long id) {
		return customerRepository.findById(id);
	}
	
//	@PostMapping
//	public Customer createCustomer(@RequestBody Customer customer) {
//		return customerRepository.save(customer);
//	}
//	
//	@PutMapping("/{customerId}")
//	public Optional<Customer> updateCustomer(@PathVariable("customerId") Long id, @RequestBody Customer customerDetails) {
//		Optional<Customer> customerOpt = customerRepository.findById(id);
//		if (customerOpt.isPresent()) {
//			Customer customer = customerOpt.get();
//			customer.setName(customerDetails.getName());
//			customer.setEmail(customerDetails.getEmail());
//			return Optional.of(customerRepository.save(customer));
//		}
//		return Optional.empty();
//	}
//	
//	@DeleteMapping("/{customerId}")
//	public void deleteCustomer(@PathVariable("customerId") Long id) {
//		customerRepository.deleteById(id);
//	}
}
