package Analitica;

import BasicModel.Item;
import MonthSales.MonthSales;
import MonthSales.MonthSalesDao;
import Offer.Offer;
import Offer.OfferDao;
import Pet4uItems.Pet4uItemsDao;
import Search.SearchDao;
import StockAnalysis.StockAnalysis;
import StockAnalysis.StockAnalysisDao;
import java.util.ArrayList;
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

        String itemCode=item.getCode();
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> itemSnapshots = pet4uItemsDao.getItemSnapshots(itemCode);
        model.addAttribute("itemSnapshots", itemSnapshots);

        MonthSalesDao monthSalesDao = new MonthSalesDao();
        MonthSales itemSales = monthSalesDao.getItemSales(itemCode);
        model.addAttribute("itemSales", itemSales);

        OfferDao offerDao = new OfferDao();
        ArrayList<Offer> offers = offerDao.getOffers(itemCode);
        model.addAttribute("offers", offers);

        StockAnalysisDao stockDao = new StockAnalysisDao();
        StockAnalysis stockAnalysis = stockDao.getStock(itemCode);
        model.addAttribute("stockAnalysis", stockAnalysis);

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
