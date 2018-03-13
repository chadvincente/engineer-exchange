package com.engineerexchange.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.engineerexchange.dao.PostDAO;
import com.engineerexchange.daoimpl.PostDAOImpl;
import com.engineerexchange.model.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet(name = "/PostServlet", value="/post", asyncSupported=true)
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PostDAO p = new PostDAOImpl();
		int postID = Integer.parseInt(request.getParameter("postID"));
		Gson gson = new Gson();
		String json = gson.toJson(p.loadReads(postID));
		response.setContentType("application/json");
		response.getWriter().write(json);
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User authUser = (User) request.getSession().getAttribute("authUser");
		String param = request.getParameter("param");
		PostDAO p = new PostDAOImpl();
		if("read".equalsIgnoreCase(param))
		{
			String postID = request.getParameter("postID");
			if(p.saveRead(authUser.getId(), Integer.parseInt(postID)))
			{
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				return;
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
		else if("post".equalsIgnoreCase(param))
		{
			String text = request.getParameter("newPostText");
			String title = request.getParameter("newPostTitle");
			int scope = Integer.parseInt(request.getParameter("scope"));
			String groupIDStr = request.getParameter("groupID");
			int groupID = (groupIDStr == null || "".equalsIgnoreCase(groupIDStr.trim()) ? 0 : Integer.parseInt(groupIDStr));
			int postID = p.savePost(text, title, authUser.getId(), scope, groupID, 0);
			if (postID == 0)
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
		else
		{
			return;
		}
		
	}

}
