/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import loc.db.DBConnection;
import loc.dtos.CommentDTO;

/**
 *
 * @author hi
 */
public class CommentDAO {

    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public CommentDAO() {
    }

    public void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (pre != null) {
            pre.close();
        }
        if (conn != null) {
            conn.close();
        }

    }

    public String getCommentID() throws Exception {
        String count = null;
        try {
            String sql = "SELECT COUNT(ID)+1 as Count From tblComment";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                count = rs.getString("Count");
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return count;
    }

    public String getCommentDate(String id) throws Exception {
        String count = null;
        try {
            String sql = "SELECT Convert(varchar,date,0) From tblComment WHERE ID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            if (rs.next()) {
                count = rs.getString(1);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return count;
    }

    public String getCommentPostID(String id) throws Exception {
        String result = null;
        try {
            String sql = "SELECT postID From tblComment WHERE ID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<CommentDTO> getArticleComment(String id) throws Exception {
        List<CommentDTO> list = null;
        try {
            String sql = "SELECT ID, email, comment, Convert(varchar,date,0) FROM tblComment WHERE postID = ? AND status = 1 ";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            list = new ArrayList<CommentDTO>();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String email = rs.getString("email");
                String comment = rs.getString("comment");
                String date = rs.getString(4);
                list.add(new CommentDTO(ID, email, comment, date));

            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean updateCommentStatus(String ID, String email) throws Exception {
        boolean result = false;
        try {
            String sql = "UPDATE tblComment SET status = 0 WHERE ID = ? AND email = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, ID);

            pre.setString(2, email);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertComment(String id, String postID, String email, String comment) throws Exception {
        boolean result = false;
        try {
            String sql = "INSERT INTO tblComment(ID,postID,email,comment,date,status) VALUES(?,?,?,?,GETDATE(),1)";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            pre.setString(2, postID);
            pre.setString(3, email);
            pre.setString(4, comment);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }
}
