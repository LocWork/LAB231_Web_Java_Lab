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
public class CommentDTO {

    String id;
    int postID, status;
    String email, comment, date;

    public CommentDTO() {
    }

    public CommentDTO(String id, String email, String comment, String date) {
        this.id = id;
        this.email = email;
        this.comment = comment;
        this.date = date;
    }

    public CommentDTO(String id, int postID, int status, String email, String comment, String date) {
        this.id = id;
        this.postID = postID;
        this.status = status;
        this.email = email;
        this.comment = comment;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
