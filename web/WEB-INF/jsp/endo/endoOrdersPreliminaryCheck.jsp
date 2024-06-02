
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
        <title>'Not For Endo' Of Todays Endo Orders</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 30px;
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
        <h1>'Not For Endo' Of Todays Endo Orders</h1>
        <hr>

        <table>
            <thead>
            <th>Destination:Item Code</th>

             </thead>
            <%
                ArrayList<String> items = (ArrayList) request.getAttribute("notForEndosForTheseOrders");

                for (String item : items) {

                    out.println("<tr>");

                    out.println("<td>");
                    out.println(item);
                    out.println("</td>");

                    out.println("</tr>");

                }

            %>
        </table>
        <hr>



    </center>




</body>
</html>
