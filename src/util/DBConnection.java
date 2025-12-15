package com.tourbooking.db;
import java.sql.*;

public class DBConnection {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=TourBookingDB;encrypt=false";
    private static final String USER = "sa";  
    private static final String PASS = "yourpassword";  // Thay bằng password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

}
