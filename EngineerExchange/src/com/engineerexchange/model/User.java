package com.engineerexchange.model;

import java.util.ArrayList;

public class User {
	private String name;
	private int id;
	private String login;
	private ArrayList<Group> groups;
	private ArrayList<Post> posts;
	private ArrayList<Post> feed;
	private String timestamp;
	private int numGroups;
	
	public User(String name, String login) {
		super();
		this.name = (name == null || "".equalsIgnoreCase(name.trim()) ? login : name);
		this.login = login;
	}
	
	public User(String name, int userID, String login) {
		super();
		this.name = (name == null || "".equalsIgnoreCase(name.trim()) ? login : name);
		this.id=userID;
		this.login = login;
	}

	public User() {
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

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public ArrayList<Post> getFeed() {
		return feed;
	}

	public void setFeed(ArrayList<Post> feed) {
		this.feed = feed;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getNumGroups() {
		return numGroups;
	}

	public void setNumGroups(int numGroups) {
		this.numGroups = numGroups;
	}
	
	
}
