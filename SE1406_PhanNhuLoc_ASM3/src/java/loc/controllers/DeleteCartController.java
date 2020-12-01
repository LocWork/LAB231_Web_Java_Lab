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
public class DeleteCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS_COUPON = "CouponController";
    private static final String SUCCESS_NORMAL = "viewCart.jsp";
    private static final Logger LOGGER = Logger.getLogger(DeleteCartController.class);

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
        try {
            String roomID = request.getParameter("txtRoomID");
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            double total = 0;
            cart.delete(roomID);
            session.setAttribute("CART", cart);
            for (RoomCartDTO product : cart.getCart().values()) {
                total = total + (product.getPrice() * product.getAmount());
            }
            session.setAttribute("TOTAL", total);

            String code = (String) session.getAttribute("DISCOUNT_CODE");
            if (code == null) {
                url = SUCCESS_NORMAL;

            } else {
                url = SUCCESS_COUPON;
            }

        } catch (Exception e) {
            LOGGER.error("Error at DeleteCartController: " + e.toString());
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
