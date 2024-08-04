<%-- 
    Document   : orderQuantityComparison
    Created on : Aug 4, 2024, 12:45:17 AM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>2023 and 2024 Orders Comparison</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
                font-size: 20px;
            }

        </style>
    </head>
    <body>
        <h1>2023 and 2024 Orders Comparison</h1>
        <table>
            <tbody>
                <tr>
                    <td>
                        <table>
                            <thead>
                            <th>DATE</th>
                            <th>COUNT</th>

                            </thead>
                            <tbody>
                                <%
                                    TreeMap<Integer, Integer> counut2023 = (TreeMap) request.getAttribute("2023");
                                    for (Map.Entry<Integer, Integer> entrySet : counut2023.entrySet()) {
                                        out.println("<tr>");

                                        out.println("<td>");
                                        out.println(entrySet.getKey());
                                        out.println("</td>");

                                        out.println("<td>");
                                        out.println(entrySet.getValue());
                                        out.println("</td>");

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table>
                            <thead>
                            <th>DATE</th>
                            <th>COUNT</th>

                            </thead>
                            <tbody>
                                <%
                                    TreeMap<Integer, Integer> counut2024 = (TreeMap) request.getAttribute("2024");
                                    for (Map.Entry<Integer, Integer> entrySet : counut2024.entrySet()) {
                                        out.println("<tr>");

                                        out.println("<td>");
                                        out.println(entrySet.getKey());
                                        out.println("</td>");

                                        out.println("<td>");
                                        out.println(entrySet.getValue());
                                        out.println("</td>");

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
