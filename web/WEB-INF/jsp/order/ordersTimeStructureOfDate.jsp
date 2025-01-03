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
        <title>Orders Time Structure</title>
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
        <h1>Orders Time Structure for ${date}</h1>
           <h1>Total orders ${total}</h1>
        <table style="font-size:20px">
            <thead>
                <tr> 
                    <th>Time</th>
                    <th>Count</th>
                    <th>%</th>

                </tr>
            </thead>
            <tbody>


                <%
                    LinkedHashMap<Integer, Integer> map = (LinkedHashMap) request.getAttribute("ordersTimeStrucuterOfDate");
                    Integer total = (Integer) request.getAttribute("total");
                    for (Map.Entry<Integer, Integer> entrySet : map.entrySet()) {
                        out.println("<tr>");

                        out.println("<td>");
                        out.println(entrySet.getKey());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue());
                        out.println("</td>");

                        out.println("<td>");
                        double d = Double.valueOf(100) * Double.valueOf(entrySet.getValue()) / Double.valueOf(total);
                        out.println(d);
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>
        </table>
        <hr><hr>
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
                    LinkedHashMap<String, Integer> map2 = (LinkedHashMap) request.getAttribute("ordersThreeLayersTimeStrucuterOfDate");
                  
                    for (Map.Entry<String, Integer> entrySet2 : map2.entrySet()) {
                        out.println("<tr>");

                        out.println("<td>");
                        out.println(entrySet2.getKey());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet2.getValue());
                        out.println("</td>");

                        out.println("<td>");
                        double d = Double.valueOf(100) * Double.valueOf(entrySet2.getValue()) / Double.valueOf(total);
                        out.println(d);
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>
        </table>
    </center>
</body>
</html>
