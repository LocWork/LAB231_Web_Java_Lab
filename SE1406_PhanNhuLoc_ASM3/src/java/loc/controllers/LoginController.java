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
import javax.servlet.http.HttpSession;
import loc.dao.UserDAO;
import loc.dto.UserDTO;
import loc.extra.ShaEncryption;
import loc.recapcha.VerifyUtils;
import loc.error.UserError;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class LoginController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "home.jsp";
    private static final String INVALID = "login.jsp";
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

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

            String userID = request.getParameter("txtUserID");
            String password = request.getParameter("txtPassword");
            String regex = "^[\\w]+\\d?@\\w+[.]\\w+([.]\\w+)?";
            UserDAO dao = new UserDAO();
            UserDTO user = new UserDTO();
            UserError error = new UserError();

            if (!userID.matches(regex)) {
                valid = false;
                error.setUserID("Invalid email");
            }
            if (userID.isEmpty()) {
                valid = false;
                error.setUserID("Email can't be empty");
            }
            if (password.isEmpty()) {
                valid = false;
                error.setPassword("Password can't be empty");
            }

            if (valid == true) {
                String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
                valid = VerifyUtils.verify(gRecaptchaResponse);
                if (valid == false) {
                    request.setAttribute("NOTE", "Invalid capcha");
                }
            }

            if (valid == true) {
                ShaEncryption sha = new ShaEncryption();
                password = sha.sha256(password);
                user = dao.getUserInformation(userID, password);
                if (user != null) {
                    url = SUCCESS;
                    HttpSession session = request.getSession();
                    session.setAttribute("USER_INFO", user);
                } else {
                    url = INVALID;
                    request.setAttribute("NOTE", "User does not exist");
                }
            } else {
                url = INVALID;
                request.setAttribute("ERROR", error);
            }
        } catch (Exception e) {
            LOGGER.error("Error at LoginController: " + e.toString());
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
