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
public class RoomDTO {

    private String roomID, hotelID, typeID, statusID, image, description;
    private double price;
    private int amount;

    public RoomDTO() {
    }

    public RoomDTO(String roomID, String hotelID, String typeID, String statusID, String image, String description, double price, int amount) {
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.typeID = typeID;
        this.statusID = statusID;
        this.image = image;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public RoomDTO(String roomID, String hotelID, String typeID, String image, String description, double price, int amount) {
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.typeID = typeID;
        this.image = image;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
