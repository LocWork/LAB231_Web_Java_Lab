/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import loc.db.DBConnection;
import loc.dto.UserDTO;

/**
 *
 * @author hi
 */
public class UserDAO {

    private Connection con = null;
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
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
        }
    }

    public UserDTO getUserInformation(String userID, String password) throws Exception {
        UserDTO result = null;
        try {
            String sql = "SELECT name, phone, address, roleID FROM tblUser WHERE userID = ? AND password = ?";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, userID);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String roleID = rs.getString("roleID");
                result = new UserDTO(userID, name, password, phone, address, roleID);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkUserIDExist(String uniqueID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT name FROM tblUser WHERE userID = ?";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, uniqueID);
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
    
    public boolean insertGuestUser(UserDTO dto) throws Exception {
        boolean result = false;
        try {
            String sql = "INSERT INTO tblUser(userID,name,password,phone,address,roleID) VALUES(?,?,?,?,?,?)";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, dto.getUserID());
            pre.setString(2, dto.getName());
            pre.setString(3, dto.getPassword());
            pre.setString(4, dto.getPhone());
            pre.setString(5, dto.getAddress());
            pre.setString(6, dto.getRoleID());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<UserDTO> LoadUser()throws Exception{
        List<UserDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT userID,name,phone, address FROM tblUser WHERE statusID = 1";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while(rs.next()){
                String userID = rs.getString("userID");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                list.add(new UserDTO(userID, name, "", phone, address, userID));
            }
        } catch (Exception e) {
        }
        return list;
    }
}
