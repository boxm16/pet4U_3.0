package Offer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getStartDateString() {

        return this.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    

    public String getEndDateString() {
        return this.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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
