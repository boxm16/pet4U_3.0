package BasicModel;

import java.util.ArrayList;

public class Item {

    private String code;
    private String description;
    private ArrayList<AltercodeContainer> altercodes;
    private String position;
    private String quantity;
    private String state;
    private String supplier;

    public Item() {
        altercodes = new ArrayList();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<AltercodeContainer> getAltercodes() {
        return altercodes;
    }

    public void setAltercodes(ArrayList<AltercodeContainer> altercodes) {
        this.altercodes = altercodes;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void addAltercodeContainer(AltercodeContainer altercodeContainer) {
        this.altercodes.add(altercodeContainer);
    }

    public int getWeightCoefficient() {
        int weightCoefficient = 1;
        if (this.description.contains("20")) {
            weightCoefficient = 20;
        }
        if (this.description.contains("15")) {
            weightCoefficient = 15;
        }
        if (this.description.contains("14")) {
            weightCoefficient = 14;
        }
        if (this.description.contains("12")) {
            weightCoefficient = 12;
        }
        if (this.description.contains("10")) {
            weightCoefficient = 10;
        }
        return weightCoefficient;
    }

    public String getQunatityAsPieces() {
        if (this.quantity == null) {
            return null;
        }
        if (this.getWeightCoefficient() != 1) {

            try {
                double quantityInDouble = Double.parseDouble(this.quantity);
                double d = quantityInDouble / this.getWeightCoefficient();
                String dS = Double.toString(d);
                return dS;
            } catch (NumberFormatException ex) {
                System.out.println(ex);
                return "N/A";
            }
        }
        return this.quantity;
    }

    public String getWeightAlertColor() {

        try {
            double quantityInDouble = Double.parseDouble(this.quantity);
            if (quantityInDouble % this.getWeightCoefficient() != 0) {
                return "red";
            }
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            return "red";
        }

        return "inherited";
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

}
