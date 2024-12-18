package controller;

import database.DatabaseConnection;
import model.Invitation;
import model.User;

public class InvitationController {
    private DatabaseConnection db = DatabaseConnection.getInstance();

    // Tambahan parameter eventID
    public void sendInvitation(String email, String eventID) {
        UserController userController = new UserController();
        User user = userController.getUserByEmail(email);
        Invitation invitation = new Invitation(eventID, user.getUser_id(), user.getUser_role());
        invitation.sendInvitation(user.getUser_email());
    }

    public void acceptInvitation(String eventID) {

    }

    public void getInvitation(String email){

    }
}
