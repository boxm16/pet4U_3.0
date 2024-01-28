/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import BasicModel.Item;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Michail Sitmalidis
 */
public class EndoBinder {

    private EndoParalavis endoParalavis;

    private LinkedHashMap<String, EndoApostolis> endoApostoliss;
    private LinkedHashMap<String, Double> totalSentItems;

    private boolean binderOk;

    public EndoBinder() {
        this.endoApostoliss = new LinkedHashMap<>();
        totalSentItems = new LinkedHashMap<>();
    }

    public EndoParalavis getEndoParalavis() {
        return endoParalavis;
    }

    public void setEndoParalavis(EndoParalavis endoParalavis) {
        this.endoParalavis = endoParalavis;
    }

    public LinkedHashMap<String, EndoApostolis> getEndoApostoliss() {
        return endoApostoliss;
    }

    public void setEndosApostolis(LinkedHashMap<String, EndoApostolis> endoApostoliss) {
        this.endoApostoliss = endoApostoliss;
    }

    void addEndoApostolis(String endoApostolisId, EndoApostolis endoApostolis) {
        this.endoApostoliss.put(endoApostolisId, endoApostolis);
    }

    public LinkedHashMap<String, Double> getTotalSentItems() {
        return totalSentItems;
    }

    public void setTotalSentItems(LinkedHashMap<String, Double> totalSentItems) {
        this.totalSentItems = totalSentItems;
    }

    public boolean isBinderOk() {
        return binderOk;
    }

    public void setBinderOk(boolean binderOk) {
        this.binderOk = binderOk;
    }

    public void checkTotals() {

        LinkedHashMap<String, Item> deliveredItems = this.endoParalavis.getItems();

        if (deliveredItems.size() != this.totalSentItems.size()) {
            System.out.println("Different Sizes");
            this.binderOk = false;
            return;
        }
        for (Map.Entry<String, Item> deliveredItemsEntry : deliveredItems.entrySet()) {

            if (totalSentItems.containsKey(deliveredItemsEntry.getKey())) {
                double deliveredQuantity = Double.valueOf(deliveredItemsEntry.getValue().getQuantity());
                if (deliveredQuantity != totalSentItems.get(deliveredItemsEntry.getKey())) {
                    System.out.println("Different quantity for code: " + deliveredItemsEntry.getKey());
                    this.binderOk = false;
                    return;
                }
            } else {

                this.binderOk = false;
                return;
            }
        }
        this.binderOk = true;
    }

}
