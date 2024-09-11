/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.util.LinkedHashMap;


public class EndoApostolisDay {
   private String date;
   LinkedHashMap<String, EndoApostolis> endoApostoliss;

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
   
   
    
}
