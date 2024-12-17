package view;

import java.util.ArrayList;
import java.util.Random;
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
import model.User;

public class RegisterPage implements EventHandler<ActionEvent>{
    private BorderPane root;
    private GridPane grid;
    private Label title, emailLabel, usernameLabel, passwordLabel, roleLabel, lblAlreadyHaveAccount;
    private TextField emailField, usernameField;
    private PasswordField passwordField;
    private Button registerButton, loginButton;
    private Hyperlink linkLogin;
    private HBox hbBtn, hboxLink;


    public Scene scene;
    private ComboBox<String> cbRole;
    private UserController uc;
    private ArrayList<User> users;
    private boolean validate;
    private String id;

    public void init() {
        root = new BorderPane();
        title = new Label("Register");
        grid = new GridPane();
        emailLabel = new Label("Email :");
        emailField = new TextField();
        usernameLabel = new Label("Username :");
        usernameField = new TextField();
        passwordLabel = new Label("Password :");
        passwordField = new PasswordField();
        roleLabel = new Label("Role :");
        cbRole = new ComboBox<>();
        cbRole.getItems().addAll("Event Organizer", "Vendor", "Guest", "Admin");
        cbRole.setPromptText("Select your role");
        registerButton = new Button("Register");
        lblAlreadyHaveAccount = new Label("Already have an account?");
        linkLogin = new Hyperlink("Login here");
        hbBtn = new HBox(10);
        hboxLink = new HBox(5, lblAlreadyHaveAccount, linkLogin);
        scene = new Scene(root, 1100, 550);
        uc = new UserController();
        users = new ArrayList<>();
        validate = false;
    }

    public void setPosition() {
        root.setCenter(title);
        root.setBottom(grid);

        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPrefHeight(350);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        title.setAlignment(Pos.TOP_CENTER);
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(roleLabel, 0, 3);
        grid.add(cbRole, 1, 3);

        hbBtn.setAlignment(Pos.CENTER_LEFT);
        hbBtn.getChildren().add(registerButton);
        grid.add(hbBtn, 1, 7);

        hboxLink.setAlignment(Pos.CENTER_LEFT);
        grid.add(hboxLink, 1, 8);
    }

    public void setStyle() {
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
    }

    public void clearFields() {
        emailField.clear();
        usernameField.clear();
        passwordField.clear();

    }

    private void events() {
        registerButton.setOnAction(e -> handle(e));
        linkLogin.setOnAction(e -> handle(e));
    }

    private String generateId() {
        Random random = new Random();
        int randomNumber1 = random.nextInt(10);
        int randomNumber2 = random.nextInt(10);
        int randomNumber3 = random.nextInt(10);

        String randomID = "US" + randomNumber1 + randomNumber2 + randomNumber3;
        return randomID;
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

    public RegisterPage() {
        init();
        setPosition();
        setStyle();
//        users = uc.getUsers(users);
        events();
        view.Main.redirect(scene);
    }

    @Override
    public void handle(ActionEvent e) {
        if(e.getSource() == registerButton) {
            String email = emailField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String role = cbRole.getValue();

            // Call the Controller to handle registration
            try {
                uc.register(email, username, password, role);
                showSuccess("Registration Successful", "You have successfully registered!");

                // Redirect to Login Page
                Main.redirect(new LoginPage().scene);
            } catch (IllegalArgumentException ex) {
                showAlert("Registration Failed", ex.getMessage());
            }
        }
        else if(e.getSource() == linkLogin) {
            clearFields();
            Main.redirect(new LoginPage().scene);
        }
    }
}
