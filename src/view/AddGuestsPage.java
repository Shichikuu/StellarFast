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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Guest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddGuestsPage {
    public Scene scene;
    private BorderPane root;
    private TableView<Guest> guestTable;
    private Button btnAdd, btnBack;
    private ObservableList<Guest> guestList;
    private String eventId;
    private EventOrganizerController eoc;
    private Map<Guest, BooleanProperty> selectionMap;

    public AddGuestsPage(String eventId) {
        this.eventId = eventId;
        this.eoc = new EventOrganizerController();
        root = new BorderPane();
        selectionMap = new HashMap<>();

        initTable();
        initButtons();
        setLayout();

        fetchAvailableGuests();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initTable() {
        guestTable = new TableView<>();
        guestList = FXCollections.observableArrayList();

        TableColumn<Guest, String> colGuestName = new TableColumn<>("Guest Name");
        colGuestName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));

        TableColumn<Guest, String> colGuestEmail = new TableColumn<>("Guest Email");
        colGuestEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));

        TableColumn<Guest, Boolean> colSelect = new TableColumn<>("Select");

        // Bind the checkbox state to the selection map
        colSelect.setCellValueFactory(data -> {
            Guest guest = data.getValue();
            selectionMap.putIfAbsent(guest, new SimpleBooleanProperty(false));
            return selectionMap.get(guest);
        });

        // Create a custom CheckBoxTableCell
        colSelect.setCellFactory(tc -> {
            CheckBoxTableCell<Guest, Boolean> cell = new CheckBoxTableCell<>(index -> {
                Guest guest = guestTable.getItems().get(index);
                return selectionMap.computeIfAbsent(guest, key -> new SimpleBooleanProperty(false));
            });

            return cell;
        });
        guestTable.setEditable(true);

        guestTable.getColumns().addAll(colGuestName, colGuestEmail, colSelect);
        guestTable.setItems(guestList);
    }

    private void initButtons() {
        btnAdd = new Button("Add Selected Guests");
        btnAdd.setOnAction(e -> addSelectedGuests());

        btnBack = new Button("Back");
        btnBack.setOnAction(e -> Main.redirect(new OrganizerEventDetailsPage(eventId).scene));
    }

    private void setLayout() {
        HBox buttonBox = new HBox(10, btnAdd, btnBack);
        buttonBox.setPadding(new Insets(10));

        root.setCenter(guestTable);
        root.setBottom(buttonBox);
        root.setPadding(new Insets(10));
    }

    private void fetchAvailableGuests() {
        List<Guest> guests = eoc.getGuests();
        guestList.setAll(guests);
    }

    private void addSelectedGuests() {
        List<Guest> selectedGuests = guestTable.getItems().stream()
                .filter(guest -> selectionMap.getOrDefault(guest, new SimpleBooleanProperty(false)).get())
                .collect(Collectors.toList());

        if (selectedGuests.isEmpty()) {
            showAlert("No Guest Selected", "Please select at least one guest to invite.");
            return;
        }

        try {
            eoc.addGuestsToEvent(eventId, selectedGuests);
            showAlert("Success", "Guests have been successfully invited.");
            Main.redirect(new OrganizerEventDetailsPage(eventId).scene);
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
