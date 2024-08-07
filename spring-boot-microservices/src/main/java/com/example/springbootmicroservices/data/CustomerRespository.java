package com.example.springbootmicroservices.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface CustomerRespository extends CrudRepository<Customer, Long>{
	public Optional<Customer> findFirstByName(String name);
}
