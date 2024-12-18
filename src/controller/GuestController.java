package controller;

import model.Event;
import model.Guest;
import model.Invitation;
import model.User;
import util.Session;

import java.util.ArrayList;
import java.util.List;

public class GuestController {

    public void acceptInvitation(String eventID) {
        User currUser = Session.getInstance().getCurrentUser();
        Guest guest = getGuestById(currUser.getUser_id());
        guest.acceptInvitation(eventID);
    }

    // Asumsi : Di dalam sequence diagram, Vendor memiliki method ini, tetapi di dalam model class diagram tidak ada.
    //          Diasumsikan Guest juga memiliki method ini untuk melihat list invitation yang diterima.
    public List<Event> viewAcceptedEvents(String email) {
        User currUser = Session.getInstance().getCurrentUser();
        Guest guest = getGuestById(currUser.getUser_id());
        return guest.viewAcceptedEvents(email);
    }


    public List<Invitation> getInvitations(String email) {
        User currUser = Session.getInstance().getCurrentUser();
        Guest guest = getGuestById(currUser.getUser_id());
        return guest.getInvitations(email);
    }

    // Added method
    public Guest getGuestById(String guestId) {
        return Guest.getGuestById(guestId);
    }
}
