
<%@page import="java.util.ArrayList"%>
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
                font-size: 15px;
            }
            th{
                font-size: 20px;
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
        <h1>ΑΠΟΓΡΑΦΕΣ</h1>
        <hr>

        <table>
            <thead>
            <th>Date</th>
            <th>Time</th>
            <th>Altercode</th>
            <th>Description</th>
            <th>Real<br>Stock</th>
            <th>System<br>Stock</th>
            <th>Diff.</th>
            </thead>
            <%
                ArrayList<InventoryItem> items = (ArrayList) request.getAttribute("inventories");
                for (InventoryItem inventoryItem : items) {

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
                    out.println(inventoryItem.getRealStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getSystemStock());
                    out.println("</td>");

                    out.println("<td>");

                    try {
                        double systemStockInte = Double.parseDouble(inventoryItem.getSystemStock());
                        double realStockInte = Double.parseDouble(inventoryItem.getRealStock());
                        out.println(realStockInte - systemStockInte);

                    } catch (NumberFormatException ex) {
                        out.println("Conversion not possible");
                    }
                    out.println("</td>");

                    out.println("</tr>");

                }
            %>
        </table>

    </center>

</body>
</html>
