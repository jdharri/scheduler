package com.mycompany.scheduler.model;
// Generated Jan 27, 2018 12:21:46 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Reminder generated by hbm2java
 */
public class Reminder  implements java.io.Serializable {


     private int reminderId;
     private Date reminderDate;
     private int snoozeIncrement;
     private int snoozeIncrementTypeId;
     private int appointmentId;
     private String createdBy;
     private Date createdDate;
     private String remindercol;

    public Reminder() {
    }

    public Reminder(int reminderId, Date reminderDate, int snoozeIncrement, int snoozeIncrementTypeId, int appointmentId, String createdBy, Date createdDate, String remindercol) {
       this.reminderId = reminderId;
       this.reminderDate = reminderDate;
       this.snoozeIncrement = snoozeIncrement;
       this.snoozeIncrementTypeId = snoozeIncrementTypeId;
       this.appointmentId = appointmentId;
       this.createdBy = createdBy;
       this.createdDate = createdDate;
       this.remindercol = remindercol;
    }
   
    public int getReminderId() {
        return this.reminderId;
    }
    
    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }
    public Date getReminderDate() {
        return this.reminderDate;
    }
    
    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }
    public int getSnoozeIncrement() {
        return this.snoozeIncrement;
    }
    
    public void setSnoozeIncrement(int snoozeIncrement) {
        this.snoozeIncrement = snoozeIncrement;
    }
    public int getSnoozeIncrementTypeId() {
        return this.snoozeIncrementTypeId;
    }
    
    public void setSnoozeIncrementTypeId(int snoozeIncrementTypeId) {
        this.snoozeIncrementTypeId = snoozeIncrementTypeId;
    }
    public int getAppointmentId() {
        return this.appointmentId;
    }
    
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public String getRemindercol() {
        return this.remindercol;
    }
    
    public void setRemindercol(String remindercol) {
        this.remindercol = remindercol;
    }




}


