/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Component;

import DAO.ComponentDAO;
import Model.Component;
import Utils.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ComponentAction", urlPatterns = {"/ComponentWarehouse/Detail", "/ComponentWarehouse/Delete", "/ComponentWarehouse/Edit", "/ComponentWarehouse/Add"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 100, // 100MB
        maxRequestSize = 1024 * 1024 * 200 // 200MB
)
public class ComponentAction extends HttpServlet {

    private final ComponentDAO componentDAO = new ComponentDAO();
    private static final int PAGE_SIZE = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            if (action.equals("/ComponentWarehouse/Add")) {
                handleAddComponent(request, response);
            } else if (action.startsWith("/ComponentWarehouse")) {
                handleComponentActions(request, response, action);
            } else {
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            }
        } catch (ServletException | IOException e) {
            response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
        }
        // Xử lý thêm mới component

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

// Xử lý thêm mới Component
    private void handleAddComponent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer maxUploadSizeImageMB = (Integer) request.getServletContext().getAttribute("maxUploadSizeImageMB");

        // Nếu maxSizeMB chưa có, đặt giá trị mặc định 5MB
        if (maxUploadSizeImageMB == null) {
            maxUploadSizeImageMB = 5; // Giá trị mặc định
            request.getServletContext().setAttribute("maxUploadSizeImageMB", maxUploadSizeImageMB);
        }

        String newName = request.getParameter("Name");
        String newCode = request.getParameter("Code");
        String newBrand = request.getParameter("Brand");
        String newType = request.getParameter("Type");
        Integer newQuantity = FormatUtils.tryParseInt(request.getParameter("Quantity"));
        Double newPrice = FormatUtils.tryParseDouble(request.getParameter("Price"));
        List<String> imagePaths = new ArrayList<>();
        List<String> videoPaths = new ArrayList<>();
        boolean canAdd = true;
        for (Part part : request.getParts()) {
            if ("mediaFiles".equals(part.getName()) && part.getSize() > 0) {
                String mimeType = part.getContentType();
                String mediaPath;
                if (mimeType != null && mimeType.startsWith("video/")) {
                    mediaPath = OtherUtils.saveVideo(part, request, "media/component");
                    if (mediaPath != null && !mediaPath.startsWith("Invalid") && !mediaPath.startsWith("File is too large")) {
                        videoPaths.add(mediaPath);
                    } else {
                        canAdd = false;
                        request.setAttribute("pictureAlert", mediaPath != null ? mediaPath : "Error uploading media");
                    }
                } else {
                    mediaPath = OtherUtils.saveImage(part, request, "media/component");
                    if (mediaPath != null && !mediaPath.startsWith("Invalid") && !mediaPath.startsWith("File is too large")) {
                        imagePaths.add(mediaPath);
                    } else {
                        canAdd = false;
                        request.setAttribute("pictureAlert", mediaPath != null ? mediaPath : "Error uploading media");
                    }
                }

            }
        }

        // Kiểm tra dữ liệu đầu vào
        request.setAttribute("brandList", componentDAO.getListBrand());
        request.setAttribute("typeList", componentDAO.getListType());
        if (newName == null) {
            canAdd = false;
        } else if (newName.isBlank()) {
            request.setAttribute("nameAlert", "Name must not be empty!");
            canAdd = false;
        } else {
            request.setAttribute("name", newName);
        }
        if (newCode == null) {
            canAdd = false;
        } else if (newCode.isBlank()) {
            request.setAttribute("codeAlert", "Code must not be empty!");
            canAdd = false;
        } else if (componentDAO.isComponentCodeExist(newCode)) {
            request.setAttribute("codeAlert", "Code existed, choose another!");
            canAdd = false;
        } else {
            request.setAttribute("code", newCode);
        }
        if (componentDAO.getTypeID(newType) == null) {
            canAdd = false;
        } else {
            request.setAttribute("type", newType);
        }
        if (componentDAO.getBrandID(newBrand) == null) {
            canAdd = false;
        } else {
            request.setAttribute("brand", newBrand);
        }
        if (newQuantity == null) {
            canAdd = false;
        } else if (newQuantity < 0) {
            request.setAttribute("quantityAlert", "Quantity must be an integer greater than or equal to 0");
            canAdd = false;
        } else {
            request.setAttribute("quantity", newQuantity);
        }

        if (newPrice == null) {
            canAdd = false;
        } else if (newPrice < 0) {
            request.setAttribute("priceAlert", "Price must be a float greater than or equal to 0");
            canAdd = false;
        } else {
            request.setAttribute("price", newPrice);
        }

        // Nếu dữ liệu hợp lệ, lưu ảnh và thêm Component
        // Khong hop le thi tra lai trang Add
        if (canAdd) {
            Component component = new Component();
            component.setComponentName(newName.trim());
            component.setComponentCode(newCode.trim());
            component.setType(newType);
            component.setBrand(newBrand);
            component.setPrice(newPrice);
            component.setQuantity(newQuantity);
            component.setImages(imagePaths);
            component.setVideos(videoPaths);
            boolean add = componentDAO.add(component);
            Component addedComponent = componentDAO.getLast();
            if (add) {
                request.setAttribute("addAlert1", "Added to warehouse");
                request.setAttribute("component", addedComponent);
                request.getRequestDispatcher("/views/Component/ComponentDetail.jsp").forward(request, response);
            } else {
                request.setAttribute("addAlert0", "Fail add to warehouse");
                request.getRequestDispatcher("/views/Component/ComponentAdd.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/views/Component/ComponentAdd.jsp").forward(request, response);
        }
    }

    private void handleEditComponent(HttpServletRequest request, HttpServletResponse response, Component component)
            throws ServletException, IOException {
        Integer maxUploadSizeImageMB = (Integer) request.getServletContext().getAttribute("maxUploadSizeImageMB");

        // Nếu maxSizeMB chưa có, đặt giá trị mặc định 5MB
        if (maxUploadSizeImageMB == null) {
            maxUploadSizeImageMB = 5; // Giá trị mặc định
            request.getServletContext().setAttribute("maxUploadSizeImageMB", maxUploadSizeImageMB);
        }

        String newName = request.getParameter("Name");
        Integer newQuantity = FormatUtils.tryParseInt(request.getParameter("Quantity"));
        Double newPrice = FormatUtils.tryParseDouble(request.getParameter("Price"));
        String newBrand = request.getParameter("Brand");
        String newType = request.getParameter("Type");
        String newCode = request.getParameter("Code");
        String deleteMedia = request.getParameter("deleteMedia"); // Thêm tham số để xóa media
        boolean canUpdate = true;
        List<String> videoPaths = new ArrayList<>(component.getVideos()); // Sao chép danh sách để chỉnh sửa
        List<String> imagePaths = new ArrayList<>(component.getImages());

        // Xử lý xóa media nếu có yêu cầu
        if (deleteMedia != null && !deleteMedia.isEmpty()) {
            String filePath = request.getServletContext().getRealPath("") + deleteMedia; // Đường dẫn file trên server
            if (imagePaths.remove(deleteMedia)) {
                component.setImages(imagePaths);
                if (componentDAO.update(component)) {
                    try {
                        Files.deleteIfExists(Paths.get(filePath));
                    } catch (IOException e) {
                        e.printStackTrace(); // Log lỗi nếu không xóa được file
                    }
                    response.sendRedirect(request.getContextPath() + "/ComponentWarehouse/Detail?ID=" + component.getComponentID() + "&deleteMedia=true");
                    return;
                }
            } else if (videoPaths.remove(deleteMedia)) {
                component.setVideos(videoPaths);
                if (componentDAO.update(component)) {
                    try {
                        Files.deleteIfExists(Paths.get(filePath));
                    } catch (IOException e) {
                        e.printStackTrace(); // Log lỗi nếu không xóa được file
                    }
                }
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse/Detail?ID=" + component.getComponentID() + "&deleteMedia=true");
                return;
            }
        }

        // Xử lý upload file mới
        for (Part part : request.getParts()) {
            if ("mediaFiles".equals(part.getName()) && part.getSize() > 0) {
                String mimeType = part.getContentType();
                String mediaPath;
                if (mimeType != null && mimeType.startsWith("video/")) {
                    mediaPath = OtherUtils.saveVideo(part, request, "media/component");
                    if (mediaPath != null && !mediaPath.startsWith("Invalid") && !mediaPath.startsWith("File is too large")) {
                        videoPaths.add(mediaPath);
                    } else {
                        canUpdate = false;
                        request.setAttribute("pictureAlert", mediaPath != null ? mediaPath : "Error uploading media");
                    }
                } else {
                    mediaPath = OtherUtils.saveImage(part, request, "media/component");
                    if (mediaPath != null && !mediaPath.startsWith("Invalid") && !mediaPath.startsWith("File is too large")) {
                        imagePaths.add(mediaPath);
                    } else {
                        canUpdate = false;
                        request.setAttribute("pictureAlert", mediaPath != null ? mediaPath : "Error uploading media");
                    }
                }
            }
        }

        request.setAttribute("brandList", componentDAO.getListBrand());
        request.setAttribute("typeList", componentDAO.getListType());

        // Kiểm tra dữ liệu đầu vào
        if (newName == null || newName.isBlank()) {
            request.setAttribute("nameAlert", "Name must not be empty!");
            canUpdate = false;
        }
        if (newCode == null || newCode.isBlank()) {
            request.setAttribute("codeAlert", "Code must not be empty!");
            canUpdate = false;
        } else if (componentDAO.isComponentCodeExist(newCode) && !newCode.trim().equals(component.getComponentCode())) {
            request.setAttribute("codeAlert", "Code exists, choose another!");
            canUpdate = false;
        }
        if (componentDAO.getTypeID(newType) == null) {
            canUpdate = false;
        } else {
            request.setAttribute("type", newType);
        }
        if (componentDAO.getBrandID(newBrand) == null) {
            canUpdate = false;
        } else {
            request.setAttribute("brand", newBrand);
        }
        if (newQuantity == null || newQuantity < 0) {
            request.setAttribute("quantityAlert", "Quantity must be an integer greater than or equal to 0");
            canUpdate = false;
        }
        if (newPrice == null || newPrice < 0) {
            request.setAttribute("priceAlert", "Price must be a float greater than or equal to 0");
            canUpdate = false;
        }

        // Nếu có thể cập nhật, thực hiện cập nhật
        if (canUpdate) {
            component.setComponentCode(newCode.trim());
            component.setComponentName(newName.trim());
            component.setType(newType);
            component.setBrand(newBrand);
            component.setQuantity(newQuantity);
            component.setPrice(newPrice);
            component.setVideos(videoPaths);
            component.setImages(imagePaths);

            boolean update = componentDAO.update(component);
            if (update) {
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse/Detail?ID=" + component.getComponentID() + "&updateSuccess=true");
                return;
            } else {
                request.setAttribute("updateAlert0", "Fail to edit");
            }
        }

        // Trả về trang chi tiết Component
        request.setAttribute("list", componentDAO.getProductsByComponentId(component.getComponentID()));
        request.setAttribute("component", component);
        request.setAttribute("images", component.getImages());
        request.setAttribute("videos", component.getVideos());
        request.getRequestDispatcher("/views/Component/ComponentDetail.jsp").forward(request, response);
    }

// Lưu ảnh vào thư mục /img/Component
    private void handleComponentActions(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        String componentID = request.getParameter("ID");
        Integer id = FormatUtils.tryParseInt(componentID);

        // Kiểm tra nếu không có ID hợp lệ
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            return;
        }

        // Lấy Component từ database
        Component component = componentDAO.getComponentByID(id);
        if (component == null) {
            response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            return;
        }

        switch (action) {
            case "/ComponentWarehouse/Detail" -> {
                // Hiển thị chi tiết component
                String paraProduct = request.getParameter("product");
                String updateSuccess = request.getParameter("updateSuccess");
                String deleteMedia = request.getParameter("deleteMedia");
                if (updateSuccess != null && updateSuccess.equals("true")) {
                    request.setAttribute("updateAlert1", "Component updated successfully.");
                }
                if (deleteMedia != null && deleteMedia.equals("true")) {
                    request.setAttribute("updateAlert1", "Media deleted.");

                }
                Integer productID = FormatUtils.tryParseInt(paraProduct);
                if (productID != null) {
                    if (componentDAO.removeProductComponent(component.getComponentID(), productID)) {
                        request.setAttribute("remove", "Product removed");
                    }
                }
                request.setAttribute("list", componentDAO.getProductsByComponentId(id));
                request.setAttribute("typeList", componentDAO.getListType());
                request.setAttribute("brandList", componentDAO.getListBrand());
                request.setAttribute("component", component);
                request.setAttribute("images", component.getImages());
                request.setAttribute("videos", component.getVideos());
                request.getRequestDispatcher("/views/Component/ComponentDetail.jsp").forward(request, response);
            }
            case "/ComponentWarehouse/Delete" -> {
                String pageParam = request.getParameter("page");
                String paraSearch = request.getParameter("search");
                int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
                // Lấy page-size từ request, mặc định là PAGE_SIZE
                String pageSizeParam = request.getParameter("page-size");
                String sort = request.getParameter("sort");
                String order = request.getParameter("order");
                Integer pageSize;
                pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
                paraSearch = (paraSearch == null || paraSearch.isBlank()) ? "" : paraSearch;
                // Neu co nhieu para hon thi dang o advance, xu ly tra ve trang advance
                String paraSearchCode = request.getParameter("searchCode");
                String paraSearchName = request.getParameter("searchName");
                String paraSearchQuantityMin = request.getParameter("searchQuantityMin");
                String paraSearchQuantityMax = request.getParameter("searchQuantityMax");
                String paraSearchPriceMin = request.getParameter("searchPriceMin");
                String paraSearchPriceMax = request.getParameter("searchPriceMax");
                String type = request.getParameter("searchType");
                String brand = request.getParameter("searchBrand");

                // Xóa component
                boolean check = componentDAO.delete(id);
                StringBuilder redirect = new StringBuilder();
                if (paraSearchCode == null || paraSearchName == null || paraSearchQuantityMin == null || paraSearchQuantityMax == null || paraSearchPriceMax == null || paraSearchPriceMin == null) {
                    redirect.append(request.getContextPath())
                            .append("/ComponentWarehouse?page=")
                            .append(page).append("&page-size=")
                            .append(pageSize).append("&search=")
                            .append(paraSearch).append("&sort=")
                            .append(sort).append("&order=")
                            .append(order);
                    redirect.append(check ? "&delete=1" : "delete=0");
                } else {
                    redirect.append(request.getContextPath());
                    redirect.append("/ComponentWarehouse/Search?page=").append(page);
                    redirect.append("&page-size=").append(pageSize);
                    redirect.append("&searchCode=").append(paraSearchCode);
                    redirect.append("&searchName=").append(paraSearchName);
                    redirect.append("&sort=").append(sort);
                    redirect.append("&order=").append(order);
                    redirect.append("&searchType=").append(type);
                    redirect.append("&searchBrand=").append(brand);
                    redirect.append("&searchQuantityMin=").append(paraSearchQuantityMin);
                    redirect.append("&searchQuantityMax=").append(paraSearchQuantityMax);
                    redirect.append("&searchPriceMin=").append(paraSearchPriceMin);
                    redirect.append("&searchPriceMax=").append(paraSearchPriceMax);
                    redirect.append(check ? "&delete=1" : "delete=0");
                }

                response.sendRedirect(redirect.toString());
            }
            case "/ComponentWarehouse/Edit" -> {
                // Sửa component
                handleEditComponent(request, response, component);
            }

            default ->
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
        }
    }

}
