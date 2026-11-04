package com.complaint.controller;

import java.io.IOException;
import com.complaint.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CurrentUserNameServlet")
public class CurrentUserNameServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
           @Override
           protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        	    response.setContentType("text/plain");
        	    response.setCharacterEncoding("UTF-8");
        	    
        	    HttpSession session = request.getSession(false);
        	    
        	    if(session == null) {
        	    	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please log in first.");
        	    	return;
        	    }
        	    
        	    Object userObject = session.getAttribute("user");
        	    
        	    if (!(userObject instanceof User)) {
        	    	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User session not found.");
        	    	return;
        	    }
        	    
        	    User user = (User) userObject;
        	    
        	    response.getWriter().write(user.getName()); 
        	    }
}
