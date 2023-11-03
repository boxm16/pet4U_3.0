<%-- 
    Document   : itemSnapshots
    Created on : Mar 16, 2023, 10:57:36 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="CamelotItemsOfInterest.CamelotItemSnapshot"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Items Snapshots</title>
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
        <h1>Camelot  Item Snapshot: ${code}</h1>
        <h1><a href='camelotItemsOfOurInterestDashboard.htm'>Go Back To Dashboard</a></h1>
        <hr>
        <table>
            <th>Date Stamp</th>
            <th>Quantity</th>
                <%
                    ArrayList<CamelotItemSnapshot> itemSnapshots = (ArrayList) request.getAttribute("itemSnapshots");
                    double stockBefore = 0.0;
                    for (CamelotItemSnapshot itemSnapshot : itemSnapshots) {
                        Double stock = Double.parseDouble(itemSnapshot.getQuantity());
                        out.println("<tr>");

                        out.println("<td>");
                        out.println(itemSnapshot.getDateStamp());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(itemSnapshot.getQuantity());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(stock - stockBefore);
                        out.println("</td>");

                        out.println("</tr>");
                        stockBefore = stock;

                    }
                %>
        </table>
    </center>
</body>
</html>
