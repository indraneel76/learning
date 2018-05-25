package com.bb.cust.bloomrentalweb.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bb.cust.bloomrentalweb.processor.ConsoleProcessor;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet(name = "/ConsoleServlet", urlPatterns = { "/showconsole",
		"/showlog" })
public class ConsoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getRequestURI().contains("showconsole")) {
			showConsole(request, response);
		} else if (request.getRequestURI().contains("showlog")) {
			showLog(request, response);
		}
	}

	public void showConsole(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disp = request
				.getRequestDispatcher("WEB-INF/views/console.jsp");
		disp.forward(request, response);
	}

	public void showLog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ConsoleProcessor proc = new ConsoleProcessor();
		proc.getLog(request);

		showConsole(request, response);
	}
}
