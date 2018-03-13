package com.engineerexchange.dao;

import java.util.ArrayList;

import com.engineerexchange.model.Group;
import com.engineerexchange.model.Post;
import com.engineerexchange.model.User;

public interface UserDAO {
	
	public User loadUser(int userID);
	
	public ArrayList<User> loadUsers();
	
	public ArrayList<Post> loadPosts(int userID, int currentUsrID);
	
	public ArrayList<Post> loadFeed(int userID);
	
	public ArrayList<Group> loadGroups(int userID);

	User loadAuthUser(String name, String login);
	
}
