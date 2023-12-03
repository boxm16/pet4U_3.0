package Offer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Offer {

    private int id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String itemCode;
    private String itemDescription;
    private String offerPart;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

   

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

   

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getStartDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(this.getStartDate());
    }
    public String getEndDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(this.getEndDate());
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getOfferPart() {
        return offerPart;
    }

    public void setOfferPart(String offerPart) {
        this.offerPart = offerPart;
    }
    
    
}
