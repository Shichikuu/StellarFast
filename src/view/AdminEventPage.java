package view;

import controller.AdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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
        setLayout();

        fetchAllEvents();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initTable() {
        eventTable = new TableView<>();
        eventList = FXCollections.observableArrayList();

        TableColumn<Event, String> colEventId = new TableColumn<>("Event ID");
        colEventId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_id()));
        colEventId.setMinWidth(root.getWidth() / 4);

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_name()));
        colEventName.setMinWidth(root.getWidth() / 4);

        TableColumn<Event, String> colEventDate = new TableColumn<>("Event Date");
        colEventDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_date()));
        colEventDate.setMinWidth(root.getWidth() / 4);

        TableColumn<Event, String> colEventLocation = new TableColumn<>("Event Location");
        colEventLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_location()));
        colEventLocation.setMinWidth(root.getWidth() / 4);

        eventTable.getColumns().addAll(colEventId, colEventName, colEventDate, colEventLocation);
        eventTable.setItems(eventList);
        eventTable.setPrefHeight(500);

        eventTable.setRowFactory(tv -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Event clickedEvent = row.getItem();
                    Main.redirect(new AdminEventDetailsPage(clickedEvent.getEvent_id()).scene);
                }
            });
            return row;
        });
    }

    private void setLayout() {
        root.setCenter(eventTable);
        root.setPadding(new Insets(10));
    }

    private void fetchAllEvents() {
        List<Event> events = ac.getAllEvents();
        eventList.setAll(events);
    }
}
