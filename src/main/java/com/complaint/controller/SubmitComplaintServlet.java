
package com.complaint.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.complaint.dao.ComplaintDAO;
import com.complaint.model.Complaint;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SubmitComplaintServlet")
public class SubmitComplaintServlet extends HttpServlet {

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

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Ensure the user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.html?error=login_required"
            );
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryValue = request.getParameter("categoryId");

        if (title == null || title.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || categoryValue == null || categoryValue.trim().isEmpty()) {

            response.sendRedirect(
                request.getContextPath()
                    + "/submit-complaint.html?error=empty"
            );
            return;
        }

        try {
            int categoryId = Integer.parseInt(categoryValue);

            Object userIdValue = session.getAttribute("userId");

            if (!(userIdValue instanceof Number)) {
                response.sendRedirect(
                    request.getContextPath() + "/login.html?error=session"
                );
                return;
            }

            int userId = ((Number) userIdValue).intValue();

            if (categoryId <= 0 || userId <= 0
                    || title.trim().length() > 200) {
                response.sendRedirect(
                    request.getContextPath()
                        + "/submit-complaint.html?error=invalid"
                );
                return;
            }

            Complaint complaint = new Complaint();

            complaint.setUserId(userId);
            complaint.setCategoryId(categoryId);
            complaint.setTitle(title.trim());
            complaint.setDescription(description.trim());

            int complaintId = complaintDAO.addComplaint(complaint);

            if (complaintId > 0) {
                response.sendRedirect(
                    request.getContextPath()
                        + "/submit-complaint.html?success=1"
                );
            } else {
                response.sendRedirect(
                    request.getContextPath()
                        + "/submit-complaint.html?error=save"
                );
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(
                request.getContextPath()
                    + "/submit-complaint.html?error=invalid"
            );

        } catch (SQLException e) {
            getServletContext().log("Unable to save complaint", e);

            response.sendRedirect(
                request.getContextPath()
                    + "/submit-complaint.html?error=save"
            );
        }
    }
}
