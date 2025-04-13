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

            // Add Document Lines
            JSONArray documentLines = new JSONArray();
            documentLines.put(createOrderLine(ITEM_CODE_1, 10, 50.0));
            documentLines.put(createOrderLine(ITEM_CODE_2, 15, 75.0));
            documentLines.put(createOrderLine(ITEM_CODE_3, 20, 100.0));
            payload.put("DocumentLines", documentLines);

            try {
                sapApiClient.applySSLBypass(conn); // Optional
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Send the request
            sapApiClient.sendRequestBody(conn, payload.toString());

            // Get response code first
            int responseCode = conn.getResponseCode();

            if (responseCode == 201) {
                try {
                    JSONObject jsonResponse = sapApiClient.getJsonResponse(conn); // May throw HeadersTooLarge
                    System.out.println("✅ Purchase Order Created Successfully with all items!");
                    modelMap.addAttribute("message", "Purchase Order created successfully. Details: " + jsonResponse.toString());
                } catch (Exception headerEx) {
                    if (headerEx.getClass().getSimpleName().contains("HeadersTooLarge")) {
                        System.out.println("⚠️ PO created, but response headers too large. Skipping body parsing.");
                        modelMap.addAttribute("message", "Purchase Order created successfully, but response was too large to parse.");
                    } else {
                        throw headerEx; // Unknown issue? Bubble it up
                    }
                }
            } else {
                String errorResponse = sapApiClient.getErrorResponse(conn);
                System.out.println("❌ Error Creating Purchase Order: " + errorResponse);
                modelMap.addAttribute("message", "Error Creating Purchase Order: " + errorResponse);
            }

        } catch (IOException ex) {
            if (ex.getMessage().contains("HeadersTooLarge")
                    || ex.getClass().getSimpleName().contains("HeadersTooLarge")) {
                Logger.getLogger(SapCamelotOrderController.class.getName()).log(Level.WARNING, "⚠️ Headers too large, but PO might be created", ex);
                modelMap.addAttribute("message", "Purchase Order may be created, but the response was too large.");
            } else {
                Logger.getLogger(SapCamelotOrderController.class.getName()).log(Level.SEVERE, null, ex);
                modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
            }
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
