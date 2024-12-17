package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User{
    public Admin(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role);
    }

    public Admin(){
        super();
    }

    // Asumsi : Di dalam sequence diagram, model Event mengakses method ini, tetapi di dalam model class diagram,
    //          hanya admin yang bisa mengakses method ini sehingga method ini tetap di class Admin.
    public List<Event> viewAllEvents(){
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events";
        try (PreparedStatement ps = db.preparedStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                events.add(new Event(
                        rs.getString("eventId"),
                        rs.getString("eventName"),
                        rs.getString("eventDate"),
                        rs.getString("eventLocation"),
                        rs.getString("eventDescription"),
                        rs.getString("organizerId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;

    }

    public String viewEventDetails(String EventID){
        StringBuilder details = new StringBuilder();
        String query = "SELECT * FROM events WHERE eventId = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, EventID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    details.append("Event Details:\n")
                            .append("Name: ").append(rs.getString("eventName")).append("\n")
                            .append("Date: ").append(rs.getString("eventDate")).append("\n")
                            .append("Location: ").append(rs.getString("eventLocation")).append("\n")
                            .append("Description: ").append(rs.getString("eventDescription")).append("\n\n");
                } else {
                    return "Event not found.";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details.toString();
    }

    // Asumsi : Di dalam sequence diagram, model Event mengakses method ini, tetapi di dalam model class diagram, hanya admin yang bisa mengakses method ini
    //          sehingga method ini tetap di class Admin.
    public boolean deleteEvent(String EventID){
        String deleteInvitations = "DELETE FROM invitations WHERE eventId = ?";
        String deleteEvent = "DELETE FROM events WHERE eventId = ?";
            try (PreparedStatement psInv = db.preparedStatement(deleteInvitations);
                 PreparedStatement psEvent = db.preparedStatement(deleteEvent)) {
                psInv.setString(1, EventID);
                psInv.executeUpdate();

                psEvent.setString(1, EventID);
                int rowsAffected = psEvent.executeUpdate();
                return rowsAffected > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }

    public boolean deleteUser(String UserID){
        String deleteInvitations = "DELETE FROM invitations WHERE userId = ?";
        String deleteEvent = "DELETE FROM events WHERE organizerId = ?";
        String deleteUser = "DELETE FROM users WHERE userId = ?";
        try (PreparedStatement psInv = db.preparedStatement(deleteInvitations);
             PreparedStatement psEvent = db.preparedStatement(deleteEvent);
             PreparedStatement psUser = db.preparedStatement(deleteUser)) {
            psInv.setString(1, UserID);
            psInv.executeUpdate();
            psEvent.setString(1, UserID);
            psEvent.executeUpdate();
            psUser.setString(1, UserID);
            int rowsAffected = psUser.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement ps = db.preparedStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("userId"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userPassword"),
                        rs.getString("userRole")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // Asumsi : viewAllEvents() memiliki fungsi yang sama dengan getAllEvents();
    public List<Event> getAllEvents(){
        return viewAllEvents();
    }

    public List<User> getGuestsByTransactionID(String eventID){
        List<User> guests = new ArrayList<>();
        String query = "SELECT u.* FROM users u JOIN Invitation i ON u.userID = i.userID WHERE i.eventID = ? AND u.userRole = 'Guest'";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    guests.add(new User(
                            rs.getString("userID"),
                            rs.getString("userEmail"),
                            rs.getString("userName"),
                            rs.getString("userPassword"),
                            rs.getString("userRole")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guests;
    }

    public List<User> getVendorsByTransactionID(String eventID){
        List<User> vendors = new ArrayList<>();
        String query = "SELECT u.* FROM users u JOIN Invitation i ON u.userID = i.userID WHERE i.eventID = ? AND u.userRole = 'Vendor'";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vendors.add(new User(
                            rs.getString("userID"),
                            rs.getString("userEmail"),
                            rs.getString("userName"),
                            rs.getString("userPassword"),
                            rs.getString("userRole")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vendors;
    }


}
