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
            <th>Α/Α</th>
            <th>Position</th>
            <th>Code</th>
            <th>Description</th>
            <th>State</th>
            <th>Stock</th>
            <th>Disabled</th>
            <th>Camelot Data</th>


            <tbody>
                <%
                    int index = 1;
                    LinkedHashMap<String, Item> items = (LinkedHashMap) request.getAttribute("items");
                    LinkedHashMap<String, Item> camelotItems = (LinkedHashMap) request.getAttribute("camelotItems");

                    for (Map.Entry<String, Item> entrySet : items.entrySet()) {
                        Item item = entrySet.getValue();

                        out.println("<tr>");
                        out.println("<td>");
                        out.println(index);
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

                        Item camelotItem = camelotItems.get(item.getCode());
                        String camelotStock = "0.000000";
                        String status = "inherited";
                        if (camelotItem == null) {
                            camelotStock = "Not Cam. Item";
                            status = "green";
                        } else {
                            camelotStock = camelotItem.getQuantity();
                            if (camelotStock == null) {
                                camelotStock = "NULL";
                            } else if (camelotStock.equals("0")
                                    || camelotStock.equals("0.0")
                                    || camelotStock.equals("0.000000")) {
                                status = "#F7B2F7";

                                if (camelotItem.isDisabled()) {
                                    status = "red";
                                }
                            }
                        }

                        out.println("<td style='background-color: " + status + "'>");
                        out.println(camelotStock);
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
