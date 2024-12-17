package model;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private String user_id;
    private String user_email;
    private String user_name;
    private String user_password;
    private String user_role;
    protected static DatabaseConnection db = DatabaseConnection.getInstance();


    public User(String user_id, String user_email, String user_name, String user_password, String user_role) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_role = user_role;
    }

    public User(){

    }

    public void register(String email, String name, String password, String role) {
        String query = "INSERT INTO users (userId, userEmail, userName, userPassword, userRole) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            // Asumsikan user_id adalah UUID
            this.user_id = java.util.UUID.randomUUID().toString();
            ps.setString(1, this.user_id);
            ps.setString(2, email);
            ps.setString(3, name);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User login(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null && user.getUser_password().equals(password)) {
            return user;
        }
        return null;
    }

    public void changeProfile(String email, String name, String oldPassword, String newPassword) {
        String query = "UPDATE users SET userEmail = ?, userName = ?, userPassword = ? WHERE userId = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, name);
            ps.setString(3, newPassword);
            ps.setString(4, this.user_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE userEmail = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("userId"),
                            rs.getString("userEmail"),
                            rs.getString("userName"),
                            rs.getString("userPassword"),
                            rs.getString("userRole")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserByUsername(String name) {
        String query = "SELECT * FROM users WHERE userName = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("userId"),
                            rs.getString("userEmail"),
                            rs.getString("userName"),
                            rs.getString("userPassword"),
                            rs.getString("userRole")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkRegisterInput(String email, String name, String password) {
        // Cek apakah email sudah digunakan
        if (getUserByEmail(email) != null) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        // Cek apakah username sudah digunakan
        if (getUserByUsername(name) != null) {
            throw new IllegalArgumentException("Username is already in use.");
        }

    }

    public void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {

        // Cek apakah email baru sudah digunakan oleh user lain
        User existingEmailUser = getUserByEmail(email);
        if (existingEmailUser != null && !existingEmailUser.getUser_id().equals(this.user_id)) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        // Cek apakah username baru sudah digunakan oleh user lain
        User existingNameUser = getUserByUsername(name);
        if (existingNameUser != null && !existingNameUser.getUser_id().equals(this.user_id)) {
            throw new IllegalArgumentException("Username is already in use.");
        }

        if (!this.user_password.equals(oldPassword)) {
            throw new IllegalArgumentException("Old password does not match.");
        }


    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
