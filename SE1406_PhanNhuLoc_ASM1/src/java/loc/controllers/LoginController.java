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
import loc.daos.AccountDAO;
import loc.dtos.AccountDTO;
import loc.extra.ShaEncryption;
import loc.errors.LoginError;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class LoginController extends HttpServlet {

    public static final String ERROR = "error.jsp";
    public static final String SUCCESS = "home.jsp";
    public static final String INVALID = "login.jsp";
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
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            LoginError error = new LoginError();
            String regex = "^[\\w]+\\d?@\\w+[.]\\w+([.]\\w+)?";
            AccountDAO dao = new AccountDAO();
            if (!email.matches(regex)) {
                valid = false;
                error.setEmail("Invalid Email");
            }
            if (email.trim().isEmpty()) {
                valid = false;
                error.setEmail("Email cannot be empty!");
            }
            if (password.trim().isEmpty()) {
                valid = false;
                error.setPassword("Password cannot be empty");
            }

            if (valid == true) {
                ShaEncryption sha = new ShaEncryption();
                password = sha.sha256(password);
                AccountDTO dto = dao.getAccount(email, password);
                if (dto != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER_INFO", dto);
                    url = SUCCESS;
                } else {
                    request.setAttribute("NOTE", "User does not exist");
                    url = INVALID;
                }
            } else {
                request.setAttribute("ERROR_LOGIN", error);
                url = INVALID;
            }

        } catch (Exception e) {
            LOGGER.error("Error at LoginController");
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
