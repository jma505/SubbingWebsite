package org.jmanderson.subbing;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jmanderson.subbing.hibernate.Users;

public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -112249888882038437L;

	/**
	 * Constructor of the object.
	 */
	public LogoutServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			validateUser(request);
		} catch (Exception e) {
			System.out.println("LogoutServlet Exception caught: " + e);
		}

//		forwardToPage(request, response, "index.jsp");
//		System.out.println(request.getContextPath());
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			validateUser(request);
		} catch (Exception e) {
			System.out.println("LogoutServlet Exception caught: " + e);
		}

//		forwardToPage(request, response, "index.jsp");
//		System.out.println(request.getContextPath());
		response.sendRedirect(request.getContextPath());	
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	/**
	 * Validates the username and password and puts the Organist object into the
	 * HttpSession
	 * 
	 * @param request
	 *            HttpRequest object provided to the Servlet
	 * @throws Exception
	 */
	private void validateUser(HttpServletRequest request) throws Exception {
		Users user = null;

		// Remove session objects
		HttpSession session = request.getSession();
		user = (Users) session.getAttribute("user");
		System.out.println("--User '" + user.getUsername()
				+ "' logged out in Subbing session " + session.getId() + " " + new Date().toString());
		session.removeAttribute("user");
		session.invalidate();
		return;
	}
}
