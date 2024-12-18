package view;

import controller.GuestController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Event;

public class EventDetailsPage {
    public Scene scene;
    private BorderPane root;
    private TextField txtEventName, txtEventDate, txtEventLocation;
    private TextArea txtEventDescription;
    private Button btnBack;

    private Event event;

    public EventDetailsPage(Event event) {
        this.event = event;
        root = new BorderPane();

        initFields();
        initButtons();
        setLayout();

        populateFields();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initFields() {
        txtEventName = new TextField();
        txtEventName.setEditable(false);

        txtEventDate = new TextField();
        txtEventDate.setEditable(false);

        txtEventLocation = new TextField();
        txtEventLocation.setEditable(false);

        txtEventDescription = new TextArea();
        txtEventDescription.setEditable(false);
        txtEventDescription.setWrapText(true);
        txtEventDescription.setPrefRowCount(6);
        txtEventDescription.setPrefColumnCount(40);
    }

    private void initButtons() {
        btnBack = new Button("Back to Events");
        btnBack.setOnAction(e -> Main.redirect(new GuestEventPage().scene));
    }

    private void setLayout() {
        GridPane detailPane = new GridPane();
        detailPane.setPadding(new Insets(10));
        detailPane.setHgap(10);
        detailPane.setVgap(10);

        detailPane.add(new Label("Event Name:"), 0, 0);
        detailPane.add(txtEventName, 1, 0);

        detailPane.add(new Label("Event Date:"), 0, 1);
        detailPane.add(txtEventDate, 1, 1);

        detailPane.add(new Label("Event Location:"), 0, 2);
        detailPane.add(txtEventLocation, 1, 2);

        detailPane.add(new Label("Event Description:"), 0, 3);
        detailPane.add(txtEventDescription, 1, 3);

        VBox content = new VBox(10, detailPane, btnBack);
        content.setPadding(new Insets(10));

        root.setCenter(content);
    }

    private void populateFields() {
        txtEventName.setText(event.getEvent_name());
        txtEventDate.setText(event.getEvent_date());
        txtEventLocation.setText(event.getEvent_location());
        txtEventDescription.setText(event.getEvent_description());
    }
}
