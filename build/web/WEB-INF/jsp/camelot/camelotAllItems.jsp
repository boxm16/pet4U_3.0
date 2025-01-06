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
        <table>
            <th>A/A</th>
            <th>Item Code</th>
            <th>Description</th>
            <th>Position</th>
            <th>Stock</th>
            <th>Disabled</th>
            <tbody>
                <%
                    LinkedHashMap<String, Item> items = (LinkedHashMap) request.getAttribute("camelotAllItems");
                    int index = 1;
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
                        out.println(item.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getPosition());
                        out.println("</td>");

                        if (item.getQuantity() == null) {
                            out.println("<td>");
                        } else {
                            if (item.getQuantity().equals("0")
                                    || item.getQuantity().equals("0.0")
                                    || item.getQuantity().equals("0.000000")) {
                                out.println("<td style='background-color: #F7B2F7'>");
                            } else {
                                out.println("<td>");
                            }
                        }
                        out.println(item.getQuantity());
                        out.println("</td>");

                        if (item.isDisabled()) {
                            out.println("<td style='background-color: red'>");
                        } else {
                            out.println("<td>");
                        }

                        out.println(item.isDisabled());
                        out.println("</td>");
                        out.println("</tr>");
                        index++;
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
