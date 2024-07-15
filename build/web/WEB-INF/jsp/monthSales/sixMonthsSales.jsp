<%-- 
    Document   : sixMonthsSales
    Created on : Nov 6, 2023, 10:16:22 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="MonthSales.EksagogesB"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="MonthSales.ItemEksagoges"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Εξαγωγες</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
                font-size: 20px;
            }

        </style>
    </head>
    <body>
        <table style="font-size:20px">
            <thead>
                <tr> 
                    <th>CODE</th>
                    <th>POSITION</th>
                    <th>DESCRIPTION</th>

                    <!--  <th>M.U.<br> UNIT</th> -->
                    <!--     <th>COEF.</th>-->
                    <th>State </th>
                    <th>Eshop <br> Sales </th>
                    <th>Shop <br>Supply </th>
                    <th>Total<br> Sales </th>
                    <!--     <th>Total<br> Sales In <br>Pieces </th>-->

                    <th>SALES GRAPHICAL</th>
                </tr>
            </thead>
            <tbody>


                <%
                    LinkedHashMap<String, ItemEksagoges> sales = (LinkedHashMap) request.getAttribute("eksagoges");

                    for (Map.Entry<String, ItemEksagoges> entrySet : sales.entrySet()) {

                        if (entrySet.getValue().getPosition().equals("")) {
                            continue;
                        }
                        out.println("<tr>");

                        out.println("<td>");
                        if (entrySet.getValue().getCode() == null) {
                            out.println("");
                        } else {
                            out.println("<a href='itemAnalysis.htm?code=" + entrySet.getValue().getCode() + "' target='_blank'>" + entrySet.getValue().getCode() + "</a>");

                        }

                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getState());
                        out.println("</td>");

                        EksagogesB eksagoges = entrySet.getValue().getEksagogesForLastMonths(6);

                        out.println("<td>");
                        out.println(eksagoges.getEshopSales());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(eksagoges.getShopsSupply());
                        out.println("</td>");

                        double totalEksagoges = eksagoges.getEshopSales() + eksagoges.getShopsSupply();
                        out.println("<td>");
                        out.println(totalEksagoges);
                        out.println("</td>");

                        out.println("<td>");

                        out.println("<svg width='" + totalEksagoges + "' height='30'>");
                        out.println("<rect width='" + totalEksagoges + "' height='30' style='fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)' />");
                        out.println("</svg>");

                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>

            </tbody>
        </table>

    </body>
</html>
