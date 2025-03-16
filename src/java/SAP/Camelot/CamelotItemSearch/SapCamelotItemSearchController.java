/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotItemSearch;

import BasicModel.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SapCamelotItemSearchController {

    @RequestMapping(value = "sapCamelotSearchDashboard")
    public String sapCamelotSearchDashboard() {

        return "sap/camelot/search/sapCamelotSearchDashboard";
    }

    @RequestMapping(value = "findCamelotItemByAltercodeFromSapView")
    public String findCamelotItemByAltercodeFromSapView(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        SapCamelotItemSearchDao itemDao = new SapCamelotItemSearchDao();
        Item item = itemDao.getItemByAltercode(altercode);
        modelMap.addAttribute("target", altercode);
        modelMap.addAttribute("item", item);
        return "sap/camelot/search/sapCamelotSingleItemSearchResult";
    }
}
