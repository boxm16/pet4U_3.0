/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BestBefore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class BestBeforeStatement {

    public int id;
    public String description;
    public String altercode;
    public String bestBefore;
    public String alert;
    public String note;
    public String alertColor;
    public String position;

    public String getAltercode() {
        return altercode;
    }

    public void setAltercode(String altercode) {
        this.altercode = altercode;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(String bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlertColor() {
        try {
            Date alertDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.alert);
            Date nowDate = new Date();
            if (alertDate.compareTo(nowDate) > 0) {
                return "";
            } else if (alertDate.compareTo(nowDate) < 0) {
                return "red";
            } else if (alertDate.compareTo(nowDate) == 0) {
                return "yellow";
            }
            return "";
        } catch (ParseException ex) {
            Logger.getLogger(BestBeforeStatement.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
