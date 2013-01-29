package org.jmanderson.subbing;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jmanderson.subbing.hibernate.Users;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -112249888882038437L;

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
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
			System.out.println("Processing login request for " + request.getParameter("username") + " " + new Date().toString());
			validateUser(request);
		} catch (Exception e) {
			System.out.println("LoginServlet Exception caught: " + e);
		}

//		forwardToPage(request, response, "index.jsp");
//		System.out.println(request.getContextPath());
		String redirectURL = response.encodeRedirectURL(request.getContextPath());
		response.sendRedirect(redirectURL);
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
			System.out.println("LoginServlet Exception caught: " + e);
		}

//		forwardToPage(request, response, "index.jsp");
//		System.out.println(request.getContextPath());
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));	
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

		String username;
		String password;
		username = request.getParameter("username");
		password = request.getParameter("password");

		if (username == null) {
			return;
		}
		if (password == null) {
			System.out.println("--NULL password received for " + username + " " + new Date().toString());
		}


		user = ValidateUser.validate(username);
		if (user == null) {
			System.out.println("--Invalid username: " + username + " " + new Date().toString());
			return;
		}
		if (!user.passwordMatches(password)) {
			System.out.println("--Invalid password for: " + username + " (" + password + ") " + new Date().toString());
			return;
		}
		if (!user.canLogin()) {
			System.out.println("--User is not allowed to login: " + username + " " + new Date().toString());
			return;
		}

		// Update the lastlogin field in the database
		DataUpdater.updateLastLogin(username);

		// Put the UserBean into the session scope for future use
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		System.out.println("--User '" + user.getUsername()
				+ "' logged on in Subbing session " + session.getId() + " " + new Date().toString());
		return;
	}
}
