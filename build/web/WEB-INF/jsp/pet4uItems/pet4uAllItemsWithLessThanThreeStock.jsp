<%-- 
    Document   : camelot
    Created on : Mar 1, 2023, 10:29:43 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="Endo.EndoPackaging"%>
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
        <title>Pet4U: All Items One Line</title>
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
        <h1><a href="pet4uAllItems.htm">Show Full Version</a></h1>

        <table>
            <th>A/A</th>
            <th>Position</th>
            <th>Altercode</th>
            <th>Description</th>
            <th>State</th>
            <th>Stock</th>



            <tbody>
                <%
                    int x = 0;
                    LinkedHashMap<String, Item> items = (LinkedHashMap) request.getAttribute("items");
                    for (Map.Entry<String, Item> entrySet : items.entrySet()) {

                        Item item = entrySet.getValue();
                        String quantityString = item.getQuantity();
                        double quantity = Double.parseDouble(quantityString);
                        if (quantity == 0 || quantity > 3 || item.getPosition().equals("")) {
                            continue;
                        }
                        x++;
                        out.println("<tr>");

                        out.println("<td>");
                        out.println(x);
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<a href='itemAnalysis.htm?code=" + item.getCode() + "' target='_blank'>" + item.getCode() + "</a>");
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
