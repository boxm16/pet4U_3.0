package StockAnalysis;

import java.util.HashMap;
import org.springframework.stereotype.Controller;

@Controller
public class StockAnalysisController {

    public StockAnalysis getStock(String itemCode) {
        StockAnalysisDao stockDao = new StockAnalysisDao();
        StockAnalysis stock = stockDao.getStock(itemCode);
        return stock;
    }

    public void addStockSnapshot() {
        HashMap<String, StockAnalysis> totalStock = getTotalStock();
        System.out.println("TOTAL STOCK" + totalStock.size());
    }

    public HashMap<String, StockAnalysis> getTotalStock() {
        StockAnalysisDao stockDao = new StockAnalysisDao();
        HashMap<String, StockAnalysis> stock = stockDao.getTotalStock();
        return stock;
    }
}
