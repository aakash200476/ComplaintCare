
package com.complaint.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/MyComplaintServlet")
public class MyComplaintServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();

        // Check login session
        if (session == null ||
            !(session.getAttribute("user") instanceof User)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("UNAUTHORIZED");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            ComplaintDAO dao = new ComplaintDAO();

            // Fetch all complaints belonging to the logged-in user
            List<Complaint> complaints =
                    dao.getComplaintsByUserId(user.getId());
            
            System.out.println("Logged-in User ID: " + user.getId());
            System.out.println("Complaints fetched: " + complaints.size());

            for (Complaint c : complaints) {
                System.out.println(
                    "Complaint ID: " + c.getId()
                    + ", Title: " + c.getTitle()
                );
            }

            if (complaints.isEmpty()) {
                out.print("NO_COMPLAINTS");
                return;
            }

            // Output six tab-separated fields per complaint
            for (Complaint c : complaints) {
                out.print(clean(String.valueOf(c.getId())));
                out.print("\t");

                out.print(clean(c.getTitle()));
                out.print("\t");

                out.print(clean(String.valueOf(c.getCategoryId())));
                out.print("\t");

                out.print(clean(c.getDescription()));
                out.print("\t");

                out.print(clean(c.getStatus()));
                out.print("\t");

                out.print(clean(c.getAdminResponse()));

                // End the complete complaint record
                out.print("\n");
            }

        } catch (Exception e) {
            getServletContext().log(
                    "Error loading user complaints", e);

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("ERROR");
        }
    }

    // Prevent tabs and line breaks from breaking the output format
    private String clean(String value) {
        if (value == null) {
            return "";
        }

        return value.replace('\t', ' ')
                    .replace('\r', ' ')
                    .replace('\n', ' ');
    }
}
