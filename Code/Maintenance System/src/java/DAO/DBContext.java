package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class DBContext {
    protected Connection connection;
    public DBContext()
    {

        try {
            String user = "sa";
<<<<<<< HEAD
            String pass = "123";
=======
            String pass = "123456";
>>>>>>> d9f7c03f5a3f12cd9b9d773fbdaab22ade21ba55
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=MaintainManagement;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
