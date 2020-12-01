/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.dtos;

/**
 *
 * @author hi
 */
public class ArticleDTO {

    String postID, image, title, email, description, date;
    int status;

    public ArticleDTO() {
    }

    public ArticleDTO(String postID, String image, String title, String email, String description) {
        this.postID = postID;
        this.image = image;
        this.title = title;
        this.email = email;
        this.description = description;
    }

    public ArticleDTO(String postID, String image, String title, String email, String description, String date, int status) {
        this.postID = postID;
        this.image = image;
        this.title = title;
        this.email = email;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
