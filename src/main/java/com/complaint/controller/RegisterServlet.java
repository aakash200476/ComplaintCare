package com.complaint.controller;

import java.io.IOException;

import com.complaint.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        
        boolean registered = userDAO.register(
                name.trim(),
                email.trim(),
                password
            );
        

response.setContentType("text/html;charset=UTF-8");

if (registered) {
    response.getWriter().println(
        "<!DOCTYPE html>"
        + "<html lang='en'>"
        + "<head>"
        + "<meta charset='UTF-8'>"
        + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
        + "<title>Registration Successful</title>"
        + "<style>"
        + "* { box-sizing: border-box; }"
        + "body { margin: 0; font-family: Arial, sans-serif; "
        + "background: #f0f4f8; min-height: 100vh; "
        + "display: flex; align-items: center; justify-content: center; "
        + "padding: 20px; }"
        + ".card { background: white; width: 100%; max-width: 450px; "
        + "padding: 40px 30px; text-align: center; "
        + "border-radius: 12px; box-shadow: 0 8px 25px rgba(0,0,0,0.10); }"
        + ".icon { width: 75px; height: 75px; line-height: 75px; "
        + "margin: 0 auto 20px; background: #e6f7ed; color: #1b9b59; "
        + "border-radius: 50%; font-size: 42px; font-weight: bold; }"
        + "h1 { color: #173b67; font-size: 27px; margin: 0 0 15px; }"
        + "p { color: #667085; font-size: 15px; line-height: 1.6; "
        + "margin-bottom: 28px; }"
        + ".btn { display: inline-block; background: #1769aa; "
        + "color: white; padding: 13px 25px; border-radius: 6px; "
        + "text-decoration: none; font-size: 16px; font-weight: bold; "
        + "transition: background 0.2s; }"
        + ".btn:hover { background: #124f83; }"
        + "</style>"
        + "</head>"
        + "<body>"
        + "<div class='card'>"
        + "<div class='icon'>&#10003;</div>"
        + "<h1>Registration Successful!</h1>"
        + "<p>Your account has been created successfully. "
        + "You can now log in to your account.</p>"
        + "<a class='btn' href='login.html'>Login Now</a>"
        + "</div>"
        + "</body>"
        + "</html>"
    );
} else {
    response.getWriter().println(
        "<!DOCTYPE html><html><head>"
        + "<meta charset='UTF-8'>"
        + "<title>Registration Failed</title>"
        + "</head><body>"
        + "<h2>Registration failed.</h2>"
        + "<p>This email may already be registered, "
        + "or a database error occurred.</p>"
        + "<a href='register.html'>Try Again</a>"
        + "</body></html>"
    );
}

    }
}