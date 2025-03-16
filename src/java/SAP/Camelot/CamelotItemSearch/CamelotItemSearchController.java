/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotItemSearch;

import BasicModel.Item;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class CamelotItemSearchController {

    @RequestMapping(value = "sapCamelotSearchDashboard")
    public String sapCamelotSearchDashboard() {

        return "sap/search/sapCamelotSearchDashboard";
    }

    @RequestMapping(value = "findCamelotItemByAltercodeFromSapHanaTable")
    public String findCamelotItemByAltercodeFromSapHanaTable(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        CamelotItemSearchDao itemDao = new CamelotItemSearchDao();
        Item item = itemDao.getItemByAltercode(altercode);
        modelMap.addAttribute("target", altercode);
        modelMap.addAttribute("item", item);
        return "sap/search/sapCamelotSingleItemSearchResult";
    }
}
