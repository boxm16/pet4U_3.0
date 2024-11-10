/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputOutput;

import BasicModel.Item;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class InputOutputContainer {

    private Item item;
    private LinkedHashMap<LocalDate, InputOutput> inputOutputs;

    public InputOutputContainer() {
        inputOutputs = new LinkedHashMap<LocalDate, InputOutput>();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

   

    public LinkedHashMap<LocalDate, InputOutput> getInputOutputs() {
        return inputOutputs;
    }

    public void setInputOutputs(LinkedHashMap<LocalDate, InputOutput> inputOutputs) {
        this.inputOutputs = inputOutputs;
    }

}
