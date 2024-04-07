/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotAnalytica;

import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import CamelotItemsOfInterest.ItemSnapshot;
import CamelotSearch.CamelotSearchDao;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CA {
    
    @RequestMapping(value = "/mmmm", method = RequestMethod.GET)
    public String mmmm(@RequestParam(name = "code") String code, ModelMap model) {
        System.out.println("+++++++++++++++");

        return "camelotAnalitica/camelotItemAnalysis";
    }
    

    @RequestMapping(value = "/camelotItemAnalysis", method = RequestMethod.GET)
    public String camelotItemAnalysis(@RequestParam(name = "code") String code, ModelMap model) {
        System.out.println("-------------------------");
        CamelotSearchDao searchDao = new CamelotSearchDao();
        Item item = searchDao.getItemByAltercode(code);
        model.addAttribute("item", item);

        /*
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

         */
        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();

        System.out.println("ITEMCODE FOR CAMELOT: " + item.getCode());
        ArrayList<ItemSnapshot> camelotItemSnapshots = camelotItemsOfInterestDao.getItemSnapshots(item.getCode());
        System.out.println("size:" + camelotItemSnapshots.size());
        model.addAttribute("camelotItemSnapshots", camelotItemSnapshots);

        return "camelotAnalitica/camelotItemAnalysis";
    }
    //------------------------------------------
    
}
