package controller;

import model.*;
import util.Session;

import java.util.ArrayList;
import java.util.List;

public class VendorController {
    public void acceptInvitation(String eventID) {
        User currUser = Session.getInstance().getCurrentUser();
        Vendor vendor = getVendorById(currUser.getUser_id());
             vendor.acceptInvitation(eventID);

    }

    // Asumsi : Di dalam sequence diagram, Vendor memiliki method ini, tetapi di dalam model class diagram tidak ada
    //          sehingga method ini tetap di dalam class VendorController.
    public List<Event> viewAcceptedEvents(String email) {
        User currUser = Session.getInstance().getCurrentUser();
        Vendor vendor = getVendorById(currUser.getUser_id());
        return vendor.viewAcceptedEvents(email);
    }


    public List<Invitation> getInvitations(String email) {
        User currUser = Session.getInstance().getCurrentUser();
        Vendor vendor = getVendorById(currUser.getUser_id());
        return vendor.getInvitations(email);
    }

    public void manageVendor(String description, String product) {
        User currUser = Session.getInstance().getCurrentUser();
        Vendor vendor = getVendorById(currUser.getUser_id());
        checkManageVendorInput(description, product);
        vendor.manageVendor(description, product);
    }

    public void checkManageVendorInput(String description, String product) {
        if (product == null || product.trim().isEmpty()) {
            throw new IllegalArgumentException("Product Name cannot be empty.");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Product Description cannot be empty.");
        }

        if (description.length() > 200) {
            throw new IllegalArgumentException("Product Description cannot exceed 200 characters.");
        }
    }

    //Added Method
    public Vendor getVendorById(String vendorID){
        return Vendor.getVendorById(vendorID);
    }
}
