package view;

import controller.VendorController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ManageVendorPage {
    public Scene scene;
    private BorderPane root;
    private TextField productField;
    private TextArea descriptionField;
    private Button btnSave;
    private VendorController vc;

    public ManageVendorPage() {
        vc = new VendorController();
        root = new BorderPane();

        initFields();
        initButtons();
        setLayout();

        scene = new Scene(root, 800, 600);
        Main.redirect(scene);
    }

    private void initFields() {
        productField = new TextField();
        descriptionField = new TextArea();
        descriptionField.setWrapText(true);
        descriptionField.setPrefRowCount(5);
        descriptionField.setPrefColumnCount(30);
    }

    private void initButtons() {
        btnSave = new Button("Save Changes");
        btnSave.setOnAction(e -> saveVendorInfo());
    }

    private void setLayout() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Product Name:"), 0, 0);
        grid.add(productField, 1, 0);

        grid.add(new Label("Product Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);

        HBox buttonBox = new HBox(10, btnSave);
        buttonBox.setPadding(new Insets(10));

        root.setCenter(grid);
        root.setBottom(buttonBox);
        root.setPadding(new Insets(10));
    }

    private void saveVendorInfo() {
        String product = productField.getText().trim();
        String description = descriptionField.getText().trim();

        try {
            vc.manageVendor(description, product);
            showAlert("Success", "Vendor information updated successfully.");
        } catch (IllegalArgumentException ex) {
            showAlert("Validation Error", ex.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
