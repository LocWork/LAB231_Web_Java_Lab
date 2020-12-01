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
import loc.dao.ExtraDAO;
import loc.dto.CouponDTO;
import loc.error.CouponError;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class CreateCouponController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "coupon.jsp";
    private static final Logger LOGGER = Logger.getLogger(CreateCouponController.class);

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
            String name = request.getParameter("txtName");
            String amountString = request.getParameter("txtAmount");
            String exdate = request.getParameter("txtExDate");
            int amount = 0;
            CouponError error = new CouponError();
            ExtraDAO dao = new ExtraDAO();

            if (code == null || code.isEmpty()) {
                valid = false;
                error.setCodeID("Code can't be empty");
            }

            if (dao.checkCodeIDExist(code)) {
                valid = false;
                error.setCodeID("CodeID already exist");
            }

            if (name == null || name.isEmpty()) {
                valid = false;
                error.setCodeName("Name can't be empty");
            }

            if (amountString == null || amountString.isEmpty()) {
                valid = false;
                error.setAmount("Amount can't be empty");
            }

            if (exdate == null || exdate.isEmpty()) {
                valid = false;
                error.setExpireDate("ExpireDate can't be empty");
            }

            if (valid == true) {
                amount = Integer.parseInt(amountString);
                if (amount < 1 || amount > 100) {
                    valid = false;
                    error.setAmount("Invalid amount");
                }
            }

            if (valid == true) {
                CouponDTO cdto = new CouponDTO(code, name, "", exdate, amount);
                if (dao.insertCoupon(cdto)) {
                    request.setAttribute("NOTE", "Coupon has been created");
                } else {
                    request.setAttribute("NOTE", "Unable to Create");
                }
            } else {
                request.setAttribute("ERROR", error);
            }
            url = SUCCESS;
        } catch (Exception e) {
            LOGGER.error("Error at CreateCouponController: " + e.toString());
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
