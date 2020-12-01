/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loc.dao.ExtraDAO;
import loc.dto.BookingDTO;
import loc.dto.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class SearchHistoryController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "historySearch.jsp";
    private static final Logger LOGGER = Logger.getLogger(SearchHistoryController.class);

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
            String bookingID = request.getParameter("txtBookingID");
            String bookingDate = request.getParameter("txtBookingDate");
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER_INFO");

            ExtraDAO extra = new ExtraDAO();

            if (bookingID == null || bookingID.isEmpty()) {
                bookingID = "";
            }
            if (bookingDate == null || bookingDate.isEmpty()) {
                bookingDate = "";
            }

            List<BookingDTO> list = extra.searchBooking(user.getUserID(), bookingID, bookingDate);

            request.setAttribute("LIST", list);
            url = SUCCESS;
        } catch (Exception e) {
            LOGGER.error("Error at SearchHistoryDateController: " + e.toString());
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
