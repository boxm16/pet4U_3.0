package SAP;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class SAPApiClientX {

    private static final String BASE_URL = "https://192.168.0.183:50000/b1s/v2";
    private static final String ITEM_CODE = "1271";
    private static final String SESSION_ID = "your-session-id"; // Replace with actual session ID

    public void push() {
        try {
            // Step 1: Assign UoM2 to the Item
            assignUoM2ToItem(ITEM_CODE);

            // Step 2: Add Barcode with UoM2
            addBarcodeToItem(ITEM_CODE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Function to assign UoM2 to Item
    public void assignUoM2ToItem(String itemCode) throws Exception {
        URL url = new URL(BASE_URL + "/Items('" + itemCode + "')");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");  // First set to POST
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");  // Trick server

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Cookie", "B1SESSION=" + SESSION_ID);
        conn.setDoOutput(true);

        // JSON Payload to assign UoM2
        JSONObject payload = new JSONObject();
        JSONArray uomCollection = new JSONArray();

        // Add UoM1
        JSONObject uom1 = new JSONObject();
        uom1.put("UoMType", "iutInventory");
        uom1.put("UoMEntry", 1);
        uom1.put("DefaultBarcode", 2805);
        uomCollection.put(uom1);

        // Add UoM2
        JSONObject uom2 = new JSONObject();
        uom2.put("UoMType", "iutSales");
        uom2.put("UoMEntry", 2);
        uom2.put("DefaultBarcode", "");
        uomCollection.put(uom2);

        payload.put("ItemUnitOfMeasurementCollection", uomCollection);

        // Send PATCH request
        OutputStream os = conn.getOutputStream();
        os.write(payload.toString().getBytes());
        os.flush();
        os.close();

        if (conn.getResponseCode() == 204) {
            System.out.println("✅ UoM2 Assigned Successfully!");
        } else {
            Scanner scanner = new Scanner(conn.getErrorStream());
            String errorResponse = scanner.useDelimiter("\\A").next();
            scanner.close();
            System.out.println("❌ Error in UoM Assignment: " + errorResponse);
        }
    }

    // Function to add Barcode to Item
    public static void addBarcodeToItem(String itemCode) throws Exception {
        URL url = new URL(BASE_URL + "/Items('" + itemCode + "')");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");  // First set to POST
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");  // Trick server
        
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Cookie", "B1SESSION=" + SESSION_ID);
        conn.setDoOutput(true);

        // JSON Payload to add barcode
        JSONObject payload = new JSONObject();
        JSONArray barcodeCollection = new JSONArray();

        // New Barcode
        JSONObject newBarcode = new JSONObject();
        newBarcode.put("AbsEntry", 2805);
        newBarcode.put("UoMEntry", 2);
        newBarcode.put("Barcode", "00000500001006");
        newBarcode.put("FreeText", "alba");

        barcodeCollection.put(newBarcode);
        payload.put("ItemBarCodeCollection", barcodeCollection);

        // Send PATCH request
        OutputStream os = conn.getOutputStream();
        os.write(payload.toString().getBytes());
        os.flush();
        os.close();

        if (conn.getResponseCode() == 204) {
            System.out.println("✅ Barcode Added Successfully!");
        } else {
            Scanner scanner = new Scanner(conn.getErrorStream());
            String errorResponse = scanner.useDelimiter("\\A").next();
            scanner.close();
            System.out.println("❌ Error in Barcode Addition: " + errorResponse);
        }
    }
}
