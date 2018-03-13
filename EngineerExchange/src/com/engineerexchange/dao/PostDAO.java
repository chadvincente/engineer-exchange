package com.engineerexchange.dao;

import java.util.ArrayList;

import com.engineerexchange.model.User;

public interface PostDAO {
	
	public int savePost(String text, String title, int postedBy, int postScope, int groupID, int toUsrID);
	
	public boolean saveRead(int currentUsrID, int postID);
	
	public ArrayList<User> loadReads(int postID);
	
}
