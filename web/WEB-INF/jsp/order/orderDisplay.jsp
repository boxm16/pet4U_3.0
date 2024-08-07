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
        <title>Order Display</title>
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
        <hr>
        <a href="adminIndex.htm"><h1>Admin Index</h1></a>
        <hr>
        <table style="font-size:20px">
            <tr>  <td>Order Id</td><td>${order.id}</td></tr>
            <tr>  <td>Order Number</td><td>${order.number}</td></tr>
            <tr>  <td>Creation Time Stamp</td><td>${order.creationDateTime}</td></tr>
            <tr>  <td>Sale DateTimeStamp</td><td>${order.dateTimeStamp}</td></tr>
            <tr>  <td>Creation User</td><td>${order.creationUser}</td></tr>


        </table>
        <hr>
        <table style="font-size:20px">

            <tbody>

                <%
                    Order order = (Order) request.getAttribute("order");

                    LinkedHashMap<String, Item> items = order.getItems();
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

                %>
            </tbody>
        </table>
    </center>
</body>
</html>
