/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputOutput;

import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class InputOutputContainer {

    private String itemCode;
    private LinkedHashMap<LocalDate, InputOutput> inputOutputs;

    public InputOutputContainer() {
        inputOutputs = new LinkedHashMap<LocalDate, InputOutput>();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public LinkedHashMap<LocalDate, InputOutput> getInputOutputs() {
        return inputOutputs;
    }

    public void setInputOutputs(LinkedHashMap<LocalDate, InputOutput> inputOutputs) {
        this.inputOutputs = inputOutputs;
    }

}
