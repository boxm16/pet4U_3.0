<%-- 
    Document   : sales
    Created on : Oct 23, 2022, 10:27:10 PM
    Author     : Michail Sitmalidis
--%>


<%@page import="java.util.TreeMap"%>
<%@page import="MonthSales.Sales"%>
<%@page import="java.time.LocalDate"%>
<%@page import="MonthSales.ItemSales"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="SalesX.SoldItem"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Months Sales</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
                font-size: 20px;
            }

        </style>
    </head>
    <body>
        <h1>SALES</h1>
        <table font-size:20px">
            <thead>
                <tr> 
                    <th>CODE</th>
                    <th>POSITION</th>
                    <th>DESCRIPTION</th>
                    <th>E-Shop Links</th>
                    <th>State </th>

                </tr>
            </thead>
            <tbody>


                <%
                    HashMap<String, ItemSales> itemSales = (HashMap) request.getAttribute("sales");

                    for (Map.Entry<String, ItemSales> entrySet : itemSales.entrySet()) {
                        out.println("<tr>");

                        out.println("<td>");
                        if (entrySet.getValue().getCode() == null) {
                            out.println("");
                        } else {
                            out.println("<a href='itemAnalysis.htm?code=" + entrySet.getValue().getCode() + "' target='_blank'>"+entrySet.getValue().getCode()+"</a>");
                        }

                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getDescription());
                        out.println("</td>");

                        out.println("<td>");

                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getState());
                        out.println("</td>");

                        TreeMap<LocalDate, Sales> sales = entrySet.getValue().getSales();

                        for (Map.Entry<LocalDate, Sales> salesEntry : sales.entrySet()) {
                            Sales s = salesEntry.getValue();
                            out.println("<td>");
                            out.println(s.getEshopSales());
                            out.println("</td>");
                        }

                        out.println("</tr>");
                    }
                %>

            </tbody>
        </table>
    </body>
</html>
