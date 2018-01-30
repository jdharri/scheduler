/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import com.mycompany.scheduler.model.Customer;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * FXML Controller class
 *
 * @author jdharri
 */
public class CustomerListController implements Initializable {

    @FXML
    private ListView<Customer> customerList;
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    SessionFactory fac;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        List<Customer> customerListResults = session.createQuery("from customer").list();
      customers.addAll(customerListResults);
        customerList.setCellFactory(customer -> new ListCell<Customer>(){
           @Override
           protected void updateItem(Customer cust, boolean empty){
               super.updateItem(cust, empty);
               if(empty || cust == null || cust.getCustomerName() == null){
                   setText(null);
               }else{
                   setText(cust.getCustomerName());
               }
           }
        });
     
     

    }

}
