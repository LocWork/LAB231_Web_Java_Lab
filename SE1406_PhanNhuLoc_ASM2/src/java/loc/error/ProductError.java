/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.error;

/**
 *
 * @author hi
 */
public class ProductError {

    private String productID, name, image;
    private String price;
    private String quantity;
    private String description;
    private String createDate;
    private String expireDate;

    public ProductError() {
    }

    public ProductError(String productID, String name, String image, String price, String quantity, String description, String createDate, String expireDate) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.createDate = createDate;
        this.expireDate = expireDate;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
