package Analitica;

import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import CamelotItemsOfInterest.ItemSnapshot;
import CamelotSales.CamelotSalesDao;
import CamelotSearch.CamelotSearchDao;
import DailySales.DailySale;
import DailySales.DailySalesDao;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnaliticaController {

    @RequestMapping(value = "/itemAnalysis", method = RequestMethod.GET)
    public String itemAnalysis(HttpSession session, @RequestParam String code, ModelMap model) {
        System.out.println("Starting analysis: " + LocalDateTime.now());
        String userName = (String) session.getAttribute("userName");

        if (userName == null) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }

        if (userName.equals("me") || userName.equals("vasilis")) {

            SearchDao searchDao = new SearchDao();
            Item item = searchDao.getItemByAltercode(code);

            if (item == null) {
                model.addAttribute("code", code);
                model.addAttribute("message", "No such code in Pet4u Database");

                return "analitica/itemAnalysisErrorPage";
            }
            model.addAttribute("item", item);
            //   System.out.println("Retrieving Item. Done: " + LocalDateTime.now());

            String itemCode = item.getCode();
            Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
            //  ArrayList< ItemSnapshot> itemSnapshots = pet4uItemsDao.getItemSnapshots(itemCode);
            //   model.addAttribute("itemSnapshots", itemSnapshots);

            LinkedHashMap<LocalDate, ItemSnapshot> last100DaysSnapshots = pet4uItemsDao.getLast100DaysSnapshots(item.getCode());
            model.addAttribute("last100DaysSnapshots", last100DaysSnapshots);
            // System.out.println("Retrieving Last 100 Days Snapshot. Done: " + LocalDateTime.now());

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
            //  System.out.println("Retrieving Item Sales. Done: " + LocalDateTime.now());

            //   LinkedHashMap<String, Double> daysSales = monthSalesDao.getLast30DaysSales(item.getCode());
            // model.addAttribute("daysSales", daysSales);
            DailySalesDao dailySalesDao = new DailySalesDao();
            LinkedHashMap<LocalDate, DailySale> dailySales = dailySalesDao.getLast30DaysSales(item.getCode());
            model.addAttribute("dailySales", dailySales);
            //   System.out.println("Retrieving Daily Sales. Done: " + LocalDateTime.now());

            OfferDao offerDao = new OfferDao();
            ArrayList<Offer> offers = offerDao.getOffers(itemCode);
            model.addAttribute("offers", offers);
            // System.out.println("Retrieving Offers. Done: " + LocalDateTime.now());

            StockAnalysisDao stockDao = new StockAnalysisDao();
            StockAnalysis stockAnalysis = stockDao.getStock(itemCode);
            model.addAttribute("stockAnalysis", stockAnalysis);
            //  System.out.println("Retrieving Stock Analysis. Done: " + LocalDateTime.now());

            CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
            if (itemCode.contains(".-WE")) {
                itemCode = itemCode.replace(".-WE", "");
            }
            if (itemCode.contains("-WE")) {
                itemCode = itemCode.replace("-WE", "");
            }

            LinkedHashMap<LocalDate, ItemSnapshot> camelotLast100DaysSnapshots = camelotItemsOfInterestDao.getLast100DaysSnapshots(itemCode);
            System.out.println("size:" + camelotLast100DaysSnapshots.size());
            model.addAttribute("camelotLast100DaysSnapshots", camelotLast100DaysSnapshots);
            //    System.out.println("Retrieving Camelot Last 100 Days Snapshots. Done: " + LocalDateTime.now());

            System.out.println("Analysis Done: " + LocalDateTime.now());

            LocalDate date = LocalDate.now();
         //   LocalDate startDate = LocalDate.parse("2024-01-01");
            LocalDate nowDate = LocalDate.now();
            model.addAttribute("date", date);
           // model.addAttribute("startDate", startDate);
            model.addAttribute("nowDate", nowDate);
            model.addAttribute("before10DaysDate", nowDate.minusDays(10));
            return "analitica/itemAnalysis";
        }
        model.addAttribute("message", "You are not authorized for this page");
        return "errorPage";
    }

    @RequestMapping(value = "/offerDashboard", method = RequestMethod.GET)
    public String offerDashboard(@RequestParam String code, ModelMap model) {

        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(code);
        model.addAttribute("item", item);

        return "analitica/offerDashboard";
    }

    //----------------++++++++++++++++++++_________________________
    @RequestMapping(value = "/camelotItemAnalysis", method = RequestMethod.GET)
    public String camelotItemAnalysis(HttpSession session, @RequestParam(name = "code") String code, ModelMap model) {
        System.out.println("-------------------------");
        System.out.println("Camelot Item Analisys");
        String userName = (String) session.getAttribute("userName");

        if (userName == null) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }
        if (!userName.equals("me")) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }

        CamelotSearchDao searchDao = new CamelotSearchDao();
        Item item = searchDao.getItemByAltercode(code);
        model.addAttribute("item", item);

        CamelotSalesDao camelotSalesDao = new CamelotSalesDao();

        LinkedHashMap<String, Double> daysSales = camelotSalesDao.getLast30DaysSales(item.getCode());
        model.addAttribute("daysSales", daysSales);

        ArrayList<String> period = camelotSalesDao.getSalesPeriod();
        MonthSales itemSales = camelotSalesDao.getItemSales(item.getCode());

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

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();

        System.out.println("ITEMCODE FOR CAMELOT: " + item.getCode());
        LinkedHashMap<LocalDate, ItemSnapshot> camelotLast100DaysSnapshots = camelotItemsOfInterestDao.getLast100DaysSnapshots(item.getCode());
        System.out.println("size:" + camelotLast100DaysSnapshots.size());
        model.addAttribute("camelotLast100DaysSnapshots", camelotLast100DaysSnapshots);

        return "camelotAnalitica/camelotItemAnalysis";
    }
    //------------------------------------------

    @RequestMapping(value = "/getFullItemAnalysis", method = RequestMethod.GET)
    public String getFullItemAnalysis(HttpSession session, @RequestParam String code, ModelMap model) {
        System.out.println("Starting analysis: " + LocalDateTime.now());
        String userName = (String) session.getAttribute("userName");

        if (userName == null) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }
        if (!userName.equals("me")) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }

        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(code);

        if (item == null) {
            model.addAttribute("code", code);
            model.addAttribute("message", "No such code in Pet4u Database");

            return "analitica/itemAnalysisErrorPage";
        }
        model.addAttribute("item", item);
        //   System.out.println("Retrieving Item. Done: " + LocalDateTime.now());

        String itemCode = item.getCode();

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
        //  System.out.println("Retrieving Item Sales. Done: " + LocalDateTime.now());

        //   LinkedHashMap<String, Double> daysSales = monthSalesDao.getLast30DaysSales(item.getCode());
        // model.addAttribute("daysSales", daysSales);
        DailySalesDao dailySalesDao = new DailySalesDao();
        LinkedHashMap<LocalDate, DailySale> dailySales = dailySalesDao.getLast30DaysSales(item.getCode());
        model.addAttribute("dailySales", dailySales);
        //   System.out.println("Retrieving Daily Sales. Done: " + LocalDateTime.now());

        OfferDao offerDao = new OfferDao();
        ArrayList<Offer> offers = offerDao.getOffers(itemCode);
        model.addAttribute("offers", offers);
        // System.out.println("Retrieving Offers. Done: " + LocalDateTime.now());

        StockAnalysisDao stockDao = new StockAnalysisDao();
        StockAnalysis stockAnalysis = stockDao.getStock(itemCode);
        model.addAttribute("stockAnalysis", stockAnalysis);
        //  System.out.println("Retrieving Stock Analysis. Done: " + LocalDateTime.now());

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        //  ArrayList< ItemSnapshot> itemSnapshots = pet4uItemsDao.getItemSnapshots(itemCode);
        //   model.addAttribute("itemSnapshots", itemSnapshots);

        LinkedHashMap<LocalDate, ItemSnapshot> last100DaysSnapshots = pet4uItemsDao.getItemSnapshotsFullVersion(item.getCode());
        model.addAttribute("last100DaysSnapshots", last100DaysSnapshots);// its not for 100 days, its for full version
        // System.out.println("Retrieving Last 100 Days Snapshot. Done: " + LocalDateTime.now());
//--------------------------------------------------------
        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        if (itemCode.contains(".-WE")) {
            itemCode = itemCode.replace(".-WE", "");
        }
        if (itemCode.contains("-WE")) {
            itemCode = itemCode.replace("-WE", "");
        }

        LinkedHashMap<LocalDate, ItemSnapshot> camelotLast100DaysSnapshots = camelotItemsOfInterestDao.getCamelotItemSnapshotsFullVersion(itemCode);
        model.addAttribute("camelotLast100DaysSnapshots", camelotLast100DaysSnapshots);

        System.out.println("Analysis Done: " + LocalDateTime.now());

        return "analitica/itemAnalysis";
    }

}
