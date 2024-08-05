package com.example.springbootmicroservices.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootmicroservices.Customer;

@RestController
@RequestMapping("/api")
public class CustomerAPI {
	
	@GetMapping("/customers")
	public Iterable<Customer> getAll() {
		return null;
	}
}
