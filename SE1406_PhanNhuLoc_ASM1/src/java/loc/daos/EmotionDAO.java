/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import loc.db.DBConnection;

/**
 *
 * @author hi
 */
public class EmotionDAO {

    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public EmotionDAO() {
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

    public String getEmotionDate(String id, String email) throws Exception {
        String count = null;
        try {
            String sql = "SELECT Convert(varchar,date,0) From tblEmotion WHERE postID = ? AND email = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            pre.setString(2, email);
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

    public String getEmotionID() throws Exception {
        String count = null;
        try {
            String sql = "SELECT COUNT(ID)+1 as Count From tblEmotion";
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

    public int getLikeCount(String id) throws Exception {
        int result = 0;
        try {
            String sql = "SELECT ID FROM tblEmotion WHERE postID = ? and isLike = 'true'";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            while (rs.next()) {
                result++;
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getDislikeCount(String id) throws Exception {
        int result = 0;
        try {
            String sql = "SELECT ID FROM tblEmotion WHERE postID = ? and disLike = 'true'";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            while (rs.next()) {
                result++;
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean getLikeStatus(String postID, String email) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT isLike FROM tblEmotion WHERE postID = ? AND email = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, postID);
            pre.setString(2, email);
            rs = pre.executeQuery();
            if (rs.next()) {
                int status = rs.getByte("isLike");
                if (status == 1) {
                    result = true;
                } else {
                    result = false;
                }

            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean getDisLikeStatus(String postID, String email) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT disLike FROM tblEmotion WHERE postID = ? AND email = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, postID);
            pre.setString(2, email);
            rs = pre.executeQuery();
            if (rs.next()) {
                int status = rs.getByte("disLike");
                if (status == 1) {
                    result = true;
                } else {
                    result = false;
                }

            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateLike(boolean likeStatus, boolean dislikeStatus, String postID, String email) throws Exception {
        boolean result = false;
        try {
            String sql = null;
            if (likeStatus == true && dislikeStatus == false) {
                sql = "UPDATE tblEmotion SET isLike = 0, date = GETDATE() WHERE postID = ? AND email = ?";
            } else if (likeStatus == false && dislikeStatus == true) {
                sql = "UPDATE tblEmotion SET isLike = 1, disLike = 0, date = GETDATE() WHERE postID = ? AND email = ?";
            } else if (likeStatus == false && dislikeStatus == false) {
                sql = "UPDATE tblEmotion SET isLike = 1, date = GETDATE() WHERE postID = ? AND email = ?";
            } else {
                sql = "UPDATE tblEmotion SET isLike = 0, disLike = 0, date = GETDATE() WHERE postID = ? AND email = ?";
            }
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

    public boolean updateDisLike(boolean likeStatus, boolean dislikeStatus, String postID, String email) throws Exception {
        boolean result = false;
        try {
            String sql = null;
            if (likeStatus == true && dislikeStatus == false) {
                sql = "UPDATE tblEmotion SET disLike = 1, isLike = 0, date = GETDATE() WHERE postID = ? AND email = ?";
            } else if (likeStatus == false && dislikeStatus == true) {
                sql = "UPDATE tblEmotion SET disLike = 0, date = GETDATE() WHERE postID = ? AND email = ?";
            } else if (likeStatus == false && dislikeStatus == false) {
                sql = "UPDATE tblEmotion SET disLike = 1, date = GETDATE() WHERE postID = ? AND email = ?";
            } else {
                sql = "UPDATE tblEmotion SET isLike = 0, disLike = 0, date = GETDATE() WHERE postID = ? AND email = ?";
            }

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

    public boolean checkUserFromtblEmotion(String postID, String email) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT ID FROM tblEmotion WHERE postID = ? AND email = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, postID);
            pre.setString(2, email);
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

    public boolean insertUserEmotion(String ID, String postID, String email) throws Exception {
        boolean result = false;
        try {
            String sql = "Insert Into tblEmotion(ID,postID,email,isLike,disLike,date) values(?,?,?,0,0,GETDATE())";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, ID);
            pre.setString(2, postID);
            pre.setString(3, email);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }
}
