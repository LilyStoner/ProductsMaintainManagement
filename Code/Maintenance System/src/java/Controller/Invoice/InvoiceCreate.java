/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Invoice;

import DAO.InvoiceDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Invoice;
import Model.Staff;
import Model.WarrantyCard;
import Model.WarrantyCardProcess;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="InvoiceCreate", urlPatterns={"/Invoice/Create"})
public class InvoiceCreate extends HttpServlet {
    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final WarrantyCardProcessDAO wcpDAO = new WarrantyCardProcessDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         //
        String warrantyCardIdParam = request.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIdParam);

        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        WarrantyCardProcess wcp = wcpDAO.getLatestProcessByWarrantyCardId(warrantyCardId);

        if (wcp == null || !wcp.getAction().equals("fixed")) {
            request.setAttribute("updateAlert0", "Product isn't fixed");
             response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
        String invoiceType = request.getParameter("invoiceType");

        // Integer receivedBy =;
        Integer customerId = wc.getCustomerID();
        Date dueDate = Date.from(Instant.now().plusSeconds(3 * 24 * 60 * 3600));

        // Kiểm tra dữ liệu đầu vào
        if (invoiceType == null || (!"RepairContractorToTechnician".equals(invoiceType) && !"TechnicianToCustomer".equals(invoiceType))) {
            request.setAttribute("updateAlert0", "Invalid invoice type.");
            request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(request, response);
        }

        if ("TechnicianToCustomer".equals(invoiceType) && customerId == null) {
            request.setAttribute("updateAlert0", "Customer ID is required for this invoice type.");
            request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(request, response);
        }

        // Tạo mã hóa đơn tự động (ví dụ: INV-YYYYMMDD-<random>)
        String invoiceNumber = "INV-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + (int) (Math.random() * 10000);

        // Tạo đối tượng Invoice
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceType(invoiceType);
        invoice.setWarrantyCardID(warrantyCardId);
        invoice.setAmount(warrantyCardDAO.getPriceOfWarrantyCard(warrantyCardId));
        invoice.setDueDate(dueDate);
        invoice.setStatus("pending");
        invoice.setCreatedBy(staff.getStaffID());
        //invoice.setReceivedBy("RepairContractorToTechnician".equals(invoiceType) ? receivedBy : null);
        invoice.setCustomerID("TechnicianToCustomer".equals(invoiceType) ? customerId : null);

        // Lưu invoice vào cơ sở dữ liệu
        boolean success = invoiceDAO.addInvoice(invoice);
        if (success) {
            request.setAttribute("updateAlert1", "Invoice created successfully!");
        } else {
            request.setAttribute("updateAlert0", "Failed to create invoice.");
            request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(request, response);
        }
        //
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
