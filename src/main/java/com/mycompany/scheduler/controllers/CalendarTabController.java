/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import com.mycompany.scheduler.util.CalendarPane;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jdharri
 */
public class CalendarTabController implements Initializable {

    @FXML
    private AnchorPane CalendarTab;
    @FXML
    private Button addAppointmentButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CalendarPane calPane = new CalendarPane();
        BorderPane root = new BorderPane(calPane.getView());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void createAppointment(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AppointmentForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Appointment");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CalendarTabController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayWeeklyAppointments() {
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate friday = LocalDate.now().with(DayOfWeek.FRIDAY);

    }

    public void displayMonthlyAppointments() {
        LocalDate first = LocalDate.now().withDayOfMonth(1);
        LocalDate last = LocalDate.now().withDayOfMonth(first.lengthOfMonth());
    }
}
