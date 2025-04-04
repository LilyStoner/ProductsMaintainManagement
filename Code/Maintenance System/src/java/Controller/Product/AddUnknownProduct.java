package Controller.Product;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import Model.Customer;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author sonNH
 */
public class AddUnknownProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDao = new CustomerDAO();
        List<Customer> listCustomer = customerDao.getAllCustomer();
        request.setAttribute("listCustomer", listCustomer);
        request.getRequestDispatcher("Product/createUnknownProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();

        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        String customerIdStr = request.getParameter("customerId");
        String description = request.getParameter("description");
        String receivedDate = request.getParameter("receivedDate");

        int customerId = customerIdStr != null && !customerIdStr.isEmpty() ? Integer.parseInt(customerIdStr) : -1;
        
        // Check if the product code already exists for an unknown product
        if (productDAO.isUnknownProductCodeExists(productCode)) {
            request.setAttribute("message", "Product Code already exists!");
            request.setAttribute("productCode", productCode);
            request.setAttribute("productName", productName);
            request.setAttribute("customerId", customerId);
            request.setAttribute("description", description);
            request.setAttribute("receivedDate", receivedDate);
            doGet(request, response);
            return;
        }
        
        boolean success = productDAO.addUnknownProduct(customerId, productName, productCode, description, receivedDate);

        if (success) {
            request.setAttribute("message", "Product added successfully!");
        } else {
            request.setAttribute("message", "Error adding product!");
        }
        doGet(request, response);
    }
}
