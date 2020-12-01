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
public class NotifyDTO {

    String id, postID, title, email, date, type;

    public NotifyDTO() {
    }

    public NotifyDTO(String postID, String email, String date, String type) {
        this.postID = postID;
        this.email = email;
        this.date = date;
        this.type = type;
    }

    public NotifyDTO(String id, String postID, String email, String date, String type) {
        this.id = id;
        this.postID = postID;
        this.title = title;
        this.email = email;
        this.date = date;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
