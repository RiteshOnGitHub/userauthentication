package com.userauth.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.userauth.app.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
}