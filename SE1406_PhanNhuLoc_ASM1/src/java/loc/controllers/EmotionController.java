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
import loc.daos.EmotionDAO;
import loc.daos.NotifyDAO;
import loc.dtos.AccountDTO;
import loc.dtos.NotifyDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class EmotionController extends HttpServlet {

    public static final String ERROR = "error.jsp";
    public static final String SUCCESS = "ArticleController";
    private static final Logger LOGGER = Logger.getLogger(EmotionController.class);

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
            //set up
            String choice = request.getParameter("type");
            String postID = request.getParameter("txtId");
            NotifyDAO ndao = new NotifyDAO();
            String notifyID = ndao.getNotifyID();
            HttpSession session = request.getSession();
            AccountDTO account = (AccountDTO) session.getAttribute("USER_INFO");
            EmotionDAO edao = new EmotionDAO();
            String id = edao.getEmotionID();
            boolean currentLikeStatus = false;
            boolean currentDislikeStatus = false;
            boolean userExist = false;

            //Check if the user has like the post if not add the user to the emotion
            userExist = edao.checkUserFromtblEmotion(postID, account.getEmail());
            if (userExist == false) {
                edao.insertUserEmotion(id, postID, account.getEmail());
            }
            //Update emotion
            //only one

            currentLikeStatus = edao.getLikeStatus(postID, account.getEmail());

            currentDislikeStatus = edao.getDisLikeStatus(postID, account.getEmail());

            if (choice.equals("Like")) {

                if (edao.updateLike(currentLikeStatus, currentDislikeStatus, postID, account.getEmail())) {
                    NotifyDTO notify = new NotifyDTO(notifyID, postID, account.getEmail(), edao.getEmotionDate(postID, account.getEmail()), "Like");

                    if (ndao.insertNotify(notify)) {
                        url = SUCCESS;
                    }
                }

            } else if (choice.equals("Dislike")) {

                if (edao.updateDisLike(currentLikeStatus, currentDislikeStatus, postID, account.getEmail()) == true) {
                    NotifyDTO notify = new NotifyDTO(notifyID, postID, account.getEmail(), edao.getEmotionDate(postID, account.getEmail()), "DisLike");
                    if (ndao.insertNotify(notify)) {
                        url = SUCCESS;
                    }
                }

            }

        } catch (Exception e) {
            LOGGER.error("Error at EmotionController");
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
