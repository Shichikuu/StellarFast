package model;

import database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Event {
    private String event_id;
    private String event_name;
    private String event_date;
    private String event_location;
    private String event_description;
    private String organizer_id;
    private EventOrganizer organizer;
    private static DatabaseConnection db = DatabaseConnection.getInstance();;

    public Event(String event_id, String event_name, String event_date, String event_location, String event_description, String organizer_id) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_description = event_description;
        this.organizer_id = organizer_id;
    }

    public void createEvent(String eventName, String date, String location, String description, String organizerID) {
        String query = "INSERT INTO events (eventId, eventName, eventDate, eventLocation, eventDescription, organizerId) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            this.event_id = "EV" + System.currentTimeMillis();
            ps.setString(1, this.event_id);
            ps.setString(2, eventName);
            ps.setString(3, date);
            ps.setString(4, location);
            ps.setString(5, description);
            ps.setString(6, organizerID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Event viewEventDetails(String eventID) {
        String query = "SELECT * FROM events WHERE eventId = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Event(
                            rs.getString("eventId"),
                            rs.getString("eventName"),
                            rs.getString("eventDate"),
                            rs.getString("eventLocation"),
                            rs.getString("eventDescription"),
                            rs.getString("organizerId")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Added get organizer from its id
    private EventOrganizer getOrganizerByOrganizerId(String organizerID) {
        return organizer;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getOrganizer_id() {
        return organizer_id;
    }

    public void setOrganizer_id(String organizer_id) {
        this.organizer_id = organizer_id;
    }

    public EventOrganizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(EventOrganizer organizer) {
        this.organizer = organizer;
    }
}
