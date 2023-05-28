<%@page import="java.util.ArrayList"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.util.Map"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Weight Items</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }

        </style>
    </head>
    <body>
    <center>
        <h1><a href='index.htm'>INDEX</a></h1>
        <h1>Pet4u Weight Items</h1>

        <table>
            <thead>
                <tr> 
                    <th>Altercode</th>
                    <th>POSITION</th>
                    <th>DESCRIPTION</th>
                    <th>QUANTITY</th>
                    <th>Coefficient</th>
                    <th>QUANTITY <br>In Pieces</th>
                </tr>
            </thead>
            <tbody>
                <%
                    LinkedHashMap<Integer, Item> weightItems = (LinkedHashMap) request.getAttribute("weightItems");
                    for (Map.Entry<Integer, Item> weightItemEntry : weightItems.entrySet()) {

                        Item item = weightItemEntry.getValue();
                        String quantity = item.getQuantity();
                        Double quantityDouble = Double.parseDouble(quantity);

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(item.getCode());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getDescription());
                        out.println("</td>");

                        out.println("<td style='text-align:center; font-size:20px'>");
                        out.println(item.getQuantity());
                        out.println("</td>");

                        out.println("<td style='text-align:center; font-size:20px'>");
                        out.println(item.getWeightCoefficient());
                        out.println("</td>");

                        out.println("<td style='text-align:center; font-size:20px; color:" + item.getWeightAlertColor() + "'>");
                        out.println(item.getQunatityAsPieces());
                        out.println("</td>");

                        out.println("</tr>");
                    }


                %>
            </tbody>
        </table>
    </center>
</body>
</html>
