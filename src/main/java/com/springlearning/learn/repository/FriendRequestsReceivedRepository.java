package com.springlearning.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springlearning.learn.entity.FriendRequestsReceived;



public interface FriendRequestsReceivedRepository extends CrudRepository<FriendRequestsReceived, Integer> {

	@Query("Select u from  FriendRequestsReceived u where u.friend=?1 and u.user_details.username=?2")
	FriendRequestsReceived findByUserIDandFriendID(String friend,String email);

	@Query("Select u from FriendRequestsReceived u where u.flag=?2 and u.user_details.username=?1")
	List<FriendRequestsReceived> getPendingRequests(String userA, int i);
	
}
