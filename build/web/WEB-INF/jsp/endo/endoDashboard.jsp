<%-- 
    Document   : endoDashboard
    Created on : Jan 1, 2024, 8:32:46 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="Endo.Endo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <hr>
        <a href="deltioApostolis.htm"><h1>Δελτιο Αποστολης</h1></a> 
        <table>

            <th>Position</th>
            <th>Altercode</th>
            <th>Description</th>
            <th>State</th>
            <th>Stock</th>
            <th>Show  Snapshots</th>

            <tbody>
                <%
                    ArrayList<String> incomingEndos = (ArrayList) request.getAttribute("incomingEndos");
                    for (String endoTitel : incomingEndos) {

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(endoTitel);
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>    
            </tbody>
        </table>
    </body>
</html>
