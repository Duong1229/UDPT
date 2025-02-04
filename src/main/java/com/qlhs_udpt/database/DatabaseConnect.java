package com.qlhs_udpt.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnect
{ private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";
    private static final String PASSWORD = "291220";
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
            return connect;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver không được tìm thấy: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Kết nối thất bại: " + e.getMessage());
        }
        return null;
    }
}

