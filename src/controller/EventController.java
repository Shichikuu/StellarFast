package controller;

import database.DatabaseConnection;
import model.Event;
import model.EventOrganizer;

import java.time.LocalDate;

public class EventController {
    private DatabaseConnection db = DatabaseConnection.getInstance();

    public void createEvent(String eventName, LocalDate date, String location, String description, String organizerID) {
        checkCreateEventInput(eventName, date, location, description);
        EventOrganizer.createEvent(eventName, date.toString(), location, description, organizerID);
    }

    public Event viewEventDetails(String eventID) {
        return Event.viewEventDetails(eventID);
    }

    // Di class diagram tidak ada method ini, tetapi di sequence diagram ada
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

    // Tambahan method buat invitation supaya bisa mengambil nama event
    public String getEventNameFromEventId(String eventID) {
        return Event.getEventNameFromEventId(eventID);
    }
}
