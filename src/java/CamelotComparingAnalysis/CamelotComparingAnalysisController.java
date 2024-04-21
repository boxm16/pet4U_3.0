/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotComparingAnalysis;

import Service.Basement;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotComparingAnalysisController {

    @RequestMapping(value = "camelotComparingAnalysis")
    public String camelotAllItems(ModelMap modelMap) {

        CamelotComparingAnalysisDao dao = new CamelotComparingAnalysisDao();

        LinkedHashMap<String, SoldItem3> camelotSoldItems = dao.getCamelotItemsForSales();

        LinkedHashMap<String, SoldItem3> camelotSoldItems1 = dao.getTotalSales(camelotSoldItems);
        Basement basement = new Basement();
        String filePath = basement.getBasementDirectory() + "/Pet4U_Uploads/SALES_PETCAMELOT_PET4U.xlsx";

        Factory factory = new Factory();
        LinkedHashMap<String, SoldItem3> camelotSoldItemsArray = factory.createSoldItemsFromUploadedFile(filePath, camelotSoldItems1);
        modelMap.addAttribute("camelotSoldItemsArray", camelotSoldItemsArray);
        System.out.println("Done");
        return "camelotComparingAnalysis/comparing";
    }
}
