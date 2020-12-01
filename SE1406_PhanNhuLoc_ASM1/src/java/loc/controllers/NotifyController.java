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
import javax.servlet.http.HttpSession;
import loc.daos.NotifyDAO;
import loc.dtos.AccountDTO;
import loc.dtos.NotifyDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class NotifyController extends HttpServlet {

    public static final String ERROR = "error.jsp";
    public static final String SUCCESS = "notify.jsp";
    private static final Logger LOGGER = Logger.getLogger(NotifyController.class);

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
            NotifyDAO dao = new NotifyDAO();
            HttpSession session = request.getSession();
            AccountDTO dto = (AccountDTO) session.getAttribute("USER_INFO");
            List<NotifyDTO> notifyList = dao.getNotifyList(dto.getEmail());
            List<String> titleList = new ArrayList<>();

            if (notifyList != null && !notifyList.isEmpty()) {
                for (int i = 0; i < notifyList.size(); i++) {
                    String title = dao.getArticleTitle(notifyList.get(i).getPostID());
                    titleList.add(title);
                }
            }
            if (notifyList != null && !notifyList.isEmpty()) {
                for (int i = 0; i < notifyList.size(); i++) {
                    notifyList.get(i).setId(titleList.get(i));
                }
            }

            request.setAttribute("NOTIFY_LIST", notifyList);
            request.setAttribute("SEARCH", "");
            url = SUCCESS;
        } catch (Exception e) {
            LOGGER.error("Error at NotifyController: " + e.toString());
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
