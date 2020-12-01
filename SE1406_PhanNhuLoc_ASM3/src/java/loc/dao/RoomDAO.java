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
import loc.dto.AreaDTO;
import loc.dto.RoomCartDTO;
import loc.dto.RoomDTO;
import loc.dto.SearchInfoDTO;
import loc.dto.TypeDTO;

/**
 *
 * @author hi
 */
public class RoomDAO {

    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public RoomDAO() {
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

    public List<AreaDTO> getAreaList() throws Exception {
        List<AreaDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT areaID, name FROM tblArea";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                String areaID = rs.getString("areaID");
                String name = rs.getString("name");
                list.add(new AreaDTO(areaID, name));
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<TypeDTO> getTypeList() throws Exception {
        List<TypeDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT typeID, name FROM tblType";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                String typeID = rs.getString("typeID");
                String name = rs.getString("name");
                list.add(new TypeDTO(typeID, name));
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<SearchInfoDTO> getHotelList(String name, String area, String checkInDate, String checkOutDate, int amount) throws Exception {
        List<SearchInfoDTO> list = new ArrayList<>();
        try {
            String sql = " SELECT TH.hotelID,TH.hotelName,TH.name AS areaName,TH.image,TH.description,TR.Room - TH.bookRoom AS availableRoom "
                    + " FROM"
                    + " (SELECT H.hotelID,H.name AS hotelName,A.name AS areaName,H.image,H.description,COALESCE(SUM(BD.amount),0) AS bookRoom,H.statusID,A.name "
                    + " FROM tblRoom AS R LEFT JOIN tblBookingDetail AS BD ON R.roomID = BD.roomID "
                    + " AND BD.checkInDate <= ? AND BD.checkOutDate >= ? "
                    + " JOIN tblHotel AS H ON H.hotelID = R.hotelID "
                    + " JOIN tblArea AS A ON A.areaID = H.areaID AND A.areaID = ? "
                    + " AND H.name LIKE  ?"
                    + " GROUP BY H.hotelID,H.name,A.name,H.image,H.description,H.statusID) AS TH "
                    + " JOIN "
                    + " (SELECT R.hotelID, SUM(R.amount) AS Room,R.statusID "
                    + " FROM tblRoom AS R JOIN tblHotel AS H ON R.hotelID = H.hotelID "
                    + " GROUP BY R.hotelID,R.statusID) AS TR ON TH.hotelID = TR.hotelID "
                    + " GROUP BY TH.hotelID,TH.hotelName,TH.name,TH.image,TH.description,TR.statusID,TH.statusID,TR.Room,TH.bookRoom "
                    + " HAVING (TR.Room - TH.bookRoom >= ?) AND (TR.statusID = 1) AND (TH.statusID = 1) ";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, checkOutDate);
            pre.setString(2, checkInDate);
            pre.setString(3, area);
            pre.setString(4, "%" + name + "%");
            pre.setInt(5, amount);
            rs = pre.executeQuery();
            while (rs.next()) {
                String hotelID = rs.getString("hotelID");
                String hotelName = rs.getString("hotelName");
                String areaName = rs.getString("areaName");
                String hotelImage = rs.getString("image");
                String hotelDescription = rs.getString("description");
                int amount2 = rs.getInt("availableRoom");
                list.add(new SearchInfoDTO(hotelID, hotelName, hotelDescription, hotelImage, areaName, amount2));
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<RoomDTO> getRoomList(String hotelID, String checkInDate, String checkOutDate) throws Exception {
        List<RoomDTO> list = new ArrayList<>();
        try {
            String sql = " SELECT TR.roomID,TR.image,TR.description,TR.price,TR.typeName, TR.Room - TH.bookRoom AS availableRoom "
                    + " FROM "
                    + " ( "
                    + " SELECT COALESCE(SUM(BD.amount),0) AS bookRoom, H.statusID, H.hotelID,R.roomID "
                    + " FROM tblRoom AS R LEFT JOIN tblBookingDetail AS BD ON R.roomID = BD.roomID "
                    + " AND BD.checkInDate <= ? AND BD.checkOutDate >= ? "
                    + " JOIN tblHotel AS H ON H.hotelID = R.hotelID "
                    + " GROUP BY H.statusID, H.hotelID,R.roomID) AS TH "
                    + " JOIN "
                    + " (SELECT R.roomID,R.image,R.description,R.price,T.name AS typeName, R.amount AS Room,R.statusID,R.hotelID "
                    + " FROM tblRoom AS R JOIN tblHotel AS H ON R.hotelID = H.hotelID AND R.hotelID = ? "
                    + " JOIN tblType AS T ON R.typeID = T.typeID "
                    + " GROUP BY R.roomID,R.image,R.description,R.price,T.name, R.amount,R.statusID,R.hotelID) AS TR ON TH.hotelID = TR.hotelID AND TH.roomID = TR.roomID "
                    + " GROUP BY TR.roomID,TR.image,TR.description,TR.price,TR.typeName,TR.Room, TH.bookRoom,TH.statusID,TR.statusID "
                    + " HAVING (TH.statusID = 1) AND (TR.statusID = 1) AND ((TR.Room - TH.bookRoom) > 0)";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, checkOutDate);
            pre.setString(2, checkInDate);
            pre.setString(3, hotelID);
            rs = pre.executeQuery();
            while (rs.next()) {
                String roomID = rs.getString("roomID");
                String typeName = rs.getString("typeName");
                String image = rs.getString("image");
                String description = rs.getString("description");
                int amount = rs.getInt("availableRoom");
                double price = rs.getDouble("price");
                list.add(new RoomDTO(roomID, hotelID, typeName, image, description, price, amount));
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return list;
    }

    public RoomCartDTO getRoomForCard(String roomID, String checkInDate, String checkOutDate, String hotelID, int searchAmount) throws Exception {
        RoomCartDTO room = null;
        try {
            String sql = " SELECT TR.roomID,TR.typeName,TR.image,TH.hotelName,TR.price, TR.Room - TH.bookRoom AS availableRoom "
                    + " FROM "
                    + " ( "
                    + " SELECT R.roomID,COALESCE(SUM(BD.amount),0) AS bookRoom, H.statusID, H.hotelID,H.name AS hotelName "
                    + " FROM tblRoom AS R LEFT JOIN tblBookingDetail AS BD ON R.roomID = BD.roomID "
                    + " AND BD.checkInDate <= ? AND BD.checkOutDate >= ? "
                    + " JOIN tblHotel AS H ON H.hotelID = R.hotelID AND H.hotelID = ? "
                    + " GROUP BY H.statusID, H.hotelID,H.name,R.roomID) AS TH "
                    + " JOIN "
                    + " (SELECT R.roomID,R.image,R.price,T.name AS typeName, R.amount AS Room,R.statusID,R.hotelID "
                    + " FROM tblRoom AS R JOIN tblHotel AS H ON R.hotelID = H.hotelID "
                    + " JOIN tblType AS T ON R.typeID = T.typeID "
                    + " GROUP BY R.roomID,R.image,R.description,R.price,T.name, R.amount,R.statusID,R.hotelID) AS TR ON TH.hotelID = TR.hotelID AND TH.roomID = TR.roomID "
                    + " GROUP BY TR.roomID,TR.image,TR.price,TR.typeName,TR.Room, TH.bookRoom,TH.statusID,TR.statusID,TH.hotelName "
                    + " HAVING (TR.Room - TH.bookRoom >= ?) AND (TH.statusID = 1) AND (TR.statusID = 1) AND (TR.roomID = ?) ";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, checkOutDate);
            pre.setString(2, checkInDate);
            pre.setString(3, hotelID);
            pre.setInt(4, searchAmount);
            pre.setString(5, roomID);
            rs = pre.executeQuery();
            if (rs.next()) {
                String typeID = rs.getString("typeName");
                String image = rs.getString("image");
                String hotelName = rs.getString("hotelName");
                double price = rs.getDouble("price");
                int amount = rs.getInt("availableRoom");
                room = new RoomCartDTO(roomID, hotelID, hotelName, typeID, image, checkInDate, checkOutDate, price, amount);
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return room;
    }

    public int getDiscountAmount(String code) throws Exception {
        int amount = 0;
        try {
            String sql = "SELECT discount FROM tblDiscount WHERE codeID = ? AND expireDate > GETDATE()";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, code);
            rs = pre.executeQuery();
            if (rs.next()) {
                amount = rs.getInt("discount");
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }

        return amount;
    }
}
