package model;

public class Admin extends User{
    public Admin(String user_id, String user_email, String user_name, String user_password, String user_role) {
        super(user_id, user_email, user_name, user_password, user_role);
    }

    public void viewAllEvents(){

    }

    public void viewEventDetails(String EventID){

    }

    public void deleteEvent(String EventID){

    }

    public void deleteUser(String UserID){

    }

    public void getAllUsers(){

    }

    public void getAllEvents(){

    }

    public void getGuestsByTransactionID(String eventID){

    }

    public void getVendorsByTransactionID(String eventID){

    }


}
