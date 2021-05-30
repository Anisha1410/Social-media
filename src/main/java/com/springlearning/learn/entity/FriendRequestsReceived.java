package com.springlearning.learn.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class FriendRequestsReceived {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String friend;
	private int flag;
	@ManyToOne
	@JoinColumn(name="user")
	private User_Details user_details;
	public FriendRequestsReceived(String friend_id,User_Details user_details, int flag ) {
		super();
		this.friend = friend_id;
		this.flag = flag;
		this.user_details = user_details;
	}
	public FriendRequestsReceived() {
		super();
	}
	public String getFriend_id() {
		return friend;
	}
	public void setFriend_id(String friend_id) {
		this.friend = friend_id;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public User_Details getUser_details() {
		return user_details;
	}
	public void setUser_details(User_Details user_details) {
		this.user_details = user_details;
	}
	
	
	
}
