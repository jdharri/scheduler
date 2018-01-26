/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author jdharri
 */
public class LoginController {

    @FXML
    private Label loginLable;
    @FXML
    private TextField loginUsername;
    @FXML
    private TextField loginPassword;

    public void Login(ActionEvent event) throws Exception {
     

    

        System.out.println("here");
        System.out.println("username entered:" + loginUsername.getText());
        loginLable.setText("blah blah blah");
        System.out.println("Password: " + loginPassword.getText());
        System.out.println("label: " + loginLable.getText());
        System.out.println("localdate: " + LocalDateTime.now());
        Locale currentLocale = Locale.getDefault();
        System.out.println(currentLocale.getDisplayLanguage());
        System.out.println(currentLocale.getCountry());
        currentLocale = Locale.CHINA;
        System.out.println(currentLocale.getDisplayLanguage());
        System.out.println(currentLocale.getCountry());
      
        if (loginUsername.getText().equals("jdharri") && loginPassword.getText().equals("pass")) {
//            loginLable.setText("Login Success");
   loginLable.setText("blah blah blah");
        } else {
            loginLable.setText("Login Failed");
        }
    }
}
