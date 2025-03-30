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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SapCamelotItemController {

    @RequestMapping(value = "camelotItemsDashboard")
    public String camelotItemsDashboard(ModelMap modelMap) {

        SapCamelotItemDao itemDao = new SapCamelotItemDao();
        LinkedHashMap<String, SapItem> items = itemDao.getAllItemsFromView();
        modelMap.addAttribute("items", items);
        return "sap/camelot/item/sapCamelotItemsDashboard";
    }

    @RequestMapping(value = "camelotItemDashboard")
    public String camelotItemDashboard(@RequestParam(name = "itemCode") String itemCode, ModelMap modelMap) {
        modelMap.addAttribute("target", itemCode);
        SapCamelotItemDao itemDao = new SapCamelotItemDao();
        //  Item item = itemDao.getItemByItemCode(itemCode);
        SapItem item = itemDao.getSapItemByItemCode(itemCode);
        modelMap.addAttribute("item", item);

        LinkedHashMap<Short, SapUnitOfMeasurementGroup> allUnitOfMeasurementGroups = itemDao.getAllUnitOfMeasurementGroups();
        modelMap.addAttribute("allUnitOfMeasurementGroups", allUnitOfMeasurementGroups);

        //  LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = itemDao.getAllUnitsOfMeasurement();
        //modelMap.addAttribute("allUnitsOfMeasurement", allUnitsOfMeasurement);
        return "sap/camelot/item/sapCamelotItemDashboard";
    }

    @RequestMapping(value = "newSapCamelotItemCreationServant")
    public String newSapCamelotItemCreationServant(ModelMap modelMap) {
        SapItem item = new SapItem();
        modelMap.addAttribute("item", item);

        SapCamelotItemDao sapCamelotItemDao = new SapCamelotItemDao();
        LinkedHashMap<Integer, String> itemGroups = sapCamelotItemDao.getAllItemsGroups();
        modelMap.addAttribute("itemGroups", itemGroups);
        return "sap/camelot/item/newSapCamelotItemCreationServant";
    }

    @RequestMapping(value = "createNewSapCamelotItem", method = RequestMethod.POST)
    public String createNewSapCamelotItem(@ModelAttribute("item") SapItem item, RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector sapCamelotApiConnector = new SapCamelotApiConnector();
            String endPoint = "/Items"; // Endpoint for creating items
            String requestMethod = "POST";

            HttpURLConnection conn = sapCamelotApiConnector.createConnection(endPoint, requestMethod);

            // Minimal JSON Payload for Item Creation
            JSONObject payload = new JSONObject();
            payload.put("ItemCode", item.getCode()); // Unique Item Code (mandatory)
            payload.put("ItemName", item.getDescription()); // Item Name (mandatory)
            payload.put("ItemsGroupCode", item.getItemsGroupCode()); // Item Group Code (mandatory)
            payload.put("InventoryItem", "tYES"); // Inventory Item (mandatory)
            if (item.getItemType().equals("food")) {//It is mandatory an item to be food or accessory
                payload.put("Properties7", "tYES");
            } else if (item.getItemType().equals("accessory")) {
                payload.put("Properties8", "tYES");
            } else {
                System.out.println("❌ Error Creating Item:Properties7 or Properties8 is not selected");
                redirectAttributes.addFlashAttribute("message", "Error Creating Item:Properties7 or Properties8 is not selected");
                return "redirect:newSapCamelotItemCreationServant.htm";
            }
            // Send the request
            sapCamelotApiConnector.sendRequestBody(conn, payload.toString());

            // Check the response
            int responseCode = conn.getResponseCode();
            if (responseCode == 201) {
                JSONObject jsonResponse = sapCamelotApiConnector.getJsonResponse(conn);
                System.out.println("✅ Item Created Successfully!");
                System.out.println("Item Details: " + jsonResponse.toString());
                redirectAttributes.addFlashAttribute("message", "Item Created Successfully.");
            } else {
                String errorResponse = sapCamelotApiConnector.getErrorResponse(conn);
                System.out.println("❌ Error Creating Item: " + errorResponse);
                redirectAttributes.addFlashAttribute("message", "Error Creating Item: " + errorResponse);
                return "redirect:newSapCamelotItemCreationServant.htm";
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotItemController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
            return "redirect:newSapCamelotItemCreationServant.htm";
        } catch (Exception ex) {
            // Log the exception and continue
            Logger.getLogger(SapCamelotItemController.class.getName()).log(Level.SEVERE, "Exception occurred, but item was created successfully.", ex);
            redirectAttributes.addFlashAttribute("message", "Item Created Successfully, but an error occurred while processing the response.");
        }

        return "redirect:camelotItemsDashboard.htm";
    }

    @RequestMapping(value = "sapCamelotItemUpdateServant", method = RequestMethod.GET)
    public String sapCamelotItemUpdateServant(@ModelAttribute("itemCode") String itemCode, ModelMap modelMap) {
        SapCamelotItemDao itemDao = new SapCamelotItemDao();
        //  Item item = itemDao.getItemByItemCode(itemCode);
        SapItem item = itemDao.getSapItemByItemCode(itemCode);
        modelMap.addAttribute("item", item);

        LinkedHashMap<Integer, String> itemGroups = itemDao.getAllItemsGroups();
        modelMap.addAttribute("itemGroups", itemGroups);
        return "sap/camelot/item/sapCamelotItemUpdateServant";
    }

    @RequestMapping(value = "updateSapCamelotItem", method = RequestMethod.POST)
    public String updateSapCamelotItem(@ModelAttribute("item") SapItem item, RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector sapCamelotApiConnector = new SapCamelotApiConnector();
            String endPoint = "/Items('" + item.getCode() + "')"; // Endpoint for specific item
            String requestMethod = "PATCH"; // or "PUT" depending on SAP API requirements

            HttpURLConnection conn = sapCamelotApiConnector.createConnection(endPoint, requestMethod);

            // JSON Payload for Item Update
            JSONObject payload = new JSONObject();

            // Only include fields that need to be updated
            if (item.getDescription() != null) {
                payload.put("ItemName", item.getDescription());
            }

            if (item.getItemsGroupCode() != null) {
                payload.put("ItemsGroupCode", item.getItemsGroupCode());
            }

            // Update item type if needed
            if (item.getItemType() != null) {
                // First clear existing values if needed (depends on SAP API behavior)
                payload.put("Properties7", "tNO");
                payload.put("Properties8", "tNO");

                if (item.getItemType().equals("food")) {
                    payload.put("Properties7", "tYES");
                } else if (item.getItemType().equals("accessory")) {
                    payload.put("Properties8", "tYES");
                } else {
                    System.out.println("❌ Error Updating Item: Invalid item type");
                    redirectAttributes.addFlashAttribute("alertColor", "red");
                    redirectAttributes.addFlashAttribute("message", "Error Updating Item: Invalid item type");
                    return "redirect:editSapCamelotItem.htm?itemCode=" + item.getCode();
                }
            }

            // Send the request
            sapCamelotApiConnector.sendRequestBody(conn, payload.toString());

            // Check the response
            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) { // Typically 200 for PUT, 204 for PATCH
                System.out.println("✅ Item Updated Successfully!");
                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "Item Updated Successfully.");
            } else {
                String errorResponse = sapCamelotApiConnector.getErrorResponse(conn);
                System.out.println("❌ Error Updating Item: " + errorResponse);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "Error Updating Item: " + errorResponse);
                return "redirect:editSapCamelotItem.htm?itemCode=" + item.getCode();
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotItemController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
            return "redirect:editSapCamelotItem.htm?itemCode=" + item.getCode();
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotItemController.class.getName()).log(Level.SEVERE, "Exception occurred during item update.", ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "Item may have been updated, but an error occurred while processing the response.");
        }

        return "redirect:editSapCamelotItem.htm?itemCode=" + item.getCode();
    }

}
