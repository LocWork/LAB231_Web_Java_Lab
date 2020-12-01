/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loc.dao.RoomDAO;
import loc.dto.SearchInfoDTO;
import loc.error.SearchError;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class SearchController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "GetListController";
    private static final Logger LOGGER = Logger.getLogger(SearchController.class);

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
            String name = request.getParameter("txtName");
            String area = request.getParameter("txtArea");
            String checkInDate = request.getParameter("txtCheckInDate");
            String checkOutDate = request.getParameter("txtCheckOutDate");
            String amountString = request.getParameter("txtAmount");
            List<SearchInfoDTO> list = null;
            int amount = 0;
            SearchError error = new SearchError();

            if (checkInDate.isEmpty()) {
                valid = false;
                error.setCheckInDate("Invalid checkInDate input");

            }

            if (checkOutDate.isEmpty()) {
                valid = false;
                error.setCheckOutDate("Invalid checkOutDate input");

            }
            if (amountString.isEmpty()) {
                valid = false;
                error.setAmount("Invalid Amount input");
            }

            if (valid == true) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = formatter.parse(checkInDate);
                Date date2 = formatter.parse(checkOutDate);

                if (date1.after(date2)) {
                    valid = false;
                    request.setAttribute("NOTE_SEARCH", "Invalid date input");
                }

                amount = Integer.parseInt(amountString);

                if (amount < 1) {
                    valid = false;
                    error.setAmount("Invalid Amount input");
                }
            }

            if (valid == true) {
                RoomDAO dao = new RoomDAO();
                list = dao.getHotelList(name, area, checkInDate, checkOutDate, amount);

                if (!list.isEmpty()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("CHECK_IN_DATE", checkInDate);
                    session.setAttribute("CHECK_OUT_DATE", checkOutDate);
                    session.setAttribute("ROOM_AMOUNT", amount);
                    request.setAttribute("RESULT_LIST", list);
                } else {
                    request.setAttribute("NOTE_SEARCH", "No matches found");
                }
            } else {
                request.setAttribute("ERROR_SEARCH", error);
            }
            url = SUCCESS;
        } catch (Exception e) {
            LOGGER.error("Error at SearchController: " + e.toString());
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
