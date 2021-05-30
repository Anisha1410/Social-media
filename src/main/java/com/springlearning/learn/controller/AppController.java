package com.springlearning.learn.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springlearning.learn.entity.User_Details;
import com.springlearning.learn.service.SocialMediaService;



@RestController
public class AppController {
	@Autowired
	private SocialMediaService socialmediaService;
	
	@PostMapping("/create")
	public ResponseEntity<Map<String,Object>> createUser(@RequestBody User_Details user) {
		// Thread.sleep(3000);
		
		return socialmediaService.createUser(user);
		
		
		
	}
	
	
	@PostMapping("/add/{userA}/{userB}")
	public ResponseEntity<Map<String,Object>> sendRequest(@PathVariable String userA, @PathVariable String userB) {

		//insert in requestsent
		//insert in request received
		return socialmediaService.sendRequest(userA,userB);
		
		
	}
	
	@GetMapping("/friendRequests/{userA}")
	public ResponseEntity<Map<String,Object>> getPendingRequests(@PathVariable String userA){
		//select from Requests received
		return socialmediaService.getPendingRequests(userA);
	}
	
	@GetMapping("/friends/{userA}")
	public ResponseEntity<Map<String,Object>> getAllFriends(@PathVariable String userA) {
		// Thread.sleep(3000);
		
		
			return socialmediaService.listOfFriends(userA);
		
		
		//return "";
	}
	
	@GetMapping("/suggestions/{userA}")
	public ResponseEntity<Map<String,Object>> showSuggestions(@PathVariable String userA){
		
		
		return socialmediaService.showSuggestions(userA);
	}
	
	
	
}
