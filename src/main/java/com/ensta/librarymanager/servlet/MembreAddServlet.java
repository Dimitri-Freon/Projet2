package com.ensta.librarymanager.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;

@WebServlet("/membre_add")
public class MembreAddServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");

		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");

		dispatcher.forward(request, response);
					
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
		
		try {
			int id = MembreServiceImpl.getInstance().create(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("adresse"), request.getParameter("email"), request.getParameter("telephone"));
			response.sendRedirect("membre_details?id=" + id);
			return;
		} 
		catch (ServiceException e) {
			e.printStackTrace();
		}
		doGet(request,response);
	}	
	
}