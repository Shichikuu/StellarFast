package view;

import java.util.ArrayList;

import controller.UserController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.User;

public class LoginPage implements EventHandler<ActionEvent>{
    private BorderPane root;
    private GridPane grid;
    private Label title, emailLabel, passwordLabel;
    private TextField emailField;
    private PasswordField passwordField;
    private Button registerButton, loginButton;
    private HBox hbBtn;
    public Scene scene;
    private ArrayList<User> users;
    private static String userID = "";
    private static String userRole = "";
    private UserController uc;

    public static String getUserID() {
        return userID;
    }

    public static String getUserRole() {
        return userRole;
    }

    public void init() {
        root = new BorderPane();
        title = new Label("Login");
        grid = new GridPane();
        emailLabel = new Label("Email :");
        emailField = new TextField();
        passwordLabel = new Label("Password :");
        passwordField = new PasswordField();
        registerButton = new Button("Register");
        loginButton = new Button("Login");
        hbBtn = new HBox(10);
        scene = new Scene(root, 1100, 550);
        users = new ArrayList<>();
        uc = new UserController();
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
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(registerButton);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);
    }

    public void setStyle() {
        title.setStyle("-fx-font-size: 36px;");
    }

    public void clearFields() {
        emailField.clear();
        passwordField.clear();
    }

    private void events() {
        registerButton.setOnAction(e -> handle(e));
        loginButton.setOnAction(e -> handle(e));
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

    @Override
    public void handle(ActionEvent e) {
        if(e.getSource() == loginButton) {
//            int result = uc.loginValidation(emailField.getText(), passwordField.getText());
//            if(result == -1) {
//                showAlert("Error", "Email must be filled");
//            } else if (result == -2) {
//                showAlert("Error", "Password must be filled");
//            }else if(result == -3) {
//                showAlert("Login Error", "User does not exist");
//            }
        }
        else if(e.getSource() == registerButton) {
            clearFields();
            RegisterPage regist = new RegisterPage();
            view.Main.redirect(regist.scene);
        }
    }

    public LoginPage() {
        init();
        setPosition();
//        users = uc.getUsers(users);
        setStyle();
        events();
        view.Main.redirect(scene);
    }

}