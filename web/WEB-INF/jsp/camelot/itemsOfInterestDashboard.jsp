<%-- 
    Document   : itemsOfInterest
    Created on : Feb 8, 2023, 11:18:39 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="CamelotItemsOfInterest.CamelotItemOfInterest"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot: Items Of Our Interest</title>
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
        <h1>Camelot: Items Of Our Interest</h1>
        <h1><a href="goForAddingItemOfInterest.htm">Add Item Of Interest</a></h1>
        <hr>
        <p>Pet4U Items Reference File <strong style="background-color:greenyellow"> ${pet4UItemsReferenceFile}.</strong> Camelot Items Reference File <strong style="background-color:greenyellow">${camelotItemsReferenceFile}</strong></p>
        <table>
            <th>Owner</th>
            <th>Position</th>
            <th>Altercode</th>
            <th>Pet4u Description</th>
            <th>Minimal Stock</th>
            <th>Pet4u Stock</th>
            <th>Camelot Free Stock</th>
            <th>Order Unit</th>
            <th>Order Quantity</th>
            <th>Order Quantity Items</th>
            <th>  Edit  </th>
                <%
                    ArrayList<CamelotItemOfInterest> camelotItemsOfInterest = (ArrayList) request.getAttribute("camelotItemsOfInterest");
                    for (CamelotItemOfInterest camelotItemOfInterest : camelotItemsOfInterest) {
                        String alarmColor = "";
                        int minimalStock = camelotItemOfInterest.getMinimalStock();
                        int pet4uStock = camelotItemOfInterest.getPet4uStock() / camelotItemOfInterest.getWeightCoefficient();
                        int camelotFreeStock = camelotItemOfInterest.getCamelotStock() - camelotItemOfInterest.getCamelotBinded();
                        if (pet4uStock < minimalStock * 2) {
                            alarmColor = "yellow";
                        }
                        if (pet4uStock < minimalStock) {
                            alarmColor = "red";
                        }
                        if (camelotItemOfInterest.getCamelotStock() == 0) {
                            alarmColor = "#9D0EA9";
                        }
                        out.println("<tr style='background-color: " + alarmColor + "'>");
                        out.println("<td>");
                        out.println(camelotItemOfInterest.getOwner());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotItemOfInterest.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotItemOfInterest.getCode());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotItemOfInterest.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotItemOfInterest.getMinimalStock());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotItemOfInterest.getPet4uStock() / camelotItemOfInterest.getWeightCoefficient());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotFreeStock);
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotItemOfInterest.getOrderUnit());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotItemOfInterest.getOrderQuantity());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(camelotItemOfInterest.getOrderTotalItems());
                        out.println("</td>");
                        out.println("<td>");
                        out.println("<a href='goForEditingCamelotItemOfInterest.htm?code=" + camelotItemOfInterest.getCode() + "'>Edit</a>");
                        out.println("</td>");

                        out.println("</tr>");

                    }
                %>
        </table>
    </center>
</body>
</html>
