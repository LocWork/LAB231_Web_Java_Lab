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
import loc.dto.CategoryDTO;

/**
 *
 * @author hi
 */
public class OptionDAO {

    private Connection con = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public OptionDAO() {
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

    public List<CategoryDTO> getCategoryList() throws Exception {
        List<CategoryDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT categoryID, name FROM tblCategory";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                String id = rs.getString("categoryID");
                String name = rs.getString("name");
                result.add(new CategoryDTO(id, name));
            }

        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }
}
