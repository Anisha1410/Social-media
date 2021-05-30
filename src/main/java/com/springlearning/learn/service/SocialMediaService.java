package com.springlearning.learn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springlearning.learn.entity.FriendRequestsReceived;
import com.springlearning.learn.entity.FriendRequestsSent;
import com.springlearning.learn.entity.UserFriends;
import com.springlearning.learn.entity.User_Details;
import com.springlearning.learn.repository.CreateUserRepository;
import com.springlearning.learn.repository.FriendRequestsReceivedRepository;
import com.springlearning.learn.repository.FriendRequestsSentRepository;
import com.springlearning.learn.repository.UserFriendsRepository;


@Service
public class SocialMediaService {
	@Autowired
	CreateUserRepository createUser;
	
	@Autowired
	FriendRequestsSentRepository friendRequestsSent;
	
	@Autowired
	FriendRequestsReceivedRepository friendRequestsReceived;
	
	@Autowired
	UserFriendsRepository userfriends;
	
	 List<String> list=new ArrayList<String>();

	public ResponseEntity<Map<String,Object>> createUser(User_Details user) {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			User_Details user1=createUser.findByUsername(user.getUsername());
			if(user1==null) {
			createUser.save(user);
			result.put("username","");
			return new ResponseEntity(result,HttpStatus.CREATED);
			}
			else {
				result.put("status","failure");
				result.put("reason", "explanation");
				return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			result.put("status","failure");
			result.put("reason", "explanation");
			return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
		}
		
	}

	public ResponseEntity<Map<String, Object>> sendRequest(String userA, String userB) {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		try {
		User_Details user1=createUser.findByUsername(userA);
		User_Details user2=createUser.findByUsername(userB);
		
		if(user1!=null && user2!=null) {
			List<FriendRequestsSent> f=friendRequestsSent.findByFriend(userB);
			List<FriendRequestsSent> g=friendRequestsSent.findByFriend(userA);
			Iterator itr=f.iterator();
			while(itr.hasNext()) {
				FriendRequestsSent fr=(FriendRequestsSent)itr.next();
				if(fr.getUser_details().getUsername().equals(userA)) {
					result.put("status","failure");
					result.put("reason","explanation");
					return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
							}
			}
			itr=g.iterator();
			while(itr.hasNext()) {
				FriendRequestsSent fr=(FriendRequestsSent)itr.next();
				if(fr.getUser_details().getUsername().equals(userB)) {
					FriendRequestsSent frndReqSent =new FriendRequestsSent(userB,0,user1);
					friendRequestsSent.save(frndReqSent);
					FriendRequestsReceived r=friendRequestsReceived.findByUserIDandFriendID(userB, userA);
					r.setFlag(0);
					friendRequestsReceived.save(r);
					FriendRequestsReceived frndReqRecived=new FriendRequestsReceived(userA,user2,0);
					friendRequestsReceived.save(frndReqRecived);
					UserFriends u=new UserFriends(userA,user2);
					UserFriends u2=new UserFriends(userB,user1);
					userfriends.save(u);
					userfriends.save(u2);
					result.put("status","success");
					return new ResponseEntity(result,HttpStatus.ACCEPTED);
					
				}
			}
			FriendRequestsReceived frndReqRecived=new FriendRequestsReceived(userA,user2,1);
			FriendRequestsSent frndReqSent =new FriendRequestsSent(userB,0,user1);
			friendRequestsSent.save(frndReqSent);
			friendRequestsReceived.save(frndReqRecived);
			result.put("status","success");
			return new ResponseEntity(result,HttpStatus.ACCEPTED);
		}
		else {
			result.put("status","failure");
			result.put("reason","explanation");
			return new ResponseEntity(result,HttpStatus.NOT_FOUND);
		}
		}
		catch(Exception e) {
			result.put("status","failure");
			result.put("reason","explanation");
			return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
		}
		//return null;
	}

	public ResponseEntity<Map<String, Object>> getPendingRequests(String userA) {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			User_Details user1=createUser.findByUsername(userA);
			if(user1==null) {
				result.put("status","failure");
				result.put("reason", "explanation");
				return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
			}
			else {
				List<FriendRequestsReceived> pendingRequests= friendRequestsReceived.getPendingRequests(userA,1);
				if(pendingRequests==null)
				{
					result.put("status","failure");
					result.put("reason","explanation");
					return new ResponseEntity<Map<String,Object>>(result,HttpStatus.NOT_FOUND);
				}
				else {
					Iterator itr=pendingRequests.iterator();
					List l=new ArrayList();
					while(itr.hasNext())
					{
						FriendRequestsReceived r=(FriendRequestsReceived)itr.next();
						l.add(r.getFriend_id());
					}
					result.put("friend_requests",l);
					return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK);
				}
			}
		}
		catch(Exception e) {
			return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
		}
		
		//return null;
	}

	public ResponseEntity<Map<String, Object>> listOfFriends(String userA) {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			List<UserFriends>friends=userfriends.listOfFriends(userA);
			User_Details u=createUser.findByUsername(userA);
			if(u==null) {
				result.put("status","failure");
				result.put("reason","explanation");
				return new ResponseEntity<Map<String,Object>>(result,HttpStatus.BAD_REQUEST);
			}
			if(friends==null) {
				result.put("status","failure");
				result.put("reason","explanation");
				return new ResponseEntity<Map<String,Object>>(result,HttpStatus.NOT_FOUND);
			}
			Iterator itr=friends.iterator();List<String> l=new ArrayList<String>();
			while(itr.hasNext()) {
				UserFriends f=(UserFriends) itr.next();
				l.add(f.getFriend_id());
			}
			result.put("friends",l);
			return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK);
		}
		catch(Exception e) {
			
		}
		
		return null;
	}

	public ResponseEntity<Map<String, Object>> showSuggestions(String userA) {
		// TODO Auto-generated method stub
		try {
		Map<String,Object> result=new HashMap<String,Object>(); 
		User_Details user1=createUser.findByUsername(userA);
		if(user1==null)
		{
			result.put("status","failure");
			result.put("reason","explanation");
			return new ResponseEntity<Map<String,Object>>(result,HttpStatus.BAD_REQUEST);
		}
		List<UserFriends> firstDegree =userfriends.listOfFriends(userA);
		if(firstDegree==null || firstDegree.isEmpty()) {
			result.put("status","failure");
			result.put("reason","explanation");
			return new ResponseEntity(result,HttpStatus.NOT_FOUND);
		}
		List<UserFriends> friendList= new ArrayList<UserFriends>();
		Iterator itr=firstDegree.iterator();List<String> l=new ArrayList<String>();
		while(itr.hasNext()) {
			UserFriends u=(UserFriends)itr.next();
			friendList.addAll(userfriends.listOfFriends(u.getFriend_id()));
			//friendList.remove(u);
			friendList=removeFromList(friendList,user1);
			List<UserFriends> secondDegree=userfriends.listOfFriends(u.getFriend_id());
			//secondDegree.remove(u);
			secondDegree=removeFromList(secondDegree,user1);
			Iterator itr2=secondDegree.iterator();
			while(itr2.hasNext())
			{
				UserFriends u2=(UserFriends)itr2.next();
				friendList.addAll(userfriends.listOfFriends(u2.getFriend_id()));
				//friendList.remove(u2);
				friendList=removeFromList(friendList,user1);
			}
			//friendList.addAll(userFriends.listOfFriends(u.getFriend_id()));
		}
		//friendList=removeFriendsFromList(friendList,firstDegree);
		List<String> firstDegreeFriends=new ArrayList<String>();
		Iterator itr1=firstDegree.iterator();
		while(itr1.hasNext()) {
			UserFriends u=(UserFriends)itr1.next();
			firstDegreeFriends.add(u.getFriend_id());
		}
		
		TreeSet<String> t=friendsSuggestions(friendList,firstDegreeFriends);
		if(t==null || t.isEmpty()) {
			result.put("status","failure");
			result.put("reason", "explanation");
			return new ResponseEntity<Map<String,Object>>(result,HttpStatus.NOT_FOUND);
		}
		result.put("suggestions", t);
		return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public TreeSet<String> friendsSuggestions(List friends,List firstDegreeFriends){
		
		TreeSet<String> t=new TreeSet<String>();
		
		Iterator itr=friends.iterator();
		while(itr.hasNext()) {
			
			UserFriends u=(UserFriends)itr.next();
			if(!firstDegreeFriends.contains(u.getFriend_id()))
			t.add(u.getFriend_id());
			
		}
		
		return t;
	}
	
	
	public List<UserFriends>  removeFromList(List<UserFriends> l,User_Details u  ){
		
		/*
		 * List<UserFriends> list=l; Iterator itr=l.iterator(); while(itr.hasNext() ) {
		 * 
		 * UserFriends f=(UserFriends)itr.next(); if(f.getFriend_id()==null) continue;
		 * if(f.getFriend_id().equals(u.getEmail())) l.remove(f); }
		 */
		for(UserFriends f: l) {
			if(f.getFriend_id().equals(u.getUsername()))
				l.remove(f);
			if(l.isEmpty())
				break;
		}
		return l;
	}
	
  
}
