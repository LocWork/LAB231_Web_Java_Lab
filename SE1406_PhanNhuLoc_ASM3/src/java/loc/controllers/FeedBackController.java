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
import loc.dto.FeedBackDTO;
import loc.dto.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class FeedBackController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "LoadHistoryController";
    private static final String FAIL = "feedback.jsp";
    private static final Logger LOGGER = Logger.getLogger(FeedBackController.class);

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
            int score = 0;
            String scoreString = request.getParameter("txtScore");
            String description = request.getParameter("txtDescription");
            ExtraDAO extraDAO = new ExtraDAO();
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER_INFO");
            String feedbackID = "";
            if (roomID == null || roomID.isEmpty()) {
                valid = false;
            }

            if (description == null || description.isEmpty()) {
                description = "N/A";
            }
            if (scoreString == null || scoreString.isEmpty()) {
                valid = false;
            }

            if (valid == true) {
                score = Integer.parseInt(scoreString);
                if (score < 0 || score > 10) {
                    valid = false;
                }
            }

            if (valid == true) {
                if (extraDAO.validateUserBooking(roomID, user.getUserID())) {
                    do {
                        feedbackID = UUID.randomUUID().toString();
                    } while (extraDAO.checkFeedBackIDExist(feedbackID));
                    FeedBackDTO dto = new FeedBackDTO(feedbackID, roomID, user.getUserID(), description, score);
                    if (extraDAO.checkFeedBackExist(user.getUserID(), roomID)) {
                        if (extraDAO.updateExistingFeedBack(dto)) {
                            url = SUCCESS;
                            request.setAttribute("NOTE", "SUCCESS");
                        } else {
                            url = FAIL;
                            request.setAttribute("NOTE", "Fail");
                            request.setAttribute("ROOM_ID", roomID);
                        }
                    } else {
                        if (extraDAO.insertFeedBack(dto)) {
                            url = SUCCESS;
                            request.setAttribute("NOTE", "SUCCESS");
                        } else {
                            url = FAIL;
                            request.setAttribute("NOTE", "Fail");
                            request.setAttribute("ROOM_ID", roomID);
                        }
                    }
                } else {
                    url = FAIL;
                    request.setAttribute("NOTE", "Fail");
                    request.setAttribute("ROOM_ID", roomID);
                }
            } else {
                url = FAIL;
                request.setAttribute("NOTE", "Fail");
                request.setAttribute("ROOM_ID", roomID);
            }

        } catch (Exception e) {
            LOGGER.error("Error at FeedBackController: " + e.toString());
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
