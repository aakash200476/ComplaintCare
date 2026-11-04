package com.complaint.controller;

import java.io.IOException;

import com.complaint.dao.UserDAO;
import com.complaint.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
	
	public void init() throws ServletException {
		userDAO = new UserDAO();
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
        	response.sendRedirect(request.getContextPath() + "/login.html?error=empty");
                return;
        }
        
        try {
        	User user = userDAO.login(email.trim(), password);
        	
        	if(user != null) {
        		HttpSession session = request.getSession(true);
        		
        		session.setAttribute("user", user);
        		session.setAttribute("userId",user.getId());
        		session.setAttribute("userName", user.getName());
        		session.setAttribute("userEmail", user.getEmail());
        		session.setAttribute("userRole", user.getRole());
        		
        		if ("admin".equalsIgnoreCase(user.getRole())) {
        	        response.sendRedirect(request.getContextPath() + "/admin-dashboard.html");
        	    } else {
        	        response.sendRedirect(request.getContextPath() + "/user-dashboard.html");
        	    }
        	} else {
        	    response.sendRedirect(request.getContextPath() + "/login.html?error=invalid");
        	}
        }
        
           catch(Exception e) {
        	   getServletContext().log("Login Failed");
        	   
        	   response.sendRedirect(request.getContextPath() + "/login.html?error=invalid");
           }
	}
}