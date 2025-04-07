package SAP.Camelot.UnitOfMeasurement;

import SAP.SapBasicModel.SapUnitOfMeasurement;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import SAP.SapCamelotApiConnector;
import java.io.IOException;
import java.net.HttpURLConnection;
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
            String endpoint = "/UnitOfMeasurementGroups(" + ugpEntry + ")";
            HttpURLConnection getConn = connector.createConnection(endpoint, "GET");

            JSONObject groupData = connector.getJsonResponse(getConn);
            JSONArray existingLines = groupData.getJSONArray("UoMGroupDefinitionCollection");

            // 2. Prepare new line (with ALL required fields)
            JSONObject newLine = new JSONObject();
            newLine.put("AlternateUoM", uomEntry);
            newLine.put("AlternateQuantity", 1);
            newLine.put("BaseQuantity", 1);
            newLine.put("WeightFactor", 0);
            newLine.put("UdfFactor", -1);
            newLine.put("Active", "tYES");
            existingLines.put(newLine);

            // 3. PATCH with full wrapper object
            JSONObject patchPayload = new JSONObject();
            patchPayload.put("UoMGroupDefinitionCollection", existingLines);

            HttpURLConnection patchConn = connector.createConnection(endpoint, "PATCH");

            connector.sendRequestBody(patchConn, patchPayload.toString());

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

}
