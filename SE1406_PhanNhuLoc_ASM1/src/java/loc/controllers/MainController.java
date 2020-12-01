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
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class MainController extends HttpServlet {

    public static final String ERROR = "error.jsp";
    public static final String REGISTER = "RegisterController";
    public static final String LOGIN = "LoginController";
    public static final String LOGOUT = "LogoutController";
    public static final String SEARCH = "SearchController";
    public static final String VIEW_ARTICLE = "ArticleController";
    public static final String CHOOSE_EMOTION = "EmotionController";
    public static final String DELETE_COMMENT = "DeleteCommentController";
    public static final String CREATE_ARTICLE = "CreateArticleController";
    public static final String DELETE_ARTICLE = "DeleteArticleController";
    public static final String COMMENT_ARTICLE = "CommentArticleController";
    public static final String NOTIFY = "NotifyController";
    private static final Logger LOGGER = Logger.getLogger(MainController.class);

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

            String action = request.getParameter("action");
            if (action.equals("Register")) {
                url = REGISTER;
            } else if (action.equals("Login")) {
                url = LOGIN;
            } else if (action.equals("Logout")) {
                url = LOGOUT;
            } else if (action.equals("Search")) {
                url = SEARCH;
            } else if (action.equals("ViewArticle")) {
                url = VIEW_ARTICLE;
            } else if (action.equals("ChooseEmotion")) {
                url = CHOOSE_EMOTION;
            } else if (action.equals("DeleteComment")) {
                url = DELETE_COMMENT;
            } else if (action.equals("CreateArticle")) {
                url = CREATE_ARTICLE;
            } else if (action.equals("DeleteArticle")) {
                url = DELETE_ARTICLE;
            } else if (action.equals("CommentArticle")) {
                url = COMMENT_ARTICLE;
            } else if (action.equals("Notify")) {
                url = NOTIFY;
            } else if (action.equals("Next")) {
                url = SEARCH;
            } else if (action.equals("Previous")) {
                url = SEARCH;
            }
        } catch (Exception e) {
            LOGGER.error("Error at MainController");
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
