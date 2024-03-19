package Analitica;

import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import CamelotItemsOfInterest.ItemSnapshot;
import MonthSales.MonthSales;
import MonthSales.MonthSalesDao;
import MonthSales.Sales;
import Offer.Offer;
import Offer.OfferDao;
import Pet4uItems.Pet4uItemsDao;
import Search.SearchDao;
import StockAnalysis.StockAnalysis;
import StockAnalysis.StockAnalysisDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        String itemCode = item.getCode();
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        ArrayList< ItemSnapshot> itemSnapshots = pet4uItemsDao.getItemSnapshots(itemCode);
        model.addAttribute("itemSnapshots", itemSnapshots);

        MonthSalesDao monthSalesDao = new MonthSalesDao();
        ArrayList<String> period = monthSalesDao.getSalesPeriod();
        MonthSales itemSales = monthSalesDao.getItemSales(itemCode);

        for (String p : period) {
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate salePeriod = LocalDate.parse(p, formatter2);
            if (itemSales.getSales().containsKey(salePeriod)) {
            } else {
                MonthSales ms = new MonthSales();

                Sales sales = new Sales();
                sales.setEshopSales(0);
                sales.setShopsSupply(0);
                ms.addSales(salePeriod, sales);
                itemSales.addSales(salePeriod, sales);

            }
        }
        model.addAttribute("itemSales", itemSales);

        OfferDao offerDao = new OfferDao();
        ArrayList<Offer> offers = offerDao.getOffers(itemCode);
        model.addAttribute("offers", offers);

        StockAnalysisDao stockDao = new StockAnalysisDao();
        StockAnalysis stockAnalysis = stockDao.getStock(itemCode);
        model.addAttribute("stockAnalysis", stockAnalysis);

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        if (itemCode.contains(".-WE")) {
            itemCode = itemCode.replace(".-WE", "");
        }
        if (itemCode.contains("-WE")) {
            itemCode = itemCode.replace("-WE", "");
        }

        System.out.println("ITEMCODE FOR CAMELTO: " + itemCode);
        ArrayList<ItemSnapshot> camelotItemSnapshots = camelotItemsOfInterestDao.getItemSnapshots(itemCode);
        model.addAttribute("camelotItemSnapshots", camelotItemSnapshots);

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
