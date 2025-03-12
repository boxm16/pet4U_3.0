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

    @RequestMapping(value = "addBarcode")
    public String addBarcode(ModelMap modelMap) { //this method works, it adds barcode, but only for UoM 1
        try {
            String itemCode = "1271";  // The item to which we add a barcode
            String apiUrl = BASE_URL + "/Items('" + itemCode + "')"; // ✅ Correct endpoint

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // 1. Retrieve the existing item data
            HttpURLConnection getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject existingData = sapApiClient.getJsonResponse(getConn);

            // 2. Get the existing barcodes and add a new one
            JSONArray barcodesArray = existingData.optJSONArray("ItemBarCodeCollection");
            if (barcodesArray == null) {
                barcodesArray = new JSONArray();
            }

            JSONObject newBarcode = new JSONObject();
            newBarcode.put("Barcode", "0000000000000005"); // New barcode
            newBarcode.put("UoMEntry", 1); // Unit of Measure Entry
            newBarcode.put("FreeText", "TEMAXIA11");//FreeText -dont know what it does

            barcodesArray.put(newBarcode);  // Append new barcode to the array

            // 3. Create the updated JSON payload
            JSONObject updatedItem = new JSONObject();
            updatedItem.put("ItemBarCodeCollection", barcodesArray);
            String jsonBody = updatedItem.toString();

            // 4. Send MERGE request (PATCH not supported, so we use MERGE)
            HttpURLConnection conn = sapApiClient.createConnection(apiUrl, "POST");
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

            conn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            sapApiClient.sendRequestBody(conn, jsonBody);

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            String message = "";
            if (responseCode == 204) {
                System.out.println("Response: Empy Response");
                message = "Barcode added successfully!";
            } else if (responseCode == 200 || responseCode == 201) {
                System.out.println("Response: " + sapApiClient.getJsonResponse(conn));
                message = "Barcode added successfully!";
            } else {
                message = sapApiClient.getErrorResponse(conn);
                System.out.println("Error Response: " + message);
            }

            modelMap.addAttribute("message", message);

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "addBarcode1")
    public String addBarcode1(ModelMap modelMap) {
        String sapLogin = sapLogin();
        SAPApiClientX sapacx = new SAPApiClientX();

        sapacx.push();

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "addBarcodeWithUoM")
    public String addBarcodeWithUoM(ModelMap modelMap) {
        try {
            String itemCode = "1271";  // The item to which we add barcodes
            String apiUrl = BASE_URL + "/Items('" + itemCode + "')";

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // 1. Retrieve the existing item data
            HttpURLConnection getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject existingData = sapApiClient.getJsonResponse(getConn);

            System.out.println("Existing Item Data: " + existingData.toString(2)); // Pretty print JSON

            // 2. Get the UoM Group of the item
            int uomGroupEntry = existingData.optInt("UoMGroupEntry");

            // 3. Retrieve the UoMs in the UoM Group
            String uomGroupUrl = BASE_URL + "/UnitOfMeasurementGroups(" + uomGroupEntry + ")";
            HttpURLConnection uomConn = sapApiClient.createConnection(uomGroupUrl, "GET");
            uomConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

            try {
                sapApiClient.applySSLBypass(uomConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject uomGroupData = sapApiClient.getJsonResponse(uomConn);
            JSONArray uomEntries = uomGroupData.optJSONArray("UoMGroupDefinitionCollection");

            // 4. Check if UoM 2 and UoM 3 exist
            boolean hasUoM2 = false, hasUoM3 = false;

            for (int i = 0; i < uomEntries.length(); i++) {
                JSONObject uom = uomEntries.getJSONObject(i);
                int uomEntry = uom.optInt("AlternateUoM");

                if (uomEntry == 2) {
                    hasUoM2 = true;
                }
                if (uomEntry == 3) {
                    hasUoM3 = true;
                }
            }

            // 5. Ensure UoM 2 and UoM 3 are assigned to the item
            JSONArray itemUoMCollection = existingData.optJSONArray("ItemUnitOfMeasurementCollection");
            if (itemUoMCollection == null) {
                itemUoMCollection = new JSONArray();
            }

            if (!hasUoM2) {
                JSONObject uom2 = new JSONObject();
                uom2.put("UoMEntry", 2);
                itemUoMCollection.put(uom2);
            }

            if (!hasUoM3) {
                JSONObject uom3 = new JSONObject();
                uom3.put("UoMEntry", 3);
                itemUoMCollection.put(uom3);
            }

            // 6. Update the item with new UoM assignments
            JSONObject updatedUoMData = new JSONObject();
            updatedUoMData.put("ItemUnitOfMeasurementCollection", itemUoMCollection);

            HttpURLConnection updateConn = sapApiClient.createConnection(apiUrl, "POST");
            try {
                sapApiClient.applySSLBypass(updateConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateConn.setRequestProperty("X-HTTP-Method-Override", "POST");
            updateConn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

            updateConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            updateConn.setRequestProperty("Content-Type", "application/json");

            sapApiClient.sendRequestBody(updateConn, updatedUoMData.toString());

            int updateResponse = updateConn.getResponseCode();
            if (updateResponse != 204 && updateResponse != 200 && updateResponse != 201) {
                modelMap.addAttribute("message", "Failed to update UoMs: " + sapApiClient.getErrorResponse(updateConn));
                return "/sap/sapDashboard";
            }

            // 7. Retrieve the updated item data (to confirm UoM assignment)
            getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            existingData = sapApiClient.getJsonResponse(getConn);

            // 8. Get the existing barcodes and add new ones
            JSONArray barcodesArray = existingData.optJSONArray("ItemBarCodeCollection");
            if (barcodesArray == null) {
                barcodesArray = new JSONArray();
            }

            // Add barcodes for UoM 1, 2, and 3
            JSONObject barcode1 = new JSONObject();
            barcode1.put("Barcode", "0000000000000005");
            barcode1.put("UoMEntry", 1);

            JSONObject barcode2 = new JSONObject();
            barcode2.put("Barcode", "0000000000000006");
            barcode2.put("UoMEntry", 2);

            JSONObject barcode3 = new JSONObject();
            barcode3.put("Barcode", "0000000000000007");
            barcode3.put("UoMEntry", 3);

            barcodesArray.put(barcode1);
            barcodesArray.put(barcode2);
            barcodesArray.put(barcode3);

            // 9. Update the item with new barcodes
            JSONObject updatedItem = new JSONObject();
            updatedItem.put("ItemBarCodeCollection", barcodesArray);

            HttpURLConnection barcodeConn = sapApiClient.createConnection(apiUrl, "POST");
            barcodeConn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            barcodeConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            barcodeConn.setRequestProperty("Content-Type", "application/json");

            try {
                sapApiClient.applySSLBypass(barcodeConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            sapApiClient.sendRequestBody(barcodeConn, updatedItem.toString());

            int barcodeResponse = barcodeConn.getResponseCode();
            String message = "";
            if (barcodeResponse == 204 || barcodeResponse == 200 || barcodeResponse == 201) {
                message = "Barcodes added successfully!";
            } else {
                message = "Failed to add barcodes: " + sapApiClient.getErrorResponse(barcodeConn);
            }

            modelMap.addAttribute("message", message);

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "getApiCallResponse")
    public String getApiCallResponse(ModelMap modelMap) {
        try {
            String itemCode = "1271";  // The item to which we add barcodes
            String apiUrl = BASE_URL + "/Items('" + itemCode + "')";

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // 1. Retrieve the existing item data
            HttpURLConnection getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject existingData = sapApiClient.getJsonResponse(getConn);

            System.out.println("Existing Item Data: " + existingData.toString(2)); // Pretty print JSON

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "/sap/sapDashboard";
    }

}
