package Controller.WarrantyCard;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Customer;
import Model.Staff;
import Model.UnknownProduct;
import Model.WarrantyCard;
import Model.WarrantyCardProcess;
import Utils.OtherUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.PrintWriter;

@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class AddWarrantyCardUnknownProduct extends HttpServlet {
        private final WarrantyCardProcessDAO WarrantyCardProcessDAO = new WarrantyCardProcessDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu cần, xử lý cho GET request
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
        try {
            // Lấy dữ liệu từ form
            String customerIdStr = request.getParameter("customerId");
            String productIdStr = request.getParameter("productId");
            int warrantyProductId = Integer.parseInt(request.getParameter("warrantyProductId"));
            String issueDescription = request.getParameter("issueDescription");
            String warrantyStatus = request.getParameter("warrantyStatus");
            String returnDate = request.getParameter("returnDate");
            String doneDate = request.getParameter("doneDate");
            String completeDate = request.getParameter("completeDate");
            String cancelDate = request.getParameter("cancelDate");

            CustomerDAO customerDAO = new CustomerDAO();
            ProductDAO productDAO = new ProductDAO();
            StaffDAO staffDAO = new StaffDAO();
            Customer customer = customerDAO.getCustomerByID(Integer.parseInt(customerIdStr));
            UnknownProduct unknownProduct = productDAO.getUnknownProductById(Integer.parseInt(productIdStr));
            List<Staff> technicians = staffDAO.getAllTechnicians();

            // Lưu các dữ liệu form vào request để hiển thị lại khi có lỗi
            request.setAttribute("warrantyProductId", warrantyProductId);
            request.setAttribute("issueDescription", issueDescription);
            request.setAttribute("warrantyStatus", warrantyStatus);
            request.setAttribute("returnDate", returnDate);
            request.setAttribute("doneDate", doneDate);
            request.setAttribute("completeDate", completeDate);
            request.setAttribute("cancelDate", cancelDate);
            request.setAttribute("customer", customer);
            request.setAttribute("unknownProduct", unknownProduct);
            request.setAttribute("staffList", technicians);

            // Xử lý các file media được upload
            Collection<Part> parts = request.getParts();
            List<Part> mediaParts = new ArrayList<>();
            for (Part part : parts) {
                if (part.getName().equals("warrantyMedia") && part.getSize() > 0) {
                    mediaParts.add(part);
                }
            }
            if (mediaParts.isEmpty()) {
                request.setAttribute("errorMessage", "Please select at least one media file.");
                request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                return;
            }
            WarrantyCard add = warrantyCardDAO.createWarrantyCard(warrantyProductId, issueDescription, warrantyStatus, returnDate, doneDate, completeDate, cancelDate);

            if (add == null) {
                request.setAttribute("errorMessage", "Failed to create warranty card. Please try again.");
                request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                return;
            } else {
                HttpSession session = request.getSession();
                Staff staff = (Staff) session.getAttribute("staff");
                int handlerID = (staff != null) ? staff.getStaffID() : (customer != null ? customer.getCustomerID() : -1);
                WarrantyCardProcess wcp = new WarrantyCardProcess();
                wcp.setWarrantyCardID(add.getWarrantyCardID());
                wcp.setHandlerID(handlerID);
                wcp.setAction("create");
                wcp.setNote(staff != null ? "Created by staff (Repair)" : "Created by customer");
                WarrantyCardProcessDAO.addWarrantyCardProcess(wcp);
                WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(wcp.getWarrantyCardID());
                if (customer == null) {
                    wc.setHandlerID(handlerID);
                    warrantyCardDAO.updateWarrantyCard(wc);
                    wcp.setAction("receive");
                    wcp.setHandlerID(handlerID);
                    wcp.setNote("technician received");
                    WarrantyCardProcessDAO.addWarrantyCardProcess(wcp);
                }
            }

            int warrantyCardId = add.getWarrantyCardID();

            PrintWriter out = response.getWriter();
            out.print(warrantyProductId);

            out.print(warrantyCardId);
            // Xác định các định dạng file hợp lệ
            String[] allowedImageExtensions = {".jpg", ".jpeg", ".png"};
            String[] allowedVideoExtensions = {".mp4", ".avi", ".mov", ".wmv"};

            // Xử lý từng file media
            for (Part mediaPart : mediaParts) {
                String fileName = mediaPart.getSubmittedFileName();
                if (fileName == null || fileName.isEmpty()) {
                    continue;
                }
                String lowerCaseFileName = fileName.toLowerCase();
                String mediaType = "";
                boolean isValid = false;
                for (String ext : allowedImageExtensions) {
                    if (lowerCaseFileName.endsWith(ext)) {
                        mediaType = "image";
                        isValid = true;
                        break;
                    }
                }
                if (!isValid) {
                    for (String ext : allowedVideoExtensions) {
                        if (lowerCaseFileName.endsWith(ext)) {
                            mediaType = "video";
                            isValid = true;
                            break;
                        }
                    }
                }
                if (!isValid) {
                    request.setAttribute("errorMessage", "Media file " + fileName + " must be in a valid format (JPG, JPEG, PNG for images; MP4, AVI, MOV, WMV for videos).");
                    request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                    return;
                }
                // Lưu file media và nhận về đường dẫn file
                String mediaPath = OtherUtils.saveImage(mediaPart, request, "img/photos");
                // Thêm bản ghi vào bảng Media với ObjectType là "WarrantyCard"
                boolean mediaAdded = warrantyCardDAO.addMedia(warrantyCardId, "WarrantyCard", mediaPath, mediaType);
                if (!mediaAdded) {
                    request.setAttribute("errorMessage", "Failed to add media: " + fileName);
                    request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                    return;
                }
            }
            request.setAttribute("successMessage", "Create Warranty Card Success");
            request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);

        } catch (ServletException | IOException | NumberFormatException e) {
        }
    }
}
