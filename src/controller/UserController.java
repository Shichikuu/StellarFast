package controller;

import model.User;
import util.Session;


public class UserController {

    public void register(String email, String name, String password, String role) {
            // Validasi input
            checkRegisterInput(email, name, password);

            // Validasi database
            User user = new User();
            user.checkRegisterInput(email, name, password);

            // Jika semua validasi lolos, lakukan registrasi
            user.register(email, name, password, role);
    }

    public User login(String email, String password) {
        // For Administrator
        if(email.equals("admin") && password.equals("admin")) {
            User admin = new User();
            admin.setUser_email("admin");
            admin.setUser_name("admin");
            admin.setUser_password("admin");
            admin.setUser_role("Admin");
            return admin;
        }

        checkLoginInput(email, password);

        User user = User.login(email, password);

        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return user;

    }

    public void changeProfile(String email, String name, String oldPassword, String newPassword) {
        checkChangeProfileInput(email, name, oldPassword, newPassword);

        User user = Session.getInstance().getCurrentUser();
        user.checkChangeProfileInput(email, name, oldPassword, newPassword);

        if(newPassword.trim().isEmpty()) {
            user.changeProfile(email, name, oldPassword, user.getUser_password());
        }else{
            user.changeProfile(email, name, oldPassword, newPassword);
        }


        user.setUser_email(email);
        user.setUser_name(name);
        user.setUser_password(newPassword);
    }

    public User getUserByEmail(String email) {
        User user = User.getUserByEmail(email);
        return user;
    }

    public User getUserByUsername(String name) {
        User user = User.getUserByUsername(name);
        return user;
    }


    public void checkRegisterInput(String email, String name, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        // Check for "@" symbol
        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex == 0 || atIndex == email.length() - 1) {
            throw new IllegalArgumentException("Invalid email format: missing or misplaced '@'.");
        }

        // Check for "." after the "@" symbol
        int dotIndex = email.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex < atIndex || dotIndex == email.length() - 1) {
            throw new IllegalArgumentException("Invalid email format: missing or misplaced domain.");
        }

        // Check for invalid characters
        for (char c : email.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '@' && c != '.' && c != '-' && c != '_') {
                throw new IllegalArgumentException("Invalid email format: contains invalid characters.");
            }
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
        }

        // Check for "@" symbol
        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex == 0 || atIndex == email.length() - 1) {
            throw new IllegalArgumentException("Invalid email format: missing or misplaced '@'.");
        }

        // Check for "." after the "@" symbol
        int dotIndex = email.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex < atIndex || dotIndex == email.length() - 1) {
            throw new IllegalArgumentException("Invalid email format: missing or misplaced domain.");
        }

        // Check for invalid characters
        for (char c : email.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '@' && c != '.' && c != '-' && c != '_') {
                throw new IllegalArgumentException("Invalid email format: contains invalid characters.");
            }
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if(newPassword != null && !newPassword.trim().isEmpty()) {
            if (newPassword.length() < 5) {
                throw new IllegalArgumentException("New password must be at least 5 characters long.");
            }

            if (oldPassword == null || oldPassword.trim().isEmpty()) {
                throw new IllegalArgumentException("Old password cannot be empty.");
            }
        }

    }

    //Added method
    public User getUserById(String id) {
        return User.getUserById(id);
    }
}
