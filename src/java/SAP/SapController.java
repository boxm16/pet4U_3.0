package SAP;

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
public class SapController {
    // 🔹 SAP Business One API credentials

    private final String BASE_URL = "https://192.168.0.183:50000/b1s/v2";
    private final String USERNAME = "scanner1";
    private final String PASSWORD = "1234";
    private final String COMPANY_DB = "PETCAMELOT_UAT2";

    @RequestMapping(value = "sapDashboard")
    public String sapDashboard() {

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "sapLogin")
    public String sapLogin() {
        try {
            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "createSupplier")
    public String createSupplier() {
        try {
            String apiUrl = BASE_URL + "/BusinessPartners";
            String jsonBody = "{\"CardCode\": \"c1\", "
                    + " \"CardName\": \"customer c21\", "
                    + " \"CardType\": \"cCustomer\" "
                    + "}";

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            HttpURLConnection conn = sapApiClient.createConnection(apiUrl, "POST");

            conn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            sapApiClient.sendRequestBody(conn, jsonBody);

            int responseCode = conn.getResponseCode();
            System.out.println(" Response Code: " + responseCode);

            if (responseCode == 200 || responseCode == 201) {
                System.out.println(" Response : " + sapApiClient.getJsonResponse(conn));
            } else if (responseCode == 401) {
                System.out.println(" Session expired! Please re-login.");
            } else {
                System.out.println("  Response: " + sapApiClient.getErrorResponse(conn));
            }

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "createItem")
    public String createItem(ModelMap modelMap) {
        try {
            // Define the API endpoint for creating items
            String apiUrl = BASE_URL + "/Items";

            // JSON body for creating an item
            String jsonBody = "{"
                    + "\"ItemCode\": \"1003-12224P\", "
                    + "\"ItemName\": \"ΚΟΚΚΑΛΟ ΚΟΜΠΟΣ-6.35cm-10gr/10pcs (NEW)\", "
                    + "\"ItemsGroupCode\": 110, "
                    + "\"SalesUnit\": \"ΣΥΣΚΕΥΑΣΙΑ\", "
                    + "\"PurchaseUnit\": \"ΤΕΜΑΧΙΑ\", "
                    + "\"InventoryItem\": \"tYES\", "
                    + "\"SalesItem\": \"tYES\", "
                    + "\"PurchaseItem\": \"tYES\", "
                    + "\"VatLiable\": \"tYES\", "
                    + "\"SalesVATGroup\": \"Φ7000-24\", "
                    + "\"PurchaseVATGroup\": \"Φ2000-24\", "
                    + "\"Properties8\" : \"tYES\"," //  for Αξεσουαρ -Properties7 tYES, if i want food i choose Properties8 tYES
                    + "\"BarCode\": \"1003-12224P\", " // Add BarCode here
                    + "\"ItemPrices\": ["
                    + "  {"
                    + "    \"PriceList\": 1, "
                    + "    \"Price\": 1.3, "
                    + "    \"Currency\": \"EUR\""
                    + "  },"
                    + "  {"
                    + "    \"PriceList\": 2, "
                    + "    \"Price\": 2.32, "
                    + "    \"Currency\": \"EUR\""
                    + "  }"
                    + "],"
                    + "\"ItemWarehouseInfoCollection\": ["
                    + "  {"
                    + "    \"WarehouseCode\": \"AX-BAR\", "
                    + "    \"MinimalStock\": 0.0, "
                    + "    \"MaximalStock\": 0.0, "
                    + "    \"MinimalOrder\": 0.0"
                    + "  }"
                    + "],"
                    + "\"ItemPreferredVendors\": ["
                    + "  {"
                    + "    \"BPCode\": \"ΠΡΟ-000076\""
                    + "  }"
                    + "]"
                    + "}";

            // Initialize SAP API client and log in to get the session token
            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // Create the HTTP connection for the POST request
            HttpURLConnection conn = sapApiClient.createConnection(apiUrl, "POST");

            // Set the session token in the request header
            conn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

            // Send the JSON body in the request
            sapApiClient.sendRequestBody(conn, jsonBody);

            // Get the response code from the server
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            String message = "";
// Handle the response
            if (responseCode == 200 || responseCode == 201) {
                System.out.println("Response: " + sapApiClient.getJsonResponse(conn));
            } else if (responseCode == 401) {
                System.out.println("Session expired! Please re-login.");
            } else {
                message += sapApiClient.getErrorResponse(conn);
                System.out.println("Error Response: " + message);
            }
            modelMap.addAttribute("message", message);
// Return the view name (assuming this is part of a Spring MVC controller)

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "addMainBarcode")
    public String addMainBarcode(ModelMap modelMap) {
        try {
            String itemCode = "1271";
            String apiUrl = BASE_URL + "/Items('" + itemCode + "')";
            // JSON body for creating an item

            String jsonBody = "{ \"BarCode\": \"0000000000000003\"}";
            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();
            HttpURLConnection conn = sapApiClient.createConnection(apiUrl, "POST");

            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

            conn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            sapApiClient.sendRequestBody(conn, jsonBody);

            // Get the response code from the server
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            String message = "";
// Handle the response
            if (responseCode == 204) {
                System.out.println("Empty Response");
            } else if (responseCode == 200 || responseCode == 201 || responseCode == 204) {
                System.out.println("Response: " + sapApiClient.getJsonResponse(conn));
            } else if (responseCode == 401) {
                System.out.println("Session expired! Please re-login.");
            } else {
                message = sapApiClient.getErrorResponse(conn);
                System.out.println("Error Response: " + message);
            }
            modelMap.addAttribute("message", message);
// Return the view name (assuming this is part of a Spring MVC controller)

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "addBarcode1")
    public String addBarcode1(ModelMap modelMap) {
        try {
            String itemCode = "1271";
            String apiUrl = BASE_URL + "/Items('" + itemCode + "')"; // Ensure BarCodes are retrieved

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // 1. Retrieve the existing item data
            HttpURLConnection getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SAPApiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            JSONObject existingData = sapApiClient.getJsonResponse(getConn);

            JSONObject itemJson = new JSONObject(existingData);
            JSONArray barcodesArray = itemJson.optJSONArray("ItemBarCodeCollection");

            // 2. Add a new barcode for the BOX (10 items inside)
            JSONObject newBarcode = new JSONObject();
            newBarcode.put("Barcode", "0000000000000004"); // New barcode for the box
            newBarcode.put("UoMEntry", 2); // Set the Unit of Measure Entry for Box (You must find the correct UoMEntry ID)
            newBarcode.put("FreeText", "Box of 10 items");

            if (barcodesArray == null) {
                barcodesArray = new JSONArray();
            }
            barcodesArray.put(newBarcode);

            // 3. Create the updated JSON payload
            JSONObject updatedItem = new JSONObject();
            updatedItem.put("ItemBarCodeCollection", barcodesArray);
            String jsonBody = updatedItem.toString();

            // 4. Send update request using MERGE
            HttpURLConnection conn = sapApiClient.createConnection(apiUrl, "POST");
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

            conn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            sapApiClient.sendRequestBody(conn, jsonBody);

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            String message = "";
            if (responseCode == 200 || responseCode == 201) {
                System.out.println("Response: " + sapApiClient.getJsonResponse(conn));
            } else if (responseCode == 401) {
                System.out.println("Session expired! Please re-login.");
            } else {
                message += sapApiClient.getErrorResponse(conn);
                System.out.println("Error Response: " + message);
            }
            modelMap.addAttribute("message", message);

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "addBarcode")

    public String addUoM(ModelMap modelMap) {
        try {
            String itemCode = "1271";
            String apiUrl = BASE_URL + "/Items('" + itemCode + "')";

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // Step 1: Retrieve the full item data (including UoM collection)
            HttpURLConnection getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }
            JSONObject itemJson = sapApiClient.getJsonResponse(getConn);

            // Retrieve existing UoM collection; initialize if missing.
            JSONArray existingUoMList = itemJson.optJSONArray("ItemUnitOfMeasurementCollection");
            if (existingUoMList == null) {
                existingUoMList = new JSONArray();
            }

            // Step 2: Build a full collection payload by copying existing entries.
            JSONArray fullUoMList = new JSONArray();
            for (int i = 0; i < existingUoMList.length(); i++) {
                fullUoMList.put(existingUoMList.getJSONObject(i));
            }

            // Check if UoMEntry 5 exists.
            boolean uom5Exists = false;
            for (int i = 0; i < fullUoMList.length(); i++) {
                if (fullUoMList.getJSONObject(i).optInt("UoMEntry", -1) == 5) {
                    uom5Exists = true;
                    break;
                }
            }

            if (uom5Exists) {
                modelMap.addAttribute("message", "UoMEntry 5 already exists for the item.");
                System.out.println("UoMEntry 5 already exists for the item.");
                return "/sap/sapDashboard";
            }

            // Step 3: Build the new UoM object for UoMEntry 5 using your provided sample details.
            JSONObject newUoM = new JSONObject();
            newUoM.put("UoMType", "iutInventory");
            newUoM.put("UoMEntry", 5);
            newUoM.put("DefaultBarcode", 2805);
            newUoM.put("DefaultPackage", JSONObject.NULL);  // Use JSONObject.NULL for null values
            newUoM.put("Length1", 0.0);
            newUoM.put("Length1Unit", JSONObject.NULL);
            newUoM.put("Length2", 0.0);
            newUoM.put("Length2Unit", JSONObject.NULL);
            newUoM.put("Width1", 0.0);
            newUoM.put("Width1Unit", JSONObject.NULL);
            newUoM.put("Width2", 0.0);
            newUoM.put("Width2Unit", JSONObject.NULL);
            newUoM.put("Height1", 0.0);
            newUoM.put("Height1Unit", JSONObject.NULL);
            newUoM.put("Height2", 0.0);
            newUoM.put("Height2Unit", JSONObject.NULL);
            newUoM.put("Volume", 0.0);
            newUoM.put("VolumeUnit", 4);
            newUoM.put("Weight1", 0.0);
            newUoM.put("Weight1Unit", JSONObject.NULL);
            newUoM.put("Weight2", 0.0);
            newUoM.put("Weight2Unit", JSONObject.NULL);
            newUoM.put("ItemUoMPackageCollection", new JSONArray());

            System.out.println("Final Payload: " + newUoM.toString(4)); // Pretty print for debugging

            // Append the new UoM entry (UoMEntry 5) to our full collection.
            fullUoMList.put(newUoM);

            // Step 4: Construct the complete payload with the full item data
            JSONObject updatedPayload = new JSONObject(itemJson.toString()); // Clone the full item JSON
            updatedPayload.put("ItemUnitOfMeasurementCollection", fullUoMList); // Replace only the UoM part

// Step 5: Create a PATCH request to update the item.
            HttpURLConnection patchConn = sapApiClient.createConnection(apiUrl, "POST");
            patchConn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            patchConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

// Use ETag for optimistic concurrency; retrieve it from the GET response.
            String etag = itemJson.optString("@odata.etag");
            if (etag != null && !etag.isEmpty()) {
                patchConn.setRequestProperty("If-Match", etag);
            }

// Send the complete updated payload.
            sapApiClient.sendRequestBody(patchConn, updatedPayload.toString());
            int responseCode = patchConn.getResponseCode();

            if (responseCode == 200 || responseCode == 204) {
                modelMap.addAttribute("message", "Successfully updated the UoM collection with UoMEntry 5.");
                System.out.println("Successfully updated the UoM collection with UoMEntry 5.");
            } else {
                String errorMessage = sapApiClient.getErrorResponse(patchConn);
                modelMap.addAttribute("message", "Failed to update UoM collection: " + errorMessage);
                System.out.println("Failed to update UoM collection: " + errorMessage);
            }
        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }
        return "/sap/sapDashboard";
    }

}
