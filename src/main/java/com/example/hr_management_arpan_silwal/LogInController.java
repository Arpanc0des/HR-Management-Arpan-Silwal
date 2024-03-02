package com.example.hr_management_arpan_silwal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogInController {
    @FXML
    public TextField emailInput;
    @FXML
    public PasswordField passwordInput;
    @FXML
    public Label errorMessage;
    private final Map<String, String> userPasswords = new HashMap<>();

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void handleLogIn() {
        if (validated()) {
            String email = emailInput.getText();
            String password = passwordInput.getText();
            // Add some sample users and passwords
            userPasswords.put("user@gmail.com","pass");
            userPasswords.put("root@gmail.com","toor");
            if (userPasswords.containsKey(email) && userPasswords.get(email).equals(password)) {

                loadDashboardView();
            } else {
                errorMessage.setText("Invalid email or password. Please try again.");
            }
        }
    }

    public boolean validated() {
        String email = emailInput.getText();
        String password = passwordInput.getText();
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.setText("You cant log in if you dont give me your credentials... Try again please.");
            return false;
        }
        //minimum requirement for regex to pass 1character before @, 1 between . and 2after .
        else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errorMessage.setText("The email you have provided might not be complete. Please try again.");
            return false;
        }
        errorMessage.setText("");
        return true;
    }

    private void loadDashboardView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
            Scene scene = new Scene(loader.load());

            DashboardController dashboardController = loader.getController();
            dashboardController.setPrimaryStage(primaryStage); // Pass primaryStage to DashboardController

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}