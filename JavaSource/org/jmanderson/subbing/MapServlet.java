package org.jmanderson.subbing;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmanderson.subbing.hibernate.Location;

public class MapServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public MapServlet() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <BODY>");
		String locid = request.getParameter("id");
		out.print(getScript(locid));
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	private String getScript(String locid) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		Location location = DataPreparer.getLocation(Integer.parseInt(locid),
				"");
		if (location != null && location.getAddress().length() > 0) {
			sb.append("<script src=\"http://gmodules.com/ig/ifr");
			sb
					.append("?url=http://ralph.feedback.googlepages.com/googlemap.xml");
			sb.append("&up_locname=").append(
					EncodeDecode.encode(location.getName()));
			sb2.append(location.getAddress()).append(", ");
			String zip = location.getZipDisplay();
			if (zip.length() > 0) {
				sb2.append(zip);
			} else {
				sb2.append(location.getCity()).append(", ").append(
						location.getState());
			}
			sb.append("&up_loc=").append(EncodeDecode.encode(sb2.toString()));
			sb
					.append("&up_zoom=Main%20Street&up_view=Map&synd=open&w=320&h=320");
			sb.append("&title=Google+Map&border=%23ffffff%7C3px%2C1px+solid");
			sb.append("+%23999999&output=js\">");
		} else {
			sb.append("<h4>Unable to get Location information</h4>");
		}

		return sb.toString();
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
