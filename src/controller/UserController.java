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

        user.changeProfile(email, name, oldPassword, newPassword);

        user.setUser_email(email);
        user.setUser_name(name);
        user.setUser_password(newPassword);
    }

    public User getUserByEmail(String email) {
        User user = User.getUserByEmail(email);
        if (user != null) {
            return user;
        }
        return null;
    }

    public User getUserByUsername(String name) {
        User user = User.getUserByUsername(name);
        if (user != null) {
            return user;
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
        }

        if(!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if (newPassword == null || newPassword.length() < 5) {
            throw new IllegalArgumentException("New password must be at least 5 characters long.");
        }

        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Old password cannot be empty.");
        }
    }
}
