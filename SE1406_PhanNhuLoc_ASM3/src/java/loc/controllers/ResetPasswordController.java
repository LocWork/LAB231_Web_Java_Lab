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
import loc.extra.ShaEncryption;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class ResetPasswordController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "login.jsp";
    private static final String FAIL = "resetPassword.jsp";
    private static final Logger LOGGER = Logger.getLogger(ResetPasswordController.class);

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
            HttpSession session = request.getSession();
            String password = request.getParameter("txtPassword");
            String rePassword = request.getParameter("txtRePassword");
            UserDAO dao = new UserDAO();
            String userID = (String) session.getAttribute("USER_EMAIL");
            if (password == null || password.isEmpty()) {
                valid = false;
                request.setAttribute("ERROR_PASSWORD", "empty input");
            }

            if (rePassword == null || rePassword.isEmpty()) {
                valid = false;
                request.setAttribute("ERROR_REPASSWORD", "empty input");
            }

            if (valid == true) {

                if (!rePassword.equals(password)) {
                    valid = false;
                    request.setAttribute("NOTE", "Repassword does not match");
                }
            }
            if (userID == null || userID.isEmpty()) {
                valid = false;
                request.setAttribute("NOTE", "USER EMAIL ERROR!!!");
            }

            if (valid == true) {
                ShaEncryption sha = new ShaEncryption();
                password = sha.sha256(password);
                if (dao.updateUserPassword(password, userID)) {
                    url = SUCCESS;
                    session.setAttribute("RESET_CODE", null);
                    session.setAttribute("USER_EMAIL", null);
                } else {
                    url = FAIL;
                }

            } else {
                url = FAIL;
            }

        } catch (Exception e) {
            LOGGER.error("Error at ResetPasswordController: " + e.toString());
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
