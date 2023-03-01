package BasicModel;

import java.util.ArrayList;

public class Item {

    private String code;
    private String description;
    private ArrayList<AltercodeContainer> altercodes;
    private String position;
    private String quantity;
    private String state;

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

}
