package view;

import controller.AdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.*;

import java.util.List;

public class AdminEventDetailsPage {
    public Scene scene;
    private BorderPane root;
    private TextField txtEventName, txtEventDate, txtEventLocation;
    private TextArea txtEventDescription;
    private TableView<Guest> guestTable;
    private TableView<Vendor> vendorTable;
    private Button btnBack, btnDeleteEvent;
    private String eventId;

    private AdminController ac;


    public AdminEventDetailsPage(String eventId) {
        this.eventId = eventId;
        this.ac = new AdminController();
        root = new BorderPane();

        initFields();
        initTables();
        initButtons();
        setLayout();

        populateFields();
        fetchAttendees();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initFields() {
        txtEventName = new TextField();
        txtEventName.setEditable(false);

        txtEventDate = new TextField();
        txtEventDate.setEditable(false);

        txtEventLocation = new TextField();
        txtEventLocation.setEditable(false);

        txtEventDescription = new TextArea();
        txtEventDescription.setEditable(false);
        txtEventDescription.setWrapText(true);
        txtEventDescription.setPrefRowCount(6);
        txtEventDescription.setPrefColumnCount(40);
    }

    private void initTables() {
        guestTable = new TableView<>();
        vendorTable = new TableView<>();

        TableColumn<Guest, String> colGuestName = new TableColumn<>("Guest Name");
        colGuestName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));
        colGuestName.setMinWidth(root.getWidth() / 2);

        TableColumn<Guest, String> colGuestEmail = new TableColumn<>("Guest Email");
        colGuestEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));
        colGuestEmail.setMinWidth(root.getWidth() / 2);

        guestTable.getColumns().addAll(colGuestName, colGuestEmail);

        TableColumn<Vendor, String> colVendorName = new TableColumn<>("Vendor Name");
        colVendorName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));
        colGuestEmail.setMinWidth(root.getWidth() / 2);

        TableColumn<Vendor, String> colVendorEmail = new TableColumn<>("Vendor Email");
        colVendorEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));
        colVendorEmail.setMinWidth(root.getWidth() / 2);

        vendorTable.getColumns().addAll(colVendorName, colVendorEmail);
    }

    private void initButtons() {
        btnBack = new Button("Back to Events");
        btnBack.setOnAction(e -> Main.redirect(new AdminEventPage().scene));

        btnDeleteEvent = new Button("Delete Event");
        btnDeleteEvent.setOnAction(e -> deleteSelectedEvent());
    }

    private void deleteSelectedEvent() {
        boolean confirmed = showConfirmation("Delete Event", "Are you sure you want to delete this event?");
        if (confirmed) {
            boolean success = ac.deleteEvent(eventId);
            if (success) {
                showAlert("Success", "Event deleted successfully.");
                Main.redirect(new AdminEventPage().scene);
            } else {
                showAlert("Error", "Failed to delete the event.");
            }
        }
    }

    private void setLayout() {
        GridPane detailPane = new GridPane();
        detailPane.setPadding(new Insets(10));
        detailPane.setHgap(10);
        detailPane.setVgap(10);

        detailPane.add(new Label("Event Name:"), 0, 0);
        detailPane.add(txtEventName, 1, 0);

        detailPane.add(new Label("Event Date:"), 0, 1);
        detailPane.add(txtEventDate, 1, 1);

        detailPane.add(new Label("Event Location:"), 0, 2);
        detailPane.add(txtEventLocation, 1, 2);

        detailPane.add(new Label("Event Description:"), 0, 3);
        detailPane.add(txtEventDescription, 1, 3);

        ScrollPane guestScrollPane = new ScrollPane(guestTable);
        guestScrollPane.setFitToWidth(true);
        guestScrollPane.setPrefHeight(300);

        ScrollPane vendorScrollPane = new ScrollPane(vendorTable);
        vendorScrollPane.setFitToWidth(true);
        vendorScrollPane.setPrefHeight(300);

        VBox tableContainer = new VBox(10, new Label("Guest List:"), guestScrollPane, new Label("Vendor List:"), vendorScrollPane);
        tableContainer.setPadding(new Insets(10));

        VBox btnContainer = new VBox(10, btnBack, btnDeleteEvent);

        root.setTop(detailPane);
        root.setCenter(tableContainer);
        root.setBottom(btnContainer);
        root.setPadding(new Insets(10));
    }

    private void populateFields() {
        Event event = ac.viewEventDetails(eventId);
        txtEventName.setText(event.getEvent_name());
        txtEventDate.setText(event.getEvent_date());
        txtEventLocation.setText(event.getEvent_location());
        txtEventDescription.setText(event.getEvent_description());
    }

    private void fetchAttendees() {
        List<Guest> guests = ac.getGuestsByTransactionID(eventId);
        guestTable.setItems(FXCollections.observableArrayList(guests));

        List<Vendor> vendors = ac.getVendorsByTransactionID(eventId);
        vendorTable.setItems(FXCollections.observableArrayList(vendors));
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
