package com.example.hr_management_arpan_silwal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    public TextField emailInput;
    @FXML
    public PasswordField passwordInput;
    @FXML
    public Label errorMessage;
    private Map<String, String> userPasswords;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the HashMap
        userPasswords = new HashMap<>();
        // Add some sample users and passwords
        userPasswords.put("user@gmail.com", "pass");
        userPasswords.put("root@gmail.com", "toor");
    }

    @FXML
    public void handleLogIn() {
        if (validated()) {
            String email = emailInput.getText();
            String password = passwordInput.getText();

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