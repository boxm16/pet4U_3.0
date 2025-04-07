package SAP.Camelot.UnitOfMeasurement;

import SAP.SapBasicModel.SapUnitOfMeasurement;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import SAP.SapCamelotApiConnector;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SapCamelotUnitOfMeasurementControlle {

    @RequestMapping(value = "camelotUnitOfMeasurementDashboard")
    public String camelotUnitOfMeasurementDashboard(ModelMap modelMap) {
        SapCamelotUnitOfMeasurementDao sapCamelotUnitOfMeasurementDao = new SapCamelotUnitOfMeasurementDao();
        LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = sapCamelotUnitOfMeasurementDao.getAllUnitsOfMeasurement();
        modelMap.addAttribute("allUnitsOfMeasurement", allUnitsOfMeasurement);
        LinkedHashMap<Short, SapUnitOfMeasurementGroup> allUnitOfMeasurementGroups = sapCamelotUnitOfMeasurementDao.getAllUnitOfMeasurementGroups();
        modelMap.addAttribute("allUnitOfMeasurementGroups", allUnitOfMeasurementGroups);
        return "sap/camelot/unitOfMeasurement/camelotUnitOfMeasurementDashboard";
    }

    @RequestMapping(value = "camelotUnitOfMeasurementGroupEditServant", method = RequestMethod.GET)
    public String camelotUnitOfMeasurementGroupEditServant(@RequestParam("ugpEntry") String ugpEntry, ModelMap modelMap) {
        SapCamelotUnitOfMeasurementDao sapCamelotUnitOfMeasurementDao = new SapCamelotUnitOfMeasurementDao();
        LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = sapCamelotUnitOfMeasurementDao.getAllUnitsOfMeasurement();
        modelMap.addAttribute("allUnitsOfMeasurement", allUnitsOfMeasurement);

        SapUnitOfMeasurementGroup unitOfMeasurementGroup = sapCamelotUnitOfMeasurementDao.getUnitOfMeasurementGroup(ugpEntry);
        modelMap.addAttribute("unitOfMeasurementGroup", unitOfMeasurementGroup);

        return "sap/camelot/unitOfMeasurement/camelotUnitOfMeasurementGroupEditServant";
    }

    @RequestMapping(value = "updateUnitOfMeasurementGroup", method = RequestMethod.POST)
    public String updateUnitOfMeasurementGroup(
            @ModelAttribute("unitOfMeasurementGroup") SapUnitOfMeasurementGroup group,
            RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector sapCamelotApiConnector = new SapCamelotApiConnector();
            String endPoint = "/UnitOfMeasurementGroups(" + group.getUgpEntry() + ")";
            String requestMethod = "PATCH"; // or "PUT" depending on SAP API requirements

            HttpURLConnection conn = sapCamelotApiConnector.createConnection(endPoint, requestMethod);

            // JSON Payload for UoM Group Update
            JSONObject payload = new JSONObject();

            // SAP typically expects these exact field names
            payload.put("Code", group.getUgpCode());  // Note: Might be "UgpCode" or "Code"
            payload.put("Name", group.getUgpName());   // Note: Might be "UgpName" or "Name"

            // Send the request
            sapCamelotApiConnector.sendRequestBody(conn, payload.toString());

            // Check the response
            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                System.out.println("✅ UoM Group Updated Successfully!");
                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "UoM Group Updated Successfully.");
            } else {
                String errorResponse = sapCamelotApiConnector.getErrorResponse(conn);
                System.out.println("❌ Error Updating UoM Group: " + errorResponse);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "Error Updating UoM Group: " + errorResponse);
                return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + group.getUgpEntry();
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
            return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + group.getUgpEntry();
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE,
                    "Exception occurred during UoM Group update.", ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message",
                    "UoM Group may have been updated, but an error occurred while processing the response.");
        }
        return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + group.getUgpEntry();
    }

    @RequestMapping(value = "addUomToGroup", method = RequestMethod.POST)
    public String addUomToGroup(
            @RequestParam("unitOfMeasurementGroupEntry") Integer ugpEntry,
            @RequestParam("newUomEntry") Integer uomEntry,
            RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector connector = new SapCamelotApiConnector();

            // 1. GET existing group
            // 1. GET existing group
            String endpoint = "/UnitOfMeasurementGroups(" + ugpEntry + ")";
            HttpURLConnection getConn = connector.createConnection(endpoint, "GET");

            JSONObject groupData = connector.getJsonResponse(getConn);
            JSONArray existingLines = groupData.getJSONArray("UoMGroupDefinitionCollection");

// 2. Add new UoM entry
            JSONObject newLine = new JSONObject();
            newLine.put("AlternateUoM", uomEntry);
            newLine.put("AlternateQuantity", 1);
            newLine.put("BaseQuantity", 1);
            newLine.put("WeightFactor", 0);
            newLine.put("UdfFactor", -1);
            newLine.put("Active", "tYES");

// 3. Rebuild updated line array to include new entry
            JSONArray updatedLines = new JSONArray();
            for (int i = 0; i < existingLines.length(); i++) {
                JSONObject line = existingLines.getJSONObject(i);
                updatedLines.put(line); // retain original metadata like LineNumber if present
            }
            updatedLines.put(newLine);

// 4. Put updated lines back into group data
            groupData.put("UoMGroupDefinitionCollection", updatedLines);

// 5. Send PATCH with full group object
            HttpURLConnection patchConn = connector.createConnection(endpoint, "PUT");
            connector.sendRequestBody(patchConn, groupData.toString());

            int responseCode = patchConn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                System.out.println("✅ UoM Group Updated Successfully!");
                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "UoM Group Updated Successfully.");
            } else {
                String errorResponse = connector.getErrorResponse(patchConn);
                System.out.println("❌ Error Updating UoM Group: " + errorResponse);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "Error Updating UoM Group: " + errorResponse);
                return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
            return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE,
                    "Exception occurred during UoM Group update.", ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message",
                    "UoM Group may have been updated, but an error occurred while processing the response.");
        }
        return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
    }

    @RequestMapping(value = "removeUomFromGroup", method = RequestMethod.POST)
    public String removeUomFromGroup(
            @RequestParam("unitOfMeasurementGroupEntry") Integer ugpEntry,
            @RequestParam("uomEntryToRemove") Integer uomEntryToRemove,
            RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector connector = new SapCamelotApiConnector();

            // 1. GET the full UoM Group
            String endpoint = "/UnitOfMeasurementGroups(" + ugpEntry + ")";
            HttpURLConnection getConn = connector.createConnection(endpoint, "GET");

            JSONObject groupData = connector.getJsonResponse(getConn);
            JSONArray existingLines = groupData.getJSONArray("UoMGroupDefinitionCollection");

            // 2. Rebuild UoMGroupDefinitionCollection without the target UoM
            JSONArray updatedLines = new JSONArray();
            boolean uomFound = false;

            for (int i = 0; i < existingLines.length(); i++) {
                JSONObject line = existingLines.getJSONObject(i);
                int existingUomEntry = line.getInt("AlternateUoM");

                if (existingUomEntry != uomEntryToRemove) {
                    updatedLines.put(line);
                } else {
                    uomFound = true;
                }
            }

            if (!uomFound) {
                redirectAttributes.addFlashAttribute("alertColor", "yellow");
                redirectAttributes.addFlashAttribute("message", "UoM not found in group — nothing was removed.");
                return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
            }

            // 3. Update the group with the modified list
            groupData.put("UoMGroupDefinitionCollection", updatedLines);

            HttpURLConnection putConn = connector.createConnection(endpoint, "PUT");
            connector.sendRequestBody(putConn, groupData.toString());

            int responseCode = putConn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                System.out.println("✅ UoM removed from group successfully!");
                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "UoM removed from group successfully.");
            } else {
                String errorResponse = connector.getErrorResponse(putConn);
                System.out.println("❌ Error removing UoM: " + errorResponse);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "Error removing UoM: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "An I/O error occurred: " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE,
                    "Exception occurred while removing UoM.", ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "An unexpected error occurred while removing UoM.");
        }

        return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
    }

    @RequestMapping(value = "updateBaseQuantity", method = RequestMethod.POST)
    public String updateBaseQuantity(
            @RequestParam("unitOfMeasurementGroupEntry") Integer ugpEntry,
            @RequestParam("uomEntry") Integer uomEntry,
            @RequestParam("baseQuantity") Double baseQuantity,
            RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector connector = new SapCamelotApiConnector();

            // 1. GET existing group
            String endpoint = "/UnitOfMeasurementGroups(" + ugpEntry + ")";
            HttpURLConnection getConn = connector.createConnection(endpoint, "GET");
            JSONObject groupData = connector.getJsonResponse(getConn);
            JSONArray existingLines = groupData.getJSONArray("UoMGroupDefinitionCollection");

            // 2. Find and update the specific UoM entry
            boolean found = false;
            for (int i = 0; i < existingLines.length(); i++) {
                JSONObject line = existingLines.getJSONObject(i);
                if (line.getInt("AlternateUoM") == uomEntry) {
                    line.put("BaseQuantity", baseQuantity);
                    found = true;
                    break;
                }
            }

            if (!found) {
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "UoM entry not found in group");
                return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
            }

            // 3. Put updated lines back into group data
            groupData.put("UoMGroupDefinitionCollection", existingLines);

            // 4. Send PUT with updated group object
            HttpURLConnection putConn = connector.createConnection(endpoint, "PUT");
            connector.sendRequestBody(putConn, groupData.toString());

            int responseCode = putConn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                System.out.println("✅ Base Quantity Updated Successfully!");
                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "Base Quantity Updated Successfully.");
            } else {
                String errorResponse = connector.getErrorResponse(putConn);
                System.out.println("❌ Error Updating Base Quantity: " + errorResponse);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "Error Updating Base Quantity: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE,
                    "Exception occurred during Base Quantity update.", ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message",
                    "Base Quantity may have been updated, but an error occurred while processing the response.");
        }
        return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
    }

    @RequestMapping(value = "assignUomGroupToItem", method = RequestMethod.POST)
    public String safeAssignUomGroupToItem(
            @RequestParam("itemCode") String itemCode,
            @RequestParam("uomGroupEntry") Integer uomGroupEntry,
            RedirectAttributes redirectAttributes) {

        try {

            // Initialize API connector
            SapCamelotApiConnector connector = new SapCamelotApiConnector();
            String encodedItemCode = URLEncoder.encode(itemCode, StandardCharsets.UTF_8.toString());
            String endpoint = "/Items('" + encodedItemCode + "')";

            try {
                // 1. First GET the complete item snapshot (for backup)
                JSONObject originalItem = connector.getJsonResponse(
                        connector.createConnection(endpoint + "?$select=*", "GET"));

                // 2. Create minimal update payload with ONLY the field we want to change
                JSONObject updatePayload = new JSONObject();
                updatePayload.put("UoMGroupEntry", uomGroupEntry);

                // 3. Execute safe PATCH request
                HttpURLConnection patchConn = connector.createConnection(endpoint, "PATCH");
                connector.sendRequestBody(patchConn, updatePayload.toString());

                // 4. Verify the update didn't affect other fields
                JSONObject updatedItem = connector.getJsonResponse(
                        connector.createConnection(endpoint + "?$select=UoMGroupEntry,Barcode,Prices", "GET"));

                // 5. Validation checks
                if (!validateUpdateSuccess(originalItem, updatedItem, uomGroupEntry)) {
                    // Restore original data if validation fails
                    HttpURLConnection restoreConn = connector.createConnection(endpoint, "PUT");
                    connector.sendRequestBody(restoreConn, originalItem.toString());

                    throw new Exception("Field validation failed - rolled back changes");
                }

                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "UoM group updated safely");

            } catch (Exception ex) {
                Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "Safe update failed: " + ex.getMessage());
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:sapCamelotItemUpdateServant.htm?itemCode=" + itemCode;
    }

    /**
     * Validates that only the UoMGroupEntry was modified
     */
    private boolean validateUpdateSuccess(JSONObject original, JSONObject updated, int expectedUoMGroup) {
        try {
            // 1. Check the target field was updated correctly
            if (updated.getInt("UoMGroupEntry") != expectedUoMGroup) {
                return false;
            }

            // 2. Verify critical fields remain unchanged
            String[] criticalFields = {"Barcode", "ItemName", "Prices", "InventoryUoM"};
            for (String field : criticalFields) {
                if (original.has(field) && !original.get(field).equals(updated.opt(field))) {
                    return false;
                }
            }

            // 3. Additional checks for numeric fields with tolerance
            if (Math.abs(original.optDouble("Price", 0) - updated.optDouble("Price", 0)) > 0.01) {
                return false;
            }

            return true;

        } catch (JSONException e) {
            return false;
        }
    }
}
