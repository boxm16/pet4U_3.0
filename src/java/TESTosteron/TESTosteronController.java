package TESTosteron;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import CamelotItemsOfInterest.CamelotDao;
import Inventory.InventoryItem;
import Notes.NotesDao;
import Pet4uItems.Pet4uItemsDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TESTosteronController {

    @RequestMapping(value = "testosteronDashboard")
    public String testosteronDashboard(HttpSession session) {
        String user = (String) session.getAttribute("user");
        System.out.println("Super User Status:" + user);
        if (user == null) {
            return "errorPage";
        } else if (user.equals(
                "identified")) {
            return "testosteron/testosteronDashboard";
        } else {
            return "errorPage";
        }
    }

    @RequestMapping(value = "testViewAndDeepSearch")
    public String testViewAndDeepSearch() {
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();

        TESTosteronDao testosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allPet4UItemsWithDeepSearch = testosteronDao.getAllPet4UItemsWithDeepSearch();
        for (Map.Entry<String, Item> pet4uAllItemsEntry : pet4uAllItems.entrySet()) {
            String codeEx = pet4uAllItemsEntry.getKey();
            Item value1 = pet4uAllItemsEntry.getValue();
            Item v2 = allPet4UItemsWithDeepSearch.remove(codeEx);
            if (v2 == null) {
                System.out.println("SHOUT, NULL IN DEEP SEARCH " + codeEx + "-DESCRIPTION-" + value1.getDescription() + "+POSITION+" + value1.getPosition());

            } else {
                if (!value1.getQuantity().equals(v2.getQuantity())) {
                    System.out.println("QYT " + codeEx + "--" + value1.getDescription() + "++" + value1.getQuantity() + "X" + v2.getQuantity());

                }
            }

        }

        System.out.println("LEFT OVERS: " + allPet4UItemsWithDeepSearch.size());
        for (Map.Entry<String, Item> pet4uAllItemsEntry : allPet4UItemsWithDeepSearch.entrySet()) {
            System.out.println(pet4uAllItemsEntry.getValue().getCode()
                    + "="
                    + pet4uAllItemsEntry.getValue().getDescription()
                    + "-"
                    + pet4uAllItemsEntry.getValue().getPosition()
                    + "+"
                    + pet4uAllItemsEntry.getValue().getPosition());

        }
        System.out.println("TEST COMPLETED: RESULT SEE ABOVE");
        return "testosteron/testResult";
    }

    @RequestMapping(value = "camelotStockPositonsTesting")
    public String camelotStockPositonsTesting(ModelMap modelMap) {
        CamelotDao camelotDao = new CamelotDao();
        LinkedHashMap<String, Item> camelotAllItems = camelotDao.getCamelotItems();

        NotesDao notesDao = new NotesDao();
        int index = 0;
        for (Map.Entry<String, Item> caiEntrySet : camelotAllItems.entrySet()) {
            String result = notesDao.saveCamelotNote(caiEntrySet.getValue().getCode(), "dokimastiko");
            System.out.println(index + " : " + caiEntrySet.getValue().getCode() + " : " + result);
            index++;
        }
        modelMap.addAttribute("camelotAllItems", camelotAllItems);
        return "testosteron/camelotAllItemsNotedTestResult";
    }

    @RequestMapping(value = "showAllDeletedCamelotNotesBatches")
    public String showAllDeletedCamelotNotesBatches(ModelMap modelMap) {
        NotesDao notesDao = new NotesDao();
        LinkedHashMap<String, Integer> deletedCamelotNotesBatches = notesDao.getAllDeletedCamelotNotesBatches();
        modelMap.addAttribute("deletedCamelotNotesBatches", deletedCamelotNotesBatches);
        return "testosteron/allDeletedCamelotNotesBatches";
    }

    @RequestMapping(value = "showDeletedCamelotNotesBatch")
    public String showDeletedCamelotNotesBatch(@RequestParam(name = "batch") String batch, ModelMap modelMap) {

        NotesDao notesDao = new NotesDao();
        ArrayList<InventoryItem> notes = notesDao.getDeletedCamelotNotesBatch(batch);

        modelMap.addAttribute("notes", notes);

        return "testosteron/deletedCamelotNotesBatch";
    }

    //-------------------------------------
    @RequestMapping(value = "printOut")
    public String printOut(ModelMap modelMap) {

        /* 
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("Number of print services: " + printServices.length);
        DocPrintJob job = null;
        boolean printerCheck = false;
        String printerMessage = "PRINTER MESSAGE:";

        for (PrintService printer : printServices) {
            System.out.println("Printer: " + printer.getName());

            if (printer.getName().equals("HP LaserJet Pro MFP M127-M128 PCLmS")) {
                System.out.println("----------");
                System.out.println("Printer: " + printer.getName());

                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                InputStream inputStream = new ByteArrayInputStream(printerMessage.getBytes());
                Doc doc = new SimpleDoc(inputStream, flavor, null);
                job = printer.createPrintJob();
                try {
                    job.print(doc, null);
                } catch (PrintException ex) {
                    Logger.getLogger(TESTosteronController.class.getName()).log(Level.SEVERE, null, ex);
                }
                printerCheck = true;
            }
            //  }

        }
         */
        String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        PrintWithoutDialog pwd = new PrintWithoutDialog();
        pwd.printSomething(printName);
        return "testosteron/trc";
    }

    @RequestMapping(value = "showShadowCodes")
    public String showShadowCodes(ModelMap modelMap) {
        TESTosteronDao dao = new TESTosteronDao();
        LinkedHashMap<String, Item> allActiveItems = dao.getAllActiveItems();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = dao.getPet4UItemsRowByRow();
        for (Map.Entry<String, Item> allActiveItemsEntry : allActiveItems.entrySet()) {
            Item item = allActiveItemsEntry.getValue();
            ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
            for (AltercodeContainer altercodeContainer : altercodes) {
                String altercode = altercodeContainer.getAltercode();
                if (altercode.contains("-")) {
                    if (altercode.equals(item.getCode())) {
                        continue;
                    }
                    char firstChar = altercode.charAt(0);
                    char lastChar = altercode.charAt(altercode.length() - 1);
                    if (firstChar == '-' || lastChar == '-') {
                        //    System.out.println(item.getCode() + "   " + item.getDescription() + "   " + altercode);
                        String repfactoredAltercode = altercode.replaceAll("-", "");
                        Item shadowItem = pet4UItemsRowByRow.get(repfactoredAltercode);
                        System.out.println("     Item:" + item.getCode() + " " + item.getDescription());
                        if (shadowItem == null) {
                            System.out.println("No Shadow Item");
                        } else {
                            System.out.println("Shadow Item:" + shadowItem.getCode() + " " + shadowItem.getDescription());
                        }
                        System.out.println("----------------------------------------");
                    }

                }
            }
        }
        return "testosteron/shadowCodes";
    }

    @RequestMapping(value = "testSapHanaDB")
    public String testSapHanaDB(ModelMap modelMap) {
        TESTosteronDao tESTosteronDao = new TESTosteronDao();
        ArrayList<String> allSapHanaDatabases = tESTosteronDao.getAllSapHanaDatabases();

        return "testosteron/testosteronDashboard";
    }

    @RequestMapping(value = "getItemsFromSapHanaDB")
    public String getItemsFromSapHanaDB(ModelMap modelMap) {
        TESTosteronDao tESTosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allItems = tESTosteronDao.getItemsFromSapHanaDB();
        modelMap.addAttribute("allItems", allItems);
        return "testosteron/itemsFromSapHana";
    }

    @RequestMapping(value = "cccSApHANA")
    public String cccSApHANA(ModelMap modelMap) {

        try {

            // Set up the URL and the PATCH request
            URL url = new URL("https://192.168.0.183:50000/b1s/v2/sml.svc/ItemBins");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set HTTP method to PATCH (this is the workaround)
            connection.setRequestMethod("POST");

            // Set custom header to tell the server it's a PATCH
            connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");

            // Enable input/output streams for sending and receiving data
            connection.setDoOutput(true);

            // Set the required headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic c2Nhbm5lcjE6MTIzNA==");  // Base64 encoded username:password

            // Create the JSON body for the PATCH request
            String jsonInputString = "{\"ItemCode\": \"1271\", \"WarehouseCode\": \"PETCAMELOT_UAT2\", \"PickLocation\": \"A17\", \"NewPickLocation\": \"A02\"}";

            // Write the JSON data to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code and handle the response
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read and print the response from the server
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String inputLine;
                StringBuffer response = new StringBuffer();

                try {
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TESTosteronController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Response: " + response.toString());
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(TESTosteronController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TESTosteronController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(TESTosteronController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    @RequestMapping(value = "cccSApHANA1")
    public String cccSApHANA1(ModelMap modelMap) {

        try {
            // 🔹 Credentials
            String username = "scanner1";
            String password = "1234";
            String companyDB = "PETCAMELOT_UAT2";

            // 🔹 Encode credentials for Basic Authentication
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + encodedAuth;

            // 🔹 API URL (Direct Call)
            String itemCode = "1271";  // Item Code to update
            String newPickLocation = "A27";  // New Pick Location
            String apiUrl = "https://192.168.0.183:50000/b1s/v2/Items('" + itemCode + "')";

            // 🔹 JSON Body
            String jsonBody = "{ \"U_PickLocation\": \"" + newPickLocation + "\" }";

            // 🔹 Create HTTP Connection
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            applySSLBypass(conn); // Ignore SSL for local network

            conn.setRequestMethod("POST"); // Override to POST
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", authHeaderValue); // 🔥 Direct Credentials
            conn.setRequestProperty("CompanyDB", companyDB); // SAP Business One DB
            conn.setDoOutput(true);

            // 🔹 Write JSON data
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            }

            // 🔹 Get Response
            int responseCode = conn.getResponseCode();
            System.out.println("✅ Response Code: " + responseCode);

            // 🔹 Read Response (if any)
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    private static String getSessionId(String username, String password, String companyDB) {
        try {

            String loginUrl = "https://192.168.0.183:50000/b1s/v2/Login";
            String loginPayload = "{ \"UserName\": \"" + username + "\", \"Password\": \"" + password + "\", \"CompanyDB\": \"" + companyDB + "\" }";

            HttpURLConnection conn = (HttpURLConnection) new URL(loginUrl).openConnection();
            applySSLBypass(conn);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Write login JSON payload
            try (OutputStream os = conn.getOutputStream()) {
                os.write(loginPayload.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {

                // Parse JSON response
                /*  try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    System.out.println("🚨 API ERROR RESPONSE: " + errorResponse.toString());
                }*/
            } else {
                System.out.println("❌ Login failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Login failed
    }

    private static void applySSLBypass(HttpURLConnection conn) throws Exception {
        if (conn instanceof HttpsURLConnection) {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new java.security.SecureRandom());

            ((HttpsURLConnection) conn).setSSLSocketFactory(sslContext.getSocketFactory());
            ((HttpsURLConnection) conn).setHostnameVerifier((hostname, session) -> hostname.equals("192.168.0.183"));
        }
    }

    // Encode username & password to Base64 for Basic Authentication
    private static String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    //+++++
    @RequestMapping(value = "itemFromSAPApit")
    public String itemFromSAPApit(ModelMap modelMap) {

        return "testosteron/itemFromSapApi";
    }
}
