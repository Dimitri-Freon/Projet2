package com.ensta.librarymanager.servlet;

import java.io.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

 
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");
		
		
		// display JSP
		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
		
		// send parameters 
 		try { 			
			request.setAttribute("nbLivres", LivreServiceImpl.getInstance().count() );
			request.setAttribute("nbMembres", MembreServiceImpl.getInstance().count() );
			request.setAttribute("nbEmprunts", EmpruntServiceImpl.getInstance().count() );
			request.setAttribute("getListCurrent", EmpruntServiceImpl.getInstance().getListCurrent() );
		} 
 		
		catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
  		}	
 	
		// send
		dispatcher.forward(request, response);
					
			
 		
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
		doGet(request,response);
	}

	
	
}