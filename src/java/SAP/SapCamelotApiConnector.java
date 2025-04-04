package SAP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

public class SapCamelotApiConnector {

    private static final String BASE_URL = "https://192.168.0.183:50000/b1s/v2";
    private static String SESSION_ID = null; // Initialize as null
    private final String USERNAME = "scanner1";
    private final String PASSWORD = "1234";
    private final String COMPANY_DB = "PETCAMELOT_UAT2";

    public SapCamelotApiConnector() {
        this.login();
    }

    public String login() {
        if (SESSION_ID != null && !SESSION_ID.isEmpty()) {
            System.out.println("Already Logged in. Session ID: " + SESSION_ID);
            return SESSION_ID;
        }

        try {

            String endPoint = "/Login";
            String loginPayload = String.format(
                    "{ \"UserName\": \"%s\", \"Password\": \"%s\", \"CompanyDB\": \"%s\" }",
                    USERNAME, PASSWORD, COMPANY_DB
            );

            HttpURLConnection conn = createConnection(endPoint, "POST");

            try {
                applySSLBypass(conn);
            } catch (Exception ex) {
                Logger.getLogger(SAPApiClientX.class.getName()).log(Level.SEVERE, null, ex);
            }

            sendRequestBody(conn, loginPayload);

            int responseCode = conn.getResponseCode();
            System.out.println("? Login Response Code: " + responseCode);

            // Inspect headers AFTER sending the request
            logResponseHeaders(conn);

            if (responseCode == 200) {
                JSONObject jsonResponse = getJsonResponse(conn);
                //    System.out.println(jsonResponse);
                SESSION_ID = jsonResponse.getString("SessionId");
                System.out.println(" Logged in. Session ID: " + SESSION_ID);
                return SESSION_ID;
            } else {
                System.out.println("Login failed. Response: " + getErrorResponse(conn));
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(SapCamelotApiConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SESSION_ID;
    }
    // 🔹 Utility: Create HTTP Connection

    public HttpURLConnection createConnection(String endPoint, String method) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(BASE_URL + endPoint).openConnection();

        if (method.equals("PATCH")) {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH"); //Important!
        } else {
            conn.setRequestMethod(method);
        }
        conn.setRequestProperty("Cookie", "B1SESSION=" + SESSION_ID);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        return conn;
    }

    public void sendRequestBody(HttpURLConnection conn, String jsonBody) throws IOException {

        try {
            applySSLBypass(conn);
        } catch (Exception ex) {
            Logger.getLogger(SapCamelotApiConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }
    }

    // 🔹 Utility: Read Error Response
    public String getErrorResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    public JSONObject getJsonResponse(HttpURLConnection conn) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return new JSONObject(response.toString());
        }
    }

    public void applySSLBypass(HttpURLConnection conn) throws Exception {
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

    public void logResponseHeaders(HttpURLConnection conn) {
        System.out.println("Response Headers:");
        conn.getHeaderFields().forEach((key, values) -> {
            System.out.println(key + ": " + String.join(", ", values));
        });
    }
    
    
}
