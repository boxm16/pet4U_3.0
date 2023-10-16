<%-- 
    Document   : itemSnapshots
    Created on : Mar 16, 2023, 10:57:36 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pet4U Item Snapshots</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 20px;
            }
            th{
                font-size: 30px;
                font-weight: bold;
            }

        </style>
    </head>
    <body>
    <center>
        <h1><a href="index.htm">INDEX</a></h1>
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
    </center>
</body>
</html>
