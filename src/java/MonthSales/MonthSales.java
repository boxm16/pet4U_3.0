package MonthSales;

import BasicModel.Item;
import java.time.LocalDate;
import java.util.TreeMap;

public class MonthSales extends Item {

    private TreeMap<LocalDate, Sales> sales;

    public MonthSales() {
        this.sales = new TreeMap<>();
    }
    
    

    public TreeMap<LocalDate, Sales> getSales() {
        return sales;
    }

    public void setSales(TreeMap<LocalDate, Sales> sales) {
        this.sales = sales;
    }

    public void addSales(LocalDate date, Sales sales) {
        this.sales.put(date, sales);
    }
}
