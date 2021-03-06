/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loc.dao.ProductDAO;
import loc.dto.LogDTO;
import loc.dto.ProductDTO;
import loc.dto.UserDTO;
import loc.error.ProductError;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 *
 * @author hi
 */
public class UpdateProductController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "ProductInfoController";
    private static final String INVALID = "updateProduct.jsp";
    private static final Logger LOGGER = Logger.getLogger(UpdateProductController.class);

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

            boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
            if (!isMultiPart) {
                url = INVALID;
            } else {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    LOGGER.error("Error at UpdateProductController: " + e.toString());
                }
                Iterator iter = items.iterator();
                Hashtable params = new Hashtable();
                String fileName = null;
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        params.put(item.getFieldName(), item.getString());
                    } else {
                        String itemName = item.getName();
                        fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                        String realPath = getServletContext().getRealPath("/") + "images\\" + fileName;
                        File saveFile = new File(realPath);
                        File destFile = new File(realPath);
                        if (destFile.exists()) {
                            destFile.delete();
                            item.write(saveFile);
                        } else {
                            item.write(saveFile);
                        }
                    }
                }
                String productID = (String) params.get("txtProductID");
                String name = (String) params.get("txtName");
                String priceString = (String) params.get("txtPrice");
                String description = (String) params.get("txtDescription");
                String quantityString = (String) params.get("txtQuantity");
                String createDateString = (String) params.get("txtCreateDate");
                String expireDateString = (String) params.get("txtExpireDate");
                String categoryID = (String) params.get("txtCategoryID");
                String status = (String) params.get("txtStatus");
                ProductDAO dao = new ProductDAO();
                ProductError error = new ProductError();
                HttpSession session = request.getSession();
                UserDTO user = (UserDTO) session.getAttribute("USER_INFO");
                session.setAttribute("PRODUCT_ID", productID);
                session.setAttribute("ERROR_PRODUCT", error);
                double price = 0;
                int quantity = 0;
                if (name.isEmpty()) {
                    valid = false;
                    error.setName("Name can't be empty");
                }
                if (priceString.isEmpty()) {
                    valid = false;
                    error.setPrice("Price can't be empty");
                }
                if (description.isEmpty()) {
                    valid = false;
                    error.setDescription("Description can't be empty");
                }
                if (quantityString.isEmpty()) {
                    valid = false;
                    error.setQuantity("Quantity can't be empty");
                }

                if (createDateString.isEmpty()) {
                    valid = false;
                    error.setCreateDate("CreateDate can't be empty");
                }
                if (expireDateString.isEmpty()) {
                    valid = false;
                    error.setExpireDate("ExpireDate can't be empty");
                }

                if (valid == true) {
                    price = Double.parseDouble(priceString);
                    quantity = Integer.parseInt(quantityString);

                    if (price < 0) {
                        valid = false;
                        error.setPrice("Price can't be negative");
                    }
                    if (price > 500) {
                        valid = false;
                        error.setPrice("Price can't be Over 500");
                    }
                    if (quantity < 0) {
                        valid = false;
                        error.setQuantity("Quantity can't be negative");
                    }

                    if (quantity == 0) {
                        status = "inactive";
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date creDate = formatter.parse(createDateString);
                    Date exDate = formatter.parse(expireDateString);
                    Date today = new Date();

                    if (creDate.after(today)) {
                        status = "inactive";
                    }

                    if (exDate.equals(today) || exDate.before(today)) {
                        status = "inactive";
                    }

                    if (creDate.after(exDate) || exDate.before(creDate)) {
                        valid = false;
                        error.setCreateDate("Create date can't be over expire date");
                        error.setExpireDate("Expire date can't be before create date");
                    }

                }
                if (valid == true) {
                    ProductDTO dto = new ProductDTO(productID, name, fileName, categoryID, price, quantity, description, createDateString, expireDateString, "", status);
                    if (dao.updateProduct(dto)) {
                        String uniqueID = null;
                        do {
                            uniqueID = UUID.randomUUID().toString();
                        } while (dao.checkLogIDExist(uniqueID));
                        LogDTO log = new LogDTO(uniqueID, user.getUserID(), productID, "");
                        if (dao.insertNewLog(log)) {
                            url = SUCCESS;
                            request.setAttribute("NOTE", "Product has been updated");
                        } else {
                            url = INVALID;
                        }

                    } else {
                        url = INVALID;
                    }
                } else {
                    session.setAttribute("ERROR_PRODUCT", error);
                    url = INVALID;
                }

            }
        } catch (Exception e) {
            LOGGER.error("Error at UpdateController: " + e.toString());
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
