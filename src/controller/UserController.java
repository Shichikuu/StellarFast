package controller;

import database.DatabaseConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController {
    private DatabaseConnection db = DatabaseConnection.getInstance();

    public boolean register(String email, String name, String password, String role) {
        if(!checkRegisterInput(email, name, password)) {
            return false;
        }

        String query = "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = this.db.preparedStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.setString(4, role);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement ps = this.db.preparedStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            this.db.rs = ps.executeQuery();
            if (this.db.rs.next()) {
                // Berhasil login
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeProfile(String email, String name, String oldPassword, String newPassword) {
        // Validasi input
        if(!checkChangeProfileInput(email, name, oldPassword, newPassword)) {
            return false;
        }

        // Misalkan user login saat ini adalah user dengan email lama (oldEmail),
        // atau Anda memiliki mekanisme untuk mengetahui user mana yang sedang di-update
        // Di sini kita asumsikan bahwa kita sudah tahu email lama user dari sesi (misalnya disimpan di variable lain)
        // Untuk contoh ini, kita asumsikan user sedang mengganti data dirinya sendiri.
        User u = getUserByEmail(email);

        if(!checkChangeProfileInput(email, name, oldPassword, newPassword)) {
            return false;
        }

        // Jika oldPassword cocok, maka update email, username, password jika mereka tidak null / kosong
        // Asumsi: Jika parameter email atau name adalah string baru, maka update.
        // Jika newPassword != null && panjang >=5, update password ke newPassword.
        // Pada contoh ini, langsung update semua data sesuai parameter (dengan asumsi sudah divalidasi).

        String query = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, name);
            ps.setString(3, newPassword);
            ps.setString(4, u.getUser_id());
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, email);
            try {
                this.db.rs = ps.executeQuery();
                if (this.db.rs.next()) {
                    String id = this.db.rs.getString("id");
                    String userEmail = this.db.rs.getString("email");
                    String username = this.db.rs.getString("username");
                    String password = this.db.rs.getString("password");
                    String role = this.db.rs.getString("role");
                    return new User(id, userEmail, username, password, role);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByUsername(String name) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, name);
            try {
                this.db.rs = ps.executeQuery();
                if (this.db.rs.next()) {
                    String id = this.db.rs.getString("id");
                    String userEmail = this.db.rs.getString("email");
                    String username = this.db.rs.getString("username");
                    String password = this.db.rs.getString("password");
                    String role = this.db.rs.getString("role");
                    return new User(id, userEmail, username, password, role);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkRegisterInput(String email, String name, String password) {
        if (email == null || email.isEmpty()) {
            System.out.println("Email tidak boleh kosong!");
            return false;
        }
        if (name == null || name.isEmpty()) {
            System.out.println("Username tidak boleh kosong!");
            return false;
        }
        if (password == null || password.length() < 5) {
            System.out.println("Password minimal 5 karakter!");
            return false;
        }
        // Cek apakah email unik
        User uEmail = getUserByEmail(email);
        if (uEmail != null) {
            System.out.println("Email sudah digunakan!");
            return false;
        }

        // Cek apakah username unik
        User uName = getUserByUsername(name);
        if (uName != null) {
            System.out.println("Username sudah digunakan!");
            return false;
        }

        return true;
    }

    public boolean checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
        User u = getUserByEmail(email);
        if (email == null || email.isEmpty()) {
            System.out.println("Email tidak boleh kosong!");
            return false;
        }
        if (name == null || name.isEmpty()) {
            System.out.println("Name tidak boleh kosong!");
            return false;
        }
        if (oldPassword == null || oldPassword.isEmpty()) {
            System.out.println("Old password tidak boleh kosong!");
            return false;
        }
        if (newPassword == null || newPassword.length() < 5) {
            System.out.println("New password minimal 5 karakter!");
            return false;
        }

        // Cek apakah email baru berbeda dan unik
        // Jika user dengan email ini sudah ada dan bukan user yang sama, maka tidak unik
        // Namun disini kita tidak tahu user mana yang sedang login,
        // misal kita asumsikan jika sudah ada user dengan email itu maka invalid
        if (u != null) {

            System.out.println("Email sudah digunakan user lain!");
            return false;
        }


        return true;
    }
}
