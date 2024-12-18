package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application{
    private static Stage stage;

    public static void redirect(Scene pageScene) {
        BorderPane root = new BorderPane();
        root.setTop(Navbar.createNavbar()); // Add the navbar
        root.setCenter(pageScene.getRoot()); // Set the page content
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true); // Ensure the content fits the width
        scrollPane.setFitToHeight(false); // Allow vertical scrolling only

        Scene finalScene = new Scene(scrollPane, 800, 600); // Fixed size window
        stage.setScene(finalScene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        stage.setTitle("StellarFest");
        new RegisterPage();
    }
}
