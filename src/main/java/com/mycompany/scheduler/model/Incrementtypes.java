package com.mycompany.scheduler.model;

import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;

/**
 * Incrementtypes model object
 */
public class Incrementtypes implements java.io.Serializable {

    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private int incrementTypeId;
    private String incrementTypeDescription;

    public Incrementtypes() {
    }

    public Incrementtypes(int incrementTypeId, String incrementTypeDescription) {
        this.incrementTypeId = incrementTypeId;
        this.incrementTypeDescription = incrementTypeDescription;
    }

    public int getIncrementTypeId() {
        return this.incrementTypeId;
    }

    public void setIncrementTypeId(int incrementTypeId) {
        this.incrementTypeId = incrementTypeId;
    }

    public String getIncrementTypeDescription() {
        return this.incrementTypeDescription;
    }

    public void setIncrementTypeDescription(String incrementTypeDescription) {
        this.incrementTypeDescription = incrementTypeDescription;
    }

}
