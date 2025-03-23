/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotItem;

import SAP.SapBasicModel.SapItem;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import SAP.SapCamelotApiConnector;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
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

        SapCamelotItemDao sapCamelotItemDao = new SapCamelotItemDao();
        LinkedHashMap<Integer, String> itemGroups = sapCamelotItemDao.getAllItemsGroups();
        modelMap.addAttribute("itemGroups", itemGroups);
        return "sap/camelot/item/newSapCamelotItemCreationServant";
    }

    @RequestMapping(value = "createNewSapCamelotItem", method = RequestMethod.POST)
    public String createNewSapCamelotItem(@ModelAttribute("item") SapItem item, ModelMap modelMap) {

        try {
            SapCamelotApiConnector sapCamelotApiConnector = new SapCamelotApiConnector();
            String endPoint = "/Items"; // Endpoint for creating items
            String requestMethod = "POST";

            HttpURLConnection conn = sapCamelotApiConnector.createConnection(endPoint, requestMethod);

            // Minimal JSON Payload for Item Creation
            JSONObject payload = new JSONObject();
            payload.put("ItemCode", "ITEM-001"); // Unique Item Code (mandatory)
            payload.put("ItemName", "New Product"); // Item Name (mandatory)
            payload.put("ItemsGroupCode", 100); // Item Group Code (mandatory)
            payload.put("InventoryItem", "tYES"); // Inventory Item (mandatory)

            // Send the request
            sapCamelotApiConnector.sendRequestBody(conn, payload.toString());

            // Check the response
            int responseCode = conn.getResponseCode();
            if (responseCode == 201) {
                JSONObject jsonResponse = sapCamelotApiConnector.getJsonResponse(conn);
                System.out.println("✅ Item Created Successfully!");
                System.out.println("Item Details: " + jsonResponse.toString());
                modelMap.addAttribute("message", "Item Created Successfully. Item Details: " + jsonResponse.toString());
            } else {
                String errorResponse = sapCamelotApiConnector.getErrorResponse(conn);
                System.out.println("❌ Error Creating Item: " + errorResponse);
                modelMap.addAttribute("message", "Error Creating Item: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotItemController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "redirect:goForCamelotItemsDashboard.htm";
    }
}
