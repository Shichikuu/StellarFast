package model;

import database.DatabaseConnection;

import java.sql.PreparedStatement;

public class Invitation {
    private String invitation_id;
    private String event_id;
    private String user_id;
    private String invitation_status;
    private String invitation_role;
    private static DatabaseConnection db = DatabaseConnection.getInstance();

    public Invitation(String event_id, String user_id, String invitation_role) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.invitation_status = "Pending";
        this.invitation_role = invitation_role;
    }

    public void sendInvitation(String email) {
        String query = "INSERT INTO invitations (invitationId, eventId, userId, invitationStatus, invitationRole) " +
                "VALUES (?, ?, (SELECT userId FROM users WHERE userEmail = ?),'Pending', ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            this.invitation_id = "INV" + System.currentTimeMillis();
            ps.setString(1, this.invitation_id);
            ps.setString(2, this.event_id);
            ps.setString(3, email);
            ps.setString(4, this.invitation_role);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Added parameter for userId
    public void acceptInvitation(String eventID, String userID) {
        String query = "UPDATE invitations SET invitation_status = 'Accepted' WHERE user_id = ? AND event_id = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, userID);
            ps.setString(2, eventID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getInvitation(String email){

    }
}
