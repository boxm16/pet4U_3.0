/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotItem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SapCamelotItemController {
     @RequestMapping(value = "goForCamelotItemDashboard")
    public String goForCamelotItemDashboard(@RequestParam(name = "itemCode") String itemCode, ModelMap modelMap) {
       
        modelMap.addAttribute("target", itemCode);

        return "sap/camelot/item/sapCamelotItemDashboard";
    }
}
