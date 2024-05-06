/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotComparingAnalysis;

import CamelotSales.CamelotSalesController;
import SalesX.SoldItem;
import Service.Basement;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotComparingAnalysisController {

    @RequestMapping(value = "camelotComparingAnalysis")
    public String camelotAllItems(ModelMap modelMap) {

        CamelotComparingAnalysisDao dao = new CamelotComparingAnalysisDao();

        LinkedHashMap<String, SoldItem3> camelotSoldItems = dao.getCamelotItemsForSales();

        CamelotSalesController camelotSalesController = new CamelotSalesController();
        LinkedHashMap<String, SoldItem> camelotSixMonthSales = camelotSalesController.getCamelotSixMonthSales();
        for (Map.Entry<String, SoldItem3> camelotSoldItemsEntry : camelotSoldItems.entrySet()) {
            String key = camelotSoldItemsEntry.getKey();
            SoldItem sms = camelotSixMonthSales.get(key);
            camelotSoldItemsEntry.getValue().setSixMonthsSales(sms.getEshopSales());
        }

        LinkedHashMap<String, SoldItem3> camelotSoldItems1 = dao.getTotalSalesForComparingAnalysisPeriod(camelotSoldItems);
        Basement basement = new Basement();
        String filePath = basement.getBasementDirectory() + "/Pet4U_Uploads/SALES_PETCAMELOT_PET4U.xlsx";

        Factory factory = new Factory();
        LinkedHashMap<String, SoldItem3> camelotSoldItemsArray = factory.createSoldItemsFromUploadedFile(filePath, camelotSoldItems1);
        modelMap.addAttribute("camelotSoldItemsArray", camelotSoldItemsArray);
        System.out.println("Done");
        return "camelotComparingAnalysis/comparing";
    }
    
     @RequestMapping(value = "camelotComparingAnalysisItemsWithStock")
    public String camelotComparingAnalysisItemsWithStock(ModelMap modelMap) {

        CamelotComparingAnalysisDao dao = new CamelotComparingAnalysisDao();

        LinkedHashMap<String, SoldItem3> camelotSoldItems = dao.getCamelotItemsForSales();

        CamelotSalesController camelotSalesController = new CamelotSalesController();
        LinkedHashMap<String, SoldItem> camelotSixMonthSales = camelotSalesController.getCamelotSixMonthSales();
        for (Map.Entry<String, SoldItem3> camelotSoldItemsEntry : camelotSoldItems.entrySet()) {
            String key = camelotSoldItemsEntry.getKey();
            SoldItem sms = camelotSixMonthSales.get(key);
            camelotSoldItemsEntry.getValue().setSixMonthsSales(sms.getEshopSales());
        }

        LinkedHashMap<String, SoldItem3> camelotSoldItems1 = dao.getTotalSalesForComparingAnalysisPeriod(camelotSoldItems);
        Basement basement = new Basement();
        String filePath = basement.getBasementDirectory() + "/Pet4U_Uploads/SALES_PETCAMELOT_PET4U.xlsx";

        Factory factory = new Factory();
        LinkedHashMap<String, SoldItem3> camelotSoldItemsArray = factory.createSoldItemsFromUploadedFile(filePath, camelotSoldItems1);
        modelMap.addAttribute("camelotSoldItemsArray", camelotSoldItemsArray);
        System.out.println("Done");
        return "camelotComparingAnalysis/comparingOnlyWithStock";
    }
}
