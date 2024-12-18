package view;

import controller.EventController;
import controller.VendorController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Invitation;
import util.Session;

import java.util.List;

public class VendorInvitationPage {

    public Scene scene;
    private BorderPane root;
    private TableView<Invitation> invitationTable;
    private Button btnAccept;
    private ObservableList<Invitation> invitationList;
    private VendorController vc;
    private EventController ec;
    private String vendorEmail;

    public VendorInvitationPage() {
        vc = new VendorController();
        ec = new EventController();
        vendorEmail = Session.getInstance().getCurrentUser().getUser_email();
        root = new BorderPane();

        initTable();
        initButtons();
        setLayout();

        fetchInvitations();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initTable() {
        invitationTable = new TableView<>();
        invitationList = FXCollections.observableArrayList();

        TableColumn<Invitation, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(data -> new SimpleStringProperty(ec.getEventNameFromEventId(data.getValue().getEvent_id())));

        TableColumn<Invitation, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInvitation_status()));

        invitationTable.getColumns().addAll(colEventName, colStatus);
        invitationTable.setItems(invitationList);
        invitationTable.setPrefHeight(500);
    }

    private void initButtons() {
        btnAccept = new Button("Accept Invitation");
        btnAccept.setOnAction(e -> acceptInvitation());
    }

    private void setLayout() {
        HBox buttonBox = new HBox(10, btnAccept);
        buttonBox.setPadding(new Insets(10));

        root.setCenter(invitationTable);
        root.setBottom(buttonBox);
        root.setPadding(new Insets(10));
    }

    private void fetchInvitations() {
        try {
            List<Invitation> invitations = vc.getInvitations(vendorEmail);
            invitationList.setAll(invitations);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void acceptInvitation() {
        Invitation selectedInvitation = invitationTable.getSelectionModel().getSelectedItem();
        if (selectedInvitation == null) {
            showAlert("No Selection", "Please select an invitation to accept.");
            return;
        }

        try {
            vc.acceptInvitation(selectedInvitation.getEvent_id());
            showAlert("Success", "You have successfully accepted the invitation.");
            fetchInvitations(); // Refresh the invitation list
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
