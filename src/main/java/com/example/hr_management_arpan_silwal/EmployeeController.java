package com.example.hr_management_arpan_silwal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmployeeController {
    private Stage primaryStage;
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void updateButton() {
    }

    public void deleteButton() {
    }

    public void viewButton() {
    }
    public void backButton() {
        try {
            // Set the new scene, set new fxml loader and get the controller from the fxml
            Scene scene = new Scene(new FXMLLoader(getClass().getResource("dashboard-view.fxml")).load());
            // Override the primary stage's previous scene with the new created one
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
