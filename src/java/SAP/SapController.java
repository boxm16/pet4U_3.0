package SAP;

import TESTosteron.SAPApiClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SapController {
    // 🔹 SAP Business One API credentials

    private final String BASE_URL = "https://192.168.0.183:50000/b1s/v2";
    private final String USERNAME = "scanner1";
    private final String PASSWORD = "1234";
    private final String COMPANY_DB = "PETCAMELOT_UAT2";
    //private final String COMPANY_DB = "TRAINING_PC";

    @RequestMapping(value = "sapIndex")
    public String sapIndex() {

        return "/sap/sapIndex";
    }

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
    public String addBarcode(ModelMap modelMap) { //this method works
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
            newBarcode.put("Barcode", "000000000120"); // New barcode
            newBarcode.put("UoMEntry", 9); // Unit of Measure Entry
            newBarcode.put("FreeText", "120 Temax");//FreeText -dont know what it does

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

    @RequestMapping(value = "addBarcodeWithUoM")
    public String addBarcodeWithUoM(ModelMap modelMap) {
        try {
            String itemCode = "1272";  // The item to which we add barcodes
            String apiUrl = BASE_URL + "/Items('" + itemCode + "')";

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // 1. Retrieve existing item data
            HttpURLConnection getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject existingData = sapApiClient.getJsonResponse(getConn);
            System.out.println("Existing Item Data: " + existingData.toString(2));

            // 2. Get the UoM Group Entry for the item
            int uomGroupEntry = existingData.optInt("UoMGroupEntry");
            if (uomGroupEntry == 0) {
                modelMap.addAttribute("message", "UoM Group Entry is missing!");
                return "/sap/sapDashboard";
            }

            // 3. Retrieve the UoM Group details
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

            // 4. Add missing UoMs (UoMEntry = 2, 3)
            boolean hasUoM2 = false, hasUoM3 = false;
            JSONArray updatedUoMEntries = new JSONArray();

            for (int i = 0; i < uomEntries.length(); i++) {
                JSONObject uom = uomEntries.getJSONObject(i);
                int uomEntry = uom.optInt("AlternateUoM");
                updatedUoMEntries.put(uom);  // Retain existing UoMs

                if (uomEntry == 2) {
                    hasUoM2 = true;
                }
                if (uomEntry == 3) {
                    hasUoM3 = true;
                }
            }

            // Add missing UoMs without modifying BaseUoM
            if (!hasUoM2) {
                JSONObject uom2 = new JSONObject();
                uom2.put("AlternateUoM", 2);
                uom2.put("BaseQuantity", 1);
                uom2.put("AlternateQuantity", 1);
                uom2.put("WeightFactor", 0);  // Keep same as existing entry
                uom2.put("Active", "tYES");   // Required field
                uom2.put("UdfFactor", -1);    // Required field
                updatedUoMEntries.put(uom2);
            }

            if (!hasUoM3) {
                JSONObject uom3 = new JSONObject();
                uom3.put("AlternateUoM", 3);
                uom3.put("BaseQuantity", 1);
                uom3.put("AlternateQuantity", 1);
                uom3.put("WeightFactor", 0);
                uom3.put("Active", "tYES");
                uom3.put("UdfFactor", -1);
                updatedUoMEntries.put(uom3);
            }

            JSONObject updatedUoMGroup = new JSONObject();
            updatedUoMGroup.put("UoMGroupDefinitionCollection", updatedUoMEntries);
            updatedUoMGroup.put("BaseUoM", uomGroupData.optInt("BaseUoM"));  // Preserve BaseUoM
            updatedUoMGroup.put("Code", uomGroupData.optString("Code", "DefaultUoMCode"));
            updatedUoMGroup.put("Name", uomGroupData.optString("Name", "Default Name"));
            // 5. Send PATCH request to update UoM Group
            HttpURLConnection updateUomConn = sapApiClient.createConnection(uomGroupUrl, "POST");
            updateUomConn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

            updateUomConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            updateUomConn.setRequestProperty("Content-Type", "application/json");

            System.out.println("Updated UoM Group JSON: " + updatedUoMGroup.toString(2));

            sapApiClient.sendRequestBody(updateUomConn, updatedUoMGroup.toString());

            int updateUomResponse = updateUomConn.getResponseCode();
            if (updateUomResponse != 200 && updateUomResponse != 204) {
                modelMap.addAttribute("message", "Failed to update UoM Group: " + sapApiClient.getErrorResponse(updateUomConn));
                return "/sap/sapDashboard";
            }

            // 6. Retrieve updated item data
            getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }
            existingData = sapApiClient.getJsonResponse(getConn);

            // 7. Add Barcodes linked to UoMs
            JSONArray barcodesArray = existingData.optJSONArray("ItemBarCodeCollection");
            if (barcodesArray == null) {
                barcodesArray = new JSONArray();
            }

            barcodesArray.put(new JSONObject().put("Barcode", "0000000000000005").put("UoMEntry", 1));
            barcodesArray.put(new JSONObject().put("Barcode", "0000000000000006").put("UoMEntry", 2));
            barcodesArray.put(new JSONObject().put("Barcode", "0000000000000007").put("UoMEntry", 3));

            JSONObject updatedItem = new JSONObject();
            updatedItem.put("ItemBarCodeCollection", barcodesArray);

            HttpURLConnection barcodeConn = sapApiClient.createConnection(apiUrl, "POST");
            barcodeConn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

            barcodeConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            barcodeConn.setRequestProperty("Content-Type", "application/json");
            sapApiClient.sendRequestBody(barcodeConn, updatedItem.toString());

            int barcodeResponse = barcodeConn.getResponseCode();
            if (barcodeResponse == 204 || barcodeResponse == 200) {
                modelMap.addAttribute("message", "Barcodes added successfully!");
            } else {
                modelMap.addAttribute("message", "Failed to add barcodes: " + sapApiClient.getErrorResponse(barcodeConn));
            }

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "addPalletBarcode")
    public String addPalletBarcode(ModelMap modelMap) {
        try {
            String itemCode = "1271"; // Replace with your item code
            String apiUrl = BASE_URL + "/Items('" + itemCode + "')";

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // 1. Retrieve existing item data
            HttpURLConnection getConn = sapApiClient.createConnection(apiUrl, "GET");
            getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            getConn.setRequestProperty("Accept", "application/json");

            try {
                sapApiClient.applySSLBypass(getConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject existingData = sapApiClient.getJsonResponse(getConn);
            System.out.println("Existing Item Data: " + existingData.toString(2));

            // 2. Prepare the barcode payload for the pallet
            JSONArray barcodesArray = new JSONArray();

            // Add barcode for the pallet (UoMEntry = 9)
            barcodesArray.put(new JSONObject()
                    .put("Barcode", "000000000120") // Replace with your barcode
                    .put("UoMEntry", 9)); // UoMEntry for the pallet

            // 3. Update the item with the new barcode
            JSONObject updatedItem = new JSONObject();
            updatedItem.put("ItemBarCodeCollection", barcodesArray);

            // Log the payload for debugging
            System.out.println("Updated Item Payload: " + updatedItem.toString(2));

            // Send the PATCH request to update the item
            HttpURLConnection barcodeConn = sapApiClient.createConnection(apiUrl, "POST");
            barcodeConn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

            barcodeConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            barcodeConn.setRequestProperty("Content-Type", "application/json");

            try {
                sapApiClient.applySSLBypass(barcodeConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }

            sapApiClient.sendRequestBody(barcodeConn, updatedItem.toString());

            // Check the response
            int barcodeResponse = barcodeConn.getResponseCode();
            if (barcodeResponse == 204 || barcodeResponse == 200) {
                System.out.println("Barcode for pallet added successfully!");
                modelMap.addAttribute("message", "Barcode for pallet added successfully!");
            } else {
                String errorResponse = sapApiClient.getErrorResponse(barcodeConn);
                System.err.println("Failed to add barcode for pallet: " + errorResponse);
                modelMap.addAttribute("message", "Failed to add barcode for pallet: " + errorResponse);
            }

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
            String apiUrl = BASE_URL + "/UnitOfMeasurementGroups";

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

            System.out.println("RESPONSE: " + existingData.toString(2)); // Pretty print JSON
            // modelMap.addAttribute("response", existingData.toString(2));
        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "getApiCallResponseGET")
    public String getApiCallResponse(@RequestParam(name = "apiText") String apiText, ModelMap modelMap) {
        try {
            String itemCode = "1271";  // The item to which we add barcodes
            String apiUrl = BASE_URL + "/" + apiText;

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

            System.out.println("RESPONSE: " + existingData.toString(2)); // Pretty print JSON
            // modelMap.addAttribute("response", existingData.toString(2));
        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "createUoMGroup")
    public String createUoMGroup(ModelMap modelMap) {
        try {
            String apiUrl = BASE_URL + "/UnitOfMeasurementGroups";

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // 1. Prepare the JSON payload
            JSONObject uomGroup = new JSONObject();
            uomGroup.put("Code", "ΤΕΜ2ΠΑΛ120");
            uomGroup.put("Name", "ΤΕΜΑΧΙΑ ΣΕ ΠΑΛΕΤΑ 120");
            uomGroup.put("BaseUoM", 1); // Base UoM: Piece (UoMEntry = 1)

            JSONArray uomDefinitions = new JSONArray();

            // Add base UoM (Piece)
            JSONObject baseUoM = new JSONObject();
            baseUoM.put("AlternateUoM", 1); // Piece
            baseUoM.put("BaseQuantity", 1); // 1 Piece = 1 Piece
            baseUoM.put("AlternateQuantity", 1);
            baseUoM.put("WeightFactor", 0);
            baseUoM.put("Active", "tYES");
            baseUoM.put("UdfFactor", -1);
            uomDefinitions.put(baseUoM);

            JSONObject alternateUoM_2 = new JSONObject();
            alternateUoM_2.put("AlternateUoM", 2); // Piece
            alternateUoM_2.put("BaseQuantity", 1); // 1 Piece = 1 Piece
            alternateUoM_2.put("AlternateQuantity", 1);
            alternateUoM_2.put("WeightFactor", 0);
            alternateUoM_2.put("Active", "tYES");
            alternateUoM_2.put("UdfFactor", -1);
            uomDefinitions.put(alternateUoM_2);

            JSONObject alternateUoM_3 = new JSONObject();
            alternateUoM_3.put("AlternateUoM", 3); // Piece
            alternateUoM_3.put("BaseQuantity", 1); // 1 Piece = 1 Piece
            alternateUoM_3.put("AlternateQuantity", 1);
            alternateUoM_3.put("WeightFactor", 0);
            alternateUoM_3.put("Active", "tYES");
            alternateUoM_3.put("UdfFactor", -1);
            uomDefinitions.put(alternateUoM_3);

            // Add Pallet UoM
            JSONObject palletUoM = new JSONObject();
            palletUoM.put("AlternateUoM", 9); // Pallet
            palletUoM.put("BaseQuantity", 120); // 1 Pallet = 120 Pieces
            palletUoM.put("AlternateQuantity", 1);
            palletUoM.put("WeightFactor", 0);
            palletUoM.put("Active", "tYES");
            palletUoM.put("UdfFactor", -1);
            uomDefinitions.put(palletUoM);

            uomGroup.put("UoMGroupDefinitionCollection", uomDefinitions);

            // 2. Send the POST request
            HttpURLConnection postConn = sapApiClient.createConnection(apiUrl, "POST");

            postConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
            postConn.setRequestProperty("Content-Type", "application/json");
            sapApiClient.sendRequestBody(postConn, uomGroup.toString());

            // 3. Check the response
            int responseCode = postConn.getResponseCode();
            if (responseCode == 201) { // 201 = Created
                System.out.println("UoM Group created successfully!");
                modelMap.addAttribute("message", "UoM Group created successfully!");
            } else {
                String errorResponse = sapApiClient.getErrorResponse(postConn);
                System.err.println("Failed to create UoM Group: " + errorResponse);
                modelMap.addAttribute("message", "Failed to create UoM Group: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "deleteUoMGroup")
    public String deleteUoMGroup(ModelMap modelMap) {
        try {
            int uomGroupAbsEntry = 55; // Replace with the AbsEntry of the UoM Group to delete
            String apiUrl = BASE_URL + "/UnitOfMeasurementGroups(" + uomGroupAbsEntry + ")";

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // 1. Send the DELETE request
            HttpURLConnection deleteConn = sapApiClient.createConnection(apiUrl, "DELETE");
            deleteConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

            // 2. Check the response
            try {
                sapApiClient.applySSLBypass(deleteConn);
            } catch (Exception ex) {
                Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            }
            int responseCode = deleteConn.getResponseCode();
            if (responseCode == 204) { // 204 = No Content (successful deletion)
                System.out.println("UoM Group deleted successfully!");
                modelMap.addAttribute("message", "UoM Group deleted successfully!");
            } else {
                String errorResponse = sapApiClient.getErrorResponse(deleteConn);
                System.err.println("Failed to delete UoM Group: " + errorResponse);
                modelMap.addAttribute("message", "Failed to delete UoM Group: " + errorResponse);
            }

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "assignUoMGroupToItem")

    public String assignUoMGroupToItem(ModelMap modelMap) {
        try {
            String itemCode = "1271"; // Replace with the item code
            int originalUoMGroupAbsEntry = 13; // AbsEntry of the original UoM Group
            int newUoMGroupAbsEntry = 52; // AbsEntry of the new UoM Group

            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();

            // Step 1: Retrieve the original UoM Group details
            JSONObject originalUoMGroup = getUoMGroupDetails(originalUoMGroupAbsEntry, sessionToken, sapApiClient);
            if (originalUoMGroup == null) {
                modelMap.addAttribute("message", "Failed to retrieve original UoM Group details.");
                return "/sap/sapDashboard";
            }

            // Step 2: Retrieve the new UoM Group details
            JSONObject newUoMGroup = getUoMGroupDetails(newUoMGroupAbsEntry, sessionToken, sapApiClient);
            if (newUoMGroup == null) {
                modelMap.addAttribute("message", "Failed to retrieve new UoM Group details.");
                return "/sap/sapDashboard";
            }

            // Step 3: Compare UoM definitions and update the new UoM Group if necessary
            JSONArray originalDefinitions = originalUoMGroup.getJSONArray("UoMGroupDefinitionCollection");
            JSONArray newDefinitions = newUoMGroup.getJSONArray("UoMGroupDefinitionCollection");

            boolean needsUpdate = false;
            for (int i = 0; i < originalDefinitions.length(); i++) {
                JSONObject originalDefinition = originalDefinitions.getJSONObject(i);
                if (!containsDefinition(newDefinitions, originalDefinition)) {
                    newDefinitions.put(originalDefinition); // Add missing definition
                    needsUpdate = true;
                }
            }

            if (needsUpdate) {
                // Update the new UoM Group with the missing definitions
                newUoMGroup.put("UoMGroupDefinitionCollection", newDefinitions);
                boolean updateSuccess = updateUoMGroup(newUoMGroupAbsEntry, newUoMGroup, sessionToken, sapApiClient);
                if (!updateSuccess) {
                    modelMap.addAttribute("message", "Failed to update new UoM Group with missing definitions.");
                    return "/sap/sapDashboard";
                }
            }

            // Step 4: Reassign the item to the new UoM Group
            boolean reassignSuccess = reassignItemToUoMGroup(itemCode, newUoMGroupAbsEntry, sessionToken, sapApiClient);
            if (reassignSuccess) {
                modelMap.addAttribute("message", "Item reassigned to UoM Group " + newUoMGroupAbsEntry + " successfully!");
            } else {
                modelMap.addAttribute("message", "Failed to reassign item to UoM Group.");
            }

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.addAttribute("message", "An error occurred: " + ex.getMessage());
        }

        return "/sap/sapDashboard";
    }

// Helper method to retrieve UoM Group details
    private JSONObject getUoMGroupDetails(int uomGroupAbsEntry, String sessionToken, SAPApiClient sapApiClient) throws IOException {
        String apiUrl = BASE_URL + "/UnitOfMeasurementGroups(" + uomGroupAbsEntry + ")";
        HttpURLConnection getConn = sapApiClient.createConnection(apiUrl, "GET");
        getConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);

        try {
            sapApiClient.applySSLBypass(getConn);
        } catch (Exception ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int responseCode = getConn.getResponseCode();
        if (responseCode == 200) {
            // Read the response body
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(getConn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            return new JSONObject(response.toString());
        } else {
            System.err.println("Failed to retrieve UoM Group details. Response code: " + responseCode + " : " + getConn.getResponseMessage());
            return null;
        }
    }

// Helper method to check if a UoM definition exists in a collection
    private boolean containsDefinition(JSONArray definitions, JSONObject targetDefinition) {
        for (int i = 0; i < definitions.length(); i++) {
            JSONObject definition = definitions.getJSONObject(i);
            if (definition.getInt("AlternateUoM") == targetDefinition.getInt("AlternateUoM")
                    && definition.getInt("BaseQuantity") == targetDefinition.getInt("BaseQuantity")
                    && definition.getInt("AlternateQuantity") == targetDefinition.getInt("AlternateQuantity")) {
                return true;
            }
        }
        return false;
    }

// Helper method to update a UoM Group
    private boolean updateUoMGroup(int uomGroupAbsEntry, JSONObject updatedUoMGroup, String sessionToken, SAPApiClient sapApiClient) throws IOException {
        String apiUrl = BASE_URL + "/UnitOfMeasurementGroups(" + uomGroupAbsEntry + ")";
        HttpURLConnection patchConn = sapApiClient.createConnection(apiUrl, "POST");
        patchConn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

        patchConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
        patchConn.setRequestProperty("Content-Type", "application/json");

        if (!updatedUoMGroup.has("Code")) {
            updatedUoMGroup.put("Code", "ΤΕΜ2ΠΑΛ120"); // Replace with the actual Code
        } else {
            String correctCode = "ΤΕΜ2ΠΑΛ120"; // Replace with the correct Code
            updatedUoMGroup.put("Code", correctCode); // Override the Code field}
        }
        updatedUoMGroup.put("Name", "ΤΕΜΑΧΙΑ ΣΕ ΠΑΛΕΤΑ 120"); // Mandatory field
        updatedUoMGroup.put("BaseUoM", 1); // Mandatory field (UoMEntry of the base UoM)
        try {
            sapApiClient.applySSLBypass(patchConn);
        } catch (Exception ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Send the request body
        patchConn.setDoOutput(true);
        try (OutputStream outputStream = patchConn.getOutputStream()) {
            outputStream.write(updatedUoMGroup.toString().getBytes());
        }

        int responseCode = patchConn.getResponseCode();
        if (responseCode == 204) {
            System.out.println("UoM Group updated successfully!");
            return true;
        } else {
            // Read the error response
            StringBuilder errorResponse = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(patchConn.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
            }
            System.err.println("Failed to update UoM Group: " + errorResponse.toString());
            return false;
        }
    }
    // Helper method to reassign an item to a new UoM Group

    private boolean reassignItemToUoMGroup(String itemCode, int newUoMGroupAbsEntry, String sessionToken, SAPApiClient sapApiClient) throws IOException {
        String apiUrl = BASE_URL + "/Items('" + itemCode + "')";

        JSONObject itemUpdate = new JSONObject();
        itemUpdate.put("UoMGroupEntry", newUoMGroupAbsEntry);

        HttpURLConnection patchConn = sapApiClient.createConnection(apiUrl, "POST");
        patchConn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

        patchConn.setRequestProperty("Cookie", "B1SESSION=" + sessionToken);
        patchConn.setRequestProperty("Content-Type", "application/json");

        // Send the request body
        try {
            sapApiClient.applySSLBypass(patchConn);
        } catch (Exception ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        patchConn.setDoOutput(true);
        try (OutputStream outputStream = patchConn.getOutputStream()) {
            outputStream.write(itemUpdate.toString().getBytes());
        }

        int responseCode = patchConn.getResponseCode();
        if (responseCode == 204) {
            System.out.println("Item reassigned to UoM Group " + newUoMGroupAbsEntry + " successfully!");
            return true;
        } else {
            // Read the error response
            StringBuilder errorResponse = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(patchConn.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
            }
            System.err.println("Failed to reassign item to UoM Group: " + errorResponse.toString());
            return false;
        }
    }

    @RequestMapping(value = "assignUoM9")
    public String assignUoM9() {
        try {
            SAPApiClient sapApiClient = new SAPApiClient();
            String sessionToken = sapApiClient.loginToSAP();
            SAPItemUoMUpdater s = new SAPItemUoMUpdater(sessionToken, "1271");
            s.update();

        } catch (IOException ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/sap/sapDashboard";
    }

    /////-----------------------------------
    @RequestMapping(value = "assignUoMToItem")
    public String assignUoMToItem(ModelMap modelMap) {
        try {
            String itemCode = "1271"; // The item to which we assign the UoM
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

            // 2. Get the existing UoM collection
            JSONArray uomArray = existingData.optJSONArray("ItemUnitOfMeasurementCollection");
            if (uomArray == null) {
                uomArray = new JSONArray();
            }

            // 3. Create the new UoM entry
            JSONObject newUoM = new JSONObject();
            newUoM.put("UoMEntry", 9); // Unit of Measure Entry (e.g., 9 for "Case")
            newUoM.put("UoMType", "iutSales"); // UoM Type (e.g., "iutSales" for Sales)
            newUoM.put("Weight1", 10); // Weight in the primary unit
            newUoM.put("Weight1Unit", 7); // Weight unit (e.g., 7 for kilograms)
            newUoM.put("Volume", 0); // Volume (if applicable)
            newUoM.put("VolumeUnit", 4); // Volume unit (e.g., 4 for liters)
            newUoM.put("Width1", 0); // Width in the primary unit
            newUoM.put("Width1Unit", JSONObject.NULL); // Width unit (if applicable)
            newUoM.put("Height1", 0); // Height in the primary unit
            newUoM.put("Height1Unit", JSONObject.NULL); // Height unit (if applicable)
            newUoM.put("Length1", 0); // Length in the primary unit
            newUoM.put("Length1Unit", JSONObject.NULL); // Length unit (if applicable)
            newUoM.put("DefaultBarcode", JSONObject.NULL); // Default barcode (if applicable)
            newUoM.put("ItemUoMPackageCollection", new JSONArray()); // Empty package collection

            uomArray.put(newUoM); // Append new UoM to the array

            // 4. Create the updated JSON payload
            JSONObject updatedItem = new JSONObject();
            updatedItem.put("ItemUnitOfMeasurementCollection", uomArray);
            String jsonBody = updatedItem.toString();

            // 5. Send MERGE request (PATCH not supported, so we use MERGE)
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
                System.out.println("Response: Empty Response");
                message = "UoM assigned successfully!";
            } else if (responseCode == 200 || responseCode == 201) {
                System.out.println("Response: " + sapApiClient.getJsonResponse(conn));
                message = "UoM assigned successfully!";
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
}
