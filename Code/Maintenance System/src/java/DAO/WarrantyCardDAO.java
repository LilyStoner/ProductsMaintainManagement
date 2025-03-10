package DAO;

import Model.ProductDetail;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import Model.WarrantyCard;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ADMIN
 */
public class WarrantyCardDAO extends DBContext {

    private static final WarrantyCardDAO d = new WarrantyCardDAO();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 10;
    private static final String SELECT_STRING = """
    SELECT wc.WarrantyCardID, wc.WarrantyCardCode, wc.IssueDescription, wc.WarrantyStatus,wc.WarrantyProductID, wc.HandlerID, 
           wc.CreatedDate, wc.ReturnDate, wc.DoneDate, wc.CompleteDate, wc.CancelDate, 
           COALESCE(pd.ProductCode, up.ProductCode) AS ProductCode, p.Code,
           COALESCE(p.ProductName, up.ProductName) AS ProductName,
           c.Name AS CustomerName, c.Phone AS CustomerPhone, c.CustomerID 
    FROM WarrantyCard wc 
    JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID 
    LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID 
    LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID 
    LEFT JOIN Product p ON pd.ProductID = p.ProductID 
    LEFT JOIN Customer c ON COALESCE(pd.CustomerID, up.CustomerID) = c.CustomerID
    """;

    private SecureRandom random = new SecureRandom();

    public List<WarrantyCard> getAllWarrantyCards() {
        List<WarrantyCard> warrantyCards = new ArrayList<>();
        String query = SELECT_STRING + "ORDER BY wc.WarrantyCardID DESC";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                warrantyCards.add(mapWarrantyCard(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return warrantyCards;
    }

    public boolean updateWarrantyCard(WarrantyCard wc) {
        String sql = "UPDATE WarrantyCard SET  WarrantyCardCode = ?, WarrantyProductID = ?, "
                + "IssueDescription = ?, WarrantyStatus = ?, ReturnDate = ?, DoneDate = ?, "
                + "CompleteDate = ?, CancelDate = ?, HandlerID = ? WHERE WarrantyCardID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, wc.getWarrantyCardCode());
            ps.setInt(2, wc.getWarrantyProductID());
            ps.setString(3, wc.getIssueDescription());
            ps.setString(4, wc.getWarrantyStatus());
            ps.setTimestamp(5, wc.getReturnDate() != null ? new java.sql.Timestamp(wc.getReturnDate().getTime()) : null);
            ps.setTimestamp(6, wc.getDonedDate() != null ? new java.sql.Timestamp(wc.getDonedDate().getTime()) : null);
            ps.setTimestamp(7, wc.getCompletedDate() != null ? new java.sql.Timestamp(wc.getCompletedDate().getTime()) : null);
            ps.setTimestamp(8, wc.getCanceldDate() != null ? new java.sql.Timestamp(wc.getCanceldDate().getTime()) : null);
            if (wc.getHandlerID() != null) { // Assuming HandlerID is positive; adjust if 0 is valid
                ps.setInt(9, wc.getHandlerID());
            } else {
                ps.setNull(9, java.sql.Types.INTEGER);
            }
            ps.setInt(10, wc.getWarrantyCardID());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public int createWarrantyCard(String productCode, String issue, java.util.Date returnDate, List<String> mediaPaths) {
        try {
            // Tìm ProductDetailID theo ProductCode
            String findProductDetailQuery = "SELECT ProductDetailID FROM ProductDetail WHERE ProductCode = ?";
            int productDetailID = -1;

            try (PreparedStatement ps = connection.prepareStatement(findProductDetailQuery)) {
                ps.setString(1, productCode);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        productDetailID = rs.getInt("ProductDetailID");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm có mã: " + productCode);
                        return -1;
                    }
                }
            }

            // Tìm hoặc tạo WarrantyProduct
            int warrantyProductID = -1;
            String findWarrantyProductQuery = "SELECT WarrantyProductID FROM WarrantyProduct WHERE ProductDetailID = ?";

            try (PreparedStatement ps = connection.prepareStatement(findWarrantyProductQuery)) {
                ps.setInt(1, productDetailID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        warrantyProductID = rs.getInt("WarrantyProductID");
                    }
                }
            }

            // Nếu chưa có WarrantyProduct, tạo mới
            if (warrantyProductID == -1) {
                String insertWarrantyProductQuery = "INSERT INTO WarrantyProduct (ProductDetailID) VALUES (?)";
                try (PreparedStatement ps = connection.prepareStatement(insertWarrantyProductQuery, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, productDetailID);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            warrantyProductID = rs.getInt(1);
                        }
                    }
                }
            }

            // Tạo mã WarrantyCardCode
            String warrantyCardCode = generateWarrantyCardCode();

            // Chèn vào bảng WarrantyCard
            String insertWarrantyCardQuery = "INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, IssueDescription, WarrantyStatus, CreatedDate, ReturnDate) "
                    + "VALUES (?, ?, ?, 'waiting', GETDATE(), ?)";
            int warrantyCardId = -1;
            try (PreparedStatement ps = connection.prepareStatement(insertWarrantyCardQuery, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, warrantyCardCode);
                ps.setInt(2, warrantyProductID);
                ps.setString(3, issue);
                ps.setTimestamp(4, returnDate != null ? new java.sql.Timestamp(returnDate.getTime()) : null);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            warrantyCardId = rs.getInt(1); // Retrieve the generated WarrantyCardID
                        }
                    }
                    if (warrantyCardId != -1) {
                        String insertMediaQuery = "INSERT INTO Media (ObjectID, ObjectType, MediaURL, MediaType) VALUES (?, 'WarrantyCard', ?, ?)";
                        try (PreparedStatement ps2 = connection.prepareStatement(insertMediaQuery)) {
                            for (String mediaPath : mediaPaths) {
                                ps2.setInt(1, warrantyCardId);
                                ps2.setString(2, mediaPath);
                                ps2.setString(3, mediaPath.endsWith(".mp4") || mediaPath.endsWith(".mov") || mediaPath.endsWith(".avi") || mediaPath.endsWith(".mkv") ? "video" : "image");
                                ps2.addBatch();
                            }
                            ps2.executeBatch();
                        }
                    }
                    return warrantyCardId;
                }
                return warrantyCardId;
            }
        } catch (SQLException e) {

            e.printStackTrace();
            return -1;
        }
    }

    public ProductDetail getProductDetailByCode(String code) {
        String sql = "SELECT pd.ProductDetailID, pd.ProductCode, pd.PurchaseDate, "
                + "c.UsernameC, c.Name AS CustomerName, c.Email, c.Phone, c.Address, "
                + "p.ProductName, p.WarrantyPeriod "
                + "FROM ProductDetail pd "
                + "JOIN Customer c ON pd.CustomerID = c.CustomerID "
                + "JOIN Product p ON pd.ProductID = p.ProductID "
                + "WHERE pd.ProductCode = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code); // Sử dụng setString thay vì setNString nếu cột không phải NVARCHAR
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductDetail(
                            rs.getInt("ProductDetailID"),
                            rs.getString("ProductCode"),
                            rs.getDate("PurchaseDate"),
                            rs.getString("UsernameC"),
                            rs.getString("CustomerName"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getString("ProductName"),
                            rs.getInt("WarrantyPeriod")
                    );
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    public ProductDetail getUnknownProductDetailByCode(String code) {
        String sql = "SELECT up.UnknownProductID, up.ProductCode, up.ProductName, up.Description, "
                + "up.PurchaseDate, c.UsernameC, c.Name AS CustomerName, c.Email, c.Phone, c.Address "
                + "FROM UnknownProduct up "
                + "JOIN Customer c ON up.CustomerID = c.CustomerID "
                + "WHERE up.ProductCode = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductDetail(
                            rs.getInt("ProductDetailID"),
                            rs.getString("ProductCode"),
                            rs.getDate("PurchaseDate"),
                            rs.getString("UsernameC"),
                            rs.getString("CustomerName"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getString("ProductName"),
                            rs.getInt("WarrantyPeriod")
                    );
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm không rõ nguồn gốc
    }

    private String generateWarrantyCardCode() {
        String warrantyCode;
        do {
            warrantyCode = generateRandomCode();
        } while (isWarrantyCardCodeExists(warrantyCode)); // Kiểm tra trùng mã trong database
        return warrantyCode;
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private boolean isWarrantyCardCodeExists(String warrantyCode) {
        String query = "SELECT COUNT(*) FROM WarrantyCard WHERE WarrantyCardCode = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, warrantyCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu có mã trùng, trả về true
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return false; // Không tìm thấy mã trùng
    }

    /**
     * Get warranty card by customer ID
     *
     * @param customerID
     * @param warrantyCard
     * @param productName
     * @param createDate
     * @param sortBy
     * @param sortOrder
     * @param offset
     * @param fetch
     * @return
     */
    public List<WarrantyCard> getWarrantyCardByCustomerID(int customerID, String warrantyCard, String productName, String status, String createDate, String sortBy, String sortOrder, int offset, int fetch) {

        String searchWarrantyCardCode = (warrantyCard != null) ? "%" + warrantyCard.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchProductName = (productName != null) ? "%" + productName.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchStatus = (status != null) ? "%" + status.trim().replaceAll("\\s+", "%") + "%" : "%";
        List<WarrantyCard> warrantyCards = new ArrayList<>();
        String query = SELECT_STRING + "WHERE c.CustomerID=?";
        if (searchWarrantyCardCode != null && !searchWarrantyCardCode.trim().isEmpty()) {
            query += " AND wc.WarrantyCardCode LIKE ?";
        }
        if (searchProductName != null && !searchProductName.trim().isEmpty()) {
            query += " AND (COALESCE(p.ProductName, up.ProductName) LIKE ?) ";

        }
        if (createDate != null && !createDate.trim().isEmpty()) {
            query += " AND  wc.CreatedDate = ?";
        }

        if (searchStatus != null && !searchStatus.trim().isEmpty()) {
            query += " AND  wc.WarrantyStatus LIKE ?";
        }

        // Fix lỗi OFFSET khi không có ORDER BY
        if (sortBy == null || sortBy.trim().isEmpty()) {
            query += " ORDER BY wc.WarrantyCardID";
        } else {
            query += " ORDER BY " + sortBy;
            if (sortOrder != null && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
                query += " " + sortOrder;
            } else {
                query += " ASC";
            }
        }

        query += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";  // Phân trang an toàn với SQL Server

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            ps.setInt(index++, customerID);
            if (searchWarrantyCardCode != null && !searchWarrantyCardCode.trim().isEmpty()) {
                ps.setString(index++, searchWarrantyCardCode);
            }

            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                ps.setString(index++, searchProductName);
            }
            if (createDate != null && !createDate.trim().isEmpty()) {
                ps.setDate(index++, java.sql.Date.valueOf(createDate.trim()));
            }
            if (searchStatus != null && !searchStatus.trim().isEmpty()) {
                ps.setString(index++, searchStatus);
            }
            ps.setInt(index++, offset);
            ps.setInt(index++, fetch);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                warrantyCards.add(mapWarrantyCard(rs));
            }
        } catch (SQLException e) {
        }

        return warrantyCards;
    }

    // 
    public int getPageWarrantyCardByCustomerID(int customerID, String warrantyCard, String productName, String status, String createDate) {
        String searchWarrantyCardCode = (warrantyCard != null) ? "%" + warrantyCard.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchProductName = (productName != null) ? "%" + productName.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchStatus = (status != null) ? "%" + status.trim().replaceAll("\\s+", "%") + "%" : "%";
        String query = "SELECT COUNT(*) \n"
                + " FROM WarrantyCard wc \n"
                + " JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID \n"
                + " LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID \n"
                + " LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID \n"
                + " LEFT JOIN Product p ON pd.ProductID = p.ProductID \n"
                + " LEFT JOIN Customer c ON COALESCE(pd.CustomerID, up.CustomerID) = c.CustomerID \n"
                + " WHERE c.CustomerID=?";
        if (searchWarrantyCardCode != null && !searchWarrantyCardCode.trim().isEmpty()) {
            query += " AND wc.WarrantyCardCode LIKE ?";
        }
        if (searchProductName != null && !searchProductName.trim().isEmpty()) {
            query += " AND (COALESCE(p.ProductName, up.ProductName) LIKE ?) ";

        }
        if (createDate != null && !createDate.trim().isEmpty()) {
            query += " AND  wc.CreatedDate = ?";
        }

        if (searchStatus != null && !searchStatus.trim().isEmpty()) {
            query += " AND  wc.WarrantyStatus LIKE ?";
        }

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerID);
            int index = 1;
            ps.setInt(index++, customerID);
            if (searchWarrantyCardCode != null && !searchWarrantyCardCode.trim().isEmpty()) {
                ps.setString(index++, searchWarrantyCardCode);
            }

            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                ps.setString(index++, searchProductName);
            }
            if (createDate != null && !createDate.trim().isEmpty()) {
                ps.setDate(index++, java.sql.Date.valueOf(createDate.trim()));
            }
            if (searchStatus != null && !searchStatus.trim().isEmpty()) {
                ps.setString(index++, searchStatus);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalCards(String paraSearch, String status, String type, Integer handlerId) {
        int total = 0;
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM WarrantyCard wc ");
        query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");

        // Trường hợp lấy từ ProductDetail (warranty)
        if ("warranty".equalsIgnoreCase(type)) {
            query.append("JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("JOIN Product p ON pd.ProductID = p.ProductID ");
        } // Trường hợp lấy từ UnknownProduct (repair)
        else if ("repair".equalsIgnoreCase(type)) {
            query.append("JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID ");
        } // Trường hợp lấy cả hai (null)
        else {
            query.append("LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("LEFT JOIN Product p ON pd.ProductID = p.ProductID ");
            query.append("LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID ");
        }

        query.append("WHERE (wc.WarrantyCardCode LIKE ? ");

        if (!"repair".equalsIgnoreCase(type)) {
            query.append("   OR pd.ProductCode LIKE ? OR p.ProductName LIKE ? ");
        }
        if (!"warranty".equalsIgnoreCase(type)) {
            query.append("   OR up.ProductCode LIKE ? OR up.ProductName LIKE ? ");
        }

        query.append("   OR wc.IssueDescription LIKE ?)");

        // Thêm điều kiện lọc theo trạng thái nếu có
        if (status != null && !status.isEmpty()) {
            query.append(" AND wc.WarrantyStatus = ?");
        }
        // xem theo cac card receive
        if (type != null && "myCard".equals(type)) {
            query.append(" AND wc.HandlerID = ?");
        }
        if (type != null && !"myCard".equals(type)) {
            query.append(" AND wc.HandlerID is null");
        }

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            String searchPattern = "%" + paraSearch + "%";
            ps.setString(1, searchPattern);

            int paramIndex = 2;
            if (!"repair".equalsIgnoreCase(type)) {
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (!"warranty".equalsIgnoreCase(type)) {
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }

            ps.setString(paramIndex++, searchPattern);

            // Nếu có trạng thái, truyền vào tham số cuối cùng
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            //Neu loc theo receive card
            if (type != null && "myCard".equals(type)) {
                ps.setInt(paramIndex, handlerId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return total;
    }

    public List<WarrantyCard> getCards(int page, Integer pageSize, String paraSearch, String status, String sort, String order, String type, Integer handlerId) {
        List<WarrantyCard> cards = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT wc.WarrantyCardID, wc.WarrantyCardCode, wc.IssueDescription, wc.WarrantyProductID, ");
        query.append("wc.WarrantyStatus, wc.CreatedDate, wc.ReturnDate, wc.DoneDate, wc.CompleteDate, wc.CancelDate, wc.HandlerID, c.CustomerID, ");

        // Trường hợp lấy từ ProductDetail (warranty)
        if ("warranty".equalsIgnoreCase(type)) {
            query.append("p.Code , pd.ProductCode, p.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("JOIN Product p ON pd.ProductID = p.ProductID ");
            query.append("JOIN Customer c ON pd.CustomerID = c.CustomerID ");
        } // Trường hợp lấy từ UnknownProduct (repair)
        else if ("repair".equalsIgnoreCase(type)) {
            query.append("up.ProductCode, up.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID ");
            query.append("JOIN Customer c ON up.CustomerID = c.CustomerID ");

        } // Trường hợp lấy cả hai (null)
        else {
            query.append("p.Code , COALESCE(pd.ProductCode, up.ProductCode) AS ProductCode, ");
            query.append("COALESCE(p.ProductName, up.ProductName) AS ProductName, ");
            query.append("c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID ");
            query.append("LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("LEFT JOIN Product p ON pd.ProductID = p.ProductID ");
            query.append("LEFT JOIN Customer c ON COALESCE(pd.CustomerID, up.CustomerID) = c.CustomerID ");
        }

        query.append("WHERE (wc.WarrantyCardCode LIKE ? ");

        if (!"repair".equalsIgnoreCase(type)) {
            query.append("   OR pd.ProductCode LIKE ? OR p.ProductName LIKE ? ");
        }
        if (!"warranty".equalsIgnoreCase(type)) {
            query.append("   OR up.ProductCode LIKE ? OR up.ProductName LIKE ? ");
        }

        query.append("   OR wc.IssueDescription LIKE ?)");

        // Thêm điều kiện lọc theo trạng thái nếu có
        if (status != null && !status.isEmpty()) {
            query.append(" AND wc.WarrantyStatus = ?");
        }
        // xem theo cac card receive
        if (type != null && "myCard".equals(type)) {
            query.append(" AND wc.HandlerID = ?");
        }
        if (type != null && !"myCard".equals(type)) {
            query.append(" AND wc.HandlerID is null");
        }

        // Xử lý sắp xếp
        if (sort == null || sort.isEmpty()) {
            sort = "CreatedDate"; // Mặc định sắp xếp theo ngày tạo
        }
        if (order == null || !(order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC"))) {
            order = "DESC"; // Mặc định sắp xếp giảm dần
        }

        query.append(" ORDER BY ").append(sort).append(" ").append(order).append(" ");
        query.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            String searchPattern = "%" + paraSearch + "%";
            ps.setString(1, searchPattern);

            int paramIndex = 2;
            if (!"repair".equalsIgnoreCase(type)) {
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (!"warranty".equalsIgnoreCase(type)) {
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }

            ps.setString(paramIndex++, searchPattern);

            // Nếu có trạng thái, truyền vào tham số cuối cùng
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            //Neu loc theo receive card
            if (type != null && "myCard".equals(type)) {
                ps.setInt(paramIndex++, handlerId);
            }

            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(mapWarrantyCard(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cards;
    }

    /**
     * Get Warranty Card by Phone and warranty code
     *
     * @param phone
     * @param code
     * @return
     */
    public WarrantyCard getWarrantyCardByPhoneAndCode(String phone, String code) {

        String sql = "SELECT wc.WarrantyCardID,\n"
                + "       wc.WarrantyCardCode,\n"
                + "	   pd.ProductDetailID,\n"
                + "	   up.UnknownProductID,\n"
                + "	   p.Code,\n"
                + "	   wc.IssueDescription,\n"
                + "	   wc.WarrantyStatus,\n"
                + "	   wc.CreatedDate,\n"
                + "	   wc.ReturnDate,\n"
                + "	   wc.DoneDate,\n"
                + "	   wc.CompleteDate,\n"
                + "	   wc.CancelDate,\n"
                + "	   p.ProductName,\n"
                + "	   c.CustomerID,\n"
                + "	   c.Name,\n"
                + "	   c.Phone\n"
                + "\n"
                + "\n"
                + "FROM WarrantyCard wc LEFT JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID\n"
                + "                     \n"
                + "                     LEFT JOIN ProductDetail pd ON pd.ProductDetailID = wp.ProductDetailID\n"
                + "					 LEFT JOIN Product p ON p.ProductID = pd.ProductID\n"
                + "					 LEFT JOIN Customer c ON c.CustomerID = pd.CustomerID\n"
                + "					 LEFT JOIN UnknownProduct up ON up.CustomerID = c.CustomerID\n"
                + "					 WHERE c.Phone = ? AND wc.WarrantyCardCode =?\n"
                + "		 ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                WarrantyCard warrantyCard = new WarrantyCard();
                warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                warrantyCard.setProductDetailID(rs.getInt("ProductDetailID"));
                warrantyCard.setUnknownProductID(rs.getInt("UnknownProductID"));
                warrantyCard.setProductCode(rs.getString("Code"));
                warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
                warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
                warrantyCard.setCreatedDate(rs.getDate("CreatedDate"));
                warrantyCard.setReturnDate(rs.getDate("ReturnDate"));
                warrantyCard.setDonedDate(rs.getDate("DoneDate"));
                warrantyCard.setCompletedDate(rs.getDate("CompleteDate"));
                warrantyCard.setCanceldDate(rs.getDate("CancelDate"));
                warrantyCard.setImages(getMediaURLs(warrantyCard.getWarrantyCardID(), "image"));
                warrantyCard.setVideos(getMediaURLs(warrantyCard.getWarrantyCardID(), "video"));
                warrantyCard.setProductName(rs.getString("ProductName"));
                warrantyCard.setCustomerID(rs.getInt("CustomerID"));
                warrantyCard.setCustomerName(rs.getString("Name"));
                warrantyCard.setCustomerPhone(rs.getString("Phone"));
                return warrantyCard;

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public WarrantyCard getWarrantyCardById(int id) {
        return getWarrantyCardByField("WarrantyCardID", id + "");
    }

    public WarrantyCard getWarrantyCardByCode(String code) {

        String sql = "SELECT [WarrantyCardID]\n"
                + "      ,[WarrantyCardCode]\n"
                + "      ,[IssueDescription]\n"
                + "      ,[WarrantyStatus]\n"
                + "      ,[ReturnDate]\n"
                + "      ,[DoneDate]\n"
                + "      ,[CompleteDate]\n"
                + "      ,[CancelDate]\n"
                + "      ,[CreatedDate]\n"
                + "  FROM [dbo].[WarrantyCard]\n"
                + "  WHERE WarrantyCardCode =?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                WarrantyCard wr = new WarrantyCard();
                wr.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                wr.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                wr.setIssueDescription(rs.getString("IssueDescription"));
                wr.setWarrantyStatus(rs.getString("WarrantyStatus"));
                wr.setReturnDate(rs.getDate("ReturnDate"));
                wr.setDonedDate(rs.getDate("DoneDate"));
                wr.setCompletedDate(rs.getDate("CompleteDate"));
                wr.setCanceldDate(rs.getDate("CancelDate"));
                wr.setCreatedDate(rs.getDate("CreatedDate"));
                wr.setImages(getMediaURLs(wr.getWarrantyCardID(), "image"));
                wr.setVideos(getMediaURLs(wr.getWarrantyCardID(), "video"));
                return wr;

            }
        } catch (SQLException e) {

        }
        return null;
    }

    private WarrantyCard getWarrantyCardByField(String field, String para) {

        String sql = SELECT_STRING + "WHERE wc." + field + "=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, para);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return mapWarrantyCard(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addWarrantyCardProcess(int warrantyCardId, int handlerId, String action, String note) {
        String sql = """
                     INSERT INTO [dbo].[WarrantyCardProcess]
                                ([WarrantyCardID]
                                ,[HandlerID]
                                ,[Action]
                                ,[Note])
                          VALUES(
                                ?
                                ,?
                                ,?
                                ,?)""";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, warrantyCardId);
            ps.setInt(2, handlerId);
            ps.setString(3, action);
            ps.setString(4, note);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private WarrantyCard mapWarrantyCard(ResultSet rs) throws SQLException {
        WarrantyCard warrantyCard = new WarrantyCard();
        warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
        warrantyCard.setWarrantyProductID(rs.getInt("WarrantyProductID"));
        warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
        warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
        warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
        warrantyCard.setCreatedDate(rs.getTimestamp("CreatedDate"));
        warrantyCard.setReturnDate(rs.getTimestamp("ReturnDate"));
        warrantyCard.setDonedDate(rs.getTimestamp("DoneDate"));
        warrantyCard.setHandlerID(rs.getInt("HandlerID"));
        warrantyCard.setCompletedDate(rs.getTimestamp("CompleteDate"));
        warrantyCard.setCanceldDate(rs.getTimestamp("CancelDate"));
        warrantyCard.setProductDetailCode(rs.getString("ProductCode"));
        warrantyCard.setCustomerID(rs.getInt("CustomerID"));
        try {
            warrantyCard.setProductCode(rs.getString("Code"));
        } catch (SQLException e) {
            System.out.println("Cột 'Code' không tồn tại, bỏ qua gán giá trị.");
        }

        warrantyCard.setProductName(rs.getString("ProductName"));
        warrantyCard.setCustomerName(rs.getString("CustomerName"));
        warrantyCard.setCustomerPhone(rs.getString("CustomerPhone"));

        // Lấy danh sách ảnh từ bảng Media
        warrantyCard.setImages(getMediaURLs(warrantyCard.getWarrantyCardID(), "image"));
        warrantyCard.setVideos(getMediaURLs(warrantyCard.getWarrantyCardID(), "video"));

        return warrantyCard;
    }

    private List<String> getMediaURLs(int warrantyCardID, String mediaType) {
        List<String> mediaList = new ArrayList<>();
        String sql = "SELECT MediaURL FROM Media WHERE ObjectID = ? AND ObjectType = 'WarrantyCard' AND MediaType = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, warrantyCardID);
            ps.setString(2, mediaType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mediaList.add(rs.getString("MediaURL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mediaList;
    }

    public boolean createWarrantyCardForUnknownProduct(WarrantyCard warrantyCard) {
        String sql = "INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, IssueDescription, WarrantyStatus, ReturnDate, DoneDate, CompleteDate, CancelDate, CreatedDate, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, warrantyCard.getWarrantyCardCode());
            stmt.setInt(2, warrantyCard.getUnknownProductID());
            stmt.setString(3, warrantyCard.getIssueDescription());
            stmt.setString(4, warrantyCard.getWarrantyStatus());
            stmt.setString(5, warrantyCard.getFormatReturnDate());
            stmt.setString(6, warrantyCard.getFormatDonedDate());
            stmt.setString(7, warrantyCard.getFormatCompletedDate());
            stmt.setString(8, warrantyCard.getFormatCanceldDate());
            stmt.setString(9, warrantyCard.getFormatCreatedDate());
            //stmt.setString(10, warrantyCard.getImage());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
        }
        return false;
    }

    private Timestamp parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            if (dateStr.contains("T")) {
                dateStr = dateStr.replace("T", " ") + ":00";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
            return Timestamp.valueOf(dateTime);
        } catch (Exception e) {
            System.out.println("Error in parseDate: " + e.getMessage());
            return null;
        }
    }

    public boolean createWarrantyCard(
            int handlerID, int warrantyProductID,
            String issueDescription, String warrantyStatus,
            String returnDate, String doneDate, String completeDate, String cancelDate,
            String imagePath) {

        String sql = "INSERT INTO WarrantyCard (HandlerID, WarrantyCardCode, WarrantyProductID, "
                + "IssueDescription, WarrantyStatus, ReturnDate, DoneDate, CompleteDate, CancelDate, CreatedDate, Image) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";

        String warrantyCardCode = generateWarrantyCardCode();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, handlerID);
            stmt.setString(2, warrantyCardCode);
            stmt.setInt(3, warrantyProductID);
            stmt.setString(4, issueDescription);
            stmt.setString(5, warrantyStatus);

            stmt.setTimestamp(6, parseDate(returnDate) != null ? parseDate(returnDate) : null);
            stmt.setTimestamp(7, parseDate(doneDate) != null ? parseDate(doneDate) : null);
            stmt.setTimestamp(8, parseDate(completeDate) != null ? parseDate(completeDate) : null);
            stmt.setTimestamp(9, parseDate(cancelDate) != null ? parseDate(cancelDate) : null);

            stmt.setString(10, imagePath);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
   

    public static void main(String[] args) {
        WarrantyCardDAO d = new WarrantyCardDAO();
        System.out.println(        d.getTotalCards("", "", "", 1));

    }

}
