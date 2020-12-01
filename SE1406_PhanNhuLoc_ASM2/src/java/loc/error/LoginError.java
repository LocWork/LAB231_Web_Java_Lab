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
public class LoginError {

    private String userIDError, passwordError;

    public LoginError() {
    }

    ;

    public LoginError(String userIDError, String passwordError) {
        this.userIDError = userIDError;
        this.passwordError = passwordError;
    }

    public String getUserIDError() {
        return userIDError;
    }

    public void setUserIDError(String userIDError) {
        this.userIDError = userIDError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

}
