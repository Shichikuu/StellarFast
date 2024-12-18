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

    public EventOrganizer(String user_id, String user_email, String user_name, String user_password) {
        super(user_id, user_email, user_name, user_password);
        this.user_role = "Event Organizer";
    }

    public static void createEvent(String eventName, String date, String location, String description, String organizerID) {
        String query = "INSERT INTO events (eventId, eventName, eventDate, eventLocation, eventDescription, organizerId) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, "EV" + System.currentTimeMillis());
            ps.setString(2, eventName);
            ps.setString(3, date);
            ps.setString(4, location);
            ps.setString(5, description);
            ps.setString(6, organizerID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static List<Event> viewOrganizedEvents(String userID){
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE organizerId = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, userID);
            try(ResultSet rs = ps.executeQuery()){
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }


    public static Event viewOrganizedEventDetails(String eventID) {
        String query = "SELECT * FROM events WHERE eventId = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Event(
                            rs.getString("eventId"),
                            rs.getString("eventName"),
                            rs.getString("eventDate"),
                            rs.getString("eventLocation"),
                            rs.getString("eventDescription"),
                            rs.getString("organizerId")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Guest> getGuests(){
        List<Guest> guests = new ArrayList<>();
        String query = "SELECT * FROM users WHERE userRole = 'Guest'";
        try (PreparedStatement ps = db.preparedStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                guests.add(new Guest(
                        rs.getString("userID"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userPassword")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guests;
    }

    public static List<Vendor> getVendors(){
        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT * FROM users WHERE userRole = 'Vendor'";
        try (PreparedStatement ps = db.preparedStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vendors.add(new Vendor(
                        rs.getString("userID"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userPassword")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vendors;
    }

    public static List<Guest> getGuestsByTransactionID(String eventID){
        List<Guest> guests = new ArrayList<>();
        String query = "SELECT u.* FROM users u JOIN invitations i ON u.userID = i.userID WHERE i.invitationStatus = 'Accepted' AND i.eventID = ? AND u.userRole = 'Guest'";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    guests.add(new Guest(
                            rs.getString("userID"),
                            rs.getString("userEmail"),
                            rs.getString("userName"),
                            rs.getString("userPassword")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guests;
    }

    public static List<Vendor> getVendorsByTransactionID(String eventID){
        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT u.* FROM users u JOIN invitations i ON u.userID = i.userID WHERE i.invitationStatus = 'Accepted' AND i.eventID = ? AND u.userRole = 'Vendor'";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vendors.add(new Vendor(
                            rs.getString("userID"),
                            rs.getString("userEmail"),
                            rs.getString("userName"),
                            rs.getString("userPassword")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vendors;
    }

    // Unused karena tidak mengakses database
    public void checkCreateEventInput(String eventName, String date, String location, String description) {

    }

    // Penambahan parameter eventID
    public static boolean checkAddVendorInput(String vendorID, String eventID) {
        String query = "SELECT 1 FROM invitations WHERE userId = ? AND eventId = ? AND invitationRole = 'Vendor'";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, vendorID);
            ps.setString(2, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Returns true if a record exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Penambahan parameter eventID
    public static boolean checkAddGuestInput(String guestID, String eventID) {
        String query = "SELECT 1 FROM invitations WHERE userId = ? AND eventId = ? AND invitationRole = 'Guest'";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, guestID);
            ps.setString(2, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Returns true if a record exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void editEventName(String eventID, String eventName) {
        String query = "UPDATE events SET eventName = ? WHERE eventId = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventName);
            ps.setString(2, eventID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
