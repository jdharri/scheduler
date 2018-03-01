/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import com.mycompany.scheduler.model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author jdharri
 */
public class CalendarTabController implements Initializable {
    
    @FXML
    private AnchorPane calendarTab;
    
    @FXML
    private ListView monthList;
    ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    @FXML
    private AppointmentFormController appointmentFormController;
    private SessionFactory fac;
    private final SimpleDateFormat queryFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private AnchorPane appointmentPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //   CalendarPane calPane = new CalendarPane();
        //   appointmentFormController.setCalendarTabController(this);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            fac = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            
            StandardServiceRegistryBuilder.destroy(registry);
        }
//        
        //    BorderPane root = new BorderPane(calPane.getView());
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
        this.displayMonthlyAppointments();
    }
    
    public void createAppointment(Event event) {
        try {
            appointmentPane = FXMLLoader.load(getClass().getResource("/fxml/AppointmentForm.fxml"));
           Predicate <String> p = s -> s.equals("cancelAppointmentButton");
            appointmentPane.getChildren().stream().filter(p);
            appointmentPane.visibleProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    System.out.println("changed*(**********************");
                    showCalendar();
                }
            });
            calendarTab.getChildren().forEach(node -> node.setVisible(false));
            
            if (!calendarTab.getChildren().contains(fac)) {
                System.out.println("************ not a child adding");
                calendarTab.getChildren().add(appointmentPane);
            }
            appointmentPane.setVisible(true);
            
        } catch (IOException ex) {
            Logger.getLogger(CalendarTabController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showCalendar() {
        System.out.println("****************showCalendar");
        calendarTab.getChildren().forEach(node -> node.setVisible(true));
        appointmentPane.setVisible(true);
    }
    
    public void displayWeeklyAppointments() {
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate friday = LocalDate.now().with(DayOfWeek.FRIDAY);
        
    }
    
    public void displayMonthlyAppointments() {
        System.out.println("*****************displayMonthlyAppointments");
        LocalDate first = LocalDate.now().withDayOfMonth(1);
        LocalDate last = LocalDate.now().withDayOfMonth(first.lengthOfMonth());
        Date firstOfMonth = Date.from(first.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant());
        Date lastOfMonth = Date.from(last.atTime(23, 59)
                .atZone(ZoneId.systemDefault()).toInstant());
        Session session = fac.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Appointment AS a WHERE a.start BETWEEN :firstOfMonth AND :lastOfMonth");
        //      Query query = session.createQuery("FROM Appointment ");
        query.setParameter("firstOfMonth", firstOfMonth);
        query.setParameter("lastOfMonth", lastOfMonth);
        List<Appointment> appointments = query.getResultList();
        //   Map<LocalDate, Appointment> apptMap = new TreeMap<>();

        //  appointments.stream().filter(predicate)
        appointments.forEach(appt -> monthList.getItems().add(appt));
//        List<Appointment> appointments = session.createQuery("from Appointment").list();
        //  tableview.getItems().add(e)
    }
    
    public void refresh() {
        displayMonthlyAppointments();
    }
}
