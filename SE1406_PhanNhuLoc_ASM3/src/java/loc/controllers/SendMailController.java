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
import loc.email.Mailer;
import loc.email.RandomGenerator;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class SendMailController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "validate.jsp";
    private static final String FAIL = "resetInfo.jsp";
    private static final Logger LOGGER = Logger.getLogger(SendMailController.class);

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
            String to = request.getParameter("txtUserID");
            String subject = "Reset password email";
            String msg = "";

            UserDAO dao = new UserDAO();

            if (to == null || to.isEmpty()) {
                valid = false;
                request.setAttribute("NOTE", "Empty input");
            }

            if (valid == true) {
                if (dao.checkUserIDExist(to) == false) {
                    valid = false;
                    request.setAttribute("NOTE", "This user does not exist");
                }
            }

            if (valid == true) {
                RandomGenerator rando = new RandomGenerator();
                String code = rando.randomID();
                msg = "Here is your code: " + code;
                session.setAttribute("USER_EMAIL", to);
                session.setAttribute("RESET_CODE", code);
                Mailer.send(to, subject, msg);
                url = SUCCESS;
            } else {
                url = FAIL;
            }

        } catch (Exception e) {
            LOGGER.error("Error at SendMailController: " + e.toString());
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
