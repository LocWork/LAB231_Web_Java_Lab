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
import loc.dto.CartDTO;
import loc.dto.RoomCartDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class ChangeCouponController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewCart.jsp";
    private static final Logger LOGGER = Logger.getLogger(ChangeCouponController.class);

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
            String code = request.getParameter("txtCode");
            HttpSession session = request.getSession();

            code = (String) session.getAttribute("DISCOUNT_CODE");
            if (code == null || code.isEmpty()) {
                valid = false;
            } else {
                valid = true;
            }

            if (valid == true) {
                session.setAttribute("DISCOUNT_CODE", null);
            }
            CartDTO cart = new CartDTO();
            cart = (CartDTO) session.getAttribute("CART");
            double total = 0;
            for (RoomCartDTO room : cart.getCart().values()) {
                total = total + (room.getPrice() * room.getAmount());
            }
            session.setAttribute("TOTAL", total);

            url = SUCCESS;
        } catch (Exception e) {
            LOGGER.error("Error at ChangeCouponController: " + e.toString());
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
