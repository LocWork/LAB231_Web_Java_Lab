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
import loc.dao.OrderDAO;
import loc.dao.ProductDAO;
import loc.dto.CartDTO;
import loc.dto.OrderDTO;
import loc.dto.OrderDetailDTO;
import loc.dto.ProductDTO;
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
            OrderDTO order = new OrderDTO();
            OrderDetailDTO orderDetail = new OrderDetailDTO();
            OrderDAO dao = new OrderDAO();
            ProductDAO proDAO = new ProductDAO();
            String uniqueOderID = "";
            String uniqueOderDetailID = "";
            String payment = request.getParameter("txtPayment");
            do {
                uniqueOderID = UUID.randomUUID().toString();
            } while (dao.checkOrderIDExist(uniqueOderID));

            for (ProductDTO product : cart.getCart().values()) {
                if (proDAO.checkProductQuantityAndStatus(product.getQuantity(), product.getProductID()) == false) {
                    valid = false;
                }
            }

            if (valid == true) {
                order = new OrderDTO(uniqueOderID, user.getUserID(), "", user.getName(), user.getAddress(), payment, total);
                if (proDAO.insertOrder(order)) {
                    for (ProductDTO tmpProductCart : cart.getCart().values()) {
                        do {
                            uniqueOderDetailID = UUID.randomUUID().toString();
                        } while (dao.checkOrderIDExist(uniqueOderDetailID));
                        ProductDTO tmpProductDTO = proDAO.getProductInfo(tmpProductCart.getProductID());
                        int newQuantity = tmpProductDTO.getQuantity() - tmpProductCart.getQuantity();
                        if (newQuantity >= 0) {
                            if (proDAO.updateProductQuantity(newQuantity, tmpProductDTO.getProductID())) {
                                orderDetail = new OrderDetailDTO(uniqueOderDetailID, order.getOrderID(), tmpProductCart.getProductID(), "Validating", tmpProductCart.getPrice(), tmpProductCart.getQuantity());
                                proDAO.insertOrderDetail(orderDetail);
                            }
                            if (newQuantity == 0) {
                                proDAO.updateProductStatus("inactive", tmpProductCart.getProductID());
                            }
                        }

                    }
                }
                cart = null;
                total = 0.0;
                session.setAttribute("TOTAL", total);
                session.setAttribute("CART", cart);
                request.setAttribute("ORDER_ID", uniqueOderID);
                request.setAttribute("STATE", 1);
                url = SUCCESS;
            } else {
                url = INVALID;
                cart = null;
                total = 0.0;
                session.setAttribute("TOTAL", total);
                session.setAttribute("CART", cart);
                request.setAttribute("ORDER_ID", uniqueOderID);
                request.setAttribute("STATE", 0);

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
