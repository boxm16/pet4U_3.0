<%-- 
    Document   : orderStatistics
    Created on : Jul 23, 2024, 10:46:21 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="BasicModel.WarehousePositioning"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Position Traffic Statistica</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
                font-size: 20px;
            }

        </style>
    </head>
    <body>
    <center>
        <h1>Position Traffic Statistica</h1>
        <table style="font-size:20px">
            <thead>
                <tr> 
                    <th>Codes Quantity</th>
                    <th>Count</th>
                    <th>%</th>

                </tr>
            </thead>
            <tbody>


                <%
                    TreeMap<String, Integer> positionsTraffic = (TreeMap) request.getAttribute("positionsTraffic");
                    String startDate = (String) request.getAttribute("startDate");
                    String endDate = (String) request.getAttribute("endDate");
                    String itemCode = (String) request.getAttribute("itemCode");
                    String position = (String) request.getAttribute("position");

                    for (Map.Entry<String, Integer> entrySet : positionsTraffic.entrySet()) {
                        out.println("<tr>");

                        out.println("<td>");
                        out.println(entrySet.getKey());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + entrySet.getValue() + "</a>");
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>
        </table>
        <hr><hr><hr><hr><hr><hr><hr><hr><hr>
        <%  WarehousePositioning positions = (WarehousePositioning) request.getAttribute("warehousePositioning");
            String itemBlockPosition = (String) request.getAttribute("itemBlockPosition");
        %>
        <table>
            <tbody>
                <tr>
                    <td>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> p = positions.getP();
                                    for (Map.Entry<String, Object> entrySet : p.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='25px'>");
                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(positionsTraffic.get(k));
                                                out.println("</td>");
                                            }
                                        } else {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px; '>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(0);
                                                out.println("</td>");
                                            }

                                        }

                                        if (entrySet.getKey().contains("emptySpace")) {
                                            out.println("<td style='width:20px; font-size: 15px;'>");
                                            out.println("");
                                            out.println("</td>");
                                        } else {
                                            if (itemBlockPosition.equals(entrySet.getKey())) {
                                                out.println("<td style='width:20px; font-size: 15px; background-color: red;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:20px; font-size: 15px;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            }

                                        }

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> o = positions.getO();
                                    for (Map.Entry<String, Object> entrySet : o.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='25px'>");

                                        if (entrySet.getKey().contains("emptySpace")) {
                                            out.println("<td style='width:20px; font-size: 15px;'>");
                                            out.println("");
                                            out.println("</td>");
                                        } else {
                                            if (itemBlockPosition.equals(entrySet.getKey())) {
                                                out.println("<td style='width:20px; font-size: 15px; background-color: red;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:20px; font-size: 15px;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            }

                                        }

                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(positionsTraffic.get(k));
                                                out.println("</td>");
                                            }
                                        } else {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px; '>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(0);
                                                out.println("</td>");
                                            }

                                        }

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tbody>
                                <%
                                    for (int x = 0; x < 29; x++) {

                                        out.println("<tr height='25px'>");

                                        out.println("<td width='20px'>");
                                        out.println("    ");
                                        out.println("</td>");

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> ks = positions.getKS();
                                    for (Map.Entry<String, Object> entrySet : ks.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='28px'>");
                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(positionsTraffic.get(k));
                                                out.println("</td>");
                                            }
                                        } else {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px; '>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(0);
                                                out.println("</td>");
                                            }

                                        }

                                        if (entrySet.getKey().contains("emptySpace")) {
                                            out.println("<td style='width:20px; font-size: 15px;'>");
                                            out.println("");
                                            out.println("</td>");
                                        } else {
                                            if (itemBlockPosition.equals(entrySet.getKey())) {
                                                out.println("<td style='width:20px; font-size: 15px; background-color: red;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:20px; font-size: 15px;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            }

                                        }

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> n = positions.getN();
                                    for (Map.Entry<String, Object> entrySet : n.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='25px'>");

                                        if (entrySet.getKey().contains("emptySpace")) {
                                            out.println("<td style='width:20px; font-size: 15px;'>");
                                            out.println("");
                                            out.println("</td>");
                                        } else {
                                            if (itemBlockPosition.equals(entrySet.getKey())) {
                                                out.println("<td style='width:20px; font-size: 15px; background-color: red;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:20px; font-size: 15px;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            }

                                        }

                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(positionsTraffic.get(k));
                                                out.println("</td>");
                                            }
                                        } else {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px; '>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(0);
                                                out.println("</td>");
                                            }

                                        }

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tbody>
                                <%
                                    for (int x = 0; x < 30; x++) {

                                        out.println("<tr height='25px'>");

                                        out.println("<td width='20px'>");
                                        out.println("    ");
                                        out.println("</td>");

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> m = positions.getM();
                                    for (Map.Entry<String, Object> entrySet : m.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='25px'>");
                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(positionsTraffic.get(k));
                                                out.println("</td>");
                                            }
                                        } else {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px; '>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println(0);
                                                out.println("</td>");
                                            }

                                        }

                                        if (entrySet.getKey().contains("emptySpace")) {
                                            out.println("<td style='width:20px; font-size: 15px;'>");
                                            out.println("");
                                            out.println("</td>");
                                        } else {
                                            if (itemBlockPosition.equals(entrySet.getKey())) {
                                                out.println("<td style='width:20px; font-size: 15px; background-color: red;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:20px; font-size: 15px;'>");
                                                out.println(entrySet.getKey());
                                                out.println("</td>");
                                            }

                                        }

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>
    </center>
</body>
</html>
