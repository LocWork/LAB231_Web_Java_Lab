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
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class FeedBackInfoController extends HttpServlet {
    
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "feedback.jsp";
    private static final String FAIL = "LoadHistoryController";
    private static final Logger LOGGER = Logger.getLogger(FeedBackInfoController.class);
    
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
            String checkOutDate = request.getParameter("txtCheckOutDate");           
            if(roomID == null || roomID.isEmpty()){
                valid = false;
            }
            
            if(checkOutDate == null || checkOutDate.isEmpty()){
                valid = false;
            }
            
            if(valid == true){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date checkDate = formatter.parse(checkOutDate);
                Date date = new Date();
                if(checkDate.after(date)){
                    valid = false;
                }
            }
            
            if(valid == true){
                url = SUCCESS;
                request.setAttribute("ROOM_ID", roomID);
            }else{
                url = FAIL;
            }
            

        } catch (Exception e) {
            LOGGER.error("Error at FeedBackInfoController: " + e.toString());
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
