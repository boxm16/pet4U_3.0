/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pet4uSales;

import SalesX.SoldItem;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Pet4uSalesController {

    @RequestMapping(value = "goForPet4uMonthSalesUpload")
    public String goForCamelotMonthSalesUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "Pet4u Last Month Upload");
        model.addAttribute("uploadTarget", "pet4uMonthSalesUpload.htm");
        return "monthSales/monthSalesUpload";
    }
    
     @RequestMapping(value = "pet4uMonthSalesUpload")
    public String camelotMonthSalesUpload(@RequestParam String date, ModelMap model) {
        Pet4uSalesDao pet4uSalesDao = new Pet4uSalesDao();

        LinkedHashMap<String, SoldItem> pet4uAllItemsForSales = pet4uSalesDao.getPet4uItemsForSales();

        LinkedHashMap<String, SoldItem> sodlItems = pet4uSalesDao.getMonthSalesFromMicrosoftDB(date, pet4uAllItemsForSales);
        String result = pet4uSalesDao.insertNewUpload(date, sodlItems);
        return "monthSales/monthSalesUpload";
    }
}
