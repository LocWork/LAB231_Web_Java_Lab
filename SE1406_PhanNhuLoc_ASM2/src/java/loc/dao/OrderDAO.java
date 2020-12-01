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
import loc.dto.OrderDTO;
import loc.dto.OrderDetailDTO;

/**
 *
 * @author hi
 */
public class OrderDAO {

    private Connection con = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public OrderDAO() {
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

    public boolean checkOrderIDExist(String uniqueID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT userID FROM tblOrder WHERE orderID = ?";
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

    public boolean checkOrderDetailIDExist(String uniqueID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT orderID FROM tblOrderDetail WHERE detailID = ?";
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

    public boolean insertOrder(OrderDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "INSERT into tblOrder(orderID,userID,total,date) VALUES(?,?,?,getDate())";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, dto.getOrderID());
            pre.setString(2, dto.getUserID());
            pre.setDouble(3, dto.getTotal());
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
            String sql = "INSERT into tblOrder(detailID,orderID,productID,price,quantity,currentStage) VALUES(?,?,?,?,?,'newOrder')";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, dto.getDetailID());
            pre.setString(2, dto.getOrderID());
            pre.setString(3, dto.getProductID());
            pre.setDouble(4, dto.getPrice());
            pre.setInt(5, dto.getQuantity());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public List<String> getOrderIDList(String userID) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            String sql = "SELECT TOP(50) orderID,date FROM tblOrder WHERE userID = ? ORDER BY date DESC";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, userID);
            rs = pre.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                list.add(orderID);
            }

        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return list;
    }

    public List<OrderDTO> getOrderListAdmin(String orderID) throws Exception {
        List<OrderDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT userID,name,total,date,address,payment FROM tblOrder WHERE orderID = ? ORDER BY date DESC";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, orderID);
            rs = pre.executeQuery();
            while (rs.next()) {
                String userID = rs.getString("userID");
                String name = rs.getString("name");
                double total = rs.getDouble("total");
                String date = rs.getString("date");
                String address = rs.getString("address");
                String payment = rs.getString("payment");
                list.add(new OrderDTO(orderID, userID, date, name, address, payment, total));
            }

        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return list;
    }

    public List<OrderDTO> getOrderList(String userID, String orderID) throws Exception {
        List<OrderDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT name,total,date,address,payment FROM tblOrder WHERE userID = ? AND orderID = ? ORDER BY date DESC";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, userID);
            pre.setString(2, orderID);
            rs = pre.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double total = rs.getDouble("total");
                String date = rs.getString("date");
                String address = rs.getString("address");
                String payment = rs.getString("payment");
                list.add(new OrderDTO(orderID, userID, date, name, address, payment, total));
            }

        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return list;
    }

    public List<OrderDetailDTO> getOrderDetailList(String orderID) throws Exception {
        List<OrderDetailDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT detailID,productID,price,quantity,currentStage FROM tblOrderDetail WHERE orderID = ?";
            con = DBConnection.getMyConnection();
            pre = con.prepareStatement(sql);
            pre.setString(1, orderID);
            rs = pre.executeQuery();
            while (rs.next()) {
                String detailID = rs.getString("detailID");
                String productID = rs.getString("productID");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String currentStage = rs.getString("currentStage");
                list.add(new OrderDetailDTO(detailID, orderID, productID, currentStage, price, quantity));
            }

        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return list;
    }
}
