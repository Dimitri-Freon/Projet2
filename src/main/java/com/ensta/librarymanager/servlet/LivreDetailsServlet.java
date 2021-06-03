package com.ensta.librarymanager.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;

 
@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html"); 
	 
		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
		
		
 		try { 	
 			request.setAttribute("livre", LivreServiceImpl.getInstance().getById( Integer.valueOf( request.getParameter("id" ))) );
 			request.setAttribute("emprunteur", EmpruntServiceImpl.getInstance().getListCurrentByLivre( Integer.valueOf( request.getParameter("id" )))  );
 		} 
 		
		catch (ServiceException e) {
			e.printStackTrace();
  		}	
		dispatcher.forward(request, response);
					
			
 		
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
		
		 
		
		 
		try {
			LivreServiceImpl.getInstance().update(new Livre( Integer.valueOf(request.getParameter("id")), request.getParameter("titre"),request.getParameter("auteur"),request.getParameter("isbn") ) ) ;
		} 
		catch (ServiceException e) {
			e.printStackTrace();
		}
		doGet(request,response);
	}

	
	
}