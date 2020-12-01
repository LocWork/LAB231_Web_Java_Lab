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
import loc.daos.CommentDAO;
import loc.daos.NotifyDAO;
import loc.dtos.NotifyDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class CommentArticleController extends HttpServlet {

    public static final String ERROR = "error.jsp";
    public static final String SUCCESS = "ArticleController";
    private static final Logger LOGGER = Logger.getLogger(CommentArticleController.class);

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
            String postID = request.getParameter("txtId");
            String email = request.getParameter("txtEmail");
            String comment = request.getParameter("txtComment");
            NotifyDAO ndao = new NotifyDAO();
            String notifyID = ndao.getNotifyID();
            CommentDAO cdao = new CommentDAO();
            String id = cdao.getCommentID();

            if (comment.isEmpty()) {
                valid = false;
            }
            if (valid == true) {
                if (cdao.insertComment(id, postID, email, comment)) {
                    String date = cdao.getCommentDate(id);
                    NotifyDTO notify = new NotifyDTO(notifyID, postID, email, date, "Comment");
                    if (ndao.insertNotify(notify)) {
                        url = SUCCESS;
                    }
                }
            } else {
                url = SUCCESS;
            }
        } catch (Exception e) {
            LOGGER.error("Error at CommentArticleController: " + e.toString());
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
