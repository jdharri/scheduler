/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import com.mycompany.scheduler.model.Customer;
import com.mycompany.scheduler.model.User;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    Locale currentLocale;
    SessionFactory fac;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentLocale = Locale.getDefault();
    }

    public void Login(ActionEvent event) throws Exception {
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
        Customer cust = session.find(Customer.class, 1);
        System.out.println("Name: " + cust.getCustomerName());
//        Customer c = new Customer();
//        c.setActive(true);
//        c.setCustomerName("Fred Flinstone");
//        c.setCustomerId(2);
//        c.setCreateDate(new Date());
//        c.setCreatedBy("me");
//        c.setLastUpdate(new Date());
//        c.setAddressId(1);
//        c.setLastUpdateBy("me");
//        session.save(c);
//        session.getTransaction().commit();
        session.flush();
        User user = new User();
        user.setActive(false);
        user.setCreateBy("me");
        user.setCreateDate(new Date());
        user.setUserName("test");
        user.setPassword("test");
        user.setLastUpdatedBy("me");
        user.setUserId(1);
        user.setLastUpdate(new Date());
        
        session.save(user);
        session.getTransaction().commit();
        session.flush();
        
        
        session.close();
        fac.close();

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
