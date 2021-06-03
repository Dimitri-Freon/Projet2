package com.ensta.librarymanager.servlet;

import java.io.*;
import java.time.LocalDate;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");
		

		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");

 		try { 
			request.setAttribute("livreDispo", LivreServiceImpl.getInstance().getListDispo() );			
			request.setAttribute("membreEmpruntPossible", MembreServiceImpl.getInstance().getListMembreEmpruntPossible() );			 
		} 
 		
		catch (ServiceException e) {
			e.printStackTrace();
  		}
 		
		dispatcher.forward(request, response);			
 		
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
		 
		
		try {
			if (request.getParameter("idMembre") != null && request.getParameter("idLivre") != null ) {
				EmpruntServiceImpl.getInstance().create(Integer.valueOf(request.getParameter("idMembre")), Integer.valueOf(request.getParameter("idLivre")), LocalDate.now() );
				response.sendRedirect("emprunt_list");
				return;				
			}
			
			
		} 
		catch (ServiceException e) {
			e.printStackTrace();
		}
		
		doGet(request,response);
			
	}	
	
}