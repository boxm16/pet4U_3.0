/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camelot.CamelotItemSearch;

import BasicModel.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotItemSearchController {

    @Autowired
    private CamelotItemSearchDao camelotItemSearchDao;

    @RequestMapping(value = "getCamelotItemFromSapHanaView")
    public String findCamelotItemByAltercode(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        Item item = camelotItemSearchDao.getItemByAltercode(altercode);
        modelMap.addAttribute("target", altercode);
        modelMap.addAttribute("item", item);
        if (item == null) {
            return "camelotItemSearch/searchErrorPage";
        }
        return "camelotItemSearch/singleItemSearchResult";
    }
 
}
