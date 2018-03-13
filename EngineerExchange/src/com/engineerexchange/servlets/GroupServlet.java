package com.engineerexchange.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.engineerexchange.dao.GroupDAO;
import com.engineerexchange.daoimpl.GroupDAOImpl;
import com.engineerexchange.model.Group;
import com.engineerexchange.model.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet(name = "/GroupServlet", value="/groups", asyncSupported=true)
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User authUser = (User) request.getSession().getAttribute("authUser");
		//check if user is authenticated and in the session
		if (authUser == null)
		{
			response.sendRedirect("/index");
			return;
		}
		
		String groupIDStr = request.getParameter("id");
		GroupDAO group = new GroupDAOImpl();
		
		//for ajax request to populate group members list
		if("getMembers".equalsIgnoreCase(request.getParameter("param")))
		{
			Gson gson = new Gson();
			String json = gson.toJson(group.loadUsers(Integer.parseInt(groupIDStr)));
			response.setContentType("application/json");
			response.getWriter().write(json);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}
		
		//for requests to the groups page
		if (groupIDStr == null || "".equalsIgnoreCase(groupIDStr))
		{
			request.setAttribute("items", group.loadGroups(authUser.getId()));
			request.setAttribute("name", "GROUPS");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/search.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//for requests to an individual group blog
		else
		{
			int groupID = Integer.parseInt(groupIDStr);
			Group g = group.loadGroup(groupID);
			request.setAttribute("posts",group.loadPosts(groupID, authUser.getId()));
			g.setUsers(group.loadUsers(groupID));
			request.setAttribute("numMembers", g.getUsers().size());
			request.setAttribute("isGroup", "true");
			request.setAttribute("id", groupID);
			request.setAttribute("name", g.getName().toUpperCase());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/blog.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User authUser = (User) request.getSession().getAttribute("authUser");
		String param = request.getParameter("param");
		GroupDAO g = new GroupDAOImpl();
		if("add".equalsIgnoreCase(param))
		{
			int groupID = g.saveGroup(request.getParameter("newGroupName"));
			if(groupID == 0)
			{
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				return;
			}
		}
		else if ("join".equalsIgnoreCase(param))
		{
			int groupID = Integer.parseInt(request.getParameter("groupID"));
			g.joinGroup(groupID, authUser.getId());
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}
		
	}

}
