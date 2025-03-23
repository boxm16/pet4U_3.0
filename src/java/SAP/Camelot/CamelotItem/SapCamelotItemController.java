/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotItem;

import SAP.SapBasicModel.SapItem;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SapCamelotItemController {

    @RequestMapping(value = "goForCamelotItemsDashboard")
    public String goForCamelotItemsDashboard(ModelMap modelMap) {

        SapCamelotItemDao itemDao = new SapCamelotItemDao();
        LinkedHashMap<String, SapItem> items = itemDao.getAllItemsFromView();
        modelMap.addAttribute("items", items);
        return "sap/camelot/item/sapCamelotItemsDashboard";
    }

    @RequestMapping(value = "goForCamelotItemDashboard")
    public String goForCamelotItemDashboard(@RequestParam(name = "itemCode") String itemCode, ModelMap modelMap) {

        SapCamelotItemDao itemDao = new SapCamelotItemDao();
        //  Item item = itemDao.getItemByItemCode(itemCode);
        SapItem item = itemDao.getSapItemByItemCode(itemCode);
        LinkedHashMap<Short, SapUnitOfMeasurementGroup> allUnitOfMeasurementGroups = getAllUnitOfMeasurementGroups();
        modelMap.addAttribute("allUnitOfMeasurementGroups", allUnitOfMeasurementGroups);
        modelMap.addAttribute("target", itemCode);
        modelMap.addAttribute("item", item);

        return "sap/camelot/item/sapCamelotItemDashboard";
    }

    private LinkedHashMap<Short, SapUnitOfMeasurementGroup> getAllUnitOfMeasurementGroups() {
        SapCamelotItemDao itemDao = new SapCamelotItemDao();
        LinkedHashMap<Short, SapUnitOfMeasurementGroup> allUnitOfMeasurementGroups = itemDao.getAllUnitOfMeasurementGroups();
        return allUnitOfMeasurementGroups;
    }

    @RequestMapping(value = "goForCreationNewSapCamelotItem")
    public String goForCreationNewCamelotItem(ModelMap modelMap) {
        SapItem item = new SapItem();
        modelMap.addAttribute("item", item);
        return "sap/camelot/item/newSapCamelotItemCreationServant";
    }

    @RequestMapping(value = "createNewSapCamelotItem", method = RequestMethod.POST)
    public String createNewSapCamelotItem(@ModelAttribute("item") SapItem item, ModelMap modelMap) {

        System.out.println("Created Item: " + item);

        modelMap.addAttribute("item", item);

        return "redirect:goForCamelotItemsDashboard.htm";
    }
}
