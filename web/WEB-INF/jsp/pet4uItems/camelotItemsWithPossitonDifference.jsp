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
        <title>Camelot Items With Position Difference</title>
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
    <center>Camelot Items With Position Difference
        <table>
            <thead>
                <tr>
                    <th>
                        Code
                    </th>
                    <th>
                        Description
                    </th>
                    <th>
                        Pet4U Position
                    </th>
                    <th>
                        Camelot Position
                    </th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<ArrayList<String>> items = (ArrayList) request.getAttribute("differences");
                    for (ArrayList<String> item : items) {

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(item.get(0));
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.get(1));
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.get(2));
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.get(3));
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>
            </tbody>
        </table>
    </center>
</body>
</html>
