package com.ensta.librarymanager.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;



@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");

		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");		

		dispatcher.forward(request, response);
					
			
 		
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
		 
		try {
			int id = LivreServiceImpl.getInstance().create(request.getParameter("titre"), request.getParameter("auteur"), request.getParameter("isbn") );
			response.sendRedirect("livre_details?id=" + id);
			return;
		} 
		
		catch (ServiceException e) {
			e.printStackTrace();
		}
		
		doGet(request,response);
		
	}

	
	
}