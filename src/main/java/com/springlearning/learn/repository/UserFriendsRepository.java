package com.springlearning.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springlearning.learn.entity.UserFriends;

public interface UserFriendsRepository extends CrudRepository<UserFriends, Integer> {

	@Query("Select u from UserFriends u where u.user_details.username=?1")
	List<UserFriends> listOfFriends(String user);
	
}
