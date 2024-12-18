package view;

import controller.EventOrganizerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Event;
import model.Guest;
import model.User;
import model.Vendor;

import java.util.List;

public class OrganizerEventDetailsPage {
    public Scene scene;
    private BorderPane root;
    private TextField txtEventName, txtEventDate, txtEventLocation;
    private TextArea txtEventDescription;
    private TableView<User> guestTable, vendorTable;
    private Button btnBack, btnEditName, btnAddVendors, btnAddGuests;
    private String eventId;

    private EventOrganizerController eoc;

    public OrganizerEventDetailsPage(String eventId) {
        this.eventId = eventId;
        this.eoc= new EventOrganizerController();
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

        TableColumn<User, String> colGuestName = new TableColumn<>("Guest Name");
        colGuestName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));
        colGuestName.setMinWidth(root.getWidth() / 2);

        TableColumn<User, String> colGuestEmail = new TableColumn<>("Guest Email");
        colGuestEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));
        colGuestEmail.setMinWidth(root.getWidth() / 2);

        guestTable.getColumns().addAll(colGuestName, colGuestEmail);
        guestTable.setPrefHeight(300);

        TableColumn<User, String> colVendorName = new TableColumn<>("Vendor Name");
        colVendorName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));
        colVendorName.setMinWidth(root.getWidth() / 2);

        TableColumn<User, String> colVendorEmail = new TableColumn<>("Vendor Email");
        colVendorEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));
        colVendorEmail.setMinWidth(root.getWidth() / 2);

        vendorTable.getColumns().addAll(colVendorName, colVendorEmail);
        guestTable.setPrefHeight(300);
    }

    private void initButtons() {
        btnBack = new Button("Back to Events");
        btnBack.setOnAction(e -> Main.redirect(new OrganizerEventPage().scene));

        btnEditName = new Button("Edit Event Name");
        btnEditName.setOnAction(e -> editEventName());

        btnAddVendors = new Button("Add Vendors");
        btnAddVendors.setOnAction(e -> Main.redirect(new AddVendorsPage(eventId).scene));

        btnAddGuests = new Button("Add Guests");
        btnAddGuests.setOnAction(e -> Main.redirect(new AddGuestsPage(eventId).scene));
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

        HBox buttonBox = new HBox(10, btnEditName, btnAddVendors, btnAddGuests, btnBack);
        buttonBox.setPadding(new Insets(10));

        root.setTop(detailPane);
        root.setCenter(tableContainer);
        root.setBottom(buttonBox);
        root.setPadding(new Insets(10));
    }

    private void populateFields() {
        Event event = eoc.viewOrganizedEventDetails(eventId);
        txtEventName.setText(event.getEvent_name());
        txtEventDate.setText(event.getEvent_date());
        txtEventLocation.setText(event.getEvent_location());
        txtEventDescription.setText(event.getEvent_description());
    }

    private void fetchAttendees() {
        List<Guest> guests = eoc.getGuestsByTransactionID(eventId);
        guestTable.setItems(FXCollections.observableArrayList(guests));

        List<Vendor> vendors = eoc.getVendorsByTransactionID(eventId);
        vendorTable.setItems(FXCollections.observableArrayList(vendors));
    }

    private void editEventName() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Event Name");
        dialog.setHeaderText("Enter the new event name:");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        TextField newEventNameField = new TextField(txtEventName.getText());
        dialog.getDialogPane().setContent(newEventNameField);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return newEventNameField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newEventName -> {
            try {
                eoc.editEventName(eventId, newEventName);
                txtEventName.setText(newEventName);
                showAlert("Success", "Event name updated successfully.");
            } catch (IllegalArgumentException e) {
                showAlert("Error", e.getMessage());
            }

        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
