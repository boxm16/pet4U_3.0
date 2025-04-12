package Delivery;

import java.util.LinkedHashMap;

public class DeliveryInvoice {

    private String invoiceId;
    private String id;
    private String number;
    private String insertionDate;
    private LinkedHashMap<String, DeliveryItem> items;
    private String errorMessages;
    private String supplier;
    private String warehouseCode;
    private String baseEntry;

    public DeliveryInvoice() {
        this.errorMessages = "";
        this.items = new LinkedHashMap<>();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(String insertionDate) {
        this.insertionDate = insertionDate;
    }

    public LinkedHashMap<String, DeliveryItem> getItems() {
        return items;
    }

    public void setItems(LinkedHashMap<String, DeliveryItem> items) {
        this.items = items;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

}
