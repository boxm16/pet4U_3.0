<%-- 
    Document   : endoApostoles
    Created on : Feb 4, 2024, 2:56:48 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="Endo.EndoApostolis"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="Endo.EndoOrder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Endo ΑΠΟΣΤΟΛΕΣ</title>
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
    <center>
        <a href="index.htm"><h3>INDEX</h3></a>

        <hr>
        <table>
            <tr>

                <td>
                    <table>

                        <thead>
                            <tr>


                                <th>Order Destination</th>

                            </tr>
                        </thead>
                        <tbody>
                            <%     LinkedHashMap<String, EndoOrder> endoOrdersTitles = (LinkedHashMap) request.getAttribute("endoOrdersTitles");

                                for (Map.Entry<String, EndoOrder> endoOrdersTitlesEntry : endoOrdersTitles.entrySet()) {
                                    out.println("<tr style='background-color: #90EE90'>");

                                    out.println("<td>");
                                    out.println("<a href='showEndoOrder.htm?id=" + endoOrdersTitlesEntry.getValue().getId() + "' target='_blank'>" + endoOrdersTitlesEntry.getValue().getDestination() + "</a>");
                                    out.println("</td>");
                                    out.println("</tr>");
                                }
                            %>
                        </tbody>
                    </table>
                <td>
                <td>--------------</td>
                <td>

                    <table>

                        <thead>
                            <tr>


                                <th>id</th>
                                <th>Number</th>
                                <th>Destination</th>

                            </tr>
                        </thead>
                        <tbody>
                            <%     LinkedHashMap<String, EndoApostolis> outgoingDeltioApostolisTitles = (LinkedHashMap) request.getAttribute("outgoingDeltioApostolisTitles");

                                for (Map.Entry<String, EndoApostolis> outgoingDeltioApostolisTitlesEntry : outgoingDeltioApostolisTitles.entrySet()) {
                                    out.println("<tr style='background-color: #90EE90'>");

                                    out.println("<td>");
                                    out.println("<a href='showDeltioApostolisVaribobis.htm?id=" + outgoingDeltioApostolisTitlesEntry.getValue().getId() + "' target='_blank'>" + outgoingDeltioApostolisTitlesEntry.getValue().getId() + "</a>");
                                    out.println("</td>");

                                    out.println("<td style='font-weight: bold;'>");
                                    out.println(outgoingDeltioApostolisTitlesEntry.getValue().getNumber());
                                    out.println("</td>");

                                    out.println("<td style='font-weight: bold;'>");
                                    out.println(outgoingDeltioApostolisTitlesEntry.getValue().getReceiver());
                                    out.println("</td>");

                                    out.println("</tr>");
                                }
                            %>
                        </tbody>
                    </table>
                </td>
            </tr>
        </table>
    </center>
</body>
</html>
