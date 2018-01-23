package com.mycompany.scheduler;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApp extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        // SpringApplicationBuilder builder = new SpringApplicationBuilder(MainApp.class);
        springContext = SpringApplication.run(MainApp.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        loader.setControllerFactory(springContext::getBean);
        rootNode = loader.load();
    }

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
