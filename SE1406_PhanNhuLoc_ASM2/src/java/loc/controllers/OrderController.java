/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import loc.dao.OrderDAO;
import loc.dto.OrderDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class OrderController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "LoadOrderIDController";
    private static final Logger LOGGER = Logger.getLogger(OrderController.class);

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
            String orderID = request.getParameter("txtOrderID");
            String userID = request.getParameter("txtUserID");
            String roleID = request.getParameter("txtRoleID");

            if (orderID.isEmpty()) {
                request.setAttribute("NOTE", "Empty Search");
                valid = false;
            }

            if (userID.isEmpty()) {
                request.setAttribute("NOTE", "Invalid User");
            }

            if (valid == true) {
                List<OrderDTO> list = new ArrayList<>();
                OrderDAO dao = new OrderDAO();
                if (roleID.equals("1")) {
                    list = dao.getOrderList(userID, orderID);
                } else if (roleID.equals("0")) {
                    list = dao.getOrderListAdmin(orderID);
                } else {
                    request.setAttribute("NOTE", "Invalid role ID");
                    valid = false;
                }

                if (valid = true) {
                    if (list == null || list.isEmpty()) {
                        request.setAttribute("NOTE", "There is no order with this ID");
                    } else {
                        request.setAttribute("ORDER_LIST", list);
                        request.setAttribute("SEARCH", orderID);
                    }
                }
            }

            url = SUCCESS;
        } catch (Exception e) {
            LOGGER.error("Error at OrderController: " + e.toString());
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
