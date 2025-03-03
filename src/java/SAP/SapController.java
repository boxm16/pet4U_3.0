package SAP;

import TESTosteron.SAPApiClient;
import java.io.IOException;
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

}
