package com.complaint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.complaint.model.Complaint;
import com.complaint.util.DBConnection;

public class ComplaintDAO {

    public int addComplaint(Complaint complaint) throws SQLException {

        String sql = "INSERT INTO complaints "
                + "(user_id, category_id, title, description) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, complaint.getUserId());
            statement.setInt(2, complaint.getCategoryId());
            statement.setString(3, complaint.getTitle());
            statement.setString(4, complaint.getDescription());

            int rows = statement.executeUpdate();

            if (rows == 0) {
                return -1;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        return -1;
    }
    
 // Get all complaints submitted by a particular user
    public List<Complaint> getComplaintsByUserId(int userId)
            throws SQLException {

        List<Complaint> complaints = new ArrayList<>();

        String sql = "SELECT id, user_id, category_id, title, "
                + "description, status, admin_response "
                + "FROM complaints "
                + "WHERE user_id = ? "
                + "ORDER BY id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Complaint complaint = new Complaint();

                    complaint.setId(resultSet.getInt("id"));
                    complaint.setUserId(resultSet.getInt("user_id"));
                    complaint.setCategoryId(
                            resultSet.getInt("category_id"));
                    complaint.setTitle(resultSet.getString("title"));
                    complaint.setDescription(
                            resultSet.getString("description"));
                    complaint.setStatus(resultSet.getString("status"));
                    complaint.setAdminResponse(resultSet.getString("admin_response"));

                    complaints.add(complaint);
                }
            }
        }

        return complaints;
    }
    

    // Get all complaints for the admin Dashboard
    public List<Complaint> getAllComplaints() throws SQLException {

        List<Complaint> complaints = new ArrayList<>();

        String sql = "SELECT id, user_id, category_id, title, "
                + "description, status, admin_response "
                + "FROM complaints "
                + "ORDER BY id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Complaint complaint = new Complaint();

                complaint.setId(resultSet.getInt("id"));
                complaint.setUserId(resultSet.getInt("user_id"));
                complaint.setCategoryId(
                        resultSet.getInt("category_id"));
                complaint.setTitle(resultSet.getString("title"));
                complaint.setDescription(
                        resultSet.getString("description"));
                complaint.setStatus(
                		resultSet.getString("status"));
                complaint.setAdminResponse(
                        resultSet.getString("admin_response"));
                
                complaints.add(complaint);
            }
        }

        return complaints;
    }
    

    // Update complaint status
    public boolean updateComplaintStatus(int complaintId, String status)
            throws SQLException {

    	String sql = "UPDATE complaints SET status = ?, "
    	        + "updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, complaintId);

            int rows = statement.executeUpdate();

            return rows > 0;
        }
    }
    
    public boolean updateAdminResponse(int complaintId, String adminResponse) {
        String sql = "UPDATE complaints "
                   + "SET admin_response = ?, updated_at = CURRENT_TIMESTAMP "
                   + "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, adminResponse);
            ps.setInt(2, complaintId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


public int getComplaintCountByStatus(String status) {
    int count = 0;

    String sql = "SELECT COUNT(*) FROM complaints WHERE status = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, status);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return count;
}


// Delete a complaint submitted by a particular user
public boolean deleteComplaint(int complaintId, int userId)
        throws SQLException {

    String sql = "DELETE FROM complaints "
               + "WHERE id = ? "
               + "AND user_id = ? "
               + "AND status = 'Pending'";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, complaintId);
        statement.setInt(2, userId);

        return statement.executeUpdate() > 0;
    }
}

}
