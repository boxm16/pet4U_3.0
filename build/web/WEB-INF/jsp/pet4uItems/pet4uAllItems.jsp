<%-- 
    Document   : camelot
    Created on : Mar 1, 2023, 10:29:43 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="BasicModel.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot: All Items</title>
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
        <h1><a href="pet4uAllItemsOneLine.htm">Show Items With Only One Altercode</a></h1>
        <h1><a href="pet4uItemsWithPosition.htm">Show Items With Position </a></h1>

        <table>

            <th>Position</th>
            <th>Altercode</th>
            <th>Description</th>
            <th>State</th>
            <th>Stock</th>

            <tbody>
                <%
                    LinkedHashMap<String, Item> items = (LinkedHashMap) request.getAttribute("pet4uAllItems");
                    for (Map.Entry<String, Item> entrySet : items.entrySet()) {
                        Item item = entrySet.getValue();

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(item.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
                        for (AltercodeContainer altercodeContainer : altercodes) {
                            if (altercodeContainer.getStatus().equals("eshop")
                                    || altercodeContainer.getStatus().equals("eshop-on")
                                    || altercodeContainer.getStatus().equals("eshop-barf")
                                    || altercodeContainer.getStatus().equals("eshop-pro")) {

                                out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercodeContainer.getAltercode() + "' target='_blank'>" + altercodeContainer.getAltercode() + " : " + altercodeContainer.getStatus() + "</a>");
                                out.println("<br>");
                            } else {
                                out.println(altercodeContainer.getAltercode() + "</strong>");
                                out.println("<br>");
                            }
                        }
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getState());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getQuantity());
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>
            </tbody>
        </table>
    </center>
</body>
</html>
