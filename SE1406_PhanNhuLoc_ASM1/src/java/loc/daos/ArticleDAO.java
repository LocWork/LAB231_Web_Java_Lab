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
import java.util.Random;
import loc.db.DBConnection;
import loc.dtos.ArticleDTO;

/**
 *
 * @author hi
 */
public class ArticleDAO {

    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public ArticleDAO() {
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

    public String getArticleID() throws Exception {
        String result = null;
        try {
            String sql = "SELECT COUNT(postID)+1 as Count From tblArticle";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                result = rs.getString("Count");
            }
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public List<ArticleDTO> searchArticle(String search, int pageIndex, int pageSize) throws Exception {
        List<ArticleDTO> list = null;
        try {
            String sql = "exec SP_PhanNhuLoc_TestPaging ?,?,?,?,?,?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, pageIndex);
            pre.setInt(2, pageSize);
            pre.setString(3, "%" + search + "%");
            pre.setString(4, "%" + search + "%");
            pre.setString(5, "%" + search + "%");
            pre.setString(6, "%" + search + "%");
            rs = pre.executeQuery();
            list = new ArrayList<ArticleDTO>();
            while (rs.next()) {
                String postID = rs.getString("postID");
                String image = rs.getString("image");
                String title = rs.getString("title");
                String email = rs.getString("email");
                String description = rs.getString("description");
                int status = rs.getInt("status");
                String date = rs.getString(7);
                list.add(new ArticleDTO(postID, image, title, email, description, date, status));
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }

    public int countArticle(String search) throws Exception {
        int result = 0;
        try {
            String sql = "SELECT COUNT(postID) as number FROM tblArticle WHERE (title LIKE ? OR email LIKE ? OR description LIKE ? OR date LIKE ?) AND status != 0";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, "%" + search + "%");
            pre.setString(2, "%" + search + "%");
            pre.setString(3, "%" + search + "%");
            pre.setString(4, "%" + search + "%");
            rs = pre.executeQuery();
            if (rs.next()) {
                result = rs.getInt("number");
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public ArticleDTO getArticle(String postID) throws Exception {
        ArticleDTO result = null;
        try {
            String sql = "SELECT image,title,email,description,status,Convert(varchar,date,0) FROM tblArticle WHERE postID = ? AND status != 2";

            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, postID);

            rs = pre.executeQuery();
            while (rs.next()) {
                String image = rs.getString("image");
                String title = rs.getString("title");
                String email = rs.getString("email");
                String description = rs.getString("description");
                int status = rs.getInt("status");
                String date = rs.getString(6);
                result = new ArticleDTO(postID, image, title, email, description, date, status);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public String randomID() throws Exception {
        StringBuilder buildString = new StringBuilder();
        String alpha = "ABCDEFGHIJKLMNOBQRSTUVWSYZ123456789";
        String id = "";
        Random rand = new Random();
        do {
            for (int i = 0; i < 5; i++) {
                int x = rand.nextInt(35) + 1;
                buildString.append(alpha.charAt(x));
            }
            id = buildString.toString();
        } while (checkPostIDExist(id) == true);
        closeConnection();
        return id;
    }

    public boolean checkPostIDExist(String id) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT date FROM tblArticle WHERE postID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertNewArticle(ArticleDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "INSERT into tblArticle(postID,image,title,email,description,status,date) VALUES(?,?,?,?,?,1,GETDATE())";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dto.getPostID());
            pre.setString(2, dto.getImage());
            pre.setString(3, dto.getTitle());
            pre.setString(4, dto.getEmail());
            pre.setString(5, dto.getDescription());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateArticleStatus(String postID, String email) throws Exception {
        boolean result = false;
        try {
            String sql = "UPDATE tblArticle SET status = 0 WHERE postID = ? AND email = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, postID);
            pre.setString(2, email);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

}
