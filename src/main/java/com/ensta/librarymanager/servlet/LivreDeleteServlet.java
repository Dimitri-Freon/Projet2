package com.ensta.librarymanager.servlet;

import java.io.*;

import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;

 
@WebServlet("/livre_delete")
public class LivreDeleteServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");
		
		 
		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
		
		
 		try { 	
 			request.setAttribute("livre", LivreServiceImpl.getInstance().getById( Integer.valueOf( request.getParameter("id" ))) );
 		} 
 		
		catch (ServiceException e) {
			e.printStackTrace();
  		}	
 	
		dispatcher.forward(request, response);	
 		
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
	 
		
		try {
			LivreServiceImpl.getInstance().delete( Integer.valueOf(request.getParameter("id") )) ;
			
			List<Emprunt> emprunts = EmpruntServiceImpl.getInstance().getListCurrentByLivre( Integer.valueOf( request.getParameter("id" )));
			for(Emprunt emprunt : emprunts ) {
				EmpruntServiceImpl.getInstance().returnBook(emprunt.getId());
			}

			response.sendRedirect("livre_list");
			
			return;
		} 
		
		catch (ServiceException e) {
			e.printStackTrace();
		}
		
		doGet(request,response);
	}

	
	
}