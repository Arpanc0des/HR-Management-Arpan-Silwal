package com.example.hr_management_arpan_silwal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoad = new FXMLLoader(HelloApplication.class.getResource("logIn-view.fxml"));
        Scene scene = new Scene(fxmlLoad.load(), 500, 500);
        stage.setTitle("HR Management!");
        stage.setScene(scene);

        LogInController loginControllerStage = fxmlLoad.getController();
        loginControllerStage.setPrimaryStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        System.out.println(oneYearSalary(200));
    }


    public static double oneYearSalary(double monthSalary) {return monthSalary*12;};
}
