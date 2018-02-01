/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author jdharri
 */
public class AppointmentFormController implements Initializable {

    @FXML
    private DatePicker appointmentDate;
    @FXML
    private ComboBox<?> appointmentCustomer;
    @FXML
    private ComboBox<?> appointmentStartTime;
    @FXML
    private ComboBox<?> appointmentEndTime;
    @FXML
    private Button createAppointmentButton;
    @FXML
    private Button cancelAppointmentButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
