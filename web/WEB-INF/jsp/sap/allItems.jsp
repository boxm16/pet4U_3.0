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
        <title>SAP CAMELOT All Items</title>
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

        <table>
            <th>A/A</th>
            <th>Item Code</th>
            <th>Position</th>
            <th>Altercode</th>
            <th>Description</th>

            <th>Stock</th>


            <tbody>
                <%
                    int index = 1;
                    LinkedHashMap<String, Item> items = (LinkedHashMap) request.getAttribute("items");
                    for (Map.Entry<String, Item> entrySet : items.entrySet()) {
                        Item item = entrySet.getValue();

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(index);
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getCode());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
                        for (AltercodeContainer altercodeContainer : altercodes) {

                            out.println(altercodeContainer.getAltercode() + ":" + altercodeContainer.getStatus() + "</strong>");
                            out.println("<br>");
                        }

                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getQuantity());
                        out.println("</td>");

                        out.println("</tr>");
                        index++;
                    }
                %>
            </tbody>
        </table>
    </center>
</body>
</html>
