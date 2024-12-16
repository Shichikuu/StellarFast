package controller;

import database.DatabaseConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController {

    public void register(String email, String name, String password, String role) {
        try {
            // Validasi input
            checkRegisterInput(email, name, password);

            // Validasi database
            User user = new User();
            user.checkRegisterInput(email, name, password);

            // Jika semua validasi lolos, lakukan registrasi
            user.register(email, name, password, role);
            System.out.println("Registration successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    public void login(String email, String password) {
        try{
            checkLoginInput(email, password);
            User user = User.login(email, password);

            if (user == null) {
                throw new IllegalArgumentException("Invalid email or password.");
            } else {
                System.out.println("Login successful! Welcome, " + user.getUser_name());
            }
        }catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

    }

    public void changeProfile(String email, String name, String oldPassword, String newPassword) {
        User user = User.getUserByEmail(email);
        try {
            if (user == null) {
                throw new IllegalArgumentException("Profile update failed: User not found.");
            }

            // Validasi input untuk change profile
            checkChangeProfileInput(email, name, oldPassword, newPassword);

            // Panggil method changeProfile

            user.changeProfile(email, name, oldPassword, newPassword);
            System.out.println("Profile updated successfully.");
        } catch (Exception e) {
            System.out.println("Profile update failed: " + e.getMessage());
        }
    }

    public User getUserByEmail(String email) {
        User user = User.getUserByEmail(email);
        if (user != null) {
            return user;
        } else {
            System.out.println("User not found.");
        }
        return null;
    }

    public User getUserByUsername(String name) {
        User user = User.getUserByUsername(name);
        if (user != null) {
            return user;
        } else {
            System.out.println("User not found.");
        }
        return null;
    }

    public void checkRegisterInput(String email, String name, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if (password == null || password.length() < 5) {
            throw new IllegalArgumentException("Password must be at least 5 characters long.");
        }
    }

    // added method
    public void checkLoginInput(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }

    public void checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
        if (email == null || email.trim().isEmpty()) {
           throw new IllegalArgumentException("Email cannot be empty.");
//            return -1;
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
//            return -2;
        }

        if (newPassword == null || newPassword.length() < 5) {
            throw new IllegalArgumentException("New password must be at least 5 characters long.");
//            return -3;
        }

        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Old password cannot be empty.");
//            return -4;
        }
//        return 0;
    }
}
