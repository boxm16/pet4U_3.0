package SAP.Camelot.CamelotService;

import SAP.SapBasicModel.SapUnitOfMeasurement;
import SAP.SapCamelotApiConnector;
import java.io.IOException;
import java.net.HttpURLConnection;
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
public class CamelotServiceController {

   

    

    @RequestMapping(value = "addCamelotUnitOfMeasurementToGroupServant")
    public String addCamelotUnitOfMeasurementToGroupServant(@RequestParam("groupCode") String groupCode,
            ModelMap modelMap) {

        CamelotServiceDao camelotServiceDao = new CamelotServiceDao();
        LinkedHashMap<String, SapUnitOfMeasurement> unitsOfMeasurement = camelotServiceDao.getAllUnitsOfMeasurement();
        modelMap.addAttribute("groupCode", groupCode);
        modelMap.addAttribute("unitsOfMeasurement", unitsOfMeasurement);
        return "sap/camelot/service/camelotUnitOfMeasurementGroupCreationServant";
    }

    @RequestMapping(value = "addUoMToGroup", method = RequestMethod.POST)
    public String addUoMToGroup(
            @RequestParam("groupCode") String groupCode,
            @ModelAttribute("uom") SapUnitOfMeasurement uom,
            RedirectAttributes redirectAttributes) {

        try {
            SapCamelotApiConnector apiConnector = new SapCamelotApiConnector();

            // 1. First get the existing UoM group
            String getEndpoint = "/UnitOfMeasurementGroups('" + groupCode + "')";
            HttpURLConnection getConn = apiConnector.createConnection(getEndpoint, "GET");
            JSONObject existingGroup = apiConnector.getJsonResponse(getConn);

            if (existingGroup == null) {
                redirectAttributes.addFlashAttribute("message", "UoM Group not found");
                return "redirect:uomGroupManagement.htm";
            }

            // 2. Prepare updated UoM definitions
            JSONArray uomDefinitions = existingGroup.getJSONArray("UoMGroupDefinitionCollection");

            // Add new UoM definition
            JSONObject newUoM = new JSONObject()
                    .put("AlternateUoM", uom.getUomEntry())
                    .put("BaseQuantity", 1)
                    .put("AlternateQuantity", 1)
                    .put("Active", "tYES");
            uomDefinitions.put(newUoM);

            // 3. Send PATCH request to update
            String patchEndpoint = "/UnitOfMeasurementGroups('" + groupCode + "')";
            HttpURLConnection patchConn = apiConnector.createConnection(patchEndpoint, "PATCH");

            JSONObject updatePayload = new JSONObject()
                    .put("UoMGroupDefinitionCollection", uomDefinitions);

            apiConnector.sendRequestBody(patchConn, updatePayload.toString());

            // 4. Handle response
            if (patchConn.getResponseCode() == 204) { // 204 = No Content (successful update)
                redirectAttributes.addFlashAttribute("message", "UoM added successfully to group");
            } else {
                String errorResponse = apiConnector.getErrorResponse(patchConn);
                redirectAttributes.addFlashAttribute("message", "Error: " + errorResponse);
                return "redirect:editUoMGroup.htm?groupCode=" + groupCode;
            }

        } catch (IOException ex) {
            Logger.getLogger(CamelotServiceController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("message", "IO Error: " + ex.getMessage());
            return "redirect:editUoMGroup.htm?groupCode=" + groupCode;
        } catch (JSONException ex) {
            redirectAttributes.addFlashAttribute("message", "Data Error: " + ex.getMessage());
            return "redirect:editUoMGroup.htm?groupCode=" + groupCode;
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "System Error: " + ex.getMessage());
            return "redirect:editUoMGroup.htm?groupCode=" + groupCode;
        }

        return "redirect:uomGroupDetails.htm?groupCode=" + groupCode;
    }
}
