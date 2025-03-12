<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Pretty-Print JSON</title>
        <script>
            function prettyPrintJson(jsonString) {
                try {
                    // Parse the JSON string into an object
                    const jsonObject = JSON.parse(jsonString);
                    // Convert it back to a string with indentation
                    return JSON.stringify(jsonObject, null, 4); // 4 spaces for indentation
                } catch (e) {
                    return "Invalid JSON: " + e.message;
                }
            }

            // Call the function when the page loads
            window.onload = function () {
                const jsonResponse = `${response}`; // Get the JSON string from JSP
                const prettyJson = prettyPrintJson(jsonResponse); // Format it
                document.getElementById("json-output").innerText = prettyJson; // Display it
            };
        </script>
    </head>
    <body>
        <h1>Pretty-Printed JSON</h1>
        <pre id="json-output"></pre> <!-- Display formatted JSON here -->
    </body>
</html>