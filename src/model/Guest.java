package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Guest extends User{
    private String accepted_invitations;

    public Guest(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role);
    }

    public Guest(String user_id, String user_email, String user_name, String user_password) {
        super(user_id, user_email, user_name, user_password);
        this.user_role = "Guest";
    }

    public void acceptInvitation(String eventID) {
        String sql = "UPDATE invitations SET invitationStatus = 'Accepted' WHERE eventId = ? AND userId = ?";
        try (PreparedStatement ps = db.preparedStatement(sql)) {
            ps.setString(1, eventID);
            ps.setString(2, this.user_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Event> viewAcceptedEvents(String email) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.eventId, e.eventName, e.eventDate, e.eventLocation, e.eventDescription, e.organizerId " +
                "FROM events e " +
                "JOIN invitations i ON e.eventId = i.eventId " +
                "JOIN users u ON i.userId = u.userId " +
                "WHERE u.email = ? AND i.invitationStatus = 'Accepted'";
        try (PreparedStatement ps = db.preparedStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event event = new Event(
                        rs.getString("eventId"),
                        rs.getString("eventName"),
                        rs.getString("eventDate"),
                        rs.getString("eventLocation"),
                        rs.getString("eventDescription"),
                        rs.getString("organizerId")
                );
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    // Asumsi : Di dalam sequence diagram, Vendor memiliki method ini, tetapi di dalam model class diagram tidak ada.
    //          Diasumsikan Guest juga memiliki method ini untuk melihat list invitation yang diterima.
    public List<Invitation> getInvitations(String email) {
        List<Invitation> invitations = new ArrayList<>();
        String sql = "SELECT i.invitationId, i.eventId, i.userId, i.invitationRole, i.invitationStatus " +
                "FROM invitations i " +
                "JOIN events e ON i.eventId = e.eventId " +
                "JOIN users u ON i.userId = u.userId " +
                "WHERE u.email = ? AND i.invitationStatus = 'Pending'";
        try (PreparedStatement ps = db.preparedStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Invitation invitation = new Invitation(
                        rs.getString("invitationId"),
                        rs.getString("eventId"),
                        rs.getString("userId"),
                        rs.getString("invitationStatus"),
                        rs.getString("invitationRole")

                );
                invitations.add(invitation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invitations;
    }

    // Added Method
    public static Guest getGuestById(String userId) {
        String sql = "SELECT * FROM users WHERE userId = ? AND role = 'Guest'";
        try (PreparedStatement ps = db.preparedStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Guest(
                        rs.getString("userId"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
