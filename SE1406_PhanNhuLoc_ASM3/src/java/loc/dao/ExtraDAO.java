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
import loc.dto.BookingDTO;
import loc.dto.BookingDetailDTO;
import loc.dto.CouponDTO;
import loc.dto.FeedBackDTO;

/**
 *
 * @author hi
 */
public class ExtraDAO {

    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;

    public ExtraDAO() {
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

    public boolean checkCodeIDExist(String codeID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT codeName FROM tblDiscount WHERE codeID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, codeID);
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

    public boolean insertCoupon(CouponDTO dto) throws Exception {
        boolean result = false;
        try {
            String sql = "INSERT INTO tblDiscount(codeID,codeName,discount,createDate,expireDate) VALUES(?,?,?,GETDATE(),?)";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dto.getCodeID());
            pre.setString(2, dto.getCodeName());
            pre.setInt(3, dto.getAmount());
            pre.setString(4, dto.getExpireDate());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkBookingIDExist(String uniqueID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT userID FROM tblOrder WHERE orderID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
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

    public boolean checkBookingDetailIDExist(String uniqueID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT bookingID FROM tblOrder WHERE orderID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
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

    public boolean checkRoomQuantityAndStatus(int quantity, String roomID, String checkInDate, String checkOutDate) throws Exception {
        boolean result = false;
        try {
            String sql = " SELECT TR.Room - TH.bookRoom AS availableRoom "
                    + " FROM "
                    + " (SELECT H.hotelID,R.roomID,COALESCE(SUM(BD.amount),0) AS bookRoom,H.statusID "
                    + " FROM tblRoom AS R LEFT JOIN tblBookingDetail AS BD ON R.roomID = BD.roomID "
                    + " AND BD.checkInDate <= ? AND BD.checkOutDate >= ? "
                    + " JOIN tblHotel AS H ON H.hotelID = R.hotelID "
                    + " GROUP BY H.hotelID,H.name,H.image,H.description,R.roomID,H.statusID) AS TH "
                    + " JOIN "
                    + " (SELECT R.hotelID, R.amount AS Room,R.statusID,R.roomID "
                    + " FROM tblRoom AS R JOIN tblHotel AS H ON R.hotelID = H.hotelID "
                    + " GROUP BY R.hotelID,R.amount,R.statusID,R.roomID) AS TR ON TH.hotelID = TR.hotelID AND TR.roomID = TH.roomID "
                    + " GROUP BY TH.hotelID,TR.roomID,TR.statusID,TH.statusID,TR.Room,TH.bookRoom "
                    + " HAVING (TR.Room - TH.bookRoom >= ?) AND (TR.statusID = 1) AND (TH.statusID = 1) AND( TR.roomID = ?) ";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, checkOutDate);
            pre.setString(2, checkInDate);
            pre.setInt(3, quantity);
            pre.setString(4, roomID);
            rs = pre.executeQuery();
            if (rs.next()) {
                int remainder = rs.getInt("availableRoom");
                result = remainder >= quantity;
            } else {
                result = false;
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertBooking(BookingDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "INSERT into tblBooking(bookingID,userID,bookingDate,total,statusID,discountID) VALUES(?,?,GETDATE(),?,?,?)";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dto.getBookingID());
            pre.setString(2, dto.getUserID());
            pre.setDouble(3, dto.getTotal());
            pre.setString(4, dto.getStatusID());
            pre.setString(5, dto.getDiscountID());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertBookingDetail(BookingDetailDTO dto) throws Exception {
        boolean result = false;

        try {
            String sql = "INSERT into tblBookingDetail(detailID,bookingID,roomID,hotelID,price,amount,checkInDate,checkOutDate) VALUES(?,?,?,?,?,?,?,?)";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dto.getDetailID());
            pre.setString(2, dto.getBookingID());
            pre.setString(3, dto.getRoomID());
            pre.setString(4, dto.getHotelID());
            pre.setDouble(5, dto.getPrice());
            pre.setInt(6, dto.getAmount());
            pre.setString(7, dto.getCheckInDate());
            pre.setString(8, dto.getCheckOutDate());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public List<BookingDTO> getBookList(String userID) throws Exception {
        List<BookingDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT bookingID,bookingDate,total,discountID  FROM tblBooking WHERE userID = ? AND statusID = 1 ORDER BY bookingDate DESC";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userID);
            rs = pre.executeQuery();
            while (rs.next()) {
                String bookingID = rs.getString("bookingID");
                String bookingDate = rs.getString("bookingDate");
                double total = rs.getDouble("total");
                String discountID = rs.getString("discountID");
                list.add(new BookingDTO(bookingID, userID, bookingDate, discountID, total));
            }

        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return list;
    }

    public List<BookingDetailDTO> getDetailList(String bookingID) throws Exception {
        List<BookingDetailDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT detailID,roomID,hotelID,price,amount,checkInDate,checkOutDate FROM tblBookingDetail WHERE bookingID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, bookingID);
            rs = pre.executeQuery();
            while (rs.next()) {
                String detailID = rs.getString("detailID");
                String roomID = rs.getString("roomID");
                String hotelID = rs.getString("hotelID");
                double price = rs.getDouble("price");
                int amount = rs.getInt("amount");
                String checkInDate = rs.getString("checkInDate");
                String checkOutDate = rs.getString("checkOutDate");
                list.add(new BookingDetailDTO(detailID, bookingID, roomID, hotelID, checkInDate, checkOutDate, price, amount));
            }

        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean updateBooking(String bookingID) throws Exception {
        boolean result = false;

        try {
            String sql = "UPDATE tblBooking SET statusID = 0 WHERE bookingID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, bookingID);
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public List<BookingDTO> searchBooking(String uID, String bookID, String date) throws Exception {
        List<BookingDTO> list = new ArrayList<>();
        try {
            String sql = " SELECT bookingID,userID,CONVERT(varchar,bookingDate,25) AS bookDate,total,discountID "
                    + " FROM tblBooking WHERE userID = ? AND bookingDate LIKE ? AND bookingID LIKE ? AND statusID = 1 ";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, uID);
            pre.setString(2, "%" + date + "%");
            pre.setString(3, "%" + bookID + "%");
            rs = pre.executeQuery();
            while (rs.next()) {
                String bookingID = rs.getString("bookingID");
                String bookingDate = rs.getString("bookDate");
                String userID = rs.getString("userID");
                double total = rs.getDouble("total");
                String discountID = rs.getString("discountID");
                list.add(new BookingDTO(bookingID, userID, bookingDate, discountID, total));
            }
        } catch (Exception e) {

        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean validateUserBooking(String roomID, String userID) throws Exception {
        boolean result = false;
        try {
            String sql = " SELECT A.roomID, B.userID "
                    + " FROM "
                    + " (SELECT roomID, bookingID "
                    + " FROM tblBookingDetail)AS A JOIN "
                    + "(SELECT userID, bookingID "
                    + " FROM tblBooking) AS B ON A.bookingID = B.bookingID "
                    + " GROUP BY A.roomID, B.userID "
                    + " HAVING A.roomID = ? AND B.userID = ? ";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, roomID);
            pre.setString(2, userID);
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

    public boolean insertFeedBack(FeedBackDTO dto) throws Exception {
        boolean result = false;
        try {
            String sql = "INSERT INTO tblFeedBack(feedbackID,roomID,userID,score,description,date) VALUES(?,?,?,?,?,GETDATE())";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dto.getFeedbackID());
            pre.setString(2, dto.getRoomID());
            pre.setString(3, dto.getUserID());
            pre.setInt(4, dto.getScore());
            pre.setString(5, dto.getDescription());
            result = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkFeedBackIDExist(String feedbackID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT roomID FROM tblFeedBack WHERE feedbackID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, feedbackID);
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

    public boolean checkFeedBackExist(String userID, String roomID) throws Exception {
        boolean result = false;
        try {
            String sql = "SELECT feedbackID FROM tblFeedBack WHERE userID = ? and roomID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userID);
            pre.setString(2, roomID);
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

    public boolean updateExistingFeedBack(FeedBackDTO dto) throws Exception {
        boolean result = false;
        try {
            String sql = "UPDATE tblFeedBack SET score = ?, description = ?, date = GETDATE() WHERE userID = ? and roomID = ?";
            conn = DBConnection.getMyConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(4, dto.getRoomID());
            pre.setString(3, dto.getUserID());
            pre.setInt(1, dto.getScore());
            pre.setString(2, dto.getDescription());
            result = pre.executeUpdate() > 0;
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
}
