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
        <title>Pet4U: Items State Update</title>
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
    <center>Pet4U: Items State Update
        <table>
            <thead>
                <tr>
                    <th>
                        Code
                    </th>
                    <th>
                        Position
                    </th>
                    <th>
                        Description
                    </th>
                    <th>
                        Previous State
                    </th>
                    <th>
                        Now State
                    </th>
                    <th>
                        Stock
                    </th>


                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Item> items = (ArrayList) request.getAttribute("difference");
                    for (Item item : items) {

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

                        out.println("<td>");
                        out.println(item.getState());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getSupplier());
                        out.println("</td>");

                        out.println("<td>");
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
