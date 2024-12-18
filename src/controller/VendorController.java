package controller;

import model.*;
import util.Session;

import java.util.ArrayList;
import java.util.List;

public class VendorController {

    public void acceptInvitation(String eventID) {
        User currUser = Session.getInstance().getCurrentUser();
        if(currUser instanceof Vendor) {
            ((Vendor) currUser).acceptInvitation(eventID);
        }
    }

    // Asumsi : Di dalam sequence diagram, Vendor memiliki method ini, tetapi di dalam model class diagram tidak ada
    //          sehingga method ini tetap di dalam class VendorController.
    public List<Event> viewAcceptedEvents(String email) {
        User currUser = Session.getInstance().getCurrentUser();
        if(currUser instanceof Vendor) {
            return ((Vendor) currUser).viewAcceptedEvents(email);
        }
        return new ArrayList<>();
    }


    public List<Invitation> getInvitations(String email) {
        User currUser = Session.getInstance().getCurrentUser();
        if(currUser instanceof Vendor) {
            return ((Vendor) currUser).getInvitations(email);
        }
        return new ArrayList<>();
    }

    public void manageVendor(String description, String product) {

    }

    public void checkManageVendorInput(String description, String product) {

    }
}
