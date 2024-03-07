package com.example.hr_management_arpan_silwal;

import com.example.hr_management_arpan_silwal.Model.EmployeeORM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    public TableView<EmployeeORM> employeeTableView;
    public TableColumn<EmployeeORM, Integer> idTblField;
    public TableColumn<EmployeeORM, String> nameTblField;
    public TableColumn<EmployeeORM, String> emailTblField;
    public Label errorMessage;
    public TextField txtIdField;
    public TextField txtNameField;
    public TextField txtEmailField;

    ObservableList<EmployeeORM> empList = FXCollections.observableArrayList();
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTblField.setCellValueFactory(new PropertyValueFactory<EmployeeORM, Integer>("employeeIdORM"));
        nameTblField.setCellValueFactory(new PropertyValueFactory<EmployeeORM, String>("employeeNameORM"));
        emailTblField.setCellValueFactory(new PropertyValueFactory<EmployeeORM, String>("employeeEmailORM"));
        employeeTableView.setItems(empList);
    }

    public void createButton() {
        String idText = txtIdField.getText().trim();
        String name = txtNameField.getText().trim();
        String email = txtEmailField.getText().trim();

        if (idText.isEmpty() || name.isEmpty() || email.isEmpty()) {
            errorMessage.setText("All fields must be filled!");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            errorMessage.setText("ID must be an integer!");
            return;
        }
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO employee_tbl (employee_id, employee_name, employee_email,employee_title) VALUES (?, ?, ?,'employee')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            errorMessage.setText("Error: " + e.getMessage());
        } finally {
            txtIdField.clear();
            txtNameField.clear();
            txtEmailField.clear();
        }
    }

    public void updateButton() throws SQLException {
        String idText = txtIdField.getText().trim();
        String name = txtNameField.getText().trim();
        String email = txtEmailField.getText().trim();

        if (idText.isEmpty() || name.isEmpty() || email.isEmpty()) {
            errorMessage.setText("All fields must be filled!");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            errorMessage.setText("ID must be an integer!");
            return;
        }
        // Check if the employee with the given ID exists
        if (!EmployeeExists(id)) {
            errorMessage.setText("Employee with ID " + id + " does not exist!");
            return;
        }
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE employee_tbl SET employee_name = ?, employee_email = ? WHERE employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, id); // Set ID as the third parameter
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            errorMessage.setText("Error: " + e.getMessage());
        } finally {
            txtIdField.clear();
            txtNameField.clear();
            txtEmailField.clear();
        }
        viewButton();
    }


    public void deleteButton() throws SQLException {
        String idText = txtIdField.getText().trim();

        if (idText.isEmpty()) {
            errorMessage.setText("Please enter an employee ID to delete!");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            errorMessage.setText("ID must be an integer!");
            return;
        }

        if (!EmployeeExists(id)) {
            errorMessage.setText("Employee with ID " + id + " does not exist!");
            return;
        }
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM employee_tbl WHERE employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            errorMessage.setText("Employee with ID " + id + " deleted successfully.");
        } catch (SQLException e) {
            errorMessage.setText("Error: " + e.getMessage());
        } finally {
            txtIdField.clear();
        }
        viewButton();
    }

    private boolean EmployeeExists(int id) throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM employee_tbl WHERE employee_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If the result set has at least one row, the employee exists
        }
    }

    public void viewButton() {
        empList.clear(); // Clear the ObservableList before adding new items
        // Establish a database connection
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            // Execute a SQL query to retrieve data from the database
            String query = "SELECT * FROM `employee_tbl`";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // Populate the ObservableList with data from the database
            while (resultSet.next()) {
                int id = resultSet.getInt("employee_id");
                String name = resultSet.getString("employee_name");
                String email = resultSet.getString("employee_email");
                String title = resultSet.getString("employee_title");

                // Add the data to the ObservableList
                empList.add(new EmployeeORM(id, name, email, title));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
            Scene scene = new Scene(loader.load());

            DashboardController dashboardController = loader.getController();
            dashboardController.setPrimaryStage(primaryStage); // Pass primaryStage to LogInController

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
