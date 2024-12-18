package view;

import controller.EventOrganizerController;
import javafx.beans.property.BooleanProperty;
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
import model.Guest;
import model.Vendor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddVendorsPage {
    public Scene scene;
    private BorderPane root;
    private TableView<Vendor> vendorTable;
    private Button btnAdd, btnBack;
    private ObservableList<Vendor> vendorList;
    private String eventId;
    private EventOrganizerController eoc;
    private Map<Vendor, BooleanProperty> selectionMap;

    public AddVendorsPage(String eventId) {
        this.eventId = eventId;
        this.eoc = new EventOrganizerController();
        root = new BorderPane();
        selectionMap = new HashMap<>();

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
        // Bind the checkbox state to the selection map
        colSelect.setCellValueFactory(data -> {
            Vendor vendor = data.getValue();
            selectionMap.putIfAbsent(vendor, new SimpleBooleanProperty(false));
            return selectionMap.get(vendor);
        });

        // Create a custom CheckBoxTableCell
        colSelect.setCellFactory(tc -> {
            CheckBoxTableCell<Vendor, Boolean> cell = new CheckBoxTableCell<>(index -> {
                Vendor vendor = vendorTable.getItems().get(index);
                return selectionMap.computeIfAbsent(vendor, key -> new SimpleBooleanProperty(false));
            });

            return cell;
        });
        vendorTable.setEditable(true);

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
        List<Vendor> selectedVendors = vendorTable.getItems().stream()
                .filter(vendor -> selectionMap.getOrDefault(vendor, new SimpleBooleanProperty(false)).get())
                .collect(Collectors.toList());

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
