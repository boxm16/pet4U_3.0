<%-- 
    Document   : orderQuantityComparison
    Created on : Aug 4, 2024, 12:45:17 AM
    Author     : Michail Sitmalidis
--%>


<%@page import="java.time.LocalDate"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDateTime"%>
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
                vertical-align:top;
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
                                    int days = 0;
                                    int total24 = 0;
                                    TreeMap<LocalDate, Integer> counut2024 = (TreeMap) request.getAttribute("2024");
                                    for (Map.Entry<LocalDate, Integer> entrySet : counut2024.entrySet()) {
                                        LocalDate date = entrySet.getKey();
                                        DayOfWeek dayOfWeek = date.getDayOfWeek();

                                        if (dayOfWeek == DayOfWeek.SATURDAY) {
                                            out.println("<tr style='background-color: #F79A81 ;'>");

                                        } else {
                                            out.println("<tr >");

                                        }
                                        out.println("<td>");
                                        out.println(entrySet.getKey());
                                        out.println("</td>");

                                        out.println("<td>");
                                        out.println(entrySet.getValue());
                                        out.println("</td>");

                                        out.println("</tr>");
                                        days++;
                                        total24 = total24 + entrySet.getValue();
                                    }
                                    out.println("<tr style='background-color: green ;'>");
                                    out.println("<td>");
                                    out.println("Days: " + days);
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("Orders: " + total24);
                                    out.println("</td>");

                                    out.println("</tr>");
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
                                    TreeMap<LocalDate, Integer> counut2023 = (TreeMap) request.getAttribute("2023");

                                    int total23 = 0;
                                    for (Map.Entry<LocalDate, Integer> entrySet : counut2023.entrySet()) {
                                        LocalDate date2023 = entrySet.getKey();
                                        DayOfWeek dayOfWeek = date2023.getDayOfWeek();

                                        if (days == 0) {
                                            out.println("<tr style='background-color: green ;'>");
                                            out.println("<td>");
                                            out.println("Days: " + days);
                                            out.println("</td>");

                                            out.println("<td>");
                                            out.println("Orders: " + total24);
                                            out.println("</td>");
                                             days--;
                                        } else {

                                            if (dayOfWeek == DayOfWeek.SATURDAY) {
                                                out.println("<tr style='background-color: #F79A81 ;'>");

                                            } else {
                                                out.println("<tr >");

                                            }

                                            out.println("<td>");
                                            out.println(entrySet.getKey());
                                            out.println("</td>");

                                            out.println("<td>");
                                            out.println(entrySet.getValue());
                                            out.println("</td>");

                                            out.println("</tr>");
                                            days--;
                                            total23 = total23 + entrySet.getValue();

                                        }
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
