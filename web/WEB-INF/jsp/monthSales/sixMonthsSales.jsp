<%-- 
    Document   : sixMonthsSales
    Created on : Nov 6, 2023, 10:16:22 PM
    Author     : Michail Sitmalidis
--%>

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
                    LinkedHashMap<String, ItemEksagoges> sales = (LinkedHashMap) request.getAttribute("eksagoges");

                    for (Map.Entry<String, ItemEksagoges> entrySet : sales.entrySet()) {
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

                        out.println("</tr>");
                    }
                %>

            </tbody>
        </table>

    </body>
</html>
