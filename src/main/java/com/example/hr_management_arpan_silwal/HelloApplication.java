package com.example.hr_management_arpan_silwal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("logIn-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("HR Management!");
        stage.setScene(scene);

        LogInController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}