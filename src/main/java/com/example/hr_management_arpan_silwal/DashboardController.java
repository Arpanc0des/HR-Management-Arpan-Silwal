package com.example.hr_management_arpan_silwal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void admin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-view.fxml"));
            Scene scene = new Scene(loader.load());

            AdminController adminController = loader.getController();
            adminController.setPrimaryStage(primaryStage); // Pass primaryStage to LogInController

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void employee() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employee-view.fxml"));
            Scene scene = new Scene(loader.load());

            EmployeeController employeeController = loader.getController();
            employeeController.setPrimaryStage(primaryStage); // Pass primaryStage to LogInController

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("logIn-view.fxml"));
            Scene scene = new Scene(loader.load());

            LogInController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage); // Pass primaryStage to LogInController

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

}
