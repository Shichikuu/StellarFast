package view;

import controller.VendorController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.Event;
import util.Session;

import java.util.List;

public class VendorEventPage {
    public Scene scene;
    private BorderPane root;
    private TableView<Event> eventTable;
    private ObservableList<Event> eventList;
    private VendorController vc;
    private String vendorEmail;

    public VendorEventPage() {
        root = new BorderPane();
        vc = new VendorController();
        vendorEmail = Session.getInstance().getCurrentUser().getUser_email();
        initTable();
        setLayout();

        fetchAcceptedEvents();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initTable() {
        eventTable = new TableView<>();
        eventList = FXCollections.observableArrayList();

        TableColumn<Event, String> colEventId = new TableColumn<>("Event ID");
        colEventId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_id()));
        colEventId.setPrefWidth(root.getWidth() / 4);

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_name()));
        colEventId.setPrefWidth(root.getWidth() / 4);

        TableColumn<Event, String> colEventDate = new TableColumn<>("Event Date");
        colEventDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_date()));
        colEventId.setPrefWidth(root.getWidth() / 4);

        TableColumn<Event, String> colEventLocation = new TableColumn<>("Event Location");
        colEventLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_location()));
        colEventId.setPrefWidth(root.getWidth() / 4);

        eventTable.getColumns().addAll(colEventId, colEventName, colEventDate, colEventLocation);
        eventTable.setItems(eventList);
        eventTable.setPrefHeight(500);

        // Set row click event to view details
        eventTable.setRowFactory(tv -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Event clickedEvent = row.getItem();
                    Main.redirect(new EventDetailsPage(clickedEvent).scene);
                }
            });
            return row;
        });
    }

    private void setLayout() {
        root.setCenter(eventTable);
        root.setPadding(new Insets(10));
    }

    private void fetchAcceptedEvents() {
        try {
            List<Event> events = vc.viewAcceptedEvents(vendorEmail);
            eventList.setAll(events);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
