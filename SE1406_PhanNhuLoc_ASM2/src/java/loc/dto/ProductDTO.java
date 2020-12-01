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
public class ProductDTO {

    public String productID, name, image, categoryID;
    private double price;
    private int quantity;
    private String description;
    private String createDate;
    private String expireDate;
    private String updateDate;
    private String status;

    public ProductDTO() {
    }

    public ProductDTO(String productID, String name, String image, String categoryID, double price, int quantity, String description, String createDate, String expireDate, String updateDate, String status) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.categoryID = categoryID;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    public ProductDTO(String productID, String name, String image, String categoryID, double price, int quantity, String description, String createDate, String expireDate, String status) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.categoryID = categoryID;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.status = status;
    }

    public ProductDTO(String productID, String name, String image, String categoryID, double price, int quantity) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.categoryID = categoryID;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductDTO(String productID, String name, String image, String categoryID, double price, int quantity, String createDate, String expireDate) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.categoryID = categoryID;
        this.price = price;
        this.quantity = quantity;
        this.createDate = createDate;
        this.expireDate = expireDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
