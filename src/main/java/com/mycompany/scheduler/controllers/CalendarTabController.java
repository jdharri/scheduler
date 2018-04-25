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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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

    private AppointmentFormController appointmentFormController;
    private SessionFactory fac;
    private final SimpleDateFormat queryFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private AnchorPane appointmentPane;
    @FXML
    private RadioButton monthToggle;
    @FXML
    private RadioButton weekToggle;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        try {
            FXMLLoader loader;
//            ToggleGroup weekMonthRadioGroup = new ToggleGroup();
          // weekToggle.setSelected(true);
//            monthToggle.setToggleGroup(weekMonthRadioGroup);
            loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentForm.fxml"));
            appointmentPane = loader.load();
            appointmentFormController = loader.<AppointmentFormController>getController();

            appointmentFormController.setCalendarTabController(this);

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
        //    this.displayMonthlyAppointments();
        this.displayWeeklyAppointments();
        } catch (IOException ex) {

            Logger.getLogger(CalendarTabController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Makes the appointment pane visible
     *
     * @param event
     */
    public void createAppointment(final Event event) {

        calendarTab.getChildren().forEach(node -> node.setVisible(false));
        if (!calendarTab.getChildren().contains(appointmentPane)) {
            System.out.println("************ not a child adding");
            calendarTab.getChildren().add(appointmentPane);
        }
        appointmentPane.setVisible(true);
    }

    /**
     * Makes the calendar pane visible
     */
    public void showCalendar() {
        System.out.println("****************showCalendar");
        calendarTab.getChildren().forEach(node -> node.setVisible(true));

    }

    /**
     * Displays the appointments by week
     */
    public void displayWeeklyAppointments() {
        System.out.println("*****************displayWeeklyAppointments");

        LocalDate first = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate last = LocalDate.now().with(DayOfWeek.SUNDAY);
        Date firstOfWeek = Date.from(first.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant());
        Date lastOfWeek = Date.from(last.atTime(23, 59)
                .atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("query from: " + first + " to: " + last);
        monthList.getItems().removeAll(monthList.getItems());
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Appointment AS a WHERE a.start BETWEEN :firstOfWeek AND :lastOfWeek");
        //      Query query = session.createQuery("FROM Appointment ");
        query.setParameter("firstOfWeek", firstOfWeek);
        query.setParameter("lastOfWeek", lastOfWeek);
        List<Appointment> appointments = query.getResultList();

        //   Map<LocalDate, Appointment> apptMap = new TreeMap<>();
        //  appointments.stream().filter(predicate)
        appointments.forEach(appt -> monthList.getItems().add(appt));
        session.close();
//        List<Appointment> appointments = session.createQuery("from Appointment").list();
        //  tableview.getItems().add(e)

    }

    /**
     * Displays appointments by month
     */
    public void displayMonthlyAppointments() {
        System.out.println("*****************displayMonthlyAppointments");
        LocalDate first = LocalDate.now().withDayOfMonth(1);
        LocalDate last = LocalDate.now().withDayOfMonth(first.lengthOfMonth());
        Date firstOfMonth = Date.from(first.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("query from: " + first + " to: " + last);
        Date lastOfMonth = Date.from(last.atTime(23, 59)
                .atZone(ZoneId.systemDefault()).toInstant());
        monthList.getItems().removeAll(monthList.getItems());
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Appointment AS a WHERE a.start BETWEEN :firstOfMonth AND :lastOfMonth");
        //      Query query = session.createQuery("FROM Appointment ");
        query.setParameter("firstOfMonth", firstOfMonth);
        query.setParameter("lastOfMonth", lastOfMonth);
        List<Appointment> appointments = query.getResultList();

        //   Map<LocalDate, Appointment> apptMap = new TreeMap<>();
        //  appointments.stream().filter(predicate)
        appointments.forEach(appt -> monthList.getItems().add(appt));
        session.close();
//        List<Appointment> appointments = session.createQuery("from Appointment").list();
        //  tableview.getItems().add(e)
    }

    /**
     * Refreshes the appointment list
     */
    public void refresh() {
        displayMonthlyAppointments();
    }

    /**
     * Gets the current persistence session
     *
     * @return {@link Session}
     */
    private Session getSession() {
        try (Session session = fac.getCurrentSession();) {
            return session;
        } catch (Exception e) {
            return fac.openSession();
        }
    }
}
