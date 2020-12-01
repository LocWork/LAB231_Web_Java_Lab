/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import loc.dao.UserDAO;
import loc.dto.UserDTO;
import loc.extra.ShaEncryption;
import loc.error.UserError;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class RegisterController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "login.jsp";
    private static final String INVALID = "register.jsp";
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        boolean valid = true;
        try {
            String regex = "^[\\w]+\\d?@\\w+[.]\\w+([.]\\w+)?";
            String phoneRegex = "^[0-9]{10}";
            String userID = request.getParameter("txtUserID");
            String name = request.getParameter("txtName");
            String password = request.getParameter("txtPassword");
            String rePassword = request.getParameter("txtRePassword");
            String phone = request.getParameter("txtPhone");
            String address = request.getParameter("txtAddress");
            String status = "1";
            String role = "1";
            UserError error = new UserError();
            UserDAO dao = new UserDAO();

            if (!userID.matches(regex)) {
                valid = false;
                error.setUserID("Invalid userID");
            }

            if (userID.isEmpty()) {
                valid = false;
                error.setUserID("UserID can't be empty");
            }

            if (dao.checkUserIDExist(userID)) {
                valid = false;
                error.setUserID("Account already exist");
            }

            if (name.isEmpty()) {
                valid = false;
                error.setName("Name can't be empty");
            }
            if (password.isEmpty()) {
                valid = false;
                error.setPassword("Password can't be empty");
            }

            if (!rePassword.equals(password)) {
                valid = false;
                error.setRePassword("Repassword does not match");
            }

            if (rePassword.isEmpty()) {
                valid = false;
                error.setRePassword("Repassword can't be empty");
            }

            if (!phone.matches(phoneRegex)) {
                valid = false;
                error.setPhone("Invalid phone input");
            }

            if (phone.isEmpty()) {
                valid = false;
                error.setPhone("Phone can't be empty");
            }
            if (address.isEmpty()) {
                valid = false;
                error.setAddress("Address can't be empty");
            }

            if (valid == true) {
                ShaEncryption sha = new ShaEncryption();
                password = sha.sha256(password);
                UserDTO dto = new UserDTO(userID, name, password, phone, address, "", role, status);
                if (dao.insertNewUser(dto)) {
                    url = SUCCESS;
                } else {
                    url = INVALID;
                    request.setAttribute("ACCOUNT", dto);
                    request.setAttribute("NOTE", "Unable to create user");
                }
            } else {
                url = INVALID;
                UserDTO dto = new UserDTO(userID, name, password, phone, address, "", role, status);
                request.setAttribute("ACCOUNT", dto);
                request.setAttribute("ERROR", error);
            }

        } catch (Exception e) {
            LOGGER.error("Error at RegisterController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
