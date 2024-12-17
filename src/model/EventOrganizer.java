package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventOrganizer extends User{
    private String events_created;

    public EventOrganizer(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role);
    }

    public void createEvent(String eventName, String date, String location, String description, String organizerID) {

    }


    public List<Event> viewOrganizedEvents(String userID){
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE organizerId = ?";
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

    // Asumsi : Semua guest dan vendor yang dilist merupakan semua guest dan vendor yang telah diinvite tanpa peduli
    //          apakah mereka sudah accept invitation atau belum.
    public String viewOrganizedEventDetails(String eventID) {
        StringBuilder details = new StringBuilder();
        String query = "SELECT * FROM events WHERE eventId = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventID);
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

    public List<User> getGuests(){
        List<User> guests = new ArrayList<>();
        String query = "SELECT * FROM users WHERE userRole = 'Guest'";
        try (PreparedStatement ps = db.preparedStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                guests.add(new User(
                        rs.getString("userID"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userPassword"),
                        rs.getString("userRole")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guests;
    }

    public List<User> getVendors(){
        List<User> vendors = new ArrayList<>();
        String query = "SELECT * FROM users WHERE userRole = 'Vendor'";
        try (PreparedStatement ps = db.preparedStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vendors.add(new User(
                        rs.getString("userID"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userPassword"),
                        rs.getString("userRole")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vendors;
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

    public void checkCreateEventInput(String eventName, String date, String location, String description) {

    }

    public void checkAddVendorInput(String vendorID){

    }

    public void checkAddGuestInput(String vendorID){

    }

    public void editEventName(String eventID, String eventName) {

    }
}
