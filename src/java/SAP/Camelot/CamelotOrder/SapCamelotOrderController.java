package SAP.Camelot.CamelotOrder;

import SAP.SapController;
import TESTosteron.SAPApiClient;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SapCamelotOrderController {

    private final String BASE_URL = "https://192.168.0.183:50000/b1s/v2";


    private final String ITEM_CODE = "1271";

    @RequestMapping(value = "createPurchaseOrder")
    public String createPurchaseOrder(ModelMap modelMap) {
        try {
            String purchaseOrderUrl = BASE_URL + "/PurchaseOrders"; // Changed endpoint
            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            HttpURLConnection conn = sapApiClient.createConnection(purchaseOrderUrl, "POST");

            // Set headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            conn.setDoOutput(true);

            // JSON Payload for Purchase Order
            JSONObject payload = new JSONObject();
            payload.put("CardCode", "ΠΡΟ-000122"); // Supplier Code (changed from customer code)
            payload.put("DocDate", "2025-02-01"); // Document Date
            payload.put("DocDueDate", "2025-10-10"); // Due Date

            // Add Document Lines
            JSONArray documentLines = new JSONArray();

            // Line 1
            JSONObject line1 = new JSONObject();
            line1.put("ItemCode", ITEM_CODE); // Item Code
            line1.put("Quantity", 120); // Quantity
            line1.put("UnitPrice", 50.0); // Unit Price (adjusted for purchase)
            documentLines.put(line1);

            // Add lines to payload
            payload.put("DocumentLines", documentLines);

            try {
                sapApiClient.applySSLBypass(conn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Send the request
            sapApiClient.sendRequestBody(conn, payload.toString());

            // Check the response
            int responseCode = conn.getResponseCode();
            if (responseCode == 201) {
                JSONObject jsonResponse = sapApiClient.getJsonResponse(conn);
                System.out.println("✅ Purchase Order Created Successfully!");
                System.out.println("Purchase Order Details: " + jsonResponse.toString());
                modelMap.addAttribute("message", "Purchase Order Created Successfully. Purchase Order Details: " + jsonResponse.toString());
            } else {
                String errorResponse = sapApiClient.getErrorResponse(conn);
                System.out.println("❌ Error Creating Purchase Order: " + errorResponse);
                modelMap.addAttribute("message", "Error Creating Purchase Order: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotOrderController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }
        return "/sap/sapDashboard";
    }
}
