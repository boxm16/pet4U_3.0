package SAP.Camelot.CamelotOrder;

import SAP.SapController;
import TESTosteron.SAPApiClient;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class SapCamelotOrderController {

    private final String BASE_URL = "https://192.168.0.183:50000/b1s/v2";

// Sample supplier/item configurations
    private static final Map<String, List<ItemLine>> SUPPLIER_ITEM_MAP = new HashMap<>();

    static {

        SUPPLIER_ITEM_MAP.put("ΠΡΟ-000117", Arrays.asList(
                new ItemLine("90860", 10, 10.0),
                new ItemLine("90822", 15, 11.0),
                new ItemLine("90785", 20, 13.0),
                new ItemLine("90990", 25, 12.0)
        ));

        SUPPLIER_ITEM_MAP.put("ΠΡΟ-000062", Arrays.asList(
                new ItemLine("1216", 10, 50.0),
                new ItemLine("1217", 15, 75.0),
                new ItemLine("1215", 20, 100.0),
                new ItemLine("1219", 5, 100.0)
        ));

        SUPPLIER_ITEM_MAP.put("ΠΡΟ-000115", Arrays.asList(
                new ItemLine("1271", 8, 60.0),
                new ItemLine("1273", 12, 80.0),
                new ItemLine("1274", 5, 90.0)
        ));
    }

    @RequestMapping(value = "createPurchaseOrder")
    public String createPurchaseOrder(@RequestParam("supplierCode") String supplierCode, ModelMap modelMap) {
        if (!SUPPLIER_ITEM_MAP.containsKey(supplierCode)) {
            modelMap.addAttribute("message", "❌ Unknown supplier code: " + supplierCode);
            return "redirect:camelotDeliveryDashboardX.htm";
        }

        try {
            String purchaseOrderUrl = BASE_URL + "/PurchaseOrders";
            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            HttpURLConnection conn = sapApiClient.createConnection(purchaseOrderUrl, "POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            conn.setDoOutput(true);

            // Prepare payload
            JSONObject payload = new JSONObject();
            payload.put("CardCode", supplierCode);

            LocalDate today = LocalDate.now();
            LocalDate dueDate = today.plusDays(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            payload.put("DocDate", today.format(formatter));
            payload.put("DocDueDate", dueDate.format(formatter));

            // Add lines for the given supplier
            JSONArray documentLines = new JSONArray();
            for (ItemLine item : SUPPLIER_ITEM_MAP.get(supplierCode)) {
                documentLines.put(createOrderLine(item));
            }
            payload.put("DocumentLines", documentLines);

            try {
                sapApiClient.applySSLBypass(conn); // Optional
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            sapApiClient.sendRequestBody(conn, payload.toString());
            int responseCode = conn.getResponseCode();

            if (responseCode == 201) {
                try {
                    JSONObject jsonResponse = sapApiClient.getJsonResponse(conn);
                    System.out.println("✅ Purchase Order Created Successfully!");
                    modelMap.addAttribute("message", "Purchase Order created successfully.");
                } catch (Exception headerEx) {
                    if (headerEx.getClass().getSimpleName().contains("HeadersTooLarge")) {
                        System.out.println("⚠️ PO created, response headers too large.");
                        modelMap.addAttribute("message", "PO created, but response too large to parse.");
                    } else {
                        throw headerEx;
                    }
                }
            } else {
                String errorResponse = sapApiClient.getErrorResponse(conn);
                System.out.println("❌ Error Creating PO: " + errorResponse);
                modelMap.addAttribute("message", "Error Creating PO: " + errorResponse);
            }

        } catch (IOException ex) {
            if (ex.getMessage().contains("HeadersTooLarge")) {
                modelMap.addAttribute("message", "⚠️ PO may be created, but response was too large.");
            } else {
                modelMap.addAttribute("message", "❌ Error occurred: " + ex.getMessage());
            }
            Logger.getLogger(SapCamelotOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "redirect:camelotDeliveryDashboardX.htm";
    }

    private JSONObject createOrderLine(ItemLine item) {
        JSONObject line = new JSONObject();
        line.put("ItemCode", item.getItemCode());
        line.put("Quantity", item.getQuantity());
        line.put("UnitPrice", item.getUnitPrice());
        return line;
    }

// Simple helper class for item details
    static class ItemLine {

        private final String itemCode;
        private final int quantity;
        private final double unitPrice;

        public ItemLine(String itemCode, int quantity, double unitPrice) {
            this.itemCode = itemCode;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public String getItemCode() {
            return itemCode;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getUnitPrice() {
            return unitPrice;
        }
    }

}
