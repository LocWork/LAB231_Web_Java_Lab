/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.dto;

/**
 *
 * @author hi
 */
public class LogDTO {

    private String ID, userID, productID, date;

    public LogDTO() {
    }

    public LogDTO(String ID, String userID, String productID, String date) {
        this.ID = ID;
        this.userID = userID;
        this.productID = productID;
        this.date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
