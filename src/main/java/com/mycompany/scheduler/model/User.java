package com.mycompany.scheduler.model;
// Generated Jan 27, 2018 12:21:46 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import org.hibernate.annotations.Type;

/**
 * User generated by hbm2java
 */
public class User  implements java.io.Serializable {


     private int userId;
     private String userName;
     private String password;

     private byte active;
     private String createBy;
     private Date createDate;
     private Date lastUpdate;
     private String lastUpdatedBy;

    public User() {
    }

    public User(int userId, String userName, String password, byte active, String createBy, Date createDate, Date lastUpdate, String lastUpdatedBy) {
       this.userId = userId;
       this.userName = userName;
       this.password = password;
       this.active = active;
       this.createBy = createBy;
       this.createDate = createDate;
       this.lastUpdate = lastUpdate;
       this.lastUpdatedBy = lastUpdatedBy;
    }
   
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public byte getActive() {
        return this.active;
    }
    
    public void setActive(byte active) {
        this.active = active;
    }
    public String getCreateBy() {
        return this.createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getLastUpdate() {
        return this.lastUpdate;
    }
    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }




}


