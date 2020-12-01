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
import loc.daos.ArticleDAO;
import loc.dtos.ArticleDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class SearchController extends HttpServlet {

    public static final String ERROR = "error.jsp";
    public static final String SUCCESS = "home.jsp";
    private static final Logger LOGGER = Logger.getLogger(SearchController.class);

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
            String search = request.getParameter("txtSearch");
            String action = request.getParameter("action");
            int pageIndex;
            int pageSize = 20;
            int numberOfArticle = 0;
            int pageCount = 0;
            if (request.getParameter("txtPageIndex") == null) {
                pageIndex = 1;
            }
            if (search == null) {
                search = "";
            }
            ArticleDAO dao = new ArticleDAO();

            if (action == null) {
                action = "Search";
            }

            List<ArticleDTO> list = new ArrayList<>();
            if (action.equals("Search")) {
                pageIndex = 1;
                numberOfArticle = dao.countArticle(search);
                pageCount = (int) Math.ceil(numberOfArticle / (double) pageSize);
                list = dao.searchArticle(search, pageIndex, pageSize);

            } else {
                pageCount = Integer.parseInt(request.getParameter("txtPageCount"));
                pageIndex = Integer.parseInt(request.getParameter("txtPageIndex"));
            }

            if (action.equals("Next") && pageIndex < pageCount) {
                pageIndex = Integer.parseInt(request.getParameter("txtPageIndex"));

                pageIndex = pageIndex + 1;
                list = dao.searchArticle(search, pageIndex, pageSize);

            }
            if (action.equals("Previous") && pageIndex > 1) {
                pageIndex = Integer.parseInt(request.getParameter("txtPageIndex"));
                pageIndex = pageIndex - 1;
                list = dao.searchArticle(search, pageIndex, pageSize);
            }

            request.setAttribute("PAGE_INDEX", pageIndex);

            if (!list.isEmpty()) {
                url = SUCCESS;
                request.setAttribute("SEARCH", search);
                request.setAttribute("LIST", list);
                request.setAttribute("PAGE_COUNT", pageCount);
            } else {
                url = SUCCESS;
                request.setAttribute("PAGE_COUNT", pageCount);
                request.setAttribute("SEARCH", search);
            }
        } catch (Exception e) {
            LOGGER.error("Error at SearchController");
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
