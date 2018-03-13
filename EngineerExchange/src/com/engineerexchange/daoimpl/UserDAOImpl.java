package com.engineerexchange.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.engineerexchange.dao.UserDAO;
import com.engineerexchange.model.Group;
import com.engineerexchange.model.Post;
import com.engineerexchange.model.User;
import com.engineerexchange.utils.Constants;
import com.engineerexchange.utils.DatabaseConnection;
import com.engineerexchange.utils.Utils;

public class UserDAOImpl implements UserDAO {

	@Override
	public User loadAuthUser(String name,String login){

		Connection c = DatabaseConnection.openDBConnection();
		CallableStatement cs = null;
		ResultSet rs = null;
		User u = new User(name,login);
		
		try
		{
			cs = c.prepareCall("{call SP_GET_USR_BY_LOGIN(?)}");
			cs.setString("login", login);
			rs = cs.executeQuery();
			if(rs.next())
			{
				u.setId(rs.getInt("USR_ID"));
				CallableStatement cs2 = c.prepareCall("{call SP_UPDT_USR(?,?)}");
				cs2.setInt("usrID", u.getId());
				cs2.setString("usrName", name);
				if(cs2.executeUpdate() == 0)
				{
					cs2.close();
					c.rollback();
				}
				else
				{
					cs2.close();
					c.commit();
				}
			}
			else
			{
				CallableStatement cs2 = c.prepareCall("{call SP_SAVE_USR(?,?,?)}");
				cs2.setString("usrName", name);
				cs2.setString("usrLogin", login);
				cs2.registerOutParameter("usrID", java.sql.Types.INTEGER);
				if(cs2.executeUpdate() == 0)
				{
					cs2.close();
					c.rollback();
				}
				else
				{
					u.setId(cs2.getInt("usrID"));
					cs2.close();
					c.commit();
				}
			}

			
		}
		catch (SQLException e)
		{
			Logger.getGlobal().log(Level.SEVERE, "Error saving or updating user", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
		
		
		return u;
	}

	@Override
	public ArrayList<Post> loadPosts(int userID, int currentUsrID) {

		Connection c = DatabaseConnection.openDBConnection();
		ArrayList<Post> al = new ArrayList<Post>();
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try
		{
			cs = c.prepareCall("{call SP_GET_USR_POSTS(?)}");
			cs.setInt("usrID", userID);
			rs = cs.executeQuery();
			while(rs.next())
			{
				Post p = new Post();
				ArrayList<User> u = new ArrayList<User>();
				boolean read = false;
				p.setId(rs.getInt("POST_ID"));
				p.setPostTitle(rs.getString("POST_TITLE"));
				p.setPostText(rs.getString("POST_TXT"));
				p.setTimestamp(Constants.sdf.format(rs.getTimestamp("ROW_CRT_TS")) + " UTC");
				
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
				
				if (userID == currentUsrID)
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
		catch (SQLException e)
		{
			Logger.getGlobal().log(Level.SEVERE, "Error loading user posts", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
	
		return al;
	}

	@Override
	public ArrayList<Group> loadGroups(int userID) {

		Connection c = DatabaseConnection.openDBConnection();
		ArrayList<Group> al = new ArrayList<Group>();
		CallableStatement cs = null;
		ResultSet rs = null;
		try
		{
			cs = c.prepareCall("{call SP_GET_USR_GROUPS(?)}");
			cs.setInt("usrID", userID);
			rs = cs.executeQuery();
			while(rs.next())
			{
				Group g = new Group();
				g.setId(rs.getInt("GRP_ID"));
				g.setName(rs.getString("GRP_NM"));
				g.setTimestamp(Constants.sdf.format(rs.getTimestamp("ROW_CRT_TS")) + " UTC");
				al.add(g);
			}
		}
		catch (SQLException e)
		{
			Logger.getGlobal().log(Level.SEVERE, "Error loading user groups", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
		
		return al;
	}

	@Override
	public ArrayList<User> loadUsers() {
		Connection c = DatabaseConnection.openDBConnection();
		ArrayList<User> al = new ArrayList<User>();
		CallableStatement cs = null;
		ResultSet rs = null;
		try
		{
			cs = c.prepareCall("{call SP_GET_USRS()}");
			rs = cs.executeQuery();
			while(rs.next())
			{
				User r = new User(rs.getString("USR_NM"),rs.getInt("USR_ID"),rs.getString("USR_LOGIN"));
				r.setTimestamp(Constants.sdf.format(rs.getTimestamp("ROW_CRT_TS")) + " UTC");
				al.add(r);
			}
			
		}
		catch (SQLException e)
		{
			Logger.getGlobal().log(Level.SEVERE, "Error loading users", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
		return al;
	}

	@Override
	public User loadUser(int userID) {
		Connection c = DatabaseConnection.openDBConnection();
		User r = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try
		{
			cs = c.prepareCall("{call SP_GET_USR_BY_INT(?)}");
			cs.setInt("usrID",userID);
			rs = cs.executeQuery();
			if(rs.next())
			{
				r = new User(rs.getString("USR_NM"),userID,rs.getString("USR_LOGIN"));
			}
		}
		catch (SQLException e)
		{
			Logger.getGlobal().log(Level.SEVERE, "Error loading user", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
		
		return r;
	}

	@Override
	public ArrayList<Post> loadFeed(int userID) {
		Connection c = DatabaseConnection.openDBConnection();
		ArrayList<Post> al = new ArrayList<Post>();
		CallableStatement cs = null;
		ResultSet rs = null;
		try
		{
			cs = c.prepareCall("{call SP_GET_USR_FEED(?)}");
			cs.setInt("usrID", userID);
			rs = cs.executeQuery();
			while(rs.next())
			{
				Post p = new Post();
				ArrayList<User> u = new ArrayList<User>();
				boolean read = false;
				p.setId(rs.getInt("POST_ID"));
				p.setPostTitle(rs.getString("POST_TITLE"));
				p.setPostText(rs.getString("POST_TXT"));
				p.setTimestamp(Constants.sdf.format(rs.getTimestamp("ROW_CRT_TS")) + " UTC");
				p.setPostedBy(new User(rs.getString("USR_NM"),rs.getInt("ROW_CRT_USR_ID"),rs.getString("USR_LOGIN")));
				p.setPostScope(rs.getInt("POST_SCOPE_CD"));
				p.setGroupName(rs.getString("GRP_NM"));
				CallableStatement cs2 = c.prepareCall("{call SP_GET_POST_READS(?)}");
				cs2.setInt("postID", p.getId());
				ResultSet rs2 = cs2.executeQuery();
				while(rs2.next())
				{
					User r = new User(rs2.getString("USR_NM"),rs2.getInt("USR_ID"),rs2.getString("USR_LOGIN"));
					u.add(r);
					if(r.getId() == userID)
					{
						read=true;
					}
				}
				cs2.close();
				rs2.close();
				if (userID == rs.getInt("ROW_CRT_USR_ID"))
				{
					p.setOwnPost(true);
					
				}
				else
				{
					p.setOwnPost(false);
				}
				p.setRead(read);
				p.setReadBy(u);
				p.setNumReads(u.size());
				al.add(p);
			}
		}
		catch (SQLException e)
		{
			Logger.getGlobal().log(Level.SEVERE, "Error loading user posts", e);
		}
		finally
		{
			Utils.close(c, cs, rs);
		}
	
		return al;
	}


}
