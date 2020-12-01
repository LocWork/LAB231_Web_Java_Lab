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
import loc.dao.ExtraDAO;
import loc.dto.BookingDTO;
import loc.dto.BookingDetailDTO;
import loc.dto.CartDTO;
import loc.dto.RoomCartDTO;
import loc.dto.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class CheckoutController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "checkout.jsp";
    private static final String INVALID = "error.jsp";
    private static final Logger LOGGER = Logger.getLogger(CheckoutController.class);

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
            UserDTO user = (UserDTO) session.getAttribute("USER_INFO");
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            Double total = (Double) session.getAttribute("TOTAL");
            String code = (String) session.getAttribute("DISCOUNT_CODE");
            ExtraDAO extraDAO = new ExtraDAO();
            BookingDTO bookingDTO = new BookingDTO();
            String uniqueBookingID = "";
            String uniqueBookingDetailID = "";

            do {
                uniqueBookingID = UUID.randomUUID().toString();
            } while (extraDAO.checkBookingIDExist(uniqueBookingID));

            for (RoomCartDTO room : cart.getCart().values()) {
                if (extraDAO.checkRoomQuantityAndStatus(room.getAmount(), room.getRoomID(), room.getCheckInDate(), room.getCheckOutDate()) == false) {
                    valid = false;
                }
            }

            if (valid == true) {
                bookingDTO = new BookingDTO(uniqueBookingID, user.getUserID(), "", "1", code, total);
                if (extraDAO.insertBooking(bookingDTO)) {
                    for (RoomCartDTO tmpRoom : cart.getCart().values()) {
                        do {
                            uniqueBookingDetailID = UUID.randomUUID().toString();
                        } while (extraDAO.checkBookingDetailIDExist(uniqueBookingDetailID));
                        BookingDetailDTO tmpDTO = new BookingDetailDTO(uniqueBookingDetailID, uniqueBookingID, tmpRoom.getRoomID(), tmpRoom.getHotelID(), tmpRoom.getCheckInDate(), tmpRoom.getCheckOutDate(), tmpRoom.getPrice(), tmpRoom.getAmount());
                        if (extraDAO.insertBookingDetail(tmpDTO) == false) {
                            valid = false;
                        }
                    }
                }
                if (valid == true) {
                    code = null;
                    cart = null;
                    total = 0.0;
                    session.setAttribute("TOTAL", total);
                    session.setAttribute("CART", cart);
                    session.setAttribute("DISCOUNT_CODE", code);
                    session.setAttribute("CHECKOUT_CODE", null);
                    url = SUCCESS;
                } else {
                    url = INVALID;
                    cart = null;
                    code = null;
                    total = 0.0;
                    session.setAttribute("TOTAL", total);
                    session.setAttribute("DISCOUNT_CODE", code);
                    session.setAttribute("CART", cart);
                    session.setAttribute("CHECKOUT_CODE", null);
                }
            } else {
                url = INVALID;
                cart = null;
                code = null;
                total = 0.0;
                session.setAttribute("TOTAL", total);
                session.setAttribute("DISCOUNT_CODE", code);
                session.setAttribute("CART", cart);
                session.setAttribute("CHECKOUT_CODE", null);
            }

        } catch (Exception e) {
            LOGGER.error("Error at CheckoutController: " + e.toString());
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
