/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Component;

import DAO.ComponentDAO;
import Model.Component;
import Utils.NumberUtils;
import Utils.Pagination;
import Utils.SearchUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ComponentWarehouse", urlPatterns = {"/ComponentWarehouse"})
public class ComponentWarehouse extends HttpServlet {

    private final ComponentDAO componentDAO = new ComponentDAO();
    private static final int PAGE_SIZE = 5;

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
        String pageParam = request.getParameter("page");
        String paraSearch = SearchUtils.preprocessSearchQuery(request.getParameter("search"));
        int page = (NumberUtils.tryParseInt(pageParam) != null) ? NumberUtils.tryParseInt(pageParam) : 1;
        // Lấy page-size từ request, mặc định là PAGE_SIZE
        String pageSizeParam = request.getParameter("page-size");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        Integer pageSize;
        pageSize = (NumberUtils.tryParseInt(pageSizeParam) != null) ? NumberUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        //--------------------------------------------------------------------------
        List<Component> components = new ArrayList<>();
        int totalComponents = paraSearch == null || paraSearch.isBlank() ? componentDAO.getTotalComponents() : componentDAO.getTotalSearchComponents(paraSearch);
        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalComponents / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        page = page < 1 ? 1 : page;
        
        if (order != null && sort != null && (order.equals("asc") || order.equals("desc"))) {
            //xac nhan cac tham so de sort truyen vao la dung
            if (sort.equals("quantity") || sort.equals("name") || sort.equals("price") || sort.equals("code")) {
                String sortSQL;
                sortSQL = switch (sort) {
                    case "quantity" ->
                        "Quantity";
                    case "name" ->
                        "ComponentName";
                    case "code" ->
                        "ComponentCode";
                    default ->
                        "Price";
                };
                components = paraSearch == null || paraSearch.isBlank() ? componentDAO.getComponentsByPageSorted(page, pageSize, sortSQL, order)
                        : componentDAO.searchComponentsByPageSorted(paraSearch, page, pageSize, sortSQL, order);
            } else {
                components = paraSearch == null || paraSearch.isBlank() ? componentDAO.getComponentsByPage(page, pageSize) : componentDAO.searchComponentsByPage(paraSearch, page, pageSize);
            }
        } else {
            components = paraSearch == null || paraSearch.isBlank() ? componentDAO.getComponentsByPage(page, pageSize) : componentDAO.searchComponentsByPage(paraSearch, page, pageSize);
        }
        //Lay thong bao tu viec delete
        String delete = request.getParameter("delete");
        String deleteStatus;
        if (delete != null) {
            deleteStatus = delete.equals("1") ? "Success to delete" : "Fail to delete";
            request.setAttribute("deleteStatus", deleteStatus);
        }
        // Đặt các thuộc tính cho request
        request.setAttribute("tt",        request.getParameter("t"));
        request.setAttribute("totalComponents", totalComponents);
        request.setAttribute("search", paraSearch);
        request.setAttribute("totalPagesToShow", 5);
        request.setAttribute("listSize", Pagination.listPageSize(totalComponents));
        request.setAttribute("size", pageSize);
        request.setAttribute("components", components);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);
        // Chuyển tiếp đến trang JSP để hiển thị
        request.getRequestDispatcher("views/Component/ComponentWarehouse.jsp").forward(request, response);
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
