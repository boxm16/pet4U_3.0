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
                    <th>A/A</th>
                    <th>Order Id</th>
                    <th>Order Type</th>
                    <th>Creation DateTimeStamp</th>
                    <th>Creation User</th>
                    <th>Order DateTimeStamp</th>
                    <th>Order Number</th>
                    <th>Items</th>


                </tr>
            </thead>
            <tbody>


                <%
                    LinkedHashMap<Integer, Order> orders = (LinkedHashMap) request.getAttribute("orders");

                    String position = (String) request.getAttribute("position");
                    String itemCode = (String) request.getAttribute("itemCode");
                    int orderCount = 1;
                    for (Map.Entry<Integer, Order> entrySet : orders.entrySet()) {
                        out.println("<tr>");

                        out.println("<td>");
                        out.println(orderCount);
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getId());
                        out.println("</td>");

                        if (entrySet.getValue().getType().equals("ΚΑΠΔ")) {
                            out.println("<td bgcolor='green'>");
                        } else if (entrySet.getValue().getType().equals("ΚΔΑΤ1")) {
                            out.println("<td bgcolor='lightgreen'>");
                        } else if (entrySet.getValue().getType().equals("ΚΑΕΛ")
                                || entrySet.getValue().getType().equals("ΚΠΤΔ1")) {
                            out.println("<td bgcolor='pink'>");
                        } else {
                            out.println("<td>");
                        }

                        out.println(entrySet.getValue().getType());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getCreationDateTime());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getCreationUser());
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

                            if (itemEntrySet.getValue().getCode().equals(itemCode)) {
                                out.println("<td style='background-color:#8ff56c; width:200px'>");
                            } else {
                                out.println("<td style='width:200px'>");
                            }
                            out.println(itemEntrySet.getValue().getCode());
                            out.println("</td>");

                            out.println("<td style='width:350px'>");
                            out.println(itemEntrySet.getValue().getDescription());
                            out.println("</td>");

                            out.println("<td style='width:150px'>");
                            out.println(itemEntrySet.getValue().getQuantity());
                            out.println("</td>");

                            if (itemEntrySet.getValue().getPosition().contains(position)) {
                                out.println("<td style='background-color:#8ff56c; width:200px'>");
                            } else {
                                out.println("<td style='width:200px'>");
                            }
                            out.println(itemEntrySet.getValue().getPosition());
                            out.println("</td>");

                            out.println("</tr>");
                        }
                        out.println("</table>");
                        out.println("</td>");

                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("<td colspan='8'>");
                        out.println("----");
                        out.println("</td>");
                        out.println("<tr>");
                        orderCount++;
                    }
                %>
        </table>
    </center>
</body>
</html>
