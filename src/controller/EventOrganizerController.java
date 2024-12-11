package controller;

import database.DatabaseConnection;

public class EventOrganizerController {
    private DatabaseConnection db = DatabaseConnection.getInstance();

    public void createEvent(String eventName, String date, String location, String description, String organizerID) {

    }

    public void viewOrganizedEvents(String userID){

    }

    public void viewOrganizedEventDetails(String eventID) {

    }

    public void getGuests(){

    }

    public void getVendors(){

    }

    public void getGuestsByTransactionID(String eventID){

    }

    public void getVendorsByTransactionID(String eventID){

    }

    public void checkCreateEventInput(String eventName, String date, String location, String description) {

    }

    public void checkAddVendorInput(String vendorID){

    }

    public void checkAddGuestInput(String vendorID){

    }

    public void editEventName(String eventID, String eventName) {

    }
}
