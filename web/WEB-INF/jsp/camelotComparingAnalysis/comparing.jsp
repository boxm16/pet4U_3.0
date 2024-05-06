<%-- 
    Document   : sales
    Created on : Oct 23, 2022, 10:27:10 PM
    Author     : Michail Sitmalidis
--%>


<%@page import="java.util.LinkedHashMap"%>
<%@page import="CamelotComparingAnalysis.SoldItem3"%>
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
        <title>Camelot Comparing Analysis</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 25px;
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
        <h1>Camelot Comparing Analysis</h1>
        <h1>Comparing Analysis Period 2023-7-1 -----     2024-4-18</h1>
        <table style="font-size:20px">
            <thead>
                <tr> 
                    <th>CODE</th>
                    <th>POSITION</th>
                    <th>DESCRIPTION</th>
                    <th>Stock Now</th>
                    <th>Last 6 <br> Months Sales</th>
                    <th>Comparing Analysis<br>Total Sales </th>
                    <th>Shop <br> Sales </th>
                    <th>Varibobi <br> Sales </th>
                    <th>Outer <br> Sales </th>
                    <!--     <th>Total<br> Sales In <br>Pieces </th>-->
                </tr>
            </thead>
            <tbody>


                <%
                    LinkedHashMap<String, SoldItem3> sales = (LinkedHashMap) request.getAttribute("camelotSoldItemsArray");

                    for (Map.Entry<String, SoldItem3> entrySet : sales.entrySet()) {
                        out.println("<tr>");

                        out.println("<td>");
                        if (entrySet.getValue().getCode() == null) {
                            out.println("");
                        } else {
                            out.println("<a  href = 'camelotItemAnalysis.htm?code=" + entrySet.getValue().getCode() + "'  class ='btn btn-info btn-lg' role='button' aria-disabled='true' target='_blank'><h3>" + entrySet.getValue().getCode() + "</h3></a>");

                        }

                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getPosition());
                        out.println("</td>");

                        out.println("<td style='font-size: 20px;'>");
                        out.println(entrySet.getValue().getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getQuantity());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getSixMonthsSales());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getTotalSales());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getShopSales());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getVaribobiSales());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getTotalSales() - (entrySet.getValue().getShopSales() + entrySet.getValue().getVaribobiSales()));
                        out.println("</td>");

                        out.println("<td>");

                        out.println("<svg width='" + entrySet.getValue().getEshopSales() + "' height='30'>");
                        out.println("<rect width='" + entrySet.getValue().getEshopSales() + "' height='30' style='fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)' />");
                        out.println("</svg>");

                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>

            </tbody>
        </table>
    </center>
</body>
</html>
