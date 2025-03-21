package Controller.Account;

import DAO.CustomerDAO;
import DAO.StaffDAO;
import Model.Customer;
import Model.Staff;
import Utils.Encryption;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffDAO staffDao = new StaffDAO();
        CustomerDAO customerDao = new CustomerDAO();
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberme = request.getParameter("remember-me");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and Password must not be empty!");
            request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
            return;
        }
        
        String encryptionPassword = Encryption.EncryptionPassword(password);
        Staff staff = staffDao.getStaffByUsenamePassword(username, encryptionPassword);
        Customer customer = customerDao.getCustomerByUsenamePassword(username, encryptionPassword);

        if (staff == null && customer == null) {
            request.setAttribute("error", "Username or Password is incorrect!");
            request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
            return;
        }
                    session.removeAttribute("staff");
                    session.removeAttribute("customer");

        if (staff != null) {
            session.setAttribute("staff", staff);
            saveCookies(response, username, password, rememberme);
            response.sendRedirect("profile");
        } else if (customer != null) {
            session.setAttribute("customer", customer);
            saveCookies(response, username, password, rememberme);
            response.sendRedirect("profile");
        }
    }

    private void saveCookies(HttpServletResponse response, String username, String password, String rememberme) {
        Cookie cusername = new Cookie("cusername", username);
        Cookie cpassword = new Cookie("cpassword", password);
        Cookie crememberme = new Cookie("crememberme", rememberme);

        if (rememberme != null) {
            cusername.setMaxAge(60 * 60 * 24 * 7);
            cpassword.setMaxAge(60 * 60 * 24 * 7);
            crememberme.setMaxAge(60 * 60 * 24 * 7);
        } else {
            cusername.setMaxAge(0);
            cpassword.setMaxAge(0);
            crememberme.setMaxAge(0);
        }

        response.addCookie(cusername);
        response.addCookie(cpassword);
        response.addCookie(crememberme);
    }    
}
