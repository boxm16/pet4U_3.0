/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class EndoBinder {

    private EndoParalavis endoParalavis;

    private LinkedHashMap<String, EndoApostolis> endosApostolis;

    public EndoBinder() {
        this.endosApostolis = new LinkedHashMap<>();
    }

    public EndoParalavis getEndoParalavis() {
        return endoParalavis;
    }

    public void setEndoParalavis(EndoParalavis endoParalavis) {
        this.endoParalavis = endoParalavis;
    }

    public LinkedHashMap<String, EndoApostolis> getEndosApostolis() {
        return endosApostolis;
    }

    public void setEndosApostolis(LinkedHashMap<String, EndoApostolis> endosApostolis) {
        this.endosApostolis = endosApostolis;
    }

    void addEndoApostolis(String endoApostolisId, EndoApostolis endoApostolis) {
        this.endosApostolis.put(endoApostolisId, endoApostolis);
    }

}
