package StockAnalysis;

import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StockAnalysisController {

    public StockAnalysis getStock(String itemCode) {
        StockAnalysisDao stockDao = new StockAnalysisDao();
        StockAnalysis stock = stockDao.getStock(itemCode);
        return stock;
    }

    @RequestMapping(value = "stockinger")
    public String addStockSnapshot() {
        HashMap<String, StockAnalysis> totalStock = getTotalStock();
        StockAnalysisDao stockDao = new StockAnalysisDao();
        String result = stockDao.insertPet4uTotalStockSnapshot(totalStock);
        System.out.println("TOTAL STOCK Insertion Result:" + result);
        return "index";
    }

    public HashMap<String, StockAnalysis> getTotalStock() {
        StockAnalysisDao stockDao = new StockAnalysisDao();
        HashMap<String, StockAnalysis> stock = stockDao.getTotalStock();
        return stock;
    }
}
