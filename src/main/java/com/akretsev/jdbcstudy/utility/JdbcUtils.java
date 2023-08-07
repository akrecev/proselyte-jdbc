package com.akretsev.jdbcstudy.utility;

import lombok.experimental.UtilityClass;

import java.sql.*;

@UtilityClass
public class JdbcUtils {
//    private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String URL = "jdbc:h2:file:./db/jdbc";
    private static final String USER = "user";
    private static final String PASSWORD = "password";


    private static Connection connection;

    static {
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement prepareStatementWithKey(String SQL) throws SQLException {
        return getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
    }

    public static PreparedStatement prepareStatement(String SQL) throws SQLException {
        return getConnection().prepareStatement(SQL);
    }

    public static Connection getConnection() {
        return connection;
    }


}
