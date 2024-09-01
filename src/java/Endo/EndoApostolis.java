/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 *
 * @author Michail Sitmalidis
 */
public class EndoApostolis extends EndoParalavis {

    private String sender;
    private String receiver;
    private boolean isLocked;
    private boolean isChanged;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getShortNumber() {
        return this.getNumber().substring(this.getNumber().length() - 3);
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isIsChanged() {
        return isChanged;
    }

    public void setIsChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public LocalDate calculateDayOfApostilis() {

        LocalDate now = LocalDate.now();
        if (now.getDayOfWeek() == DayOfWeek.FRIDAY) {
            return now.plusDays(3);
        } else {
            return now.plusDays(1);
        }
    }
}
