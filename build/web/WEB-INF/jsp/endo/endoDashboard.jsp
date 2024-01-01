<%-- 
    Document   : endoDashboard
    Created on : Jan 1, 2024, 8:32:46 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="Endo.Endo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Endo Dashboard</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 30px;
            }
            th{
                font-size: 30px;
                font-weight: bold;
            }

        </style>
    </head>
    <body>
        <h1>Hello World!</h1>
        <hr>
        <a href="deltioApostolis.htm"><h1>Δελτιο Αποστολης</h1></a> 
        <table>

            <th>Select</th>
            <th>A/A</th>
            <th>Date</th>
            <th>Sender</th>


            <tbody>
                <%
                    LinkedHashMap<String, Endo> incomingEndos = (LinkedHashMap) request.getAttribute("incomingEndos");
                    for (Map.Entry<String, Endo> entrySet : incomingEndos.entrySet()) {

                        out.println("<tr>");

                        out.println("<td>");
                        out.println("<input type='checkbox' class='a/a' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px'>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("<a href='showDeltioApostolis.htm?id="+entrySet.getValue().getId()+"'>" + entrySet.getValue().getId() + "</a>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println(entrySet.getValue().getDateString());
                        out.println("</td>");
                        out.println("<td>");
                        out.println(entrySet.getValue().getSender());
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>    
            </tbody>
        </table>
    </body>
</html>
