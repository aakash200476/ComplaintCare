
package com.complaint.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.complaint.dao.ComplaintDAO;
import com.complaint.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DeleteComplaintServlet")
public class DeleteComplaintServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ComplaintDAO complaintDAO = new ComplaintDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        // Check logged-in user
        HttpSession session = request.getSession(false);

        if (session == null ||
                !(session.getAttribute("user") instanceof User)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("UNAUTHORIZED");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Get complaint ID
        String complaintIdParam = request.getParameter("complaintId");

        if (complaintIdParam == null || complaintIdParam.trim().isEmpty()) {
            response.getWriter().write("NOT_DELETED");
            return;
        }

        try {

            int complaintId = Integer.parseInt(complaintIdParam);

            if (complaintId <= 0) {
                response.getWriter().write("NOT_DELETED");
                return;
            }

            // Delete only the logged-in user's pending complaint
            boolean deleted = complaintDAO.deleteComplaint(
                    complaintId, user.getId()
            );

            if (deleted) {
                response.getWriter().write("SUCCESS");
            } else {
                response.getWriter().write("NOT_DELETED");
            }

        } catch (NumberFormatException e) {

            response.getWriter().write("NOT_DELETED");

        } catch (SQLException e) {

            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ERROR");
        }
    }
}
