package com.ensta.librarymanager.servlet;

import java.io.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet("/emprunt_list")
public class EmpruntListServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");
		
	 
		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
		
 		try { 	
 			if (request.getParameter("show") == null ) {
 				request.setAttribute("ListEmprunt", EmpruntServiceImpl.getInstance().getListCurrent() );
 			}
 			else {
 				request.setAttribute("ListEmprunt", EmpruntServiceImpl.getInstance().getList() ); 				
 			}
		} 
 		
		catch (ServiceException e) {
			e.printStackTrace();
  		}	

		dispatcher.forward(request, response);
						
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException { 
		doGet(request,response);
	}

	
	
}