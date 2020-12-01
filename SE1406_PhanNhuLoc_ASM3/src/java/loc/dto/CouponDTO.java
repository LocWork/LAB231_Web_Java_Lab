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
public class CouponDTO {

    private String codeID, codeName, createDate, expireDate;
    private int amount;

    public CouponDTO() {
    }

    public CouponDTO(String codeID, String codeName, String createDate, int amount) {
        this.codeID = codeID;
        this.codeName = codeName;
        this.createDate = createDate;
        this.amount = amount;
    }

    public CouponDTO(String codeID, String codeName, String createDate, String expireDate, int amount) {
        this.codeID = codeID;
        this.codeName = codeName;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.amount = amount;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
