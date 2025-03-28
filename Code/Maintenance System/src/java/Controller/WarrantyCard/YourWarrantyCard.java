/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarrantyCard;

import DAO.WarrantyCardDAO;
import Model.Customer;
import Utils.Pagination;
import Model.WarrantyCard;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class YourWarrantyCard extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet YourWarrantyCard</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet YourWarrantyCard at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = request.getSession();

        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect("HomePage.jsp");
            return;
        }
         
       
        WarrantyCardDAO warrantyCardDao = new WarrantyCardDAO();
        String warrantyCardCode = request.getParameter("warrantyCardCode");
        String productName = request.getParameter("productName");
        String warrantyStatus = request.getParameter("warrantyStatus");
        String createdDate = request.getParameter("createdDate");
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("page-size");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String createStatus = request.getParameter("create");
        if (createStatus != null && createStatus.equals("true")) {
            request.setAttribute("createStatus", "Card created successfully");
        }

        int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
        Integer pageSize;
        pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : 5;

        List<WarrantyCard> listWarrantyCard = new ArrayList<>();
        int totalWarrantyCard = 0;
        int totalPages = 1;
        int offset = (page - 1) * pageSize;

    
            listWarrantyCard = warrantyCardDao.getWarrantyCardByCustomerID(customer.getCustomerID(), warrantyCardCode, productName, warrantyStatus,createdDate, sort, order, offset, pageSize);
            totalWarrantyCard = warrantyCardDao.getPageWarrantyCardByCustomerID(customer.getCustomerID(), warrantyCardCode, productName ,warrantyStatus,createdDate);
            totalPages = (int) Math.ceil((double) totalWarrantyCard / pageSize);

            // Lay thong tin phan trang
            Pagination pagination = new Pagination();
            pagination.setPageSize(pageSize);
            pagination.setCurrentPage(page);
            pagination.setSearchFields(new String[]{"warrantyCardCode", "productName","warrantyStatus", "createDate"});
            pagination.setSearchValues(new String[]{warrantyCardCode, productName,warrantyStatus,createdDate});
            pagination.setSort(sort);
            pagination.setOrder(order);
            pagination.setUrlPattern("/yourwarrantycard");
            pagination.setTotalPagesToShow(5);
            pagination.setTotalPages(totalPages);
            pagination.setListPageSize(totalWarrantyCard);
            request.setAttribute("pagination", pagination);

        
        // Set Atribute
        
        request.setAttribute("warrantyCardCode", warrantyCardCode);
        request.setAttribute("productName", productName );
        request.setAttribute("createDate", createdDate);
        request.setAttribute("warrantyStatus", warrantyStatus);
        request.setAttribute("size", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("listWarrantyCard", listWarrantyCard );
        request.getRequestDispatcher("YourWarrantyCard.jsp").forward(request, response);
        
       
    

    
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
