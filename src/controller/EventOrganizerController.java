package controller;

import model.*;

import java.time.LocalDate;
import java.util.List;

public class EventOrganizerController {


    public void createEvent(String eventName, LocalDate date, String location, String description, String organizerID) {
        checkCreateEventInput(eventName, date, location, description);
        EventOrganizer.createEvent(eventName, date.toString(), location, description, organizerID);
    }

    public List<Event> viewOrganizedEvents(String userID){
        return EventOrganizer.viewOrganizedEvents(userID);
    }

    public Event viewOrganizedEventDetails(String eventID) {
        return EventOrganizer.viewOrganizedEventDetails(eventID);
    }

    public List<Guest> getGuests(){
        return EventOrganizer.getGuests();
    }

    public List<Vendor> getVendors(){
        return EventOrganizer.getVendors();
    }

    public List<Guest> getGuestsByTransactionID(String eventID){
        return EventOrganizer.getGuestsByTransactionID(eventID);
    }

    public List<Vendor> getVendorsByTransactionID(String eventID){
        return EventOrganizer.getVendorsByTransactionID(eventID);
    }

    public void checkCreateEventInput(String eventName, LocalDate date, String location, String description) {
        if (eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty.");
        }

        if (date == null) {
            throw new IllegalArgumentException("Event date cannot be empty.");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Event date must be in the future.");
        }

        if (location.trim().isEmpty()) {
            throw new IllegalArgumentException("Event location cannot be empty.");
        }

        if (location.trim().length() < 5) {
            throw new IllegalArgumentException("Event location must be at least 5 characters long.");
        }

        if(description.trim().isEmpty()){
            throw new IllegalArgumentException("Event description cannot be empty.");
        }

        if (description.trim().length() > 200) {
            throw new IllegalArgumentException("Event description must be 200 characters or less.");
        }
    }

    // Penambahan parameter eventID
    public void checkAddVendorInput(String vendorID, String eventID){
        UserController uc = new UserController();
        User vendor = uc.getUserById(vendorID);
        if(EventOrganizer.checkAddVendorInput(vendorID, eventID)){
            throw new IllegalArgumentException( vendor.getUser_name() + " is already invited to this event.");
        }
    }

    // Penambahan parameter eventID
    public void checkAddGuestInput(String guestID, String eventID){
        UserController uc = new UserController();
        User guest = uc.getUserById(guestID);
        if(EventOrganizer.checkAddGuestInput(guestID, eventID)){
            throw new IllegalArgumentException( guest.getUser_name() + " is already invited to this event.");
        }

    }

    public void editEventName(String eventID, String eventName) {
        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty.");
        }
        EventOrganizer.editEventName(eventID, eventName);
    }

    // Penambahan Method addGuestToEvent dan addVendorToEvent
    public void addGuestsToEvent(String eventID, List<Guest> guests){
        InvitationController invitationController = new InvitationController();
        for(Guest g : guests){
            checkAddGuestInput(g.getUser_id(), eventID);
            invitationController.sendInvitation(g.getUser_email(), eventID);
        }
    }

    public void addVendorsToEvent(String eventID, List<Vendor> vendor){
        InvitationController invitationController = new InvitationController();
        for(Vendor v : vendor){
            checkAddVendorInput(v.getUser_id(), eventID);
            invitationController.sendInvitation(v.getUser_email(), eventID);
        }
    }
}
