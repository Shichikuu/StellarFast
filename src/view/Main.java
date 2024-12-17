package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;


public class Main extends Application{
    private static Stage stage;
    public static User currUser;

    public static void redirect(Scene pageScene) {
        BorderPane root = new BorderPane();
        root.setTop(Navbar.createNavbar(currUser)); // Add the navbar
        root.setCenter(pageScene.getRoot()); // Set the page content

        Scene finalScene = new Scene(root, 800, 600); // Fixed size window
        stage.setScene(finalScene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
//        stage.setScene(newScene);
//        stage.centerOnScreen();
//        stage.setResizable(false);
//        stage.show();
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
