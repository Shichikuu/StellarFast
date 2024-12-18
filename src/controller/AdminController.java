package controller;

import model.*;

import java.util.List;

public class AdminController {

    private List<Event> viewAllEvents(){
        return Admin.viewAllEvents();
    }

    public Event viewEventDetails(String EventID){
         return Admin.viewEventDetails(EventID);
    }

    public boolean deleteEvent(String EventID){
        return Admin.deleteEvent(EventID);
    }

    public boolean deleteUser(String UserID){
        return Admin.deleteUser(UserID);
    }

    public List<User> getAllUsers(){
        return Admin.getAllUsers();
    }

    public List<Event> getAllEvents(){
        return Admin.getAllEvents();
    }

    public List<Guest> getGuestsByTransactionID(String eventID){
        return Admin.getGuestsByTransactionID(eventID);
    }

    public List<Vendor> getVendorsByTransactionID(String eventID){
        return Admin.getVendorsByTransactionID(eventID);
    }
}
