
package com.complaint.controller;

import java.io.IOException;

import com.complaint.dao.ComplaintDAO;
import com.complaint.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateAdminResponseServlet")
public class UpdateAdminResponseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("UNAUTHORIZED");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user.getRole() == null ||
                !"admin".equalsIgnoreCase(user.getRole())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("FORBIDDEN");
            return;
        }

        try {
            int complaintId = Integer.parseInt(
                    request.getParameter("complaintId"));

            String adminResponse = request.getParameter("adminResponse");

            if (complaintId <= 0 || adminResponse == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("INVALID_INPUT");
                return;
            }

            ComplaintDAO dao = new ComplaintDAO();

            boolean updated = dao.updateAdminResponse(
                    complaintId, adminResponse.trim());

            if (updated) {
                response.getWriter().write("SUCCESS");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("NOT_UPDATED");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("INVALID_ID");
        }
    }
}
