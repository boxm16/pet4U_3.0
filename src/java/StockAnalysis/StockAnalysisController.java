package StockAnalysis;

import org.springframework.stereotype.Controller;

@Controller
public class StockAnalysisController {

    public StockAnalysis getStock(String itemCode) {

        StockAnalysisDao stockDao = new StockAnalysisDao();
        StockAnalysis stock = stockDao.getStock(itemCode);
        return stock;
    }
}
