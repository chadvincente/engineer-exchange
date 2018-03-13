package com.engineerexchange.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.engineerexchange.dao.GroupDAO;
import com.engineerexchange.model.Group;
import com.engineerexchange.model.Post;
import com.engineerexchange.model.User;
import com.engineerexchange.utils.Constants;
import com.engineerexchange.utils.DatabaseConnection;
import com.engineerexchange.utils.Utils;

public class GroupDAOImpl implements GroupDAO {


	@Override
	public int saveGroup(String name) {
		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		int grpID = 0;
		try
		{
			cs = c.prepareCall("{call SP_SAVE_GRP(?,?)}");
			cs.setString("grpName", name);
			cs.registerOutParameter("grpID", java.sql.Types.INTEGER);
			
			if(cs.executeUpdate() == 0)
			{
				cs.close();
				c.rollback();
			}
			else
			{
				grpID = cs.getInt("grpID");
				cs.close();
				c.commit();
			}
		}
		catch(SQLException e)
		{
			Logger.getGlobal().log(Level.SEVERE, "Error saving group", e);
		}
		finally
		{
			Utils.close(c, cs, null);
		}
		
		return grpID;
	}

	@Override
	public Group loadGroup(int groupID){
		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		Group g = new Group();
		try
		{
			cs = c.prepareCall("{call SP_GET_GRP(?)}");
			cs.setInt("grpID", groupID);
			ResultSet rs = cs.executeQuery();
			
			if(rs.next())
			{
				g.setName(rs.getString("GRP_NM"));
			}
		}
		catch(SQLException e){
			Logger.getGlobal().log(Level.SEVERE, "Error loading group", e);
		}
		finally
		{
			Utils.close(c, cs, null);
		}
		
		return g;
	}

	@Override
	public ArrayList<Post> loadPosts(int groupID, int currentUsrID) {
		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		ResultSet rs = null;
		ArrayList<Post> al = new ArrayList<Post>();
		try
		{
			cs = c.prepareCall("{call SP_GET_GRP_POSTS(?)}");
			cs.setInt("grpID", groupID);
			rs = cs.executeQuery();
			
			while (rs.next())
			{
				Post p = new Post();
				ArrayList<User> u = new ArrayList<User>();
				boolean read = false;
				p.setId(rs.getInt("POST_ID"));
				p.setPostTitle(rs.getString("POST_TITLE"));
				p.setPostText(rs.getString("POST_TXT"));
				p.setTimestamp(Constants.sdf.format(rs.getTimestamp("ROW_CRT_TS")) + " UTC");
				p.setPostedBy(new User(rs.getString("USR_NM"),rs.getInt("ROW_CRT_USR_ID"),rs.getString("USR_LOGIN")));
				
				CallableStatement cs2 = c.prepareCall("{call SP_GET_POST_READS(?)}");
				cs2.setInt("postID", p.getId());
				ResultSet rs2 = cs2.executeQuery();
				while(rs2.next())
				{
					User r = new User(rs2.getString("USR_NM"),rs2.getInt("USR_ID"),rs2.getString("USR_LOGIN"));
					u.add(r);
					if(r.getId() == currentUsrID)
					{
						read=true;
					}
				}
				cs2.close();
				rs2.close();
				
				if (rs.getInt("ROW_CRT_USR_ID") == currentUsrID)
				{
					p.setOwnPost(true);
				}
				else
				{
					p.setOwnPost(false);
				}
				p.setReadBy(u);
				p.setRead(read);
				p.setNumReads(u.size());
				al.add(p);
				
			}
		}
		catch(SQLException e){
			Logger.getGlobal().log(Level.SEVERE, "Error loading group posts", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
		return al;
	}

	@Override
	public ArrayList<User> loadUsers(int groupID) {
		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		ResultSet rs = null;
		ArrayList<User> al = new ArrayList<User>();
		try
		{
			cs = c.prepareCall("{call SP_GET_GRP_USRS(?)}");
			cs.setInt("grpID", groupID);
			rs = cs.executeQuery();
			
			while (rs.next())
			{
				User r = new User(rs.getString("USR_NM"),rs.getInt("USR_ID"),rs.getString("USR_LOGIN"));
				r.setTimestamp(Constants.sdf.format(rs.getTimestamp("ROW_CRT_TS")) + " UTC");
				al.add(r);
			}
		}
		catch(SQLException e){
			Logger.getGlobal().log(Level.SEVERE, "Error loading group members", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
		return al;
	}

	@Override
	public ArrayList<Group> loadGroups(int currentUsrID) {
		Connection c = DatabaseConnection.openDBConnection();
		ArrayList<Group> al = new ArrayList<Group>();
		CallableStatement cs = null;
		ResultSet rs = null;
		try
		{
			cs = c.prepareCall("{call SP_GET_GRPS()}");
			rs = cs.executeQuery();
			while(rs.next())
			{
				Group g = new Group();
				g.setName(rs.getString("GRP_NM"));
				g.setId(rs.getInt("GRP_ID"));
				g.setTimestamp(Constants.sdf.format(rs.getTimestamp("ROW_CRT_TS")) + " UTC");
				boolean joined = false;
				CallableStatement cs2 = c.prepareCall("{call SP_GET_GRP_USRS(?)}");
				cs2.setInt("grpID", g.getId());
				ResultSet rs2 = cs2.executeQuery();
				
				while (rs2.next())
				{
					if(rs2.getInt("USR_ID") == currentUsrID)
					{
						joined = true;
						break;
					}
				}
				
				rs2.close();
				cs2.close();
				
				g.setJoined(joined);
				
				al.add(g);
			}
			
		}
		catch(SQLException e){
			Logger.getGlobal().log(Level.SEVERE, "Error loading groups", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
	
		return al;
	}

	@Override
	public void joinGroup(int groupID, int currentUsrID) {
		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		try
		{
			cs = c.prepareCall("{call SP_JOIN_GRP(?,?)}");
			cs.setInt("grpID", groupID);
			cs.setInt("usrID", currentUsrID);
			
			if(cs.executeUpdate() == 0)
			{
				cs.close();
				c.rollback();
			}
			else
			{
				cs.close();
				c.commit();
			}
		}
		catch(SQLException e)
		{
			Logger.getGlobal().log(Level.SEVERE, "Error joining group", e);
		}
		finally
		{
			Utils.close(c, cs, null);
		}
		return;
	}

}
