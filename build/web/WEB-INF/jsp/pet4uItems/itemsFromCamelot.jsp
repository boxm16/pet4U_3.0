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
        <title>Items From Camelot</title>
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
        <h1>Items From Camelot</h1>

        <table>
            <thead>
                <tr> 
                    <th>Altercodes</th>
                    <th>DESCRIPTION</th>
                    <th>POSITION</th>
                    <th>STATE</th>
                    <th>QUANTITY</th>
                </tr>
            </thead>
            <tbody>
                <%
                    LinkedHashMap<String, Item> itemsFromCamelot = (LinkedHashMap) request.getAttribute("itemsFromCamelot");
                    for (Map.Entry<String, Item> itemEntrySet : itemsFromCamelot.entrySet()) {
                        Item item = itemEntrySet.getValue();
                        String quantity = item.getQuantity();
                        Double quantityDouble = Double.parseDouble(quantity);

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(itemEntrySet.getKey());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getPosition());
                        out.println("</td>");
                        
                         out.println("<td>");
                        out.println(item.getState());
                        out.println("</td>");

                        out.println("<td style='text-align:center; font-size:20px'>");
                        out.println(quantityDouble);
                        out.println("</td>");

                        out.println("</tr>");

                    }
                %>
            </tbody>
        </table>
    </center>
</body>
</html>
