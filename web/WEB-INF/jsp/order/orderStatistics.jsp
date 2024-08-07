<%-- 
    Document   : orderStatistics
    Created on : Jul 23, 2024, 10:46:21 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders Statistica</title>
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
                    <th>Codes Quantity</th>
                    <th>Count</th>
                    <th>%</th>

                </tr>
            </thead>
            <tbody>


                <%
                    TreeMap<Integer, Integer> codesQuantityInOrders = (TreeMap) request.getAttribute("codesQuantityInOrders");
                    Integer totalOrders = (Integer) request.getAttribute("totalOrders");
                    for (Map.Entry<Integer, Integer> entrySet : codesQuantityInOrders.entrySet()) {
                        double d = Double.valueOf(100) * Double.valueOf(entrySet.getValue()) / Double.valueOf(totalOrders);
                        out.println("<tr>");

                        out.println("<td>");
                        out.println("<a href='getOrdersWithCodeQuantity.htm?quantity=" + entrySet.getKey() + "' target='_blank'>" + entrySet.getKey() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(d);
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>
        </table>
    </center>
</body>
</html>
