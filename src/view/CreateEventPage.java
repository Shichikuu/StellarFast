package view;

import controller.EventController;
import controller.EventOrganizerController;
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
import util.Session;

import java.time.LocalDate;

public class CreateEventPage implements EventHandler<ActionEvent> {
    private BorderPane root;
    private GridPane grid;
    private Label title, nameLabel, dateLabel, locationLabel, descriptionLabel;
    private TextField nameField, dateField, locationField;
    private TextArea descriptionField;
    private Button createButton, backButton;
    private HBox hbBtn;
    private DatePicker datePicker;

    public Scene scene;
    private EventController ec;
    private User currentUser;

    public void init() {
        root = new BorderPane();
        title = new Label("Create Event");
        grid = new GridPane();
        nameLabel = new Label("Event Name:");
        nameField = new TextField();
        dateLabel = new Label("Event Date (YYYY-MM-DD):");
//        dateField = new TextField();
        datePicker = new DatePicker();
        locationLabel = new Label("Event Location:");
        locationField = new TextField();
        descriptionLabel = new Label("Event Description:");
        descriptionField = new TextArea();
        descriptionField.setWrapText(true);
        descriptionField.setPrefRowCount(4);
        createButton = new Button("Create Event");
        backButton = new Button("Back");
        hbBtn = new HBox(10);
        scene = new Scene(root, 800, 600);
        ec = new EventController();

    }

    public void setPosition() {
        root.setBottom(grid);
        root.setCenter(title);

        grid.setAlignment(Pos.CENTER);
        grid.setPrefHeight(400);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        title.setAlignment(Pos.CENTER);
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(locationLabel, 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(descriptionLabel, 0, 3);
        grid.add(descriptionField, 1, 3);

        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().addAll(createButton, backButton);
        grid.add(hbBtn, 1, 5);
    }

    public void setStyle() {
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
    }

    private void events() {
        createButton.setOnAction(e -> handle(e));
        backButton.setOnAction(e -> handle(e));
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

    public CreateEventPage() {
        init();
        setPosition();
        setStyle();
        events();
        currentUser = Session.getInstance().getCurrentUser();
        view.Main.redirect(scene);
    }

    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() == createButton) {
            String eventName = nameField.getText().trim();
            LocalDate eventDate = datePicker.getValue();
            String eventLocation = locationField.getText().trim();
            String eventDescription = descriptionField.getText().trim();

            // Call the Controller to handle event creation
            try {
                ec.createEvent(eventName, eventDate, eventLocation, eventDescription, currentUser.getUser_id());
                showSuccess("Event Created", "Your event has been successfully created!");
                Main.redirect(new OrganizerEventPage().scene);
            } catch (IllegalArgumentException ex) {
                showAlert("Creation Failed", ex.getMessage());
            }
        } else if (e.getSource() == backButton) {
            Main.redirect(new OrganizerEventPage().scene);
        }
    }
}
