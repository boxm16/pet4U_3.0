<%-- 
    Document   : deltiaApostilisDisplay
    Created on : Jan 4, 2024, 11:21:00 PM
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
        <table>
            <thead> 



                <tr>
                    <th>A/A</th>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Sent</th>
                    <th>Delivered</th>
                    <th>Alert</th>
                </tr>
            </thead>
            <tbody id="tableBody">
                <%
                    int x = 1;
                    ArrayList<Endo> endos = (ArrayList<Endo>) request.getAttribute("endos");

                    for (Endo endo : endos) {

                        out.println("<tr>");
                        out.println("<td>");
                        out.println(endo.getId());
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
