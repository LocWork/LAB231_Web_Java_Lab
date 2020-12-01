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
import loc.dao.RoomDAO;
import loc.dto.CartDTO;
import loc.dto.RoomCartDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class UpdateCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS_COUPON = "CouponController";
    private static final String SUCCESS_NORMAL = "viewCart.jsp";
    private static final Logger LOGGER = Logger.getLogger(UpdateCartController.class);

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
            String roomID = request.getParameter("txtRoomID");
            String checkInDate = request.getParameter("txtCheckInDate");
            String checkOutDate = request.getParameter("txtCheckOutDate");
            String hotelID = request.getParameter("txtHotelID");
            String amountString = request.getParameter("txtAmount");
            HttpSession session = request.getSession();

            double total = 0;
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            int amount = 1;
            RoomDAO dao = new RoomDAO();
            RoomCartDTO dto = null;
            if (amountString == null || amountString.isEmpty()) {
                valid = false;
                request.setAttribute("NOTE", "Invalid quantity input");
            }

            if (valid == true) {
                amount = Integer.parseInt(amountString);
                if (amount <= 0) {
                    request.setAttribute("NOTE", "Quantity can't be zero or smaller");
                    valid = false;
                }
            }

            if (valid == true) {
                dto = dao.getRoomForCard(roomID, checkInDate, checkOutDate, hotelID, amount);

                if (dto == null) {
                    valid = false;
                    request.setAttribute("NOTE", "Quantity exceed limit");
                }

            }

            if (valid == true) {
                dto.setAmount(amount);
                cart.update(roomID, dto);
                session.setAttribute("CART", cart);
                for (RoomCartDTO room : cart.getCart().values()) {
                    total = total + (room.getPrice() * room.getAmount());
                }
                session.setAttribute("TOTAL", total);
                String code = (String) session.getAttribute("DISCOUNT_CODE");
                if (code == null) {
                    url = SUCCESS_NORMAL;
                } else {
                    url = SUCCESS_COUPON;
                }
            } else {
                url = SUCCESS_NORMAL;
            }
        } catch (Exception e) {
            LOGGER.error("Error at UpdateCartController: " + e.toString());
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
