package Camelot.CamelotItem;

import static Service.StaticsDispatcher.COMPANY_DB;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONObject;

public class CamelotItemPositionChangingSapApiClient {

    // 🔹 SAP Business One API credentials
    private final String BASE_URL = "https://192.168.0.183:50000/b1s/v2";
    private final String USERNAME = "scanner2";
    private final String PASSWORD = "1234";


    public String change(String itemCode, String position) {
        try {
            // Step 1: Login and get session token
            String sessionId = loginToSAP();
            if (sessionId == null) {
                System.out.println(" Login failed. Exiting.");
                return "Login failed. Exiting.";
            }

            // Step 2: Use session token to update item pick location
            updateItem(sessionId, itemCode, position);

            // Step 3: Logout to end session (optional)
          //  logoutFromSAP(sessionId);

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "DONE";
    }

    // 🔹 Step 1: Login and get session token
    private String loginToSAP() throws IOException {

        String loginUrl = BASE_URL + "/Login";
        String loginPayload = String.format(
                "{ \"UserName\": \"%s\", \"Password\": \"%s\", \"CompanyDB\": \"%s\" }",
                USERNAME, PASSWORD, COMPANY_DB
        );

        HttpURLConnection conn = createConnection(loginUrl, "POST");

        try {
            applySSLBypass(conn);
        } catch (Exception ex) {
            Logger.getLogger(CamelotItemPositionChangingSapApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        sendRequestBody(conn, loginPayload);

        int responseCode = conn.getResponseCode();
        System.out.println("? Login Response Code: " + responseCode);

        if (responseCode == 200) {
            JSONObject jsonResponse = getJsonResponse(conn);
            String sessionId = jsonResponse.getString("SessionId");
            System.out.println(" Logged in. Session ID: " + sessionId);
            return sessionId;
        } else {
            System.out.println("Login failed. Response: " + getErrorResponse(conn));
            return null;
        }
    }

    // 🔹 Step 2: Update Item Pick Location using session
    private void updateItem(String sessionId, String itemCode, String newPickLocation) throws IOException {
        String apiUrl = BASE_URL + "/Items('" + itemCode + "')";
        String jsonBody = "{ \"U_PickLocation\": \"" + newPickLocation + "\" }";

        HttpURLConnection conn = createConnection(apiUrl, "POST");

        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Trick server into treating this as PATCH

        conn.setRequestProperty("Cookie", "B1SESSION=" + sessionId);
        sendRequestBody(conn, jsonBody);

        int responseCode = conn.getResponseCode();
        System.out.println("? Update Response Code: " + responseCode);

        if (responseCode == 200 || responseCode == 204) {
            System.out.println(" Item updated successfully");
        } else if (responseCode == 401) {
            System.out.println(" Session expired! Please re-login.");
        } else {
            System.out.println(" Update failed. Response: " + getErrorResponse(conn));
        }
    }

    // 🔹 Step 3: Logout (optional)
    private void logoutFromSAP(String sessionId) throws IOException {
        String logoutUrl = BASE_URL + "/Logout";

        HttpURLConnection conn = createConnection(logoutUrl, "POST");

        try {
            applySSLBypass(conn);
        } catch (Exception ex) {
            Logger.getLogger(CamelotItemPositionChangingSapApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        conn.setRequestProperty("Cookie", "B1SESSION=" + sessionId);

        int responseCode = conn.getResponseCode();
        System.out.println(" Logout Response Code: " + responseCode);

        if (responseCode == 204) {
            System.out.println(" Logged out successfully.");
        } else {
            System.out.println(" Logout failed. Response: " + getErrorResponse(conn));
        }
    }

    // 🔹 Utility: Create HTTP Connection
    private HttpURLConnection createConnection(String url, String method) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        return conn;
    }

    // 🔹 Utility: Send JSON Request Body
    private void sendRequestBody(HttpURLConnection conn, String jsonBody) throws IOException {

        try {
            applySSLBypass(conn);
        } catch (Exception ex) {
            Logger.getLogger(CamelotItemPositionChangingSapApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }
    }

    // 🔹 Utility: Read JSON Response
    private JSONObject getJsonResponse(HttpURLConnection conn) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return new JSONObject(response.toString());
        }
    }

    // 🔹 Utility: Read Error Response
    private String getErrorResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private void applySSLBypass(HttpURLConnection conn) throws Exception {
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

}
