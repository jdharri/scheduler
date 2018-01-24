package com.mycompany.scheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainApp extends Application {

    private Parent rootNode;


    @Override
    public void start(Stage stage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        // Scene scene = new Scene(root, 600, 550);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        //  stage.setScene(new Scene(rootNode));
        stage.setTitle("login");
        Scene scene = new Scene(rootNode, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

}
