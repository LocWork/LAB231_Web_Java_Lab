/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import loc.dao.ProductDAO;
import loc.dto.ProductDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class ProductInfoController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "productInfoPage.jsp";
    private static final String DELETED = "ProductPageController";
    private static final Logger LOGGER = Logger.getLogger(ProductInfoController.class);

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
            String id = request.getParameter("txtProductID");
            ProductDAO dao = new ProductDAO();
            ProductDTO dto = dao.getProductInfo(id);
            if (dto != null) {
                if (!dto.getStatus().equals("active")) {
                    valid = false;

                }

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date creDate = formatter.parse(dto.getCreateDate());
                Date exDate = formatter.parse(dto.getExpireDate());
                Date today = new Date();
                if (!dto.getStatus().equals("active")) {
                    valid = false;
                }
                if (exDate.equals(today) || exDate.before(today)) {
                    valid = false;
                }
                if (creDate.after(exDate) || exDate.before(creDate)) {
                    valid = false;
                }
                if (valid == true) {
                    url = SUCCESS;
                    request.setAttribute("PRODUCT_INFO", dto);
                } else {
                    url = DELETED;
                    valid = false;
                }
            } else {
                url = DELETED;
            }
        } catch (Exception e) {
            LOGGER.error("Error at ProductInfoController: " + e.toString());
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
