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
import loc.dto.LogDTO;
import loc.dto.OrderDTO;
import loc.dto.OrderDetailDTO;
import loc.dto.ProductDTO;

/**
 *
 * @author hi
 */
public class ProductDAO {

    private Connection con = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public ProductDAO() {
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

    public List<ProductDTO> getProductList(int pageIndex, int pageSize, String productName, int minPrice, int maxPrice, String id) throws Exception {
        List<ProductDTO> list = new ArrayList<>();
        try {
            String sql = "exec SP_PhanNhuLoc_BakeryPaging ?,?,?,?,?,?";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, pageIndex);
            pre.setInt(2, pageSize);
            pre.setString(3, "%" + productName + "%");
            pre.setInt(4, minPrice);
            pre.setInt(5, maxPrice);
            pre.setString(6, id);
            rs = pre.executeQuery();
            while (rs.next()) {
                String productID = rs.getString("productID");
                String name = rs.getString("name");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String categoryName = rs.getString("categoryName");
                list.add(new ProductDTO(productID, name, image, categoryName, price, quantity));

            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }

    public ProductDTO getProductInfo(String productID) throws Exception {
        ProductDTO result = null;
        try {
            String sql = "SELECT name, price, description, image, quantity, categoryID, createDate, expireDate,status FROM tblProduct  WHERE quantity >= 0 AND status = 'active' AND productID = ?";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, productID);
            rs = pre.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String image = rs.getString("image");
                int quantiy = rs.getInt("quantity");
                String categoryID = rs.getString("categoryID");
                String createDate = rs.getString(7);
                String expireDate = rs.getString(8);
                String status = rs.getString("status");
                result = new ProductDTO(productID, name, image, categoryID, price, quantiy, description, createDate, expireDate, status);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public int countProduct(String productName, int minPrice, int maxPrice, String id) throws Exception {
        int result = 0;
        try {
            String sql = "SELECT COUNT(productID) as number FROM tblProduct AS A,tblCategory AS B WHERE (A.name LIKE ?) AND ((price BETWEEN ? AND ?) AND (A.categoryID = ?) AND (B.categoryID = ?) AND (status = 'active') AND (expireDate > GETDATE()) AND (A.quantity > 0))";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + productName + "%");
            pre.setInt(2, minPrice);
            pre.setInt(3, maxPrice);
            pre.setString(4, id);
            pre.setString(5, id);
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

    public boolean insertNewProduct(ProductDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "INSERT into tblProduct(productID,name,price,description,quantity,image,categoryID,createDate,expireDate,updateDate,status) VALUES(?,?,?,?,?,?,?,?,?,GETDATE(),?)";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, dto.getProductID());
            pre.setString(2, dto.getName());
            pre.setDouble(3, dto.getPrice());
            pre.setString(4, dto.getDescription());
            pre.setInt(5, dto.getQuantity());
            pre.setString(6, dto.getImage());
            pre.setString(7, dto.getCategoryID());
            pre.setString(8, dto.getCreateDate());
            pre.setString(9, dto.getExpireDate());
            pre.setString(10, dto.getStatus());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkProductIDExist(String uniqueID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT name FROM tblProduct WHERE productID = ?";
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

    public boolean updateProduct(ProductDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "UPDATE tblProduct SET name = ?, price = ?, description = ?, quantity = ?, image = ?, categoryID = ?, createDate = ?, expireDate = ?, status = ? WHERE productID = ?";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, dto.getName());
            pre.setDouble(2, dto.getPrice());
            pre.setString(3, dto.getDescription());
            pre.setInt(4, dto.getQuantity());
            pre.setString(5, dto.getImage());
            pre.setString(6, dto.getCategoryID());
            pre.setString(7, dto.getCreateDate());
            pre.setString(8, dto.getExpireDate());
            pre.setString(9, dto.getStatus());
            pre.setString(10, dto.getProductID());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkProductQuantityAndStatus(int quantity, String productID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT quantity FROM tblProduct WHERE productID = ? AND status = 'active'";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, productID);
            rs = pre.executeQuery();
            if (rs.next()) {
                int remainder = rs.getInt("quantity");
                if (remainder >= quantity) {
                    result = true;
                } else {
                    return false;
                }
            } else {
                result = false;
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkLogIDExist(String uniqueID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT userID FROM tblLog WHERE ID = ?";
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

    public boolean insertNewLog(LogDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "INSERT into tblLog(ID,userID,productID,date) VALUES(?,?,?,GETDATE())";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, dto.getID());
            pre.setString(2, dto.getUserID());
            pre.setString(3, dto.getProductID());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertOrder(OrderDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "INSERT into tblOrder(orderID,userID,name,total,date,address,payment) VALUES(?,?,?,?,GETDATE(),?,?)";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, dto.getOrderID());
            pre.setString(2, dto.getUserID());
            pre.setString(3, dto.getName());
            pre.setDouble(4, dto.getTotal());
            pre.setString(5, dto.getAddress());
            pre.setString(6, dto.getPayment());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertOrderDetail(OrderDetailDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "INSERT into tblOrderDetail(detailID,orderID,productID,price,quantity,currentStage) VALUES(?,?,?,?,?,?)";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, dto.getDetailID());
            pre.setString(2, dto.getOrderID());
            pre.setString(3, dto.getProductID());
            pre.setDouble(4, dto.getPrice());
            pre.setInt(5, dto.getQuantity());
            pre.setString(6, dto.getCurrentStage());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateProductQuantity(int quantity, String productID) throws Exception {
        boolean result = false;
        try {
            String sql = "Update tblProduct SET quantity = ? WHERE productID = ?";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setInt(1, quantity);
            pre.setString(2, productID);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateProductStatus(String status, String productID) throws Exception {
        boolean result = false;
        try {
            String sql = "Update tblProduct SET status = ? WHERE productID = ?";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, status);
            pre.setString(2, productID);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }
}
