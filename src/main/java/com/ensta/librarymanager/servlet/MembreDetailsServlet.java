package com.ensta.librarymanager.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ensta.librarymanager.model.EnumAbo;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;


 
@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");		 
		
		// display JSP
		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
		
		
 		try { 	
 			request.setAttribute("membre", MembreServiceImpl.getInstance().getById( Integer.valueOf( request.getParameter("id" ))) );
 			request.setAttribute("emprunts", EmpruntServiceImpl.getInstance().getListCurrentByMembre( Integer.valueOf( request.getParameter("id" ))) );
 		} 
 		
		catch (ServiceException e) {
			e.printStackTrace();
  		}	
		dispatcher.forward(request, response);
					
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
		
		 
 		try {
			MembreServiceImpl.getInstance().update(new Membre( Integer.valueOf(request.getParameter("id")) , request.getParameter("nom"), request.getParameter("prenom"),request.getParameter("adresse"),request.getParameter("email"), request.getParameter("telephone"), EnumAbo.valueOf( request.getParameter("abonnement")) )) ;
		} 
		catch (ServiceException e) {
			e.printStackTrace();
		}
		doGet(request,response);
	}
}