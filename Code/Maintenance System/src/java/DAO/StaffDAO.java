package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Staff;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class StaffDAO extends DBContext {

    public Staff getStaffByUsenamePassword(String username, String password) {
        String sql = """
                     SELECT [StaffID]
                           ,[UsernameS]
                           ,[PasswordS]
                           ,[Name]
                           ,[RoleID]
                           ,[Email]
                           ,[Phone]
                           ,[Address]
                           ,[Image]
                       FROM [dbo].[Staff]
                       WHERE UsernameS = ? AND PasswordS = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setPasswordS(rs.getString("PasswordS"));
                staff.setRole(rs.getInt("RoleID"));
                staff.setName(rs.getString("Name"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setImage(rs.getString("Image"));
                staff.setPermission(getPermissionsOfStaff(staff));
                return staff;

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<String> getPermissionsOfStaff(Staff staff) {
        List<String> list = new ArrayList<>();
        String sql = """
                     select p.PermissionName from Staff s 
                     join Role_Permissions rp on s.RoleID=rp.RoleID
                     join [Permissions] p on rp.PermissionID=p.PermissionID
                     where s.StaffID=?""";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, staff.getStaffID());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("PermissionName"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void changePassword(Staff s) {
        String sql = """
                     UPDATE [dbo].[Staff]
                        SET [PasswordS] = ?
                      WHERE UsernameS = ?;""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s.getPasswordS());
            ps.setString(2, s.getUsernameS());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Staff getStaffByEmail(String email) {
        String sql = "SELECT [StaffID]\n"
                + "      ,[UsernameS]\n"
                + "      ,[PasswordS]\n"
                + "      ,[Name]\n"
                + "      ,[RoleID]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Staff]\n"
                + "  WHERE Email=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setPasswordS(rs.getString("PasswordS"));
                staff.setRole(rs.getInt("RoleID"));
                staff.setName(rs.getString("Name"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setImage(rs.getString("Image"));
                return staff;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean addStaff(String useNameS, String passworldS, String name, String role, String gender, String date, String email, String phone, String address, String image) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Staff (UsernameS, PasswordS, [Name], [RoleID],Gender, DateOfBirth, Email, Phone, [Address],Image) VALUES (?,?,?,?,?,?,?,?,?,?);";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, useNameS);
            stm.setString(2, passworldS);
            stm.setString(3, name);
            stm.setString(4, role);
            stm.setString(5, gender);
            stm.setString(6, date);
            stm.setString(7, email);
            stm.setString(8, phone);
            stm.setString(9, address);
            stm.setString(10, image);

            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean addStaff_Role(String staffID, String roleID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Staff_Role (StaffID, RoleID) VALUES (?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, staffID);
            stm.setString(2, roleID);
            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean updateStaff_Role(String staffID, String roleID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "UPDATE Staff_Role set RoleID = ? where StaffID =?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, roleID);
            stm.setString(2, staffID);
            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public String updateStaff_Role(String phone) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String staffID = null;
        String sql = "SELECT StaffID FROM Staff WHERE Phone = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                staffID = rs.getString("StaffID");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return staffID;
    }

    public boolean updateStaff(String staffID, String useNameS, String passworldS, String role, String gender, String date, String name, String email, String phone, String address, String image) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "UPDATE Staff SET UsernameS = ?, PasswordS = ?, Name = ?, RoleID = ? , Gender = ?, DateOfBirth = ?, Email = ?, Phone = ?, Address = ? ,Image = ? WHERE StaffID = ?;";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, useNameS);
            stm.setString(2, passworldS);
            stm.setString(3, name);
            stm.setString(4, role);
            stm.setString(5, gender);
            stm.setString(6, date);
            stm.setString(7, email);
            stm.setString(8, phone);
            stm.setString(9, address);
            stm.setString(10, image);
            stm.setString(11, staffID);

            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public ArrayList<Staff> getAllStaff(String searchname, String search, int pageIndex, int pageSize, String column, String sortOrder) {
        ArrayList<Staff> list = new ArrayList<>();
        String sql = "select *from Staff";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (searchname != null && !searchname.trim().isEmpty()) {
            if (search.equals("Name")) {
                sql += " WHERE Name LIKE ?";
            } else {
                sql += " WHERE Email LIKE ?";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by StaffID ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        }
        sql += " offset ? rows  fetch next ? rows only;";
        try {
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }
            int startIndex = (pageIndex - 1) * pageSize;
            stm.setInt(count++, startIndex);
            stm.setInt(count++, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                int role = rs.getInt("roleid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String date = rs.getString("dateofbirth");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                list.add(new Staff(staffID, usernameS, passwordS, role, name, gender, date, email, phone, address, image));

            }
        } catch (SQLException e) {
            System.out.println(e);

        }

        return list;
    }

    public ArrayList<Staff> getAllStaff(String searchname, String search, String column, String sortOrder) {
        ArrayList<Staff> list = new ArrayList<>();
        String sql = "select *from Staff ";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (searchname != null && !searchname.trim().isEmpty()) {
            if (search.equals("Name")) {
                sql += " WHERE Name LIKE ? ";
            } else {
                sql += " WHERE Email LIKE ? ";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by StaffID ";
        }

        try {
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                int role = rs.getInt("roleid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String date = rs.getString("dateofbirth");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                list.add(new Staff(staffID, usernameS, passwordS, role, name, gender, date, email, phone, address, image));

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Staff getInformationByID(String id) {
        Staff staff = new Staff();
        PreparedStatement stm;
        ResultSet rs;
        String sql = "SELECT * FROM Staff WHERE StaffID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                int role = rs.getInt("roleid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String date = rs.getString("dateofbirth");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                if (image == null || image.isEmpty()) {
                    image = "default-image.jpg"; // Đặt ảnh mặc định nếu không có ảnh trong DB
                }
                staff = new Staff(staffID, usernameS, passwordS, role, name, gender, date, email, phone, address, image);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return staff;
    }

    public boolean deleteStaff(String staffID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "DELETE FROM Staff WHERE StaffID=?;";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, staffID);
            rs = stm.executeQuery();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean isPhoneExists(String phone) {
        String query = "SELECT Phone FROM Staff WHERE Phone = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, phone);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean isUpdatePhoneExists(String phone, String staffID) {
        String query = "SELECT * FROM Staff";
        if(phone.endsWith("com")){
            query +=" WHERE Email = ? And StaffID <> ?";
        }else{
            query +=" WHERE Phone = ? And StaffID <> ?";
        }
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, staffID);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    
    
    

    public void importStaff(List<Staff> staffList) {
        String selectSQL = "SELECT * FROM Staff WHERE StaffID = ?";
        String insertSQL = "INSERT INTO Staff (UsernameS, PasswordS, Name, RoleID, Gender, DateOfBirth, Email, Phone, Address, Image) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSQL = "UPDATE Staff SET UsernameS=?, PasswordS=?, Name=?, RoleID=?, Gender=?, DateOfBirth=?, "
                + "Email=?, Phone=?, Address=?, Image=? WHERE StaffID=?";

        try {
            PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
            PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
            for (Staff staff : staffList) {
                selectStmt.setInt(1, staff.getStaffID());
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) { // Nếu tồn tại, kiểm tra sự thay đổi
                    boolean isChanged = !staff.getUsernameS().equals(rs.getString("UsernameS"))
                            || !staff.getPasswordS().equals(rs.getString("PasswordS"))
                            || !staff.getName().equals(rs.getString("Name"))
                            || staff.getRole() != rs.getInt("RoleID")
                            || !staff.getGender().equals(rs.getString("Gender"))
                            || !staff.getDate().equals(rs.getDate("DateOfBirth"))
                            || !staff.getEmail().equals(rs.getString("Email"))
                            || !staff.getPhone().equals(rs.getString("Phone"))
                            || !staff.getAddress().equals(rs.getString("Address"))
                            || !staff.getImage().equals(rs.getString("Image"));

                    if (isChanged) { // Nếu có thay đổi, thực hiện UPDATE
                        updateStmt.setString(1, staff.getUsernameS());
                        updateStmt.setString(2, staff.getPasswordS());
                        updateStmt.setString(3, staff.getName());
                        updateStmt.setInt(4, staff.getRole());
                        updateStmt.setString(5, staff.getGender());
                        updateStmt.setString(6, staff.getDate());
                        updateStmt.setString(7, staff.getEmail());
                        updateStmt.setString(8, staff.getPhone());
                        updateStmt.setString(9, staff.getAddress());
                        updateStmt.setString(10, staff.getImage());
                        updateStmt.setInt(11, staff.getStaffID());

                        updateStmt.executeUpdate();

                    }
                } else { // Nếu chưa tồn tại, INSERT mới
                    insertStmt.setString(1, staff.getUsernameS());
                    insertStmt.setString(2, staff.getPasswordS());
                    insertStmt.setString(3, staff.getName());
                    insertStmt.setInt(4, staff.getRole());
                    insertStmt.setString(5, staff.getGender());
                    insertStmt.setString(6, staff.getDate());
                    insertStmt.setString(7, staff.getEmail());
                    insertStmt.setString(8, staff.getPhone());
                    insertStmt.setString(9, staff.getAddress());
                    insertStmt.setString(10, staff.getImage());

                    insertStmt.executeUpdate();
                }
            }
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Staff getStaffById(int staffId) {
        String query = "SELECT * FROM Staff WHERE StaffID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                            rs.getInt("StaffID"),
                            rs.getString("UsernameS"),
                            rs.getString("PasswordS"),
                            rs.getInt("RoleID"),
                            rs.getString("Name"),
                            rs.getString("Gender"),
                            rs.getString("DateOfBirth"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getString("Image")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public String getRoleNameByStaffID(int staffID) {
        String roleName = null;
        String sql = "SELECT r.RoleName FROM Staff s "
                + "JOIN Role r ON s.RoleID = r.RoleID "
                + "WHERE s.StaffID = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                roleName = rs.getString("RoleName");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return roleName;
    }

    public boolean updateStaffWithNoImage(int staffID, String name, String gender, String dateOfBirth,
            String email, String phone, String address) {
        String sql = "UPDATE Staff SET Name = ?, Gender = ?, DateOfBirth = ?, Email = ?, Phone = ?, Address = ? WHERE StaffID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, gender);
            stmt.setString(3, dateOfBirth);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, address);
            stmt.setInt(7, staffID);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean updateStaffImage(int staffId, String imageUrl) {
        String sql = "UPDATE Staff SET Image = ? WHERE StaffID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, imageUrl);
            stmt.setInt(2, staffId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Staff> getAllTechnicians() {
        List<Staff> technicians = new ArrayList<>();
        String sql = "SELECT s.StaffID, s.UsernameS, s.Name, s.Gender, s.DateOfBirth, s.Email, s.Phone, s.Address, s.Image "
                + "FROM Staff s "
                + "JOIN [Role] r ON s.RoleID = r.RoleID "
                + "WHERE r.RoleName = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "Technician");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setName(rs.getString("Name"));
                staff.setGender(rs.getString("Gender"));
                staff.setDate(rs.getString("DateOfBirth"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setImage(rs.getString("Image"));
                technicians.add(staff);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return technicians;
    }

    public static void main(String[] args) {
        StaffDAO staffDAO = new StaffDAO();
        System.out.println(staffDAO.getStaffByUsenamePassword("tech01", "Cw2LaFmhUP2i/jGdPuB5aVCxAQg="));
        List<Staff> t = staffDAO.getAllTechnicians();

        for (Staff s : t) {
            System.out.println(s.getName());
        }

    }
}
