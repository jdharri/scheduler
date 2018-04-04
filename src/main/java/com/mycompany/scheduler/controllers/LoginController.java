/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import com.mycompany.scheduler.MainApp;
import com.mycompany.scheduler.model.User;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author jdharri
 */
public class LoginController implements Initializable {

    @FXML
    private Label loginLable;
    @FXML
    private TextField loginUsername;
    @FXML
    private TextField loginPassword;
    @FXML
    private Button loginButton;
    Locale currentLocale;
    SessionFactory fac;

    /**
     * This method is where the labels are set based on locale
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currentLocale = Locale.getDefault();
     //   currentLocale = Locale.FRANCE;
        //if locale is france
        if (currentLocale.equals(Locale.FRANCE)) {
            loginLable.setText("Connexion du programmateur");
            loginUsername.setPromptText("Nom d'utilisateur");
            loginPassword.setPromptText("Mot de passe");
            loginButton.setText("S'identifier");
        }
        //if locale is us
        if (currentLocale.equals(Locale.US)) {
            loginLable.setText("Scheduler Login");
            loginUsername.setPromptText("Username");
            loginPassword.setPromptText("Password");
            loginButton.setText("Login");
        }

    }

    public void Login(ActionEvent event) throws Exception {

        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/fxml/TabPane.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            fac = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {

            StandardServiceRegistryBuilder.destroy(registry);
        }
        Session session = fac.openSession();

        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        Query query = session.createQuery("from User WHERE userName=:userName");
        query.setParameter("userName", loginUsername.getText());
        User user = (User) query.getSingleResult();
        if (loginPassword.getText().equals(user.getPassword())) {

            loginLable.setText("authentication success");
            MainApp.setCurrentUser(user);
            window.setScene(mainViewScene);
            window.show();
        } else {
            System.out.println("username entered:" + loginUsername.getText());
            System.out.println("should have been username: " + user.getUserName());
            System.out.println("Password: " + loginPassword.getText());
            System.out.println("shoudl have been password: " + user.getPassword());
            loginLable.setText("Authentication failed");
        }

        session.flush();

        session.close();
        fac.close();

    }

}
