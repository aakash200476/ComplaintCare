
package com.complaint.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.complaint.dao.ComplaintDAO;
import com.complaint.model.Complaint;
import com.complaint.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ComplaintDAO complaintDAO;

    @Override
    public void init() throws ServletException {
        complaintDAO = new ComplaintDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        // Check whether the user is logged in
        HttpSession session = request.getSession(false);

        if (session == null || !(session.getAttribute("user")
                instanceof User)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("UNAUTHORIZED");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Allow only admin users
        if (user.getRole() == null
                || !"admin".equalsIgnoreCase(user.getRole().trim())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("FORBIDDEN");
            return;
        }

        try {
            List<Complaint> complaints =
                    complaintDAO.getAllComplaints();

            if (complaints.isEmpty()) {
                response.getWriter().write("NO_COMPLAINTS");
                return;
            }

            for (Complaint complaint : complaints) {

                response.getWriter().write(
                    complaint.getId() + "\t"
                    + complaint.getUserId() + "\t"
                    + complaint.getCategoryId() + "\t"
                    + clean(complaint.getTitle()) + "\t"
                    + clean(complaint.getDescription()) + "\t"
                    + clean(complaint.getStatus()) + "\t"
                    + clean(complaint.getAdminResponse())
                    		+ "\n"
                );
            }

        } catch (SQLException e) {
            getServletContext().log(
                    "Error loading complaints for Admin Dashboard", e);

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ERROR");
        }
    }

    // Keep the tab-separated response format consistent
    private String clean(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\t", " ")
                    .replace("\r", " ")
                    .replace("\n", " ");
    }
}
