package StockAnalysis;

import BasicModel.Item;
import Search.SearchDao;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/showItemTotalStockSnapshots", method = RequestMethod.GET)
    public String itemAnalysis(@RequestParam(name = "item_code") String item_code, ModelMap model) {

        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(item_code);
        model.addAttribute("item", item);
        String itemCode = item.getCode();

        StockAnalysisDao stockDao = new StockAnalysisDao();
        StockAnalysis stockAnalysis = stockDao.getStock(itemCode);
        model.addAttribute("stockAnalysis", stockAnalysis);

        return "analitica/itemTotalStockAnalysis";
    }
}
