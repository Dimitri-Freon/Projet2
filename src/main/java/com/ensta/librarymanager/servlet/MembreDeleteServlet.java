package com.ensta.librarymanager.servlet;

import java.io.*;

import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;


 
@WebServlet("/membre_delete")
public class MembreDeleteServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");
		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
		
		
 		try { 	
 			request.setAttribute("membre", MembreServiceImpl.getInstance().getById( Integer.valueOf( request.getParameter("id" ))) );
 		} 
 		
		catch (ServiceException e) {
			e.printStackTrace();
  		}	

		dispatcher.forward(request, response);
						
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
		
		 
 		try {
			MembreServiceImpl.getInstance().delete( Integer.valueOf(request.getParameter("id") )) ;
			List<Emprunt> emprunts = EmpruntServiceImpl.getInstance().getListCurrentByMembre( Integer.valueOf( request.getParameter("id" )));
			for(Emprunt emprunt : emprunts ) {
				EmpruntServiceImpl.getInstance().returnBook(emprunt.getId());
			}				
			response.sendRedirect("membre_list");
			return;
		} 
		catch (ServiceException e) {
			e.printStackTrace();
		}
		doGet(request,response);
	}

	
	
}