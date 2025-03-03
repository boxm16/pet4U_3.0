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

        if (responseCode == 200) {
            System.out.println(" Response : " + sapApiClient.getJsonResponse(conn));
        } else if (responseCode == 401) {
            System.out.println(" Session expired! Please re-login.");
        } else {
            System.out.println("  Response: " + sapApiClient.getErrorResponse(conn));
        }
        return "/sap/sapDashboard";
    }

}
