package view;

import controller.EventOrganizerController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Vendor;

import java.util.List;

public class AddVendorsPage {
    public Scene scene;
    private BorderPane root;
    private TableView<Vendor> vendorTable;
    private Button btnAdd, btnBack;
    private ObservableList<Vendor> vendorList;
    private String eventId;
    private EventOrganizerController eoc;

    public AddVendorsPage(String eventId) {
        this.eventId = eventId;
        this.eoc = new EventOrganizerController();
        root = new BorderPane();

        initTable();
        initButtons();
        setLayout();

        fetchAvailableVendors();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initTable() {
        vendorTable = new TableView<>();
        vendorList = FXCollections.observableArrayList();

        TableColumn<Vendor, String> colVendorName = new TableColumn<>("Vendor Name");
        colVendorName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));

        TableColumn<Vendor, String> colVendorEmail = new TableColumn<>("Vendor Email");
        colVendorEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));

        TableColumn<Vendor, Boolean> colSelect = new TableColumn<>("Select");
        colSelect.setCellValueFactory(data -> new SimpleBooleanProperty(false));
        colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));

        vendorTable.getColumns().addAll(colVendorName, colVendorEmail, colSelect);
        vendorTable.setItems(vendorList);
    }

    private void initButtons() {
        btnAdd = new Button("Add Selected Vendors");
        btnAdd.setOnAction(e -> addSelectedVendors());

        btnBack = new Button("Back");
        btnBack.setOnAction(e -> Main.redirect(new OrganizerEventDetailsPage(eventId).scene));
    }

    private void setLayout() {
        HBox buttonBox = new HBox(10, btnAdd, btnBack);
        buttonBox.setPadding(new Insets(10));

        root.setCenter(vendorTable);
        root.setBottom(buttonBox);
        root.setPadding(new Insets(10));
    }

    private void fetchAvailableVendors() {
        List<Vendor> vendors = eoc.getVendors();
        vendorList.setAll(vendors);
    }

    private void addSelectedVendors() {
        List<Vendor> selectedVendors = vendorTable.getSelectionModel().getSelectedItems();

        if (selectedVendors.isEmpty()) {
            showAlert("No Vendor Selected", "Please select at least one vendor to invite.");
            return;
        }

        try{
            eoc.addVendorsToEvent(eventId, selectedVendors);
            showAlert("Success", "Vendors have been successfully invited.");
            Main.redirect(new OrganizerEventDetailsPage(eventId).scene);
        }catch (IllegalArgumentException e){
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
