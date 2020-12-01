/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import loc.db.DBConnection;
import loc.dto.UserDTO;

/**
 *
 * @author hi
 */
public class UserDAO {

    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public UserDAO() {
    }

    public void closeConnection() throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pre != null) {
                pre.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        }
    }

    public UserDTO getUserInformation(String userID, String password) throws Exception {
        UserDTO result = null;
        try {
            String sql = "SELECT name, phone, address, createDate, roleID FROM tblUser WHERE userID = ? AND password = ? AND statusID = 1";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userID);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String createDate = rs.getString("createDate");
                String roleID = rs.getString("roleID");
                String status = "1";
                result = new UserDTO(userID, name, password, phone, address, createDate, roleID, status);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkUserIDExist(String userID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT name FROM tblUser WHERE userID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userID);
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

    public boolean insertNewUser(UserDTO dto) throws Exception {
        boolean result = false;
        try {
            String sql = "INSERT INTO tblUser(userID,name,password,phone,address,roleID,statusID,createDate) VALUES(?,?,?,?,?,?,?,GETDATE())";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dto.getUserID());
            pre.setString(2, dto.getName());
            pre.setString(3, dto.getPassword());
            pre.setString(4, dto.getPhone());
            pre.setString(5, dto.getAddress());
            pre.setString(6, dto.getRoleID());
            pre.setString(7, dto.getStatus());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateUserPassword(String password, String userID) throws Exception {
        boolean result = false;
        try {
            String sql = "UPDATE tblUser SET password = ? WHERE userID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, password);
            pre.setString(2, userID);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }
}
