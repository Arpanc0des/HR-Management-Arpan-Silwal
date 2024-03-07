package com.example.hr_management_arpan_silwal;

import com.example.hr_management_arpan_silwal.Model.EmployeeORM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    }

    public void updateButton() {
    }

    public void deleteButton() {
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
