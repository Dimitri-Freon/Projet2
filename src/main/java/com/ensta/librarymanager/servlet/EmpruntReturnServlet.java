package com.ensta.librarymanager.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;



@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");		
		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
 		try { 
 			
 			if ( request.getParameter("id") != null ) {
 				EmpruntServiceImpl.getInstance().returnBook(Integer.valueOf(request.getParameter("id")));
 				response.sendRedirect("emprunt_list");
  			}
 			
 			else {
 				request.setAttribute("ListCurrentEmprunt", EmpruntServiceImpl.getInstance().getListCurrent() );
 				dispatcher.forward(request, response);
 			}
			
		} 
 		
		
 		catch (NumberFormatException e) {
			e.printStackTrace();
		}  		
 		
 		catch (ServiceException e) {
			e.printStackTrace();
		}		
			
 		
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		 
		try {
			EmpruntServiceImpl.getInstance().returnBook(Integer.valueOf( request.getParameter("id") ) );
			response.sendRedirect("livre_list");
		} 
		
		catch ( ServiceException e) {
			e.printStackTrace();
		}
		
		
		
		
 	}

	
	
}