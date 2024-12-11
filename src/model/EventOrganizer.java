package model;

public class EventOrganizer extends User{
    private String events_created;

    public EventOrganizer(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role);
    }

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
