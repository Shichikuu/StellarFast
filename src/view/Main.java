package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;


public class Main extends Application{
    private static Stage stage;
    public static User currUser;

    public static void redirect(Scene newScene) {
        stage.setScene(newScene);
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
