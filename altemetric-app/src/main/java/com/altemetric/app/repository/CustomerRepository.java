package com.altemetric.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.altemetric.app.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
}