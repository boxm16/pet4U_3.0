package SAP.Camelot.UnitOfMeasurement;

import SAP.SapBasicModel.SapUnitOfMeasurement;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import SAP.SapCamelotApiConnector;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
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

    @RequestMapping(value = "camelotUnitOfMeasurementGroupCreationServant")
    public String camelotUnitOfMeasurementGroupCreationServant(ModelMap modelMap) {

        SapUnitOfMeasurementGroup uomGroup = new SapUnitOfMeasurementGroup();
        SapCamelotUnitOfMeasurementDao dao = new SapCamelotUnitOfMeasurementDao();
        short uomGroupId = dao.getNextUomGroupId();
        uomGroup.setUgpEntry(uomGroupId);
        modelMap.addAttribute("uomGroup", uomGroup);
        return "sap/camelot/unitOfMeasurement/camelotUnitOfMeasurementGroupCreationServant";
    }

    @RequestMapping(value = "creationCamelotUnitOfMeasurementGroup", method = RequestMethod.POST)
    public String creationCamelotUnitOfMeasurementGroup(@ModelAttribute("uomGroup") SapUnitOfMeasurementGroup uomGroup,
            RedirectAttributes redirectAttributes) {
        try {
            SapCamelotApiConnector sapCamelotApiConnector = new SapCamelotApiConnector();
            String endPoint = "/UnitOfMeasurementGroups";
            String requestMethod = "POST";

            HttpURLConnection conn = sapCamelotApiConnector.createConnection(endPoint, requestMethod);

            // Build JSON payload from the uomGroup object
            JSONObject payload = new JSONObject();
            payload.put("Code", uomGroup.getUgpCode());
            payload.put("Name", uomGroup.getUgpName());
            payload.put("BaseUoM", 1); // Assuming Piece is always base UoM (UoMEntry = 1)

            JSONArray uomDefinitions = new JSONArray();

            JSONObject baseUoM = new JSONObject();
            baseUoM.put("AlternateUoM", 1);
            baseUoM.put("BaseQuantity", 1);
            baseUoM.put("AlternateQuantity", 1);
            baseUoM.put("Active", "tYES");
            uomDefinitions.put(baseUoM);

            payload.put("UoMGroupDefinitionCollection", uomDefinitions);

            // Send request
            sapCamelotApiConnector.sendRequestBody(conn, payload.toString());

            // Handle response
            int responseCode = conn.getResponseCode();
            if (responseCode == 201) {
                JSONObject jsonResponse = sapCamelotApiConnector.getJsonResponse(conn);
                redirectAttributes.addFlashAttribute("message", "UoM Group created successfully!");
                System.out.println("UoM Group created: " + jsonResponse.toString());
            } else {
                String errorResponse = sapCamelotApiConnector.getErrorResponse(conn);
                redirectAttributes.addFlashAttribute("message", "Error creating UoM Group: " + errorResponse);
                return "redirect:camelotUnitOfMeasurementGroupCreationServant.htm";
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
            return "redirect:camelotUnitOfMeasurementGroupCreationServant.htm";
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            redirectAttributes.addFlashAttribute("message", "UoM Group created but with potential issues");
        }

        return "redirect:sapDashboard.htm";
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
    ///---------------------------------------------------

    @RequestMapping(value = "assignUomGroupToItemX", method = RequestMethod.POST)
    public String assignUomGroupToItemX(
            @RequestParam("itemCode") String itemCode,
            @RequestParam("ugpEntry") Integer ugpEntry,
            RedirectAttributes redirectAttributes) {

        System.out.println("Not safe assignemt");
        try {
            SapCamelotApiConnector connector = new SapCamelotApiConnector();

            // 1. GET the existing item data
            String endpoint = "/Items('" + URLEncoder.encode(itemCode, StandardCharsets.UTF_8.toString()) + "')";
            HttpURLConnection getConn = connector.createConnection(endpoint, "GET");
            JSONObject itemData = connector.getJsonResponse(getConn);

            // 2. Update the UoM group reference
            itemData.put("UoMGroupEntry", ugpEntry);

            // 3. Send PATCH with updated item data
            HttpURLConnection patchConn = connector.createConnection(endpoint, "PATCH");
            connector.sendRequestBody(patchConn, itemData.toString());

            int responseCode = patchConn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                System.out.println("✅ UoM Group assigned to item successfully!");
                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "UoM Group assigned to item successfully.");
            } else {
                String errorResponse = connector.getErrorResponse(patchConn);
                System.out.println("❌ Error assigning UoM Group to item: " + errorResponse);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "Error assigning UoM Group to item: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE,
                    "Exception occurred during UoM Group assignment.", ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message",
                    "UoM Group may have been assigned, but an error occurred while processing the response.");
        }
        return "redirect:sapCamelotItemUpdateServant.htm?itemCode=" + itemCode; // Adjust redirect as needed
    }

    ///++++++++++++++
    @RequestMapping(value = "assignUomGroupToItem", method = RequestMethod.POST)
    public String assignUomGroupToItem(
            @RequestParam("itemCode") String itemCode,
            @RequestParam("ugpEntry") Integer ugpEntry,
            RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector connector = new SapCamelotApiConnector();

            // 1. Get the CURRENT main barcode (not from collection)
            String mainBarcode = getMainBarcode(connector, itemCode);

            // 2. Get current UoM group to check if change is needed
            int currentUoMGroup = getCurrentUoMGroup(connector, itemCode);

            // 3. Only proceed if UoM group is actually changing
            if (currentUoMGroup != ugpEntry) {
                // 4. Prepare the update payload
                JSONObject payload = new JSONObject();
                payload.put("UoMGroupEntry", ugpEntry);

                // 5. Update ONLY the UoM group (nothing else)
                updateSingleField(connector, itemCode, "UoMGroupEntry", ugpEntry);

                // 6. Verify and restore main barcode if needed
                if (mainBarcode != null && !mainBarcode.equals(getMainBarcode(connector, itemCode))) {
                    updateSingleField(connector, itemCode, "BarCode", mainBarcode);
                }
            }

            redirectAttributes.addFlashAttribute("alertColor", "green");
            redirectAttributes.addFlashAttribute("message", "✅ UoM Group updated - ALL barcodes preserved");

        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "Error: " + ex.getLocalizedMessage());
        }

        return "redirect:sapCamelotItemUpdateServant.htm?itemCode=" + itemCode;
    }

// Gets the main barcode (from the BarCode field, not collection)
    private String getMainBarcode(SapCamelotApiConnector connector, String itemCode) throws Exception {
        String endpoint = "/Items('" + URLEncoder.encode(itemCode, StandardCharsets.UTF_8.toString()) + "')?$select=BarCode";
        HttpURLConnection conn = connector.createConnection(endpoint, "GET");
        JSONObject response = connector.getJsonResponse(conn);
        return response.optString("BarCode", null);
    }

// Gets current UoM group
    private int getCurrentUoMGroup(SapCamelotApiConnector connector, String itemCode) throws Exception {
        String endpoint = "/Items('" + URLEncoder.encode(itemCode, StandardCharsets.UTF_8.toString()) + "')?$select=UoMGroupEntry";
        HttpURLConnection conn = connector.createConnection(endpoint, "GET");
        JSONObject response = connector.getJsonResponse(conn);
        return response.getInt("UoMGroupEntry");
    }

// Updates a SINGLE field without affecting anything else
    private void updateSingleField(SapCamelotApiConnector connector, String itemCode, String fieldName, Object value) throws Exception {
        String endpoint = "/Items('" + URLEncoder.encode(itemCode, StandardCharsets.UTF_8.toString()) + "')";

        JSONObject payload = new JSONObject();
        payload.put(fieldName, value);

        HttpURLConnection conn = connector.createConnection(endpoint, "PATCH");
        conn.setRequestProperty("Content-Type", "application/json");
        // Explicitly DON'T set ReplaceCollections header

        connector.sendRequestBody(conn, payload.toString());

        if (conn.getResponseCode() != 200 && conn.getResponseCode() != 204) {
            throw new RuntimeException("Failed to update " + fieldName + ": " + connector.getErrorResponse(conn));
        }
    }

    ///---------------------------------------------------
    @RequestMapping(value = "unassignUomGroupFromItem", method = RequestMethod.POST)
    public String unassignUomGroupFromItem(
            @RequestParam("itemCode") String itemCode,
            RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector connector = new SapCamelotApiConnector();
            String endpoint = "/Items('" + URLEncoder.encode(itemCode, StandardCharsets.UTF_8.toString()) + "')";

            // 1. GET existing item data
            HttpURLConnection getConn = connector.createConnection(endpoint, "GET");
            JSONObject itemData = connector.getJsonResponse(getConn);

            // 2. Unassign UoM Group
            itemData.put("UoMGroupEntry", -1);

            // 3. Send full updated item as PATCH
            HttpURLConnection patchConn = connector.createConnection(endpoint, "PATCH");
            connector.sendRequestBody(patchConn, itemData.toString());

            // 4. Handle response
            int responseCode = patchConn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "✅ UoM Group unassigned successfully");
            } else {
                String errorResponse = connector.getErrorResponse(patchConn);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "❌ Failed to unassign UoM Group: " + errorResponse);
                System.err.println("API Error Response: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "Network error during unassignment: " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE,
                    "Unexpected error during UoM Group unassignment", ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "System error during unassignment. Please check logs.");
        }

        return "redirect:sapCamelotItemUpdateServant.htm?itemCode=" + itemCode;
    }

    //------uom Barcode handling-----------
    @RequestMapping(value = "addUomBarcode", method = RequestMethod.POST)
    public String addBarcodeToUom(
            @RequestParam("unitOfMeasurementGroupEntry") Integer ugpEntry,
            @RequestParam("uomEntry") Integer uomEntry,
            @RequestParam("barcode") String barcode,
            RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector connector = new SapCamelotApiConnector();

            // 1. GET existing group
            String endpoint = "/UnitOfMeasurementGroups(" + ugpEntry + ")";
            HttpURLConnection getConn = connector.createConnection(endpoint, "GET");
            JSONObject groupData = connector.getJsonResponse(getConn);
            JSONArray existingLines = groupData.getJSONArray("UoMGroupDefinitionCollection");

            // 2. Find the specific UoM entry and add barcode
            boolean found = false;
            for (int i = 0; i < existingLines.length(); i++) {
                JSONObject line = existingLines.getJSONObject(i);
                if (line.getInt("AlternateUoM") == uomEntry) {
                    // Get existing barcodes or create new array if none exist
                    JSONArray barcodes;
                    if (line.has("Barcodes") && !line.isNull("Barcodes")) {
                        barcodes = line.getJSONArray("Barcodes");
                    } else {
                        barcodes = new JSONArray();
                    }

                    // Add new barcode if it doesn't already exist
                    boolean barcodeExists = false;
                    for (int j = 0; j < barcodes.length(); j++) {
                        if (barcodes.getString(j).equals(barcode)) {
                            barcodeExists = true;
                            break;
                        }
                    }

                    if (!barcodeExists) {
                        barcodes.put(barcode);
                        line.put("Barcodes", barcodes);
                        found = true;
                    } else {
                        redirectAttributes.addFlashAttribute("alertColor", "orange");
                        redirectAttributes.addFlashAttribute("message", "Barcode already exists for this UOM");
                        return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
                    }
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
                System.out.println("✅ Barcode Added Successfully!");
                redirectAttributes.addFlashAttribute("alertColor", "green");
                redirectAttributes.addFlashAttribute("message", "Barcode Added Successfully.");
            } else {
                String errorResponse = connector.getErrorResponse(putConn);
                System.out.println("❌ Error Adding Barcode: " + errorResponse);
                redirectAttributes.addFlashAttribute("alertColor", "red");
                redirectAttributes.addFlashAttribute("message", "Error Adding Barcode: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotUnitOfMeasurementControlle.class.getName()).log(Level.SEVERE,
                    "Exception occurred during Barcode addition.", ex);
            redirectAttributes.addFlashAttribute("alertColor", "red");
            redirectAttributes.addFlashAttribute("message",
                    "Barcode may have been added, but an error occurred while processing the response.");
        }
        return "redirect:camelotUnitOfMeasurementGroupEditServant.htm?ugpEntry=" + ugpEntry;
    }

}
