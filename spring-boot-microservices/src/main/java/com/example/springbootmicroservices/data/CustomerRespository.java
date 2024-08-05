package com.example.springbootmicroservices.data;

import java.util.ArrayList;
import java.util.Optional;

public class CustomerRespository {
	private ArrayList<Customer> customers;
	
	public CustomerRespository() {
		customers = new ArrayList<>();
		String[] names = {"Brian", "Jennifer", "Joe"};
		String[] emails = {"b@example.com", "jennifer@abc.com", "joejoe@exmail.com"};
		
		for (int i = 0; i < names.length; i++) {
			customers.add(new Customer(i, names[i], emails[i]));
		}
	}
	
	public Iterable<Customer> findAll() {
		return customers;
	}
	
	public Optional<Customer> findById(long id) {
		Customer customer  = null;
		
		for (Customer c : customers) {
			if (c.getId() == id) {
				customer = c;
				break;
			}
		}
		
		Optional<Customer> op = Optional.ofNullable(customer);
		return op;
	}
}
