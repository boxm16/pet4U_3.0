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
                    HashMap<String, ItemSales> itemSales = (HashMap) request.getAttribute("sales");

                    for (Map.Entry<String, ItemSales> entrySet : itemSales.entrySet()) {
                        out.println("<tr>");

                        out.println("<td>");
                        if (entrySet.getValue().getCode() == null) {
                            out.println("");
                        } else {
                            out.println(entrySet.getValue().getCode());
                        }

                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        ArrayList<AltercodeContainer> altercodes = entrySet.getValue().getAltercodes();
                        for (AltercodeContainer altercode : altercodes) {
                            if (altercode.getStatus().equals("eshop")
                                    || altercode.getStatus().equals("eshop-on")
                                    || altercode.getStatus().equals("eshop-barf")
                                    || altercode.getStatus().equals("eshop-pro")) {

                                out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercode.getAltercode() + "' target='_blank'>" + altercode.getAltercode() + "</a>");
                                out.println("<br>");
                            }

                        }
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getState());
                        out.println("</td>");

                        TreeMap<LocalDate, Sales> sales = entrySet.getValue().getSales();

                        for (Map.Entry<LocalDate, Sales> salesEntry : sales.entrySet()) {
                            Sales s = salesEntry.getValue();
                            out.println("<td>");
                            out.println(s);
                            out.println("</td>");
                        }
                        /*
                        out.println("<td>");
                        out.println(entrySet.getValue().getEshopSoldPieces());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getShopsSuppliedPieces());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getTotalShippedPieces());
                        out.println("</td>");

                        out.println("<td>");

                        out.println("<svg width='" + entrySet.getValue().getTotalShippedPieces() + "' height='30'>");
                        out.println("<rect width='" + entrySet.getValue().getTotalShippedPieces() + "' height='30' style='fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)' />");
                        out.println("</svg>");

                        out.println("</td>");
                         */
                        out.println("</tr>");
                    }
                %>

            </tbody>
        </table>
    </body>
</html>
