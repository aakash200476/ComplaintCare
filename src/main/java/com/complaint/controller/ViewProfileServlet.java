package com.complaint.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.complaint.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ViewProfileServlet")
public class ViewProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        PrintWriter out = response.getWriter();

        // Check whether the user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("UNAUTHORIZED");
            return;
        }

        // Retrieve the logged-in user
        Object loggedInUser = session.getAttribute("user");

        if (!(loggedInUser instanceof User)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("UNAUTHORIZED");
            return;
        }

        User user = (User) loggedInUser;

        // Send details as three plain-text lines
        out.println(user.getName() == null ? "" : user.getName());
        out.println(user.getEmail() == null ? "" : user.getEmail());
        out.println(user.getRole() == null ? "" : user.getRole());

        out.flush();
    }
}