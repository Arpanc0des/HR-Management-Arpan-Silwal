package com.example.hr_management_arpan_silwal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void admin() {
        try {
            // Set the new scene, set new fxml loader and get the controller from the fxml
            Scene scene = new Scene(new FXMLLoader(getClass().getResource("admin-view.fxml")).load());
            // Override the primary stage's previous scene with the new created one
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void employee() {
        try {
            // Set the new scene, set new fxml loader and get the controller from the fxml
            Scene scene = new Scene(new FXMLLoader(getClass().getResource("employee-view.fxml")).load());
            // Override the primary stage's previous scene with the new created one
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout() {
        try {
            // Set the new scene, set new fxml loader and get the controller from the fxml
            Scene scene = new Scene(new FXMLLoader(getClass().getResource("logIn-view.fxml")).load());
            // Override the primary stage's previous scene with the new created one
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

}
