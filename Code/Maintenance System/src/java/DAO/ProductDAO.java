/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Product;
import Model.ProductDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sonNH
 */
public class ProductDAO extends DBContext {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.ProductID,\n"
                + "    p.Code,\n"
                + "    p.ProductName,\n"
                + "    cb.BrandName,\n"
                + "    p.[Type],\n"
                + "    p.Quantity,\n"
                + "    p.WarrantyPeriod,\n"
                + "    p.[Status],\n"
                + "    p.Image\n"
                + "FROM \n"
                + "    Product p\n"
                + "JOIN \n"
                + "    ComponentBrand cb ON p.BrandID = cb.BrandID;";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("Code"));
                product.setProductName(rs.getString("ProductName"));
                product.setBrandName(rs.getString("BrandName"));
                product.setType(rs.getString("Type"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setStatus(rs.getString("Status"));
                product.setImage(rs.getString("Image"));

                products.add(product);
            }
        } catch (SQLException e) {
        }
        return products;
    }

    ////
    /**
     * Get Product detail by customer ID
     * @param customerID
     * @return 
     */
    public ArrayList<ProductDetail> getProductDetailByCustomerID(int customerID) {
        ArrayList<ProductDetail> listProductDetail = new ArrayList<>();
        String sql = "SELECT c.CustomerID,\n"
                + "       c.UsernameC,\n"
                + "	   c.Name,\n"
                + "	   c.Email,\n"
                + "	   c.Phone,\n"
                + "	   c.Address,\n"
                + "	   pd.ProductCode,\n"
                + "	   p.ProductName,\n"
                + "	   pd.PurchaseDate,\n"
                + "	   p.WarrantyPeriod\n"
                + "	   \n"
                + "FROM [Product] p LEFT JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n"
                + "LEFT JOIN Customer c ON pd.CustomerID = c.CustomerID\n"
                + "WHERE c.CustomerID =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetail pd = new ProductDetail();
                pd.setCustomerID(rs.getInt("CustomerID"));
                pd.setUsernameC(rs.getString("UsernameC"));
                pd.setName(rs.getString("Name"));
                pd.setEmail(rs.getString("Email"));
                pd.setPhone(rs.getString("Phone"));
                pd.setAddress(rs.getString("Address"));
                pd.setProductCode(rs.getString("ProductCode"));
                pd.setProductName(rs.getString("ProductName"));
                pd.setPurchaseDate(rs.getDate("PurchaseDate"));
                pd.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));

                listProductDetail.add(pd);

            }
        } catch (Exception e) {

        }
        return listProductDetail ;

    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        /*
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            System.out.println(p.getBrandName());
        }
*/
     ArrayList<ProductDetail> d = productDAO.getProductDetailByCustomerID(2);
     for (ProductDetail p : d) {
            System.out.println(p.getPurchaseDate());
        }
    }

}
