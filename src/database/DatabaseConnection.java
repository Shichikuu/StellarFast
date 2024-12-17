package database;

import java.sql.*;

public class DatabaseConnection {
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "StellarFestDB";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", "localhost:3306", "StellarFestDB");
    private Connection con;
    private Statement st;
    public ResultSet rs;
    public ResultSetMetaData rsm;
    private static DatabaseConnection db;

    public static DatabaseConnection getInstance() {
        return db == null ? new DatabaseConnection() : db;
    }

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.CONNECTION, "root", "");
            this.st = this.con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ResultSet execQuery(String query) {
        try {
            this.rs = this.st.executeQuery(query);
            this.rsm = this.rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.rs;
    }

    public void execUpdate(String query) {
        try {
            this.st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public PreparedStatement preparedStatement(String query) {
        PreparedStatement ps = null;

        try {
            ps = this.con.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ps;
    }
}
