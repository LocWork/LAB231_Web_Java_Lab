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
import loc.dtos.AccountDTO;

/**
 *
 * @author hi
 */
public class AccountDAO {

    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public AccountDAO() {
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

    public boolean insertAccount(AccountDTO dto) throws Exception {
        boolean result = false;
        try {
            String sql = "INSERT INTO tblUsers(email,name,password,status,role) VALUES (?,?,?,?,?)";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dto.getEmail());
            pre.setString(2, dto.getName());
            pre.setString(3, dto.getPassword());
            pre.setInt(4, dto.getStatus());
            pre.setString(5, dto.getRole());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkForEamil(String email) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT name FROM tblUsers WHERE email = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, email);
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

    public AccountDTO getAccount(String email, String password) throws Exception {
        AccountDTO dto = null;
        try {
            String sql = "SELECT name, status FROM tblUsers WHERE email = ? and password = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int status = rs.getInt("status");
                dto = new AccountDTO(email, name, password, status);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean updateAccountStatus(String email) throws Exception {
        boolean result = false;
        try {
            String sql = "UPDATE tblUsers SET status = 2 WHERE email = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, email);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }
}
