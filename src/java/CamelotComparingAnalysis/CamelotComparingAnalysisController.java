/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotComparingAnalysis;

import CamelotSales.CamelotSalesDao;
import SalesX.SoldItem;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotComparingAnalysisController {

    @RequestMapping(value = "camelotComparingAnalysis")
    public String camelotAllItems(ModelMap modelMap) {
        CamelotSalesDao monthSalesDao = new CamelotSalesDao();
        LinkedHashMap<String, SoldItem> camelotItemsForSales = monthSalesDao.getCamelotItemsForSales();

        CamelotComparingAnalysisDao dao = new CamelotComparingAnalysisDao();
        LinkedHashMap<String, SoldItem> camelotSales = dao.getSales(camelotItemsForSales);
        System.out.println("Database Part Done");
        return "camelotComparingAnalysis/comparing";
    }
}
