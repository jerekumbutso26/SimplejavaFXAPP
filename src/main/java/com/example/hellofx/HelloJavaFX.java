package com.example.hellofx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Customer Manager Lab");

        // Step 1: Form with name field and province list
        TextField nameField = new TextField();
        nameField.setPromptText("Enter customer name");

        ComboBox<String> provinceCombo = new ComboBox<>();
        provinceCombo.getItems().addAll(
                "Copperbelt", "Lusaka", "Central", "Southern",
                "Eastern", "Northern", "North-Western", "Luapula", "Muchinga", "Western"
        );
        provinceCombo.setPromptText("Select Province");

        Button addButton = new Button("Add Customer");
        Button deleteButton = new Button("Delete Selected");

        // Step 2: ObservableList<Customer>
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        // Step 3: TableView with Name and Province columns
        TableView<Customer> tableView = new TableView<>();
        tableView.setItems(customerList);

        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setPrefWidth(160);

        TableColumn<Customer, String> provinceColumn = new TableColumn<>("Province");
        provinceColumn.setCellValueFactory(cellData -> cellData.getValue().provinceProperty());
        provinceColumn.setPrefWidth(160);

        tableView.getColumns().addAll(nameColumn, provinceColumn);

        // Step 4: Input validation and addition
        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String province = provinceCombo.getValue();

            if (name.isEmpty() || province == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a name and select a province.");
                return;
            }

            customerList.add(new Customer(name, province));
            nameField.clear();
            provinceCombo.getSelectionModel().clearSelection();
            nameField.requestFocus();
        });

        // Step 5: Deletion confirmation dialog
        deleteButton.setOnAction(e -> {
            Customer selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer from the table to delete.");
                return;
            }

            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirm Deletion");
            confirmDialog.setHeaderText("Delete Customer Record");
            confirmDialog.setContentText("Are you sure you want to delete " + selected.getName() + "?");

            confirmDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    customerList.remove(selected);
                }
            });
        });

        // Step 6: Keyboard accessibility (Enter key fires Add button)
        nameField.setOnAction(e -> addButton.fire());

        // Layout Setup
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));
        formGrid.add(new Label("Customer Name:"), 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(new Label("Province:"), 0, 1);
        formGrid.add(provinceCombo, 1, 1);
        formGrid.add(addButton, 1, 2);

        HBox actionBox = new HBox(10, deleteButton);
        actionBox.setPadding(new Insets(5, 0, 0, 0));

        VBox root = new VBox(10, formGrid, tableView, actionBox);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 380, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}