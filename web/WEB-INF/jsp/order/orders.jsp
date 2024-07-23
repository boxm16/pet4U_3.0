<%-- 
    Document   : orders
    Created on : Jul 24, 2024, 12:39:09 AM
    Author     : Michail Sitmalidis
--%>

<%@page import="BasicModel.Item"%>
<%@page import="java.util.Map"%>
<%@page import="Order.Order"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
                font-size: 20px;
            }

        </style>
    </head>
    <body>
    <center>
        <table style="font-size:20px">
            <thead>
                <tr> 
                    <th>Order Id</th>
                    <th>Order DateTimeStamp</th>
                    <th>Order Number</th>
                    <th>Items</th>


                </tr>
            </thead>
            <tbody>


                <%
                    LinkedHashMap<Integer, Order> orders = (LinkedHashMap) request.getAttribute("orders");

                    for (Map.Entry<Integer, Order> entrySet : orders.entrySet()) {
                        out.println("<tr>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getId());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getDateTimeStamp());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getNumber());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<table>");
                        LinkedHashMap<String, Item> items = entrySet.getValue().getItems();
                        for (Map.Entry<String, Item> itemEntrySet : items.entrySet()) {
                            out.println("<tr>");

                            out.println("<td style='width:150px'>");
                            out.println(itemEntrySet.getValue().getCode());
                            out.println("</td>");

                            out.println("<td style='width:350px'>");
                            out.println(itemEntrySet.getValue().getDescription());
                            out.println("</td>");

                            out.println("<td style='width:150px'>");
                            out.println(itemEntrySet.getValue().getQuantity());
                            out.println("</td>");

                            out.println("<td style='width:200px'>");
                            out.println(itemEntrySet.getValue().getPosition());
                            out.println("</td>");

                            out.println("</tr>");
                        }
                        out.println("</table>");
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>
        </table>
    </center>
</body>
</html>
