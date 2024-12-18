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
        if(currUser instanceof Guest) {
            ((Guest) currUser).acceptInvitation(eventID);
        }
    }

    // Asumsi : Di dalam sequence diagram, Vendor memiliki method ini, tetapi di dalam model class diagram tidak ada.
    //          Diasumsikan Guest juga memiliki method ini untuk melihat list invitation yang diterima.
    public List<Event> viewAcceptedEvents(String email) {
        User currUser = Session.getInstance().getCurrentUser();
        if(currUser instanceof Guest) {
            return ((Guest) currUser).viewAcceptedEvents(email);
        }
        return new ArrayList<>();
    }


    public List<Invitation> getInvitations(String email) {
        User currUser = Session.getInstance().getCurrentUser();
        if(currUser instanceof Guest) {
            return ((Guest) currUser).getInvitations(email);
        }
        return new ArrayList<>();
    }
}
