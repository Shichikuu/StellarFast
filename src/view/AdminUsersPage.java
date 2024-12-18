package view;

import controller.AdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.User;

import java.util.List;

public class AdminUsersPage {
    public Scene scene;
    private BorderPane root;
    private TableView<User> userTable;
    private Button btnDeleteUser, btnBack;
    private ObservableList<User> userList;

    private AdminController ac;

    public AdminUsersPage() {
        root = new BorderPane();
        ac = new AdminController();

        initTable();
        initButtons();
        setLayout();

        fetchAllUsers();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initTable() {
        userTable = new TableView<>();
        userList = FXCollections.observableArrayList();

        TableColumn<User, String> colUserId = new TableColumn<>("User ID");
        colUserId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_id()));

        TableColumn<User, String> colUserName = new TableColumn<>("Name");
        colUserName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_name()));

        TableColumn<User, String> colUserEmail = new TableColumn<>("Email");
        colUserEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_email()));

        TableColumn<User, String> colUserRole = new TableColumn<>("Role");
        colUserRole.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser_role()));

        userTable.getColumns().addAll(colUserId, colUserName, colUserEmail, colUserRole);
        userTable.setItems(userList);
        userTable.setPrefHeight(400);
    }

    private void initButtons() {
        btnDeleteUser = new Button("Delete User");

        btnDeleteUser.setOnAction(e -> deleteSelectedUser());
    }

    private void setLayout() {
        HBox buttonBox = new HBox(10, btnDeleteUser);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        root.setCenter(userTable);
        root.setBottom(buttonBox);
        root.setPadding(new Insets(10));
    }

    private void fetchAllUsers() {
        List<User> users = ac.getAllUsers();
        userList.setAll(users);
    }

    private void deleteSelectedUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("No User Selected", "Please select a user to delete.");
            return;
        }

        boolean confirmed = showConfirmation("Delete User", "Are you sure you want to delete the user: " + selectedUser.getUser_name() + "?");
        if (confirmed) {
            boolean success = ac.deleteUser(selectedUser.getUser_id());
            if (success) {
                userList.remove(selectedUser);
                showAlert("Success", "User deleted successfully.");
            } else {
                showAlert("Error", "Failed to delete the user.");
            }
        }
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
