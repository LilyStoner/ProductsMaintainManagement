/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarrantyCard;

import DAO.CustomerDAO;
import DAO.WarrantyCardDAO;
import Model.ProductDetail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddWarrantyCard", urlPatterns = {"/WarrantyCard/Add"})
public class AddWarrantyCard extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

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
        String productCode = request.getParameter("productCode");
        String issue = request.getParameter("issue");
        if (issue != null) {
            if (warrantyCardDAO.createWarrantyCard(productCode, issue)) {
                response.sendRedirect("../WarrantyCard?create=true");
                return;
            } else {
                request.setAttribute("createFail", "Fail to create card");
            }
        }
        ProductDetail pd = warrantyCardDAO.getProductDetailByCode(productCode);
        if (pd == null && productCode!=null) {
            request.setAttribute("NotFoundProduct", "No product has this code!");
        } else {
            request.setAttribute("pd", pd);
            if(pd!=null)request.setAttribute("cusID", customerDAO.getCustomerByEmail(pd.getEmail()).getCustomerID());
        }

        request.setAttribute("ProductCode", productCode);
        request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardCreate.jsp").forward(request, response);
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
