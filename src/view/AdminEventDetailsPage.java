package view;

import controller.AdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.*;

import java.util.List;

public class AdminEventDetailsPage {
    public Scene scene;
    private BorderPane root;
    private TextField txtEventName, txtEventDate, txtEventLocation, txtEventDescription;
    private TableView<Guest> guestTable;
    private TableView<Vendor> vendorTable;
    private Button btnBack;
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

        txtEventDescription = new TextField();
        txtEventDescription.setEditable(false);
    }

    private void initTables() {
        guestTable = new TableView<>();
        vendorTable = new TableView<>();

        TableColumn<Guest, String> colGuestName = new TableColumn<>("Guest Name");
        colGuestName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));

        TableColumn<Guest, String> colGuestEmail = new TableColumn<>("Guest Email");
        colGuestEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));

        guestTable.getColumns().addAll(colGuestName, colGuestEmail);

        TableColumn<Vendor, String> colVendorName = new TableColumn<>("Vendor Name");
        colVendorName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));

        TableColumn<Vendor, String> colVendorEmail = new TableColumn<>("Vendor Email");
        colVendorEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));

        vendorTable.getColumns().addAll(colVendorName, colVendorEmail);
    }

    private void initButtons() {
        btnBack = new Button("Back to Events");
        btnBack.setOnAction(e -> Main.redirect(new AdminEventPage().scene));
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

        BorderPane tablePane = new BorderPane();
        tablePane.setTop(new Label("Guest List:"));
        tablePane.setCenter(guestTable);
        tablePane.setBottom(vendorTable);

        root.setTop(detailPane);
        root.setCenter(tablePane);
        root.setBottom(btnBack);
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
}
