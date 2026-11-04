
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

@WebServlet("/UpdateComplaintStatusServlet")
public class UpdateComplaintStatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ComplaintDAO complaintDAO;

    @Override
    public void init() throws ServletException {
        complaintDAO = new ComplaintDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        // Verify the logged-in user
        HttpSession session = request.getSession(false);

        if (session == null ||
                !(session.getAttribute("user") instanceof User)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("UNAUTHORIZED");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Only admins may update complaint status
        if (user.getRole() == null ||
                !"admin".equalsIgnoreCase(user.getRole().trim())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("FORBIDDEN");
            return;
        }

        String idValue = request.getParameter("complaintId");
        String status = request.getParameter("status");

        // Validate the complaint ID
        int complaintId;

        try {
            complaintId = Integer.parseInt(idValue);
            if (complaintId <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("INVALID_ID");
                return;
            }
        } catch (NumberFormatException | NullPointerException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("INVALID_ID");
            return;
        }

        // Allow only predefined statuses
        if (status == null ||
                !(status.equals("Pending")
                || status.equals("In Progress")
                || status.equals("Resolved"))) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("INVALID_STATUS");
            return;
        }

        try {
            boolean updated = complaintDAO.updateComplaintStatus(
                    complaintId, status);

            if (updated) {
                response.getWriter().write("SUCCESS");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("NOT_FOUND");
            }

        } catch (SQLException e) {
            getServletContext().log("Failed to update complaint status", e);
            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ERROR");
        }
    }
}
