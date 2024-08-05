package com.example.springbootmicroservices.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface CustomerRespository extends CrudRepository<Customer, Long>{
	
}
