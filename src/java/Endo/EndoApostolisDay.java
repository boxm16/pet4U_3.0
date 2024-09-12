/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class EndoApostolisDay {

    private String date;
    LinkedHashMap<String, EndoApostolis> endoApostoliss;

    public EndoApostolisDay() {
        endoApostoliss = new LinkedHashMap<String, EndoApostolis>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LinkedHashMap<String, EndoApostolis> getEndoApostoliss() {
        return endoApostoliss;
    }

    public void setEndoApostoliss(LinkedHashMap<String, EndoApostolis> endoApostoliss) {
        this.endoApostoliss = endoApostoliss;
    }

    public int getAllCodesQuantityOfDay() {
        int q = 0;
        int row = 0;
        for (Map.Entry<String, EndoApostolis> endoApostolissEntry : endoApostoliss.entrySet()) {
            if (row == 0) {
                continue;
            }
            q = q + endoApostolissEntry.getValue().getItems().size();
            row++;
        }
        return q;
    }

    public String getFirstEndoIdOfTheDay() {
        ArrayList<String> arrayList = new ArrayList<>(this.endoApostoliss.keySet());
        if (arrayList.size() > 0) {
            return arrayList.get(0);
        } else {
            return "";
        }
    }

    public String getLastEndoIdOfTheDay() {
        ArrayList<String> arrayList = new ArrayList<>(this.endoApostoliss.keySet());
        if (arrayList.size() > 0) {
            return arrayList.get(arrayList.size() - 1);
        } else {
            return "";
        }
    }

    public Duration getTimeSpentForDayEndo() {
        Duration timeSpent = Duration.between(this.endoApostoliss.get(getFirstEndoIdOfTheDay()).getCreationDateTime(), this.endoApostoliss.get(getLastEndoIdOfTheDay()).getCreationDateTime());
        return timeSpent;
    }

}
