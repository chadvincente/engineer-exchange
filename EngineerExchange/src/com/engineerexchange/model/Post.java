package com.engineerexchange.model;

import java.util.ArrayList;

public class Post {
	private String postTitle;
	private String postText;
	private int id;
	private User postedBy;
	private ArrayList<User> readBy;
	private int postScope;
	private int groupID;
	private String timestamp;
	private int numReads;
	private boolean ownPost;
	private boolean read;
	private String groupName;
	
	
	public Post() {
		super();
	}


	public String getPostText() {
		return postText;
	}


	public void setPostText(String postText) {
		this.postText = postText;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public User getPostedBy() {
		return postedBy;
	}


	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}


	public ArrayList<User> getReadBy() {
		return readBy;
	}


	public void setReadBy(ArrayList<User> readBy) {
		this.readBy = readBy;
	}


	public int getPostScope() {
		return postScope;
	}


	public void setPostScope(int postScope) {
		this.postScope = postScope;
	}


	public int getGroupID() {
		return groupID;
	}


	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}


	public String getPostTitle() {
		return postTitle;
	}


	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public int getNumReads() {
		return numReads;
	}


	public void setNumReads(int numReads) {
		this.numReads = numReads;
	}


	public boolean isOwnPost() {
		return ownPost;
	}


	public void setOwnPost(boolean ownPost) {
		this.ownPost = ownPost;
	}


	public boolean isRead() {
		return read;
	}


	public void setRead(boolean read) {
		this.read = read;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	
	
}
