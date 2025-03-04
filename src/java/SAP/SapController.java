package SAP;

import TESTosteron.SAPApiClient;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
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
    private String createSupplier() throws IOException {
        String apiUrl = BASE_URL + "/BusinessPartners";
        String jsonBody = "{\"CardCode\": \"c1\", "
                + " \"CardName\": \"customer c1\", "
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
        return "/sap/sapDashboard";
    }

    @RequestMapping(value = "createItem")
    private String createItem() throws IOException {
        // Define the API endpoint for creating items
        String apiUrl = BASE_URL + "/Items";

        // JSON body for creating an item
        String jsonBody = "{"
                + "\"ItemCode\": \"0001\", "
                + "\"ItemName\": \"Sample Item\", "
                + "\"ItemsGroupCode\": 100, "
                + "\"SalesUnit\": \"PCS\", "
                + "\"PurchaseUnit\": \"PCS\", "
                + "\"InventoryItem\": \"tYES\", "
                + "\"SalesItem\": \"tYES\", "
                + "\"PurchaseItem\": \"tYES\""
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

        // Handle the response
        if (responseCode == 200 || responseCode == 201) {
            System.out.println("Response: " + sapApiClient.getJsonResponse(conn));
        } else if (responseCode == 401) {
            System.out.println("Session expired! Please re-login.");
        } else {
            System.out.println("Error Response: " + sapApiClient.getErrorResponse(conn));
        }

        // Return the view name (assuming this is part of a Spring MVC controller)
        return "/sap/sapDashboard";
    }

}
