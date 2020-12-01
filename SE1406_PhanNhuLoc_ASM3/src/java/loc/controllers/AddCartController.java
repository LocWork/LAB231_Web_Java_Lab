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
public class AddCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "HotelRoomController";
    private static final Logger LOGGER = Logger.getLogger(AddCartController.class);

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
            String hotelID = request.getParameter("txtHotelID");
            HttpSession session = request.getSession();
            String checkInDate = (String) session.getAttribute("CHECK_IN_DATE");
            String checkOutDate = (String) session.getAttribute("CHECK_OUT_DATE");
            int searchAmount = (int) session.getAttribute("ROOM_AMOUNT");
            String discountCode = (String) session.getAttribute("DISCOUNT_CODE");
            if (discountCode != null) {
                session.setAttribute("DISCOUNT_CODE", null);
            }
            double total = 0;
            RoomDAO dao = new RoomDAO();
            RoomCartDTO dto = dao.getRoomForCard(roomID, checkInDate, checkOutDate, hotelID, searchAmount);
            if (dto == null) {
                valid = false;
            }

            if (valid == true) {
                CartDTO cart = new CartDTO();
                cart = (CartDTO) session.getAttribute("CART");
                if (cart == null) {
                    cart = new CartDTO();
                }
                dto.setAmount(1);
                cart.add(dto);
                session.setAttribute("CART", cart);
                for (RoomCartDTO room : cart.getCart().values()) {
                    total = total + (room.getPrice() * room.getAmount());
                }
                session.setAttribute("TOTAL", total);
            }

            url = SUCCESS;

        } catch (Exception e) {
            LOGGER.error("Error at AddCartController: " + e.toString());
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
