package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("menu.fxml"));
        BorderPane rootPane = loader.load();
        loader.getController();

        configureMenuStage(primaryStage, rootPane);

        primaryStage.show();
    }

    private void configureMenuStage(Stage primaryStage, BorderPane rootPane) {
        primaryStage.setScene(new Scene(rootPane));
        primaryStage.setTitle("Ugandan Survival Menu");
        primaryStage.minHeightProperty().bind(rootPane.minHeightProperty());
        primaryStage.minWidthProperty().bind(rootPane.minWidthProperty());
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
