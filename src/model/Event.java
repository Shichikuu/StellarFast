package model;

public class Event {
    private String event_id;
    private String event_name;
    private String event_date;
    private String event_location;
    private String event_description;
    private String organizer_id;
    private EventOrganizer organizer;

    public Event(String event_id, String event_name, String event_date, String event_location, String event_description, String organizer_id) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_description = event_description;
        this.organizer_id = organizer_id;
    }

    public void createEvent(String eventName, String date, String location, String description, String organizerID) {

    }

    public void viewEventDetails(String eventID) {

    }

    // Added get organizer from its id
    private EventOrganizer getOrganizerByOrganizerId(String organizerID) {
        return organizer;
    }
}
