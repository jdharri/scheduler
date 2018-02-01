package com.mycompany.scheduler.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.mycompany.scheduler.MainApp;
import com.mycompany.scheduler.model.Address;
import com.mycompany.scheduler.model.City;
import com.mycompany.scheduler.model.Country;
import com.mycompany.scheduler.model.Customer;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
public class CustomerFormController implements Initializable {

    @FXML
    AnchorPane customerForm;
    @FXML
    TextField customerName;
    @FXML
    private TextField address;
    @FXML
    private TextField address2;
    @FXML
    private TextField city;
    @FXML
    private TextField country;
    @FXML
    private TextField phone;
    @FXML
    private TextField postalCode;
    @FXML
    private Button cancel;
    @FXML
    private Button saveUserButton;
    @FXML
    private Label customerFormLabel;
    SessionFactory fac;
    private Integer customerId;
    @FXML
    private Button addNewUser;
//     @FXML
    private CustomerListController customerListController;

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

    }

    public void setCustomerListController(CustomerListController con) {
        this.customerListController = con;
    }

    public CustomerFormController() {
    }

    public void populateCustomer(Customer customer) {

        Session session = fac.openSession();
        session.beginTransaction();
        Address custAddr = (Address) session.createQuery("from Address WHERE addressId=:addressId")
                .setParameter("addressId", customer.getAddressId())
                .getSingleResult();

        session.flush();
        City custCity = (City) session.createQuery("from City WHERE cityId=:cityId")
                .setParameter("cityId", custAddr.getCityId())
                .getSingleResult();
        Country custCountry = (Country) session.createQuery("from Country WHERE countryId=:countryId")
                .setParameter("countryId", custCity.getCountryId())
                .getSingleResult();
        this.customerName.setText(customer.getCustomerName());
        this.address.setText(custAddr.getAddress());
        this.address2.setText(custAddr.getAddress2());
        this.city.setText(custCity.getCity());
        this.country.setText(custCountry.getCountry());
        this.postalCode.setText(custAddr.getPostalCode());
        this.phone.setText(custAddr.getPhone());
        session.flush();

        session.close();
    }

    @FXML
    public void SaveUser(ActionEvent event) {

        try (Session session = fac.openSession()) {
            session.beginTransaction();
            Customer customer;
            Country custCountry = null;
            City custCity = null;
            Address custAddress = null;
            if (this.customerId != null) {
                customer = (Customer) session.createQuery("from Customer WHERE customerId=:customerId")
                        .setParameter("customerId", this.customerId)
                        .getSingleResult();

                custAddress = (Address) session.createQuery("from Address WHERE addressId=:addressId")
                        .setParameter("addressId", customer.getAddressId())
                        .getSingleResult();

                session.flush();
                custCity = (City) session.createQuery("from City WHERE cityId=:cityId")
                        .setParameter("cityId", custAddress.getCityId())
                        .getSingleResult();
                custCountry = (Country) session.createQuery("from Country WHERE countryId=:countryId")
                        .setParameter("countryId", custCity.getCountryId())
                        .getSingleResult();
            } else {
                custCountry = new Country();
                custCity = new City();
                custAddress = new Address();
                customer = new Customer();
            }
            //  Country custCountry = new Country();
            custCountry.setCountry(country.getText());
            custCountry.setCreateDate(new Date());
            custCountry.setCreatedBy(MainApp.getCurrentUser());
            custCountry.setLastUpdate(new Date());
            custCountry.setLastUpdateBy(MainApp.getCurrentUser());
            session.save(custCountry);

            custCity.setCity(city.getText());
            custCity.setCountryId(custCountry.getCountryId());
            custCity.setCreateDate(new Date());
            custCity.setCreatedBy(MainApp.getCurrentUser());
            custCity.setLastUpdate(new Date());
            custCity.setLastUpdateBy(MainApp.getCurrentUser());
            session.save(custCity);

//        Address custAddress = new Address();
            custAddress.setAddress(address.getText());
            custAddress.setAddress2(address2.getText());
            custAddress.setCityId(custCity.getCityId());
            custAddress.setCreateDate(new Date());
            custAddress.setCreatedBy(MainApp.getCurrentUser());
            custAddress.setLastUpdateBy(MainApp.getCurrentUser());
            custAddress.setLastUpdate(new Date());
            custAddress.setPhone(phone.getText());
            custAddress.setPostalCode(postalCode.getText());

            session.save(custAddress);
            System.out.println("***********************************addressId: " + custAddress.getAddressId());

            customer.setCustomerName(customerName.getText());
            customer.setCreateDate(new Date());
            customer.setActive(true);
            customer.setLastUpdate(new Date());
            customer.setCreatedBy(MainApp.getCurrentUser());
            customer.setLastUpdateBy(MainApp.getCurrentUser());
            customer.setAddressId(custAddress.getAddressId());
            session.save(customer);
            session.flush();
            session.close();
            this.cancel.fire();
              customerListController.addCustomer(customer);
        }

    }

    @FXML
    private void clearUserId(ActionEvent event) {
      
        this.cancel.fire();
        this.customerId = null;
    }

    @FXML
    private void CancelUser(ActionEvent event) {

        customerName.clear();
        address.clear();
        address2.clear();
        city.clear();
        country.clear();
        phone.clear();
        postalCode.clear();

    }

    public String test() {
        return "it works";
    }

}
