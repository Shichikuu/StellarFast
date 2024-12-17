package view;

import java.util.ArrayList;

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

public class LoginPage implements EventHandler<ActionEvent>{
    private BorderPane root;
    private GridPane grid;
    private Label title, emailLabel, passwordLabel, lblDontHaveAccount;
    private TextField emailField;
    private PasswordField passwordField;
    private Button loginButton;
    private Hyperlink linkRegister;
    private HBox hbBtn, hboxLink;
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
        lblDontHaveAccount = new Label("Don't have an account?");
        linkRegister = new Hyperlink("Register here");
        hboxLink = new HBox(5, lblDontHaveAccount, linkRegister);
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

        hbBtn.setAlignment(Pos.CENTER_LEFT);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);

        hboxLink.setAlignment(Pos.CENTER_LEFT);
        grid.add(hboxLink, 1, 5);
    }

    public void setStyle() {
        title.setStyle("-fx-font-size: 36px;");
    }

    public void clearFields() {
        emailField.clear();
        passwordField.clear();
    }

    private void events() {
        linkRegister.setOnAction(e -> handle(e));
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
            String email = emailField.getText();
            String password = passwordField.getText();

            try {
                Main.currUser = uc.login(email, password);
                showSuccess("Login Success", "Welcome " + view.Main.currUser.getUser_name());

//                Main.redirect(new HomePage().scene);
            }catch (Exception ex) {
                showAlert("Login Failed", ex.getMessage());
            }
        }
        else if(e.getSource() == linkRegister) {
            clearFields();
            Main.redirect(new RegisterPage().scene);
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