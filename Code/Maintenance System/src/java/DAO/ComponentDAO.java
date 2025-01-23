/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Component;
import Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class ComponentDAO extends DBContext {

    public List<Component> getAllComponents() {
        List<Component> components = new ArrayList<>();
        String query = "SELECT ComponentID, ComponentCode, ComponentName, Quantity, Price, Image FROM Component where Status=1";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Component component = new Component();
                component.setComponentID(resultSet.getInt("ComponentID"));
                component.setComponentName(resultSet.getString("ComponentName"));
                component.setComponentCode(resultSet.getString("ComponentCode"));
                component.setQuantity(resultSet.getInt("Quantity"));
                component.setPrice(resultSet.getDouble("Price"));
                component.setImage(resultSet.getString("Image"));

                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return components;
    }

    public List<Component> getComponentsByPage(int page, int pageSize) {
        List<Component> components = new ArrayList<>();
        String query = "SELECT ComponentID, ComponentCode, ComponentName, Quantity, Price, Image "
                + "FROM Component where Status=1 "
                + "ORDER BY ComponentID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            int offset = (page - 1) * pageSize;
            statement.setInt(1, offset);
            statement.setInt(2, pageSize);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Component component = new Component();
                    component.setComponentID(resultSet.getInt("ComponentID"));
                    component.setComponentName(resultSet.getString("ComponentName"));
                    component.setComponentCode(resultSet.getString("ComponentCode"));
                    component.setQuantity(resultSet.getInt("Quantity"));
                    component.setPrice(resultSet.getDouble("Price"));
                    component.setImage(resultSet.getString("Image"));

                    components.add(component);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return components;
    }

    // Phương thức đếm tổng số Component
    public int getTotalComponents() {
        String query = "SELECT COUNT(*) FROM Component where Status=1";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Component getComponentByID(int componentID) {
        String sql = "SELECT * FROM [dbo].[Component] WHERE Status=1 AND componentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, componentID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Component component = new Component();
                    component.setComponentID(rs.getInt("ComponentID"));
                    component.setComponentName(rs.getString("ComponentName"));
                    component.setComponentCode(rs.getString("ComponentCode"));
                    component.setQuantity(rs.getInt("Quantity"));
                    component.setPrice(rs.getDouble("Price"));
                    component.setImage(rs.getString("Image"));
                    return component;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy component    }
    }

    public boolean delete(int componentID) {
        String sql = "UPDATE Component SET Status = 0 WHERE ComponentID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, componentID); // Thiết lập giá trị cho tham số trong câu lệnh SQL

            int rowsAffected = stmt.executeUpdate(); // Thực thi truy vấn
            return rowsAffected > 0; // Trả về true nếu có dòng bị ảnh hưởng

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi
    }

public boolean add(Component component) { 
    String query = "INSERT INTO Component (ComponentName, ComponentCode, Quantity, Price, Image) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, component.getComponentName());
        statement.setString(2, component.getComponentCode());
        statement.setInt(3, component.getQuantity());
        statement.setDouble(4, component.getPrice());

        if (component.getImage() != null && !component.getImage().isEmpty()) {
            statement.setString(5, component.getImage());
        } else {
            statement.setNull(5, java.sql.Types.NVARCHAR);
        }
        int rowsInserted = statement.executeUpdate();
        return rowsInserted > 0; // Trả về true nếu thêm thành công

    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Trả về false nếu có lỗi xảy ra
    }
}


   public boolean update(Component component) { 
    String query;
    if (component.getImage() != null) {
        query = "UPDATE Component SET ComponentCode = ?, ComponentName = ?, Quantity = ?, Price = ?, Image = ? WHERE ComponentID = ?";
    } else {
        query = "UPDATE Component SET ComponentCode = ?, ComponentName = ?, Quantity = ?, Price = ? WHERE ComponentID = ?";
    }

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        // Set giá trị chung
        statement.setString(1, component.getComponentCode());
        statement.setString(2, component.getComponentName());
        statement.setInt(3, component.getQuantity());
        statement.setDouble(4, component.getPrice());

        if (component.getImage() != null) {
            statement.setString(5, component.getImage()); // Đặt giá trị cho Image
            statement.setInt(6, component.getComponentID()); // Đặt giá trị cho ComponentID
        } else {
            statement.setInt(5, component.getComponentID()); // Đặt giá trị cho ComponentID
        }

        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0; // Trả về true nếu cập nhật thành công

    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Trả về false nếu có lỗi xảy ra
    }
}

    public Component getLast() {
        String query = "SELECT TOP 1 * FROM Component where Status=1 ORDER BY ComponentID DESC ";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                Component component = new Component();
                component.setComponentID(resultSet.getInt("ComponentID"));
                component.setComponentCode(resultSet.getString("ComponentCode"));
                component.setComponentName(resultSet.getString("ComponentName"));
                component.setQuantity(resultSet.getInt("Quantity"));
                component.setPrice(resultSet.getDouble("Price"));
                component.setImage(resultSet.getString("Image"));
                return component;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Component> searchComponentsByPage(String keyword, int page, int pageSize) {
        List<Component> components = new ArrayList<>();
        String sql = "SELECT * FROM Component WHERE "
                + "Status = 1 AND "
                + "(ComponentCode LIKE ? OR "
                + "ComponentName LIKE ? OR "
                + "CAST(Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(Price AS NVARCHAR) LIKE ? )"
                + "ORDER BY ComponentID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) {
                statement.setString(i, searchKeyword);
            }

            int offset = (page - 1) * pageSize;
            statement.setInt(5, offset);
            statement.setInt(6, pageSize);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ComponentID");
                String name = resultSet.getString("ComponentName");
                String code = resultSet.getString("ComponentCode");

                int quantity = resultSet.getInt("Quantity");
                float price = resultSet.getFloat("Price");
                String image = resultSet.getString("Image");

                Component component = new Component(id, code, name, quantity, price, image);
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public int getTotalSearchComponents(String keyword) {
        String query = "SELECT COUNT(*) FROM Component "
                + "WHERE Status=1 AND "
                + "(ComponentCode LIKE ? OR "
                + "ComponentName LIKE ? OR "
                + "CAST(Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(Price AS NVARCHAR) LIKE ? )";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String searchKeyword = "%" + keyword + "%";

            // Thiết lập các tham số cho PreparedStatement
            for (int i = 1; i <= 4; i++) {
                preparedStatement.setString(i, searchKeyword);
            }

            // Thực hiện truy vấn
            ResultSet resultSet = preparedStatement.executeQuery();

            // Lấy kết quả
            if (resultSet.next()) {
                return resultSet.getInt(1); // Lấy giá trị đếm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi hoặc không tìm thấy
    }

    public List<Component> getComponentsByPageSorted(int page, int pageSize, String sort, String order) {
        String query = "SELECT * FROM Component WHERE Status=1 ORDER BY " + sort + " " + order + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<Component> components = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("componentID"),
                        rs.getString("ComponentCode"),
                        rs.getString("componentName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public List<Component> searchComponentsByPageSorted(String search, int page, int pageSize, String sort, String order) {
        String query = "SELECT * FROM Component "
                + "WHERE Status=1 AND "
                + "(ComponentName LIKE ? OR "
                + "ComponentCode LIKE ? OR "
                + "CAST(Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(Price AS NVARCHAR) LIKE ?) ORDER BY " + sort + " " + order + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<Component> components = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {
            String searchKeyword = "%" + search + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, searchKeyword);
            }
            ps.setInt(5, (page - 1) * pageSize);
            ps.setInt(6, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("componentID"),
                        rs.getString("componentCode"),
                        rs.getString("componentName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public int getTotalSearchComponentsByFields(String searchCode, String searchName, String searchQuantity, String searchPrice) {
        String query = "SELECT COUNT (*) FROM Component WHERE "
                + "Status=1 AND "
                + "(ComponentCode LIKE ? AND "
                + "ComponentName LIKE ? AND "
                + "CAST(Quantity AS NVARCHAR) LIKE ? AND "
                + "CAST(Price AS NVARCHAR) LIKE ? )";
        List<Component> components = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, "%" + searchCode + "%");
            ps.setString(2, "%" + searchName + "%");
            ps.setString(3, "%" + searchQuantity + "%");
            ps.setString(4, "%" + searchPrice + "%");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Lấy giá trị đếm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Component> searchComponentsByFieldsPage(String searchCode, String searchName, String searchQuantity, String searchPrice, int page, int pageSize) {
        String query = "SELECT * FROM Component WHERE "
                + "Status=1 AND "
                + "ComponentCode LIKE ? AND "
                + "ComponentName LIKE ? AND "
                + "CAST(Quantity AS NVARCHAR) LIKE ? AND "
                + "CAST(Price AS NVARCHAR) LIKE ? "
                + "ORDER BY ComponentID"
                + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<Component> components = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, "%" + searchCode + "%");
            ps.setString(2, "%" + searchName + "%");
            ps.setString(3, "%" + searchQuantity + "%");
            ps.setString(4, "%" + searchPrice + "%");
            ps.setInt(5, (page - 1) * pageSize);
            ps.setInt(6, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("componentID"),
                        rs.getString("componentCode"),
                        rs.getString("componentName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public List<Component> searchComponentsByFieldsPageSorted(String searchCode, String searchName, String searchQuantity, String searchPrice, int page, int pageSize, String sort, String order) {
        String query = "SELECT * FROM Component WHERE "
                + "Status=1 AND "
                + "ComponentCode LIKE ? AND "
                + "ComponentName LIKE ? AND "
                + "CAST(Quantity AS NVARCHAR) LIKE ? AND "
                + "CAST(Price AS NVARCHAR) LIKE ? ORDER BY " + sort + " " + order + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<Component> components = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, "%" + searchCode + "%");
            ps.setString(2, "%" + searchName + "%");
            ps.setString(3, "%" + searchQuantity + "%");
            ps.setString(4, "%" + searchPrice + "%");
            ps.setInt(5, (page - 1) * pageSize);
            ps.setInt(6, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("componentID"),
                        rs.getString("componentCode"),
                        rs.getString("componentName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }
    
      private List<Product> getProductsByComponentId(int componentId) throws SQLException {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.* FROM Product p " +
                     "JOIN ProductComponents pc ON p.ProductID = pc.ProductID " +
                     "WHERE pc.ComponentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, componentId);
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getInt("Quantity"),
                        rs.getInt("WarrantyPeriod"),
                        rs.getString("Image")
                    );
                    productList.add(product);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }



    public static void main(String arg[]) throws SQLException {
        ComponentDAO d = new ComponentDAO();
        String searchCode = "";
        String searchName = "MA";
        String searchQuantity = "";
        String searchPrice = "";
        int page = 1;
        int pageSize = 5;
        String sort = "Quantity";
        String order = "asc";
        String search = "MA";
        System.out.println(d.searchComponentsByFieldsPageSorted(searchCode,"MA", "", "", 1, 5, "ComponentCode", "asc"));
        System.out.println(d.searchComponentsByFieldsPage(searchCode,"MA", "", "", 1, 5));
        System.out.println(d.getTotalSearchComponentsByFields(searchCode,searchName, searchQuantity, searchPrice));
        System.out.println(d.searchComponentsByPageSorted(search, page, pageSize, sort, order));
        System.out.println(d.getComponentsByPageSorted(page, pageSize, sort, order));
        System.out.println(d.getTotalSearchComponents(search));
        System.out.println(d.searchComponentsByPage(search, page, pageSize));
        System.out.println(d.getLast());
        System.out.println(d.getComponentByID(2));
        System.out.println(d.getTotalComponents());
        System.out.println(d.getComponentsByPage(page, pageSize));
        System.out.println(d.getAllComponents());
        System.out.println(d.getProductsByComponentId(1));
    }
}
