<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="BasicModel.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Item Analysis</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 12px;
            }
            th{
                font-size: 20px;
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
        <h1>Pet4U Item Analysis</h1>

        <table> 
            <tr><td>Code</td><td>${item.code}</td></tr>
            <tr><td>Code</td><td>${item.description}</td></tr>
        </table>

        <table>
            <tr><td>
                    <table>
                        <th>Date Stamp</th>
                        <th>State</th>
                        <th>Quantity</th>
                            <%
                                LinkedHashMap<String, Item> itemSnapshots = (LinkedHashMap) request.getAttribute("itemSnapshots");
                                for (Map.Entry<String, Item> itemSnapshotEntry : itemSnapshots.entrySet()) {
                                    Item itemSnapshot = itemSnapshotEntry.getValue();
                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println(itemSnapshotEntry.getKey());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(itemSnapshot.getState());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(itemSnapshot.getQuantity());
                                    out.println("</td>");

                                    out.println("</tr>");

                                }
                            %>
                    </table>
                </td>
            </tr>
        </table>
    </center>
</body>
</html>
