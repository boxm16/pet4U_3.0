package Analitica;

import BasicModel.Item;
import MonthSales.ItemSales;
import MonthSales.MonthSalesDao;
import Pet4uItems.Pet4uItemsDao;
import Search.SearchDao;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnaliticaController {

    @RequestMapping(value = "/itemAnalysis", method = RequestMethod.GET)
    public String itemAnalysis(@RequestParam String code, ModelMap model) {

        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(code);
        model.addAttribute("item", item);

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> itemSnapshots = pet4uItemsDao.getItemSnapshots(code);
        model.addAttribute("itemSnapshots", itemSnapshots);

        MonthSalesDao monthSalesDao = new MonthSalesDao();
        ItemSales itemSales = monthSalesDao.getItemSales(code);
        model.addAttribute("itemSales", itemSales);

        return "analitica/itemAnalysis";
    }

    @RequestMapping(value = "/offerDashboard", method = RequestMethod.GET)
    public String offerDashboard(@RequestParam String code, ModelMap model) {

        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(code);
        model.addAttribute("item", item);

        return "analitica/offerDashboard";
    }
}
