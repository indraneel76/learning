package com.bb.bloomrentalweb.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bb.bloomrentalweb.processor.AdminProcessor;

@WebServlet(name = "/AdminServlet", urlPatterns = { "/showadmin",
		"/submitadmin", "/completetask" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getRequestURI().contains("showadmin")) {
			showAdmin(request, response);
		} else if (request.getRequestURI().contains("submitadmin")) {
			submitAdmin(request, response);
		} else if (request.getRequestURI().contains("completetask")) {
			completeTask(request, response);
		}

	}

	public void showAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disp = request
				.getRequestDispatcher("WEB-INF/views/admin.jsp");
		disp.forward(request, response);
	}

	public void submitAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AdminProcessor proc = new AdminProcessor();
		proc.getTasks(request);
		RequestDispatcher disp = request
				.getRequestDispatcher("WEB-INF/views/reviewTask.jsp");
		disp.forward(request, response);
	}

	public void completeTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AdminProcessor proc = new AdminProcessor();
		proc.completeTask(request);
		RequestDispatcher disp = request
				.getRequestDispatcher("WEB-INF/views/reviewTask.jsp");
		disp.forward(request, response);
	}

}
