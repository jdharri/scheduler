/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import com.mycompany.scheduler.MainApp;
import com.mycompany.scheduler.model.Appointment;
import com.mycompany.scheduler.model.Customer;
import com.mycompany.scheduler.model.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
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
public class AppointmentFormController implements Initializable {

    @FXML
    private DatePicker appointmentDate;
    @FXML
    private ComboBox<Customer> appointmentCustomer;
    @FXML
    private ComboBox<String> appointmentStartTime;
    @FXML
    private ComboBox<String> appointmentEndTime;
    @FXML
    private Button createAppointmentButton;
    @FXML
    private Button cancelAppointmentButton;
    @FXML
    private TextField appointmentLocation;
    @FXML
    private TextField appointmentDescription;
    final ObservableList hours = FXCollections.observableArrayList();
    SessionFactory fac;
    private final User currentUser = MainApp.getCurrentUser();
    Stage stage;
    @FXML
    private AnchorPane appointmentForm;
//    @FXML
    private CalendarTabController calendarTabController;
    @FXML
    private AnchorPane calendarTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // stage = (Stage) appointmentDate.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CalendarTab.fxml"));
        calendarTabController = loader.getController();
        this.initializeDBConnection();
        this.populateCustomers();
        hours.addAll(this.getHours());
        appointmentStartTime.getItems().clear();
        appointmentStartTime.getItems().addAll(hours);
        appointmentEndTime.getItems().clear();
        appointmentEndTime.getItems().addAll(hours);
        createAppointmentButton.setOnAction((event) -> saveAppointment());
        cancelAppointmentButton.setOnAction((event) -> {
            try {
                cancelAppointment();
            } catch (IOException ex) {
                Logger.getLogger(AppointmentFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private List<String> getHours() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime nextDay = ldt.plus(1, ChronoUnit.DAYS);
        List<String> hourSelections = new ArrayList<>();
        while (ldt.isBefore(nextDay.minus(15, ChronoUnit.MINUTES))) {
            ldt = ldt.plus(15, ChronoUnit.MINUTES);
            hourSelections.add(ldt.format(DateTimeFormatter.ofPattern("hh:mm a")));
        }

        return hourSelections;
    }

    /**
     * Cancel the addition of an appointment
     *
     * @throws IOException
     */
    public void cancelAppointment() throws IOException {

        //   calendarTabController.showCalendar();
//         appointmentForm.setVisible(false);
//        ObservableList children = appointmentForm.getParent().getParent().getChildrenUnmodifiable();
//        AnchorPane calendarT = (AnchorPane) children.get(0);
//        calendarT.getChildren().forEach(node -> System.out.println(node.getId()));
//        calendarT.getChildren().forEach(node -> node.setVisible(true));
        this.clearForm();
        appointmentForm.setVisible(false);
        //  calendarT.getChildren() .forEach(node -> System.out.println(node.)));

    }

    /**
     * Reset the form
     */
    public void clearForm() {
        appointmentLocation.clear();
        appointmentDescription.clear();
        appointmentDate.getEditor().clear();
        appointmentEndTime.getEditor().clear();
        appointmentStartTime.getEditor().clear();
    }

    @FXML
    public void saveAppointment() {
        final String currentUserId = new Integer(MainApp.getCurrentUser().getUserId()).toString();
        System.out.println("Save Appointment");
        Customer customer = appointmentCustomer.getValue();
        Appointment appt = new Appointment();
        appt.setCustomerId(customer.getCustomerId());
        appt.setStart(formatDateTime(appointmentDate.getValue(), appointmentStartTime.getValue()));
        appt.setEnd(formatDateTime(appointmentDate.getValue(), appointmentEndTime.getValue()));
        appt.setCreateDate(new Date());
        appt.setCreatedBy(currentUserId);
        appt.setLastUpdate(new Date());
        appt.setLastUpdateBy(currentUserId);
        appt.setDescription(appointmentDescription.getText());
        appt.setLocation(appointmentLocation.getText());
        appt.setTitle(String.format("apointment with %s with regard to %s",
                customer.getCustomerName(), appt.getDescription()));
        appt.setUrl("http://localhost");
        appt.setContact(currentUserId);
        Session session = getSession();
        session.beginTransaction();
        session.save(appt);
        session.flush();

        Stage stage = (Stage) createAppointmentButton.getScene().getWindow();
        stage.close();
        calendarTabController.refresh();

//        if ((appointmentCustomer.getValue() != null)
        //                && (appointmentDate != null)
        //                && (appointmentStartTime != null)
        //                && (appointmentEndTime != null)) {
        //            Appointment appt = new Appointment();
        //            appt.setCustomerId(appointmentCustomer.getValue().getCustomerId());
        //            // appt.set
        //        } else {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//
//        alert.setTitle(
//                "all fields must be selected");
//        //   if (appointmentCustomer == null) {
//        alert.setContentText(
//                "You didn't select a customer");
//        alert.showAndWait();
        // }
        //  }
    }

    private Date formatDateTime(LocalDate ld, String time) {
        LocalTime lt = LocalTime.parse(time, DateTimeFormatter.ofPattern("hh:mm a"));
        LocalDateTime localDateTime = LocalDateTime.of(ld, lt);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public void populateCustomers() {

        Session session = getSession();
        session.beginTransaction();
        List<Customer> customerListResults = session.createQuery("from Customer").list();

        appointmentCustomer.getItems().addAll(customerListResults);

    }

    private void initializeDBConnection() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            fac = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {

            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @FXML
    public void validateCustomerSelection() {

    }

    @FXML
    public void validateDateSelection() {

    }

    @FXML
    public void validateStartTimeSelection() {

    }

    @FXML
    public void validateEndTimeSelection() {

    }

    public void setCalendarTabController(CalendarTabController con) {
        this.calendarTabController = con;
    }

    private Session getSession() {
        try (Session session = fac.getCurrentSession();) {
            return session;
        } catch (Exception e) {
            return fac.openSession();
        }
    }
}
