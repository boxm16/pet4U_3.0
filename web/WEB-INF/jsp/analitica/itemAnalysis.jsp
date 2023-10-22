<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="BasicModel.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h1>${itemCode}</h1>

        <h1>Pet4U Item Snapshot: ${code}</h1>
        <hr>
        <table>
            <th>Date Stamp</th>
            <th>State</th>
            <th>Quantity</th>
                <%
                    LinkedHashMap<String, Item> itemSnapshots = (LinkedHashMap) request.getAttribute("itemSnapshots");
                    for (Map.Entry<String, Item> itemSnapshotEntry : itemSnapshots.entrySet()) {
                        Item itemSnapshot = itemSnapshotEntry.getValue();
                        out.println("<tr>");

                        out.println("<td>");
                        out.println(itemSnapshotEntry.getKey());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(itemSnapshot.getState());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(itemSnapshot.getQuantity());
                        out.println("</td>");

                        out.println("</tr>");

                    }
                %>
        </table>
    </body>
</html>
