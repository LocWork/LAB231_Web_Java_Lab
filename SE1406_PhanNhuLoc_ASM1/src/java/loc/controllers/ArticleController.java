/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import loc.daos.ArticleDAO;
import loc.daos.CommentDAO;
import loc.daos.EmotionDAO;
import loc.dtos.ArticleDTO;
import loc.dtos.CommentDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class ArticleController extends HttpServlet {

    public static final String ERROR = "error.jsp";
    public static final String SUCCESS = "article.jsp";
    public static final String DELETED = "home.jsp";
    private static final Logger LOGGER = Logger.getLogger(ArticleController.class);

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
            //Basic settup
            String search = request.getParameter("txtSearch");
            String id = request.getParameter("txtId");
            ArticleDAO dao = new ArticleDAO();
            EmotionDAO edao = new EmotionDAO();
            CommentDAO cdao = new CommentDAO();
            List<CommentDTO> list;
            ArticleDTO dto = dao.getArticle(id);

            request.setAttribute("SEARCH", search);
            int like = edao.getLikeCount(id);
            int disLike = edao.getDislikeCount(id);
            if (dto.getStatus() == 0) {
                valid = false;
            }

            if (valid == true) {
                //Move to the article page
                if (dto != null) {
                    //Get comment
                    list = cdao.getArticleComment(id);
                    if (!list.isEmpty()) {
                        request.setAttribute("COMMENT_LIST", list);
                    }
                    //Get emotion
                    request.setAttribute("EMOTION_LIKE", like);
                    request.setAttribute("EMOTION_DISLIKE", disLike);

                    //Get article
                    url = SUCCESS;
                    request.setAttribute("ARTICLE", dto);

                } else {
                    url = DELETED;
                }
            }else{
                url = DELETED;
            }

        } catch (Exception e) {
            LOGGER.error("Error at Article Controller: " + e.toString());
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
