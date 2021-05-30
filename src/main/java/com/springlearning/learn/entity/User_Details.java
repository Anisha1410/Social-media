package com.springlearning.learn.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class User_Details {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	

	@Id
	private String username;
	@OneToMany(mappedBy="user_details")
	private List<UserFriends> userFriends;
	@OneToMany(mappedBy="user_details")
	private List<FriendRequestsSent> friendRequestsSent;
	@OneToMany(mappedBy="user_details")
	private List<FriendRequestsReceived> friendRequestsReceived;
	
	
	

	User_Details(){
		
	}




	public User_Details(String email) {
		super();
		
		//this.password = password;
		this.username = email;
		
	}




	/*public String getPassword() {
		return password;
	}*/




	/*public void setPassword(String password) {
		this.password = password;
	}*/




	public String getUsername() {
		return username;
	}




	public void setUsername(String email) {
		this.username = email;
	}




	public List<UserFriends> getUserFriends() {
		return userFriends;
	}




	public void setUserFriends(List<UserFriends> userFriends) {
		this.userFriends = userFriends;
	}




	public List<FriendRequestsSent> getFriendRequestsSent() {
		return friendRequestsSent;
	}




	public void setFriendRequestsSent(List<FriendRequestsSent> friendRequestsSent) {
		this.friendRequestsSent = friendRequestsSent;
	}




	public List<FriendRequestsReceived> getFriendRequestsReceived() {
		return friendRequestsReceived;
	}




	public void setFriendRequestsReceived(List<FriendRequestsReceived> friendRequestsReceived) {
		this.friendRequestsReceived = friendRequestsReceived;
	}

	
	
}
