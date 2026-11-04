
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

@WebServlet("/AdminStatisticsServlet")
public class AdminStatisticsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");

        HttpSession session = request.getSession(false);
        User user = (session == null)
                ? null
                : (User) session.getAttribute("user");

        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Admin access required.");
            return;
        }

        ComplaintDAO dao = new ComplaintDAO();

        int total = 0;
		try {
			total = dao.getAllComplaints().size();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int pending = dao.getComplaintCountByStatus("Pending");
        int inProgress = dao.getComplaintCountByStatus("In Progress");
        int resolved = dao.getComplaintCountByStatus("Resolved");
       
        response.getWriter().println(total);
        response.getWriter().println(pending);
        response.getWriter().println(inProgress);
        response.getWriter().println(resolved);
            }
}
