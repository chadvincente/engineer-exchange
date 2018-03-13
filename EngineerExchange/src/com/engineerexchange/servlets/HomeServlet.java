package com.engineerexchange.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.engineerexchange.authentication.OAuth;
import com.engineerexchange.dao.UserDAO;
import com.engineerexchange.daoimpl.UserDAOImpl;
import com.engineerexchange.model.User;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(name = "/HomeServlet", value="/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("authUser");
		UserDAO userDAO = new UserDAOImpl();
		
		if (u == null)
		{
			String code = request.getParameter("code");
			if (code == null || "".equalsIgnoreCase(code))
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
				dispatcher.forward(request, response);
				return;
			}
			OAuth auth = new OAuth();
			User s = auth.getUser(code);
			u = userDAO.loadAuthUser(s.getName(), s.getLogin());
			//used for local testing
			//u = userDAO.loadAuthUser("John Doe", "testlogin");
		}
		u.setGroups(userDAO.loadGroups(u.getId()));
		u.setNumGroups(u.getGroups().size());
		u.setPosts(userDAO.loadPosts(u.getId(), u.getId()));
		u.setFeed(userDAO.loadFeed(u.getId()));
		request.setAttribute("user", u);
		request.getSession().setAttribute("authUser", u);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/home.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
