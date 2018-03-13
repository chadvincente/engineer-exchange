package com.engineerexchange.model;

import java.util.ArrayList;

public class Group {
	private String name;
	private int id;
	private ArrayList<Post> posts;
	private ArrayList<User> users;
	private String timestamp;
	private boolean joined;
	
	public Group() {
		super();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	
	
	
}
