package view;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ProfilePage implements EventHandler<ActionEvent> {
    private BorderPane root;
    private GridPane grid;
    private Label title, emailLabel, nameLabel, oldPasswordLabel, newPasswordLabel;
    private TextField emailField, nameField;
    private PasswordField oldPasswordField, newPasswordField;
    private Button saveButton;
    private HBox hbBtn;

    public Scene scene;
    private UserController uc;

    public void init() {
        root = new BorderPane();
        title = new Label("Update Profile");
        grid = new GridPane();

        emailLabel = new Label("New Email:");
        emailField = new TextField();

        nameLabel = new Label("New Username:");
        nameField = new TextField();

        oldPasswordLabel = new Label("Old Password:");
        oldPasswordField = new PasswordField();

        newPasswordLabel = new Label("New Password:");
        newPasswordField = new PasswordField();

        saveButton = new Button("Save Changes");

        hbBtn = new HBox(10);

        scene = new Scene(root, 1100, 550);
        uc = new UserController();
    }

    public void setPosition() {
        root.setTop(title);
        root.setCenter(grid);

        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPrefHeight(350);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        title.setAlignment(Pos.CENTER);
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);

        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(oldPasswordLabel, 0, 2);
        grid.add(oldPasswordField, 1, 2);

        grid.add(newPasswordLabel, 0, 3);
        grid.add(newPasswordField, 1, 3);

        hbBtn.setAlignment(Pos.CENTER_LEFT);
        hbBtn.getChildren().add(saveButton);
        grid.add(hbBtn, 1, 5);

    }

    public void setStyle() {
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        saveButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-background-radius: 5;");
    }

    private void events() {
        saveButton.setOnAction(this);
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ProfilePage() {
        init();
        setPosition();
        setStyle();
        events();

        // Pre-fill fields with current user information
        if (Main.currUser != null) {
            emailField.setText(Main.currUser.getUser_email());
            nameField.setText(Main.currUser.getUser_name());
        }

        Main.redirect(scene);
    }

    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String newEmail = emailField.getText().trim();
            String newName = nameField.getText().trim();
            String oldPassword = oldPasswordField.getText();
            String newPassword = newPasswordField.getText();

            try {
                // Call Controller to validate and update
                uc.changeProfile(newName, newEmail, oldPassword, newPassword);

                showSuccess("Profile Updated", "Your profile has been successfully updated.");
                Main.redirect(new ProfilePage().scene);
            } catch (IllegalArgumentException ex) {
                showAlert("Update Failed", ex.getMessage());
            }
        }
    }
}
