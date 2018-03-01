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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
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
    private SessionFactory fac;
    @FXML
    private Tab CustomerTab;
    @FXML
    private Parent customerForm;
    @FXML
    private CustomerFormController customerFormController;
//    @FXML
//    private Pane customerFormPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerFormController.setCustomerListController(this);

        customerForm.setVisible(false);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            fac = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {

            StandardServiceRegistryBuilder.destroy(registry);
        }
        this.populateUserList();

        customerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                if (null != newValue) {
                    System.out.println(newValue.getCustomerName() + " Id: " + newValue.getCustomerId());
                    customerForm.setVisible(true);
                    customerFormController.populateCustomer(newValue);
                }
            }

        });

    }

    public void test() {
        System.out.println("***********************************test");
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Populates the user list
     */
    public void populateUserList() {

        Session session = getSession();
        session.beginTransaction();
        List<Customer> customerListResults = session.createQuery("from Customer").list();

        customers.addAll(customerListResults);
        customerList.setItems(customers);
        session.close();

    }

    /**
     * Used to refresh the customer list if a customer is added or edited
     */
    public void refresh() {
        customerList.getItems().clear();
        this.populateUserList();
    }

    private Session getSession() {
        try (Session session = fac.getCurrentSession();) {
            return session;
        } catch (Exception e) {
            return fac.openSession();
        }
    }

}
