/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.controllers;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loc.dao.UserDAO;
import loc.dto.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class GuestLoginController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "ProductPageController";
    private static final String INVALID = "guestLogin.jsp";
    private static final Logger LOGGER = Logger.getLogger(GuestLoginController.class);

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
            String regex = "^[0-9]{10}";
            String phoneNumber = request.getParameter("txtPhoneNumber");
            String testAddress = request.getParameter("txtAddress");
            String address = request.getParameter("txtAddress");
            String testName = request.getParameter("txtName");
            String name = request.getParameter("txtName");

            if (testName.trim().isEmpty()) {
                valid = false;
                request.setAttribute("NAME_ERROR", "Name can't be empty");
            }

            if (testAddress.trim().isEmpty()) {
                valid = false;
                request.setAttribute("ADDRESS_ERROR", "Address can't be empty");
            }

            if (!phoneNumber.matches(regex)) {
                valid = false;
                request.setAttribute("PHONE_ERROR", "Invalid phone input");
            }

            if (phoneNumber.trim().isEmpty()) {
                valid = false;
                request.setAttribute("PHONE_ERROR", "Phone number can't be empty");
            }

            if (valid == true) {
                UserDAO dao = new UserDAO();
                String uniqueID = null;
                do {
                    uniqueID = UUID.randomUUID().toString();
                } while (dao.checkUserIDExist(uniqueID));

                UserDTO dto = new UserDTO(uniqueID, name, "guest", phoneNumber, address, "2");
                if (dao.insertGuestUser(dto)) {
                    url = SUCCESS;
                    HttpSession session = request.getSession();
                    session.setAttribute("USER_INFO", dto);
                } else {
                    url = INVALID;
                    request.setAttribute("NOTE", "Unable to login");
                }
            } else {
                url = INVALID;
            }
        } catch (Exception e) {
            LOGGER.error("Error at GuestLoginController: " + e.toString());
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
