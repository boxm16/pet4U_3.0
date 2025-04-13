package SAP.Camelot.CamelotOrder;

import SAP.SapController;
import TESTosteron.SAPApiClient;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private final String ITEM_CODE_1 = "1271";
    private final String ITEM_CODE_2 = "1273";
    private final String ITEM_CODE_3 = "1274";

    @RequestMapping(value = "createPurchaseOrder")
    public String createPurchaseOrder(ModelMap modelMap) {
        try {
            String purchaseOrderUrl = BASE_URL + "/PurchaseOrders";
            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            HttpURLConnection conn = sapApiClient.createConnection(purchaseOrderUrl, "POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            conn.setDoOutput(true);

            // JSON Payload for Purchase Order
            JSONObject payload = new JSONObject();
            payload.put("CardCode", "ΠΡΟ-000115"); // Supplier Code

            // Set dates
            LocalDate today = LocalDate.now();
            LocalDate dueDate = today.plusDays(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            payload.put("DocDate", today.format(formatter));
            payload.put("DocDueDate", dueDate.format(formatter));

            // Add Document Lines for all items
            JSONArray documentLines = new JSONArray();

            // Line 1 - Item 1
            JSONObject line1 = createOrderLine(ITEM_CODE_1, 10, 50.0);
            documentLines.put(line1);

            // Line 2 - Item 2
            JSONObject line2 = createOrderLine(ITEM_CODE_2, 15, 75.0); // Example quantity/price
            documentLines.put(line2);

            // Line 3 - Item 3
            JSONObject line3 = createOrderLine(ITEM_CODE_3, 20, 100.0); // Example quantity/price
            documentLines.put(line3);

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
                System.out.println("✅ Purchase Order Created Successfully with all items!");
                modelMap.addAttribute("message", "Purchase Order Created Successfully with all items. Details: " + jsonResponse.toString());
            } else {
                String errorResponse = sapApiClient.getErrorResponse(conn);
                System.out.println("❌ Error Creating Purchase Order: " + errorResponse);
                modelMap.addAttribute("message", "Error Creating Purchase Order: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapCamelotOrderController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotOrderController.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
            modelMap.addAttribute("message", "Purchase Order may have been created but an error occurred while processing the response.");
        }
        return "redirect:camelotDeliveryDashboardX.htm";
    }

    // Helper method to create order line items
    private JSONObject createOrderLine(String itemCode, int quantity, double unitPrice) {
        JSONObject line = new JSONObject();
        line.put("ItemCode", itemCode);
        line.put("Quantity", quantity);
        line.put("UnitPrice", unitPrice);
        // You can add more line item properties if needed
        return line;
    }
}
