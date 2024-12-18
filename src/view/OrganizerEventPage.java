package view;

import controller.EventOrganizerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Event;
import model.User;
import util.Session;

import java.util.List;

public class OrganizerEventPage {
    public Scene scene;
    private BorderPane root;
    private TableView<Event> eventTable;
    private ObservableList<Event> eventList;
    private User currUser;
    private EventOrganizerController eoc;

    public OrganizerEventPage() {
        root = new BorderPane();
        eoc = new EventOrganizerController();
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
        colEventId.setMinWidth(root.getWidth()/4);

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_name()));
        colEventName.setMinWidth(root.getWidth()/4);

        TableColumn<Event, String> colEventDate = new TableColumn<>("Event Date");
        colEventDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_date()));
        colEventDate.setMinWidth(root.getWidth()/4);

        TableColumn<Event, String> colEventLocation = new TableColumn<>("Event Location");
        colEventLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEvent_location()));
        colEventLocation.setMinWidth(root.getWidth()/4);

        eventTable.getColumns().addAll(colEventId, colEventName, colEventDate, colEventLocation);
        eventTable.setItems(eventList);
        eventTable.setPrefHeight(500);

        eventTable.setRowFactory(tv -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Event clickedEvent = row.getItem();
                    Main.redirect(new OrganizerEventDetailsPage(clickedEvent.getEvent_id()).scene);
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
        List<Event> events = eoc.viewOrganizedEvents(currUser.getUser_id());
        eventList.setAll(events);
    }
}