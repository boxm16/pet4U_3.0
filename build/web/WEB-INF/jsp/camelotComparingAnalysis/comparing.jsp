<%-- 
    Document   : sales
    Created on : Oct 23, 2022, 10:27:10 PM
    Author     : Michail Sitmalidis
--%>


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
        <title>Six Months Sales</title>
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
                    <th>Stock Now</th>
                    <th>Total <br> Sales </th>
                    <th>Shop <br> Sales </th>
                    <th>Varibobi <br> Sales </th>
                    <!--     <th>Total<br> Sales In <br>Pieces </th>-->

                    <th>SALES GRAPHICAL</th>
                </tr>
            </thead>
            <tbody>


                <%
                    ArrayList<SoldItem3> sales = (ArrayList) request.getAttribute("camelotSoldItemsArray");

                    for (SoldItem3 soldItem : sales) {
                        out.println("<tr>");

                        out.println("<td>");
                        if (soldItem.getCode() == null) {
                            out.println("");
                        } else {
                            out.println("<a  href = 'camelotItemAnalysis.htm?code=" + soldItem.getCode() + "'  class ='btn btn-info btn-lg' role='button' aria-disabled='true' target='_blank'><h3>" + entrySet.getValue().getCode() + "</h3></a>");

                        }

                        out.println("</td>");

                        out.println("<td>");
                        out.println(soldItem.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(soldItem.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(soldItem.getQuantity());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(soldItem.getTotalSales());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(soldItem.getShopSales());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(soldItem.getVaribobiSales());
                        out.println("</td>");

                        out.println("<td>");

                        out.println("<svg width='" + soldItem.getEshopSales() + "' height='30'>");
                        out.println("<rect width='" + soldItem.getEshopSales() + "' height='30' style='fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)' />");
                        out.println("</svg>");

                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>

            </tbody>
        </table>
    </body>
</html>
