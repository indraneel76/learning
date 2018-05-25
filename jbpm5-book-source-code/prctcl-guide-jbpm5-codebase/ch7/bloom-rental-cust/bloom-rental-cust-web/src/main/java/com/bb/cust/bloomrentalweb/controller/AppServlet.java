package com.bb.cust.bloomrentalweb.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bb.cust.bloomrentalweb.processor.AppProcessor;

@WebServlet(name = "/AppServlet", urlPatterns = { "/showapp", "/submitapp" })
public class AppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getRequestURI().contains("showapp")) {
			showApp(request, response);
		} else if (request.getRequestURI().contains("submitapp")) {
			processApp(request, response);
		}
	}

	public void showApp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher disp = request
				.getRequestDispatcher("WEB-INF/views/app.jsp");
		disp.forward(request, response);
	}

	public void processApp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			AppProcessor processor = new AppProcessor();
			processor.processApp(request);
			response.sendRedirect("showapp");
		}
}
