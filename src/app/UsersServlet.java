
package app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/usuarios", "/HiWorld", "/ItsASmallWorld" })
public class UsersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public UsersServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			                 HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");

		PrintWriter out = response.getWriter();
		out.print("Servlet GET");
		out.close();
	}

	protected void doPost(HttpServletRequest request, 
			                  HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");

		PrintWriter out = response.getWriter();
		out.print("Servlet POST");
		out.close();
	}

	protected void doPut(HttpServletRequest request, 
			                 HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");

		PrintWriter out = response.getWriter();
		out.print("Servlet PUT");
		out.close();
	}

	protected void doDelete(HttpServletRequest request, 
			                    HttpServletResponse response)	throws ServletException, IOException {

		response.setContentType("text/plain");

		PrintWriter out = response.getWriter();
		try {
			out.print("Servlet DELETE");
		} finally {
			out.close();
		}

	}
}