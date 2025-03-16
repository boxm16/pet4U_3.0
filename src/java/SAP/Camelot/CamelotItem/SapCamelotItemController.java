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
     @RequestMapping(value = "goForCamelotItemMasterDataDashboard")
    public String goForCamelotItemMasterDataDashboard(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
       
        modelMap.addAttribute("target", altercode);

        return "sap/camelot/item/sapCamelotItemDashboard";
    }
}
