package com.example.hr_management_arpan_silwal;

import com.example.hr_management_arpan_silwal.Model.SalaryORM;
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
import java.util.concurrent.TimeUnit;

public class SalaryController implements Initializable {

    public TableColumn<SalaryORM, Integer> sidTblField;
    public TableColumn<SalaryORM, String> employeeNameTblField;
    public TableColumn<SalaryORM, Double> amountTblField;
    public TableView salaryTableView;
    public Label errorMessage;
    public TextField txtFieldsid;
    public TextField txtFieldEmpName;
    public TextField txtFieldSalaryAmt;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    ObservableList<SalaryORM> salList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sidTblField.setCellValueFactory(new PropertyValueFactory<>("sIdORM"));
        employeeNameTblField.setCellValueFactory(new PropertyValueFactory<>("employeeNameORM"));
        amountTblField.setCellValueFactory(new PropertyValueFactory<>("salaryAmountORM"));
        salaryTableView.setItems(salList);

    }

    public void viewButton() {
        salList.clear(); // Clear the ObservableList before adding new items
        // Establish a database connection
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            // Execute a SQL query to retrieve data from the salary table
            String salaryQuery = "SELECT * FROM `salary_tbl`";
            Statement salaryStatement = connection.createStatement();
            ResultSet salaryResultSet = salaryStatement.executeQuery(salaryQuery);
            // Populate the ObservableList with data from the salary table
            while (salaryResultSet.next()) {
                int sid = salaryResultSet.getInt("sid");
                int uid = salaryResultSet.getInt("uid");
                double amount = salaryResultSet.getDouble("salary_amount");

                // Fetch employee name based on uid from the employee table
                String employeeName = getEmployeeName(connection, uid);

                // Add the data to the ObservableList
                salList.add(new SalaryORM(sid, employeeName, amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createButton() {
        String sidString = txtFieldsid.getText().trim();
        String empName = txtFieldEmpName.getText().trim();
        String salaryString = txtFieldSalaryAmt.getText().trim();

        if (sidString.isEmpty() || empName.isEmpty() || salaryString.isEmpty()) {
            // Set an appropriate error message to errorMessage label
            errorMessage.setText("Please fill in all fields.");
            return;
        }
        int sid;
        try {
            sid = Integer.parseInt(sidString);
        } catch (NumberFormatException e) {
            errorMessage.setText("SID must be an integer.");
            return;
        }
        double salary;
        try {
            salary = Double.parseDouble(salaryString);
        } catch (NumberFormatException e) {
            errorMessage.setText("Salary must be a valid number.");
            return;
        }
        int uid = getEmployeeId(empName); // fetch the employee_id (UID) based on the employee name
        if (uid == -1) {
            errorMessage.setText("Employee name not found in the employee table.");
            return;
        }
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String insertQuery = "INSERT INTO `salary_tbl` (sid, uid, salary_amount) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set the parameters in the insert statement
                preparedStatement.setInt(1, sid);
                preparedStatement.setInt(2, uid); // Use fetched uid as the foreign key
                preparedStatement.setDouble(3, salary);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    txtFieldsid.clear();
                    txtFieldEmpName.clear();
                    txtFieldSalaryAmt.clear();
                    errorMessage.setText("Salary entry added successfully.");
                } else {
                    errorMessage.setText("Failed to add salary entry.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage.setText("An error occurred while adding the salary entry.");
        }
        viewButton();
    }

    //helper functions to createButton
    private int getEmployeeId(String employeeName) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        int employeeId = -1;
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT employee_id FROM `employee_tbl` WHERE employee_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, employeeName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        employeeId = resultSet.getInt("employee_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeId;
    }
    private String getEmployeeName(Connection connection, int uid) throws SQLException {
        String employeeName = "";
        String employeeQuery = "SELECT employee_name FROM `employee_tbl` WHERE employee_id = ?";
        try (PreparedStatement employeeStatement = connection.prepareStatement(employeeQuery)) {
            employeeStatement.setInt(1, uid);
            ResultSet employeeResultSet = employeeStatement.executeQuery();
            if (employeeResultSet.next()) {
                employeeName = employeeResultSet.getString("employee_name");
            }
        }
        return employeeName;
    }


    public void updateButton() {
        String sidString = txtFieldsid.getText().trim();
        String salaryString = txtFieldSalaryAmt.getText().trim();
        if (sidString.isEmpty() || salaryString.isEmpty()) {
            errorMessage.setText("SID and salary fields cannot be empty.");
            return;
        }
        int sid;
        try {
            sid = Integer.parseInt(sidString);
        } catch (NumberFormatException e) {
            errorMessage.setText("SID must be an integer.");
            return;
        }
        if (!isSidExists(sid)) {
            errorMessage.setText("SID does not exist.");
            return;
        }
        double salary;
        try {
            salary = Double.parseDouble(salaryString);
        } catch (NumberFormatException e) {
            errorMessage.setText("Salary must be a valid number.");
            return;
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String employeeName = getEmployeeName(connection, sid);
            if (employeeName.isEmpty()) {
                errorMessage.setText("Failed to retrieve employee name.");
                return;
            }
            String updateQuery = "UPDATE `salary_tbl` SET salary_amount = ? WHERE sid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setDouble(1, salary);
                preparedStatement.setInt(2, sid);
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    txtFieldsid.clear();
                    txtFieldSalaryAmt.clear();
                    errorMessage.setText("Salary entry updated successfully.");
                } else {
                    errorMessage.setText("Failed to update salary entry.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage.setText("An error occurred while updating the salary entry.");
        }
        viewButton();
    }
    //helper function for update button
    private boolean isSidExists(int sid) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
        String dbUser = "root";
        String dbPassword = "";
        boolean sidExists = false;

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT COUNT(*) AS count FROM `salary_tbl` WHERE sid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, sid);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        sidExists = resultSet.getInt("count") > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return sidExists;
    }

    public void deleteButton() {
        String sidString = txtFieldsid.getText().trim();
        if (!sidString.isEmpty()) {
            // check if the input is a valid integer
            try {
                int sid = Integer.parseInt(sidString);

                String jdbcUrl = "jdbc:mysql://localhost:3306/hr_management_db";
                String dbUser = "root";
                String dbPassword = "";
                try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                    String deleteQuery = "DELETE FROM `salary_tbl` WHERE sid = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                        preparedStatement.setInt(1, sid);

                        int rowsDeleted = preparedStatement.executeUpdate();
                        if (rowsDeleted > 0) {
                            txtFieldsid.clear();
                            errorMessage.setText("Salary entry with SID " + sid + " deleted successfully.");
                        } else {
                            errorMessage.setText("Failed to delete salary entry with SID " + sid + ".");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    errorMessage.setText("An error occurred while deleting the salary entry.");
                }
            } catch (NumberFormatException e) {
                errorMessage.setText("SID must be an integer.");
            }
        } else {
            errorMessage.setText("Please enter a SID to delete.");
        }
        viewButton();
    }

    public void backButton() {
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
