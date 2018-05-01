/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scheduler.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jdharri
 */
public class AppointmentFormControllerTest {

    public AppointmentFormControllerTest() {
    }

    @Test
    public void testFormatDateTime() {
        AppointmentFormController afc = new AppointmentFormController();
        LocalDate ld = LocalDate.of(2018, Month.MARCH, 30);
        String time = "01:01 AM";
        System.out.println("system zone: " + ZoneId.systemDefault());
     //  LocalDate first = LocalDate.now().with(DayOfWeek.MONDAY);
     //   LocalDate last = LocalDate.now().with(DayOfWeek.SUNDAY);
     //   System.out.println("first: "+first);
      //  System.out.println("last: "+last);
        Date d = afc.formatDateTime(ld, time);
     
        System.out.println("output date: " + d);
        System.out.println("output date instant: " + d.toInstant());
//
    }

}
