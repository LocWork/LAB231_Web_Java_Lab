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
public class CouponError {

    private String codeID, codeName, createDate, expireDate, amount;

    public CouponError() {
    }

    public CouponError(String codeID, String codeName, String createDate, String expireDate, String amount) {
        this.codeID = codeID;
        this.codeName = codeName;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.amount = amount;
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

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
