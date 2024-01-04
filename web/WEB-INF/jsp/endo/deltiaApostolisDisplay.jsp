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
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 40px;
                text-align: center;
            }
            th{
                font-size: 30px;
                font-weight: bold;
                text-align: center;
                background: #eee;
                position: sticky;
                top: 0px;
            }

        </style>
    </head>
    <body>
    <center>
        <table>
            <thead> 



                <tr>
                    <th>A/A</th>
                    <th>Date</th>
                    <th>Sender</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody id="tableBody">
                <%
                    int x = 1;
                    ArrayList<Endo> endos = (ArrayList<Endo>) request.getAttribute("endos");

                    for (Endo endo : endos) {

                        out.println("<tr>");
                        out.println("<td>");

                        out.println("<a href='showDeltioApostolis.htm?id=" + endo.getId() + "'>" + endo.getId() + "</a>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println(endo.getDateString());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endo.getSender());
                        out.println("</td>");

                        out.println("<td>");
                        String itemCode = (String) request.getAttribute("itemCode");
                        out.println(endo.getItems().get(itemCode).getQuantity());
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }
                %>
            </tbody>
        </table>
    </center>
</body>
</html>
