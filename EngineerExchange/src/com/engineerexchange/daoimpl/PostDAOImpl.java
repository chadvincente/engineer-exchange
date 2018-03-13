package com.engineerexchange.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.engineerexchange.dao.PostDAO;
import com.engineerexchange.model.User;
import com.engineerexchange.utils.Constants;
import com.engineerexchange.utils.DatabaseConnection;
import com.engineerexchange.utils.Utils;

public class PostDAOImpl implements PostDAO {

	@Override
	public int savePost(String text, String title, int postedBy, int postScope, int groupID, int toUsrID){
		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		int postID = 0;
		try
		{
			cs = c.prepareCall("{call SP_SAVE_POST(?,?,?,?,?,?,?)}");
			cs.setString("postText", text);
			cs.setString("title", title);
			cs.setInt("crtUsrID", postedBy);
			cs.setInt("scope", postScope);
			cs.setInt("grpID", (groupID == 0 ? java.sql.Types.NULL : groupID));
			cs.setInt("toUsrID", (toUsrID == 0 ? java.sql.Types.NULL : toUsrID));
			cs.registerOutParameter("postID", java.sql.Types.INTEGER);
			
			if(cs.executeUpdate() == 0)
			{
				cs.close();
				c.rollback();
			}
			else
			{
				postID = cs.getInt("postID");
				cs.close();
				c.commit();
			}
		}
		catch(SQLException e){
			Logger.getGlobal().log(Level.SEVERE, "Error saving post", e);
		}
		finally
		{
			Utils.close(c, cs, null);
		}
		return postID;
	}

	@Override
	public boolean saveRead(int currentUsrID, int postID) {
		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		boolean ret = false;
		try
		{
			cs = c.prepareCall("{call SP_SAVE_READ(?,?)}");
			cs.setInt("usrID", currentUsrID);
			cs.setInt("postID", postID);
			
			if(cs.executeUpdate() == 0)
			{
				cs.close();
				c.rollback();
			}
			else
			{
				cs.close();
				c.commit();
				ret = true;
			}
		}
		catch(SQLException e){
			Logger.getGlobal().log(Level.SEVERE, "Error saving read", e);
		}
		finally
		{
			Utils.close(c, cs, null);
		}
		return ret;
		
	}

	@Override
	public ArrayList<User> loadReads(int postID) {
		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		ResultSet rs = null;
		ArrayList<User> u = new ArrayList<User>();
		try
		{
			cs = c.prepareCall("{call SP_GET_POST_READS(?)}");
			cs.setInt("postID", postID);
			rs = cs.executeQuery();
			while(rs.next())
			{
				User r = new User(rs.getString("USR_NM"),rs.getInt("USR_ID"),rs.getString("USR_LOGIN"));
				r.setTimestamp(Constants.sdf.format(rs.getTimestamp("ROW_CRT_TS")) + " UTC");
				u.add(r);
			}
		}
		catch(SQLException e){
			Logger.getGlobal().log(Level.SEVERE, "Error saving read", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
		return u;
	}



}
