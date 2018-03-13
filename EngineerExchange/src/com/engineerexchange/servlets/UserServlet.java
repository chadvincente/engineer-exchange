package com.engineerexchange.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.engineerexchange.dao.UserDAO;
import com.engineerexchange.daoimpl.UserDAOImpl;
import com.engineerexchange.model.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name = "/UserServlet", value = "/users", asyncSupported=true)
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
			dispatcher.forward(request, response);
			return;
		}
		
		UserDAO userDAO = new UserDAOImpl();
		
		//for ajax requests to populate user groups list
		if ("myGroups".equalsIgnoreCase(request.getParameter("param")))
		{
			Gson gson = new Gson();
			String json = gson.toJson(userDAO.loadGroups(authUser.getId()));
			response.setContentType("application/json");
			response.getWriter().write(json);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}
		
		String userIDStr = request.getParameter("id");
		
		//for requests to the users page
		if (userIDStr == null || "".equalsIgnoreCase(userIDStr))
		{
			request.setAttribute("items", userDAO.loadUsers());
			request.setAttribute("name", "USERS");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/search.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		//for requests to one's own blog, redirect back home
		else if (authUser.getId() == Integer.parseInt(userIDStr))
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/home");
			dispatcher.forward(request, response);
			return;
		}
		
		//for requests to another user's blog
		else
		{
			int userID = Integer.parseInt(userIDStr);
			User reqUser = userDAO.loadUser(userID);
			request.setAttribute("name", reqUser.getName().toUpperCase());
			request.setAttribute("isGroup", "false");
			request.setAttribute("posts", userDAO.loadPosts(userID, authUser.getId()));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/blog.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
