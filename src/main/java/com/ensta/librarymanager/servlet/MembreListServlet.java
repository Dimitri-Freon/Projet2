package com.ensta.librarymanager.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;


@WebServlet("/membre_list")
public class MembreListServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");
		RequestDispatcher dispatcher = 	request.getRequestDispatcher("/WEB-INF/View/membre_list.jsp");
 		try { 	
 			request.setAttribute("ListMembre", MembreServiceImpl.getInstance().getList() );
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