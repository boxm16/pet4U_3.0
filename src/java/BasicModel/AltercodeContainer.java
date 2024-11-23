package BasicModel;

public class AltercodeContainer {

    private String altercode;
    private String status;
    private boolean mainBarcode;
    private boolean packageBarcode;
    private double itemsInPackage;

    public String getAltercode() {
        return altercode;
    }

    public void setAltercode(String altercode) {
        this.altercode = altercode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isMainBarcode() {
        return mainBarcode;
    }

    public void setMainBarcode(boolean mainBarcode) {
        this.mainBarcode = mainBarcode;
    }

    public boolean isPackageBarcode() {
        return packageBarcode;
    }

    public void setPackageBarcode(boolean packageBarcode) {
        this.packageBarcode = packageBarcode;
    }

    public double getItemsInPackage() {
        return itemsInPackage;
    }

    public void setItemsInPackage(double itemsInPackage) {
        this.itemsInPackage = itemsInPackage;
    }

}
