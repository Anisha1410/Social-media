package com.springlearning.learn.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class UserFriends {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String friend_id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User_Details user_details;

	public UserFriends( String friend_id, User_Details user_details) {
		super();
		
		this.friend_id = friend_id;
		this.user_details = user_details;
	}

	public UserFriends() {
		super();
	}

	public String getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}

	public User_Details getUser_details() {
		return user_details;
	}

	public void setUser_details(User_Details user_details) {
		this.user_details = user_details;
	}
	
	
	
	
	
	
}
