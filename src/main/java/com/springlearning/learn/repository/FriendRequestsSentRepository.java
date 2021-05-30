package com.springlearning.learn.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springlearning.learn.entity.FriendRequestsSent;

public interface FriendRequestsSentRepository extends CrudRepository<FriendRequestsSent, Integer> {

	List<FriendRequestsSent> findByFriend(String friend);
}
