package com.springlearning.learn.repository;

import org.springframework.data.repository.CrudRepository;

import com.springlearning.learn.entity.User_Details;



public interface CreateUserRepository extends CrudRepository<User_Details, String> {

	User_Details findByUsername(String username);
	
}
