<%-- 
    Document   : sales
    Created on : Oct 23, 2022, 10:27:10 PM
    Author     : Michail Sitmalidis
--%>


<%@page import="java.util.LinkedHashMap"%>
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
        <title>Day Stock Shortage</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
                font-size: 20px;
            }

        </style>
    </head>
    <body>
        <h1>Day Stock Shortage</h1>
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
                    <th>Eshop <br> Sales<br>For 1 Day</th>
                    <!--   <th>Shop <br>Supply </th>
                    <th>Total<br> Sales </th>
                    <!--     <th>Total<br> Sales In <br>Pieces </th>-->
                    <th>Stock Now</th>
                </tr>
            </thead>
            <tbody>


                <%
                    LinkedHashMap<String, SoldItem> sales = (LinkedHashMap) request.getAttribute("stockShortage");

                    for (Map.Entry<String, SoldItem> entrySet : sales.entrySet()) {
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
                        for (AltercodeContainer altercodeContainer : altercodes) {
                            if (altercodeContainer.getStatus().equals("eshop")
                                    || altercodeContainer.getStatus().equals("eshop-on")
                                    || altercodeContainer.getStatus().equals("eshop-barf")
                                    || altercodeContainer.getStatus().equals("eshop-pro")) {

                                out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercodeContainer.getAltercode() + "' target='_blank'>" + altercodeContainer.getAltercode() + " : " + altercodeContainer.getStatus() + "</a>");
                                out.println("<br>");
                            }

                        }
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getState());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(String.format("%.2f", entrySet.getValue().getEshopSales()));
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getQunatityAsPieces());
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>

            </tbody>
        </table>
    </body>
</html>
