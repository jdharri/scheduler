package com.mycompany.scheduler.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jdharri
 */
public class AlertBox {

    public static void show(final String title, final String message) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300);
        Label l = new Label();
        l.setText(message);
        Button close = new Button("close");
        close.setOnAction(e -> stage.close());

        VBox lo = new VBox();
        lo.getChildren().addAll(l, close);
        lo.setAlignment(Pos.CENTER);
        Scene scene = new Scene(lo);
    }
}
