package view;

import controller.AdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Event;
import model.User;
import util.Session;

import java.util.List;

public class AdminEventPage {
    public Scene scene;
    private BorderPane root;
    private TableView<Event> eventTable;
    private Button btnViewDetails, btnDeleteEvent, btnBack;
    private ObservableList<Event> eventList;
    private User currUser;
    private AdminController ac;

    public AdminEventPage() {
        root = new BorderPane();
        ac = new AdminController();
        currUser = Session.getInstance().getCurrentUser();
        initTable();
        initButtons();
        setLayout();

        fetchAllEvents();

        scene = new Scene(root, 1100, 550);
        Main.redirect(scene);
    }

    private void initTable() {
        eventTable = new TableView<>();
        eventList = FXCollections.observableArrayList();

        TableColumn<Event, String> colEventId = new TableColumn<>("Event ID");
        colEventId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_id()));

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_name()));

        TableColumn<Event, String> colEventDate = new TableColumn<>("Event Date");
        colEventDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_date()));

        TableColumn<Event, String> colEventLocation = new TableColumn<>("Event Location");
        colEventLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_location()));

        eventTable.getColumns().addAll(colEventId, colEventName, colEventDate, colEventLocation);
        eventTable.setItems(eventList);
        eventTable.setPrefHeight(500);
    }

    private void initButtons() {
        btnViewDetails = new Button("View Details");
        btnViewDetails.setOnAction(e -> viewEventDetails());
            btnDeleteEvent = new Button("Delete Event");
            btnDeleteEvent.setOnAction(e -> deleteSelectedEvent());


    }

    private void setLayout() {
            HBox buttonBox = new HBox(10, btnViewDetails, btnDeleteEvent);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10));
            root.setBottom(buttonBox);

        root.setCenter(eventTable);
        root.setPadding(new Insets(10));
    }

    private void fetchAllEvents() {
        List<Event> events = ac.getAllEvents();
        eventList.setAll(events);
    }

    private void viewEventDetails() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("No Event Selected", "Please select an event to view details.");
            return;
        }
        Main.redirect(new AdminEventDetailsPage(selectedEvent.getEvent_id()).scene);
    }

    private void deleteSelectedEvent() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("No Event Selected", "Please select an event to delete.");
            return;
        }

        boolean confirmed = showConfirmation("Delete Event", "Are you sure you want to delete the event: " + selectedEvent.getEvent_name() + "?");
        if (confirmed) {
            boolean success = ac.deleteEvent(selectedEvent.getEvent_id());
            if (success) {
                eventList.remove(selectedEvent);
                showAlert("Success", "Event deleted successfully.");
            } else {
                showAlert("Error", "Failed to delete the event.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }
}
