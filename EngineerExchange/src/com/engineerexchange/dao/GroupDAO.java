package com.engineerexchange.dao;

import java.util.ArrayList;

import com.engineerexchange.model.Group;
import com.engineerexchange.model.Post;
import com.engineerexchange.model.User;

public interface GroupDAO {
	public int saveGroup(String name);
	
	public Group loadGroup(int groupID);
	
	public ArrayList<Group> loadGroups(int currentUsrID);
	
	public ArrayList<Post> loadPosts(int groupID, int currentUsrID);
	
	public ArrayList<User> loadUsers(int groupID);
	
	public void joinGroup(int groupID, int currentUsrID);
}
