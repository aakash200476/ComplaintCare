package com.complaint.controller;

import java.io.IOException;

import com.complaint.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if(name == null || email == null || password == null || name.isBlank() || email.isBlank() || password.length() < 8) {
        	response.sendRedirect(request.getContextPath() + "/register.html");
                return;
        }
        
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt(12));
        boolean registered = userDAO.register(
                name.trim(),
                email.trim(),
                hashedPassword
            );
        
        if(registered) {
        	response.sendRedirect(request.getContextPath()+ "/registration-success.html");
        }
        else {
        	response.sendRedirect(request.getContextPath()+ "/registration-failed.html");
        }
    }
}