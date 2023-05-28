
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Inventory.InventoryItem"%>
<%@page import="java.util.TreeMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Display</title>
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
                text-align: left;
                background: #eee;
                position: sticky;
                top: 0px;
            }

        </style>
    </head>
    <body>
    <center>
        <h1><a href="index.htm">INDEX</a></h1>
        <h1>Inventories Display</h1>
        <hr>

        <table>
            <thead>
            <th>Date</th>
            <th>Time</th>
            <th>Altercode</th>
            <th>Description</th>

            <th>System Stock</th>
            <th>Real Stock</th>
            <th>Note</th>

            </thead>
            <%
                LinkedHashMap<String, InventoryItem> items = (LinkedHashMap) request.getAttribute("inventories");
                for (Map.Entry<String, InventoryItem> entrySet : items.entrySet()) {
                    InventoryItem inventoryItem = entrySet.getValue();

                    out.println("<tr>");
                    out.println("<td>");
                    out.println(inventoryItem.getDateStampString());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getTimeStampString());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getCode());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getSystemStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getRealStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getNote());
                    out.println("</td>");
                    /*

                    out.println("<td>");
                    out.println("<a href='goForEditingCamelotItemOfInterest.htm?code=" + camelotItemOfInterest.getCode() + "'>Edit</a>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='itemSnapshots.htm?code=" + camelotItemOfInterest.getCode() + "' target='_blank'>Show Day Rest Snapshots</a>");
                    out.println("</td>");
                     */
                    out.println("</tr>");

                }
            %>
        </table>

    </center>
</body>
</html>
