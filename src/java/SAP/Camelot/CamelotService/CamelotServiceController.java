package SAP.Camelot.CamelotService;

import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import SAP.SapCamelotApiConnector;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CamelotServiceController {

    @RequestMapping(value = "camelotUnitOfMeasurementGroupCreationServant")
    public String camelotUnitOfMeasurementGroupCreationServant(ModelMap modelMap) {

        SapUnitOfMeasurementGroup uomGroup = new SapUnitOfMeasurementGroup();
        CamelotServiceDao camelotServiceDao = new CamelotServiceDao();
        short uomGroupId = camelotServiceDao.getNextUomGroupId();
        uomGroup.setUgpEntry(uomGroupId);
        modelMap.addAttribute("uomGroup", uomGroup);
        return "sap/camelot/service/camelotUnitOfMeasurementGroupCreationServant";
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
            Logger.getLogger(CamelotServiceController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + ex.getMessage());
            return "redirect:camelotUnitOfMeasurementGroupCreationServant.htm";
        } catch (Exception ex) {
            Logger.getLogger(CamelotServiceController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            redirectAttributes.addFlashAttribute("message", "UoM Group created but with potential issues");
        }

        return "redirect:sapDashboard.htm";
    }
}
