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

public class SAPItemUoMUpdater {

    private static final String BASE_URL = "https://192.168.0.183:50000/b1s/v2";  // Replace with your SAP Business One Service Layer URL
    private String ITEM_CODE;
    private String b1SessionId;

    public SAPItemUoMUpdater(String b1SessionId, String itemCode) {
        this.b1SessionId = b1SessionId;
        this.ITEM_CODE = itemCode;

    }

    public void update() {
        try {
            addItemUoM(ITEM_CODE, 9, b1SessionId);  // UoM 9 for all types
        } catch (IOException e) {
            System.err.println("Error adding UoM: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addItemUoM(String itemCode, int uomEntry, String b1SessionId) throws IOException {
        String apiUrl = BASE_URL + "/Items('" + itemCode + "')";

        // 1. Get the existing item data
        JSONObject itemJson = getItem(itemCode, b1SessionId);
        if (itemJson == null) {
            System.err.println("Failed to retrieve item data for item code: " + itemCode);
            return;
        }
        String purchaseVATGroup = itemJson.getString("PurchaseVATGroup");

        // 2. Add UoM 9 entries to ItemUnitOfMeasurementCollection
        JSONArray uomCollection = itemJson.getJSONArray("ItemUnitOfMeasurementCollection");

        // Create and add the new UoM entries (Inventory, Purchasing, Sales)
        JSONObject newUomEntryInv = createUoMEntry(uomEntry, "iutInventory");
        JSONObject newUomEntryPur = createUoMEntry(uomEntry, "iutPurchasing");
        JSONObject newUomEntrySales = createUoMEntry(uomEntry, "iutSales");

        uomCollection.put(newUomEntryInv);
        uomCollection.put(newUomEntryPur);
        uomCollection.put(newUomEntrySales);

        itemJson.put("ItemUnitOfMeasurementCollection", uomCollection);

        // 3. PATCH the item with the updated ItemUnitOfMeasurementCollection
        boolean success = patchItem(itemCode, itemJson, b1SessionId, purchaseVATGroup);
        if (success) {
            System.out.println("Successfully added UoM " + uomEntry + " to item " + itemCode);
        } else {
            System.err.println("Failed to add UoM " + uomEntry + " to item " + itemCode);
        }
    }

    private static JSONObject getItem(String itemCode, String b1SessionId) throws IOException {
        String apiUrl = BASE_URL + "/Items('" + itemCode + "')";

        SAPApiClient sapApiClient = new SAPApiClient();

        HttpURLConnection conn = sapApiClient.createConnection(apiUrl, "GET");
        conn.setRequestProperty("Cookie", "B1SESSION=" + b1SessionId);
        conn.setRequestProperty("Content-Type", "application/json"); // Necessary for some SAP B1 versions

        try {
            sapApiClient.applySSLBypass(conn);
        } catch (Exception ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return new JSONObject(response.toString());
            }
        } else {
            System.err.println("GET request failed with response code: " + responseCode);
            return null;
        }
    }

    private static JSONObject createUoMEntry(int uomEntry, String uomType) {
        JSONObject newUomEntry = new JSONObject();
        newUomEntry.put("Width2", 0);
        newUomEntry.put("Width2Unit", JSONObject.NULL);
        newUomEntry.put("UoMEntry", uomEntry);
        newUomEntry.put("Length2Unit", JSONObject.NULL);
        newUomEntry.put("DefaultPackage", JSONObject.NULL);
        newUomEntry.put("Height1Unit", JSONObject.NULL);
        newUomEntry.put("DefaultBarcode", JSONObject.NULL);  //  Consider setting an appropriate value, or use JSONObject.NULL
        newUomEntry.put("Width1", 0);
        newUomEntry.put("Length1Unit", JSONObject.NULL);
        newUomEntry.put("Weight1Unit", JSONObject.NULL);
        newUomEntry.put("UoMType", uomType);
        newUomEntry.put("Width1Unit", JSONObject.NULL);
        newUomEntry.put("ItemUoMPackageCollection", new JSONArray());
        newUomEntry.put("Volume", 0);
        newUomEntry.put("Weight2", 0);
        newUomEntry.put("Height2Unit", JSONObject.NULL);
        newUomEntry.put("Weight1", 0);
        newUomEntry.put("Height1", 0);
        newUomEntry.put("Height2", 0);
        newUomEntry.put("VolumeUnit", 4); //  Check if this value is correct!
        newUomEntry.put("Length1", 0);
        newUomEntry.put("Length2", 0);
        newUomEntry.put("Weight2Unit", JSONObject.NULL);

        return newUomEntry;
    }

    private static boolean patchItem(String itemCode, JSONObject itemJson, String b1SessionId, String purchaseVATGroup) throws IOException {
        String apiUrl = BASE_URL + "/Items('" + itemCode + "')";
        JSONObject patchPayload = new JSONObject();
        patchPayload.put("ItemUnitOfMeasurementCollection", itemJson.getJSONArray("ItemUnitOfMeasurementCollection"));
        patchPayload.put("PurchaseVATGroup", purchaseVATGroup);
        SAPApiClient sapApiClient = new SAPApiClient();

        HttpURLConnection conn = sapApiClient.createConnection(apiUrl, "POST");
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); //Important!
        conn.setRequestProperty("Cookie", "B1SESSION=" + b1SessionId);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        try {
            sapApiClient.applySSLBypass(conn);
        } catch (Exception ex) {
            Logger.getLogger(SapController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = itemJson.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {  // 204 No Content is success for PATCH
            return true;
        } else {
            // Handle error response
            System.err.println("PATCH request failed with response code: " + responseCode);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
                System.err.println("Error response body: " + errorResponse.toString());
            }
            return false;
        }
    }
}
