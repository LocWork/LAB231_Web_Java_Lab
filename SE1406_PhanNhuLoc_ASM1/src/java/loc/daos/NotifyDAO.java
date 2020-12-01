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
import loc.dtos.NotifyDTO;

/**
 *
 * @author hi
 */
public class NotifyDAO {

    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public NotifyDAO() {
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

    public String getNotifyID() throws Exception {
        String count = null;
        try {
            String sql = "SELECT COUNT(ID)+1 as Count From tblNotify";
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

    public List<NotifyDTO> getNotifyList(String id) throws Exception {
        List<NotifyDTO> list = null;
        try {
            String sql = "SELECT TOP(5) postID, email, Convert(varchar,date,0), type, date FROM tblNotify tblArticle  WHERE postID IN (SELECT tblArticle.postID FROM tblArticle WHERE tblArticle.email = ? AND tblArticle.status != 0) AND email != ? ORDER BY date DESC";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            pre.setString(2, id);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String postID = rs.getString("postID");
                String email = rs.getString("email");
                String date = rs.getString(3);
                String type = rs.getString("type");
                list.add(new NotifyDTO("blank", postID, email, date, type));
            }
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean insertNotify(NotifyDTO dto) throws Exception {
        boolean result = false;
        try {
            String sql = "INSERT INTO tblNotify(ID,postID, email, date, type) VALUES(?,?,?,?,?)";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dto.getId());
            pre.setString(2, dto.getPostID());
            pre.setString(3, dto.getEmail());
            pre.setString(4, dto.getDate());
            pre.setString(5, dto.getType());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public String getArticleTitle(String postID) throws Exception {
        String result = null;
        try {
            String sql = "SELECT title FROM tblArticle WHERE postID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, postID);
            rs = pre.executeQuery();
            if (rs.next()) {
                result = rs.getString("title");
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }
}
