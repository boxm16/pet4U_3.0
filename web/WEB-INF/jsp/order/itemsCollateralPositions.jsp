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
        <title>Position Traffic Statistica: Collaterals</title>
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
        <h1>Position Traffic Statistica: Collaterals</h1>
        <hr>
        <%
            TreeMap<String, Integer> positionsTraffic = (TreeMap) request.getAttribute("positionsTraffic");
            String startDate = (String) request.getAttribute("startDate");
            String endDate = (String) request.getAttribute("endDate");
            String itemCode = (String) request.getAttribute("itemCode");
            String position = (String) request.getAttribute("position");

            WarehousePositioning positions = (WarehousePositioning) request.getAttribute("warehousePositioning");
            String itemBlockPosition = (String) request.getAttribute("itemBlockPosition");
        %>
        <table>
            <tbody>
                <tr>
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> p = positions.getP();
                                    for (Map.Entry<String, Object> entrySet : p.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='30px'>");
                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> o = positions.getO();
                                    for (Map.Entry<String, Object> entrySet : o.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='30px'>");

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
                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%
                                    out.println("<tr height='1000px'>");

                                    out.println("<td width='40px'>");
                                    out.println("    ");
                                    out.println("</td>");

                                    out.println("</tr>");

                                %>
                            </tbody>
                        </table>
                    </td>
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%                                    LinkedHashMap<String, Object> ks = positions.getKS();
                                    for (Map.Entry<String, Object> entrySet : ks.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='30px'>");
                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> n = positions.getN();
                                    for (Map.Entry<String, Object> entrySet : n.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='30px'>");

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
                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%
                                    out.println("<tr height='1000px'>");

                                    out.println("<td width='40px'>");
                                    out.println("    ");
                                    out.println("</td>");

                                    out.println("</tr>");

                                %>
                            </tbody>
                        </table>
                    </td>
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%                                    LinkedHashMap<String, Object> m = positions.getM();
                                    for (Map.Entry<String, Object> entrySet : m.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='30px'>");
                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%
                                    for (int x = 0; x < 34; x++) {

                                        out.println("<tr height='31px'>");
                                        if (x == 4) {
                                            out.println("<td style='width:10px;'>");
                                            out.println("  ");
                                            out.println("</td>");
                                        } else {
                                            out.println("<td style='width:10px; background-color:black'>");
                                            out.println("  ");
                                            out.println("</td>");
                                        }

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%
                                    //---- Y stop existing-----------
                                    /*
                                    LinkedHashMap<String, Object> y = positions.getY();

                                    for (Map.Entry<String, Object> entrySet : y.entrySet()) {
                                        String k = entrySet.getKey();

                                        out.println("<tr height='30px'>");

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
                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                                     */
                                    //-----------------------------------
                                    LinkedHashMap<String, Object> a = positions.getA();
                                    int r = 0;
                                    for (Map.Entry<String, Object> entrySet : a.entrySet()) {
                                        r++;
                                        String k = entrySet.getKey();

                                        if (r > 5) {
                                            out.println("<tr height='60px'>");
                                        } else {
                                            out.println("<tr height='30px'>");
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

                                        if (positionsTraffic.containsKey(k)) {
                                            if (entrySet.getKey().contains("emptySpace")) {
                                                out.println("<td style='width:30px;'>");
                                                out.println("");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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

                                    }
                                    out.println("</tr>");

                                    out.println("<tr  style='height: 100px; '>");
                                    out.println("<td>");
                                    out.println("");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr  style='height: 10px; background-color: black;'>");
                                    out.println("<td>");
                                    out.println("");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("");
                                    out.println("</td>");
                                    out.println("</tr>");

//-------------------------
                                    out.println("<tr>");
                                    out.println("<td>");
                                    out.println("18Ρ-03");
                                    out.println("</td>");
                                    out.println("<td  style='40px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("18Ρ-03") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("18Ρ-03"));
                                    }

                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td>");
                                    out.println("18Ρ-02");
                                    out.println("</td>");
                                    out.println("<td  style='40px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("18Ρ-02") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("18Ρ-02"));
                                    }

                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td>");
                                    out.println("18Ρ-01");
                                    out.println("</td>");
                                    out.println("<td  style='40px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("18Ρ-01") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("18Ρ-01"));
                                    }

                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td>");
                                    out.println("18Ρ-00/Μ29");
                                    out.println("</td>");
                                    out.println("<td  style='40px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("18Ρ-00/Μ29") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("18Ρ-00/Μ29"));
                                    }

                                    out.println("</td>");
                                    out.println("</tr>");
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td valign='top'>
                        <table>
                            <tbody>
                                <%                                    //diadromos start
                                    out.println("<tr height='885px'>");

                                    out.println("<td width='40px'>");
                                    out.println("    ");
                                    out.println("</td>");

                                    out.println("</tr>");

                                    out.println("<tr>");

                                    out.println("<td width='40px'>");
                                    out.println("<table>");

                                    out.println("<tr>");
                                    out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("15Κ-01") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("15Κ-01"));
                                    }

                                    out.println("</td>");

                                    out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("15Κ-02") == null) {

                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("15Κ-02"));
                                    }

                                    out.println("</td>");
                                    //++++++++
                                    out.println("<tr>");
                                    out.println("<td>");
                                    out.println("15Κ-01");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("15Κ-02");
                                    out.println("</td>");

                                    out.println("</tr>");

                                    out.println("</table>");
                                    out.println("</td>");

                                    out.println("</tr>");

                                    out.println("<tr  style='height: 10px; background-color: black;'>");
                                    out.println("<td>");
                                    out.println("");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    //=============
                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println("<table>");
                                    out.println("<tr>");
                                    out.println("<td  style='width:30px; font-size: 30px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("18Ρ-04") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("18Ρ-04"));
                                    }
                                    out.println("</td>");

                                    out.println("<td  style='width:30px; font-size: 30px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("18Ρ-05") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("18Ρ-05"));
                                    }
                                    out.println("</td>");

                                    out.println("<td  style='width:30px; font-size: 30px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("18Ρ-06") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("18Ρ-06"));
                                    }
                                    out.println("</td>");

                                    out.println("<td  style='width:30px; font-size: 30px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("18Ρ-07") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("18Ρ-07"));
                                    }
                                    out.println("</td>");

                                    out.println("</tr>");
                                    out.println("</table>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    //------------------------ Σ +++--------------------------------
                                    out.println("<tr>");
                                    out.println("<td>");
                                    out.println("<table>");

                                    out.println("<tr>");
                                    out.println("<td  style='width:90px;'>");
                                    out.println("     ");
                                    out.println("</td>");
                                    out.println("<td  style='width:40px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("17Σ-03") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("17Σ-03"));
                                    }
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td  style='width:90px;'>");
                                    out.println("     ");
                                    out.println("</td>");
                                    out.println("<td  style='width:40px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("17Σ-02") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("17Σ-02"));
                                    }
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td  style='width:90px;'>");
                                    out.println("     ");
                                    out.println("</td>");
                                    out.println("<td  style='width:40px; font-size: 25px; background-color: lightgreen;'>");
                                    if (positionsTraffic.get("17Σ-01") == null) {
                                        out.println(0);
                                    } else {
                                        out.println(positionsTraffic.get("17Σ-01"));
                                    }
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("</td>");
                                    out.println("</tr>");
                                    out.println("</table>");
                                    out.println("</tr>");
                                    //------------------------------------------------------------------

                                %>
                            </tbody>
                        </table>
                    </td>



                    <td valign='top'>
                        <table>
                            <tbody>
                                <tr>
                                    <td>
                                        <table>
                                            <tr>
                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td width='150px'>");
                                                                out.println("<table>");

                                                                out.println("<tr>");
                                                                out.println("<td  style='150px; font-size: 25px; background-color: lightgreen;'>");
                                                                if (positionsTraffic.get("19Λ-01") == null) {
                                                                    out.println(0);
                                                                } else {
                                                                    out.println(positionsTraffic.get("19Λ-01"));
                                                                }

                                                                out.println("</td>");

                                                                //++++++++
                                                                out.println("<tr>");
                                                                out.println("<td width='150px'>");
                                                                out.println("19Λ-01");
                                                                out.println("</td>");

                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>
                                                <td style="width:300px"></td>
                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td width='150px'>");
                                                                out.println("<table>");

                                                                out.println("<tr>");
                                                                out.println("<td  style='150px; font-size: 25px; background-color: lightgreen;'>");
                                                                if (positionsTraffic.get("19Λ-03") == null) {
                                                                    out.println(0);
                                                                } else {
                                                                    out.println(positionsTraffic.get("19Λ-03"));
                                                                }

                                                                out.println("</td>");

                                                                //++++++++
                                                                out.println("<tr>");
                                                                out.println("<td width='150px'>");
                                                                out.println("19Λ-03");
                                                                out.println("</td>");

                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                                <tr>
                                    <td valign='top'>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%                                    LinkedHashMap<String, Object> b = positions.getB();
                                                                    for (Map.Entry<String, Object> entrySet : b.entrySet()) {
                                                                        String k = entrySet.getKey();

                                                                        out.println("<tr height='80px'>");
                                                                        if (positionsTraffic.containsKey(k)) {
                                                                            if (entrySet.getKey().contains("emptySpace")) {
                                                                                out.println("<td style='width:30px;'>");
                                                                                out.println("");
                                                                                out.println("</td>");
                                                                            } else {
                                                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%
                                                                    LinkedHashMap<String, Object> g = positions.getG();
                                                                    for (Map.Entry<String, Object> entrySet : g.entrySet()) {
                                                                        String k = entrySet.getKey();

                                                                        out.println("<tr height='80px'>");

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
                                                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%                                    out.println("<tr height='800px'>");

                                                                    out.println("<td width='40px'>");
                                                                    out.println("    ");
                                                                    out.println("</td>");

                                                                    out.println("</tr>");

                                                                %>
                                                            </tbody>
                                                        </table>
                                                    </td>
                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%                                    LinkedHashMap<String, Object> d = positions.getD();
                                                                    for (Map.Entry<String, Object> entrySet : d.entrySet()) {
                                                                        String k = entrySet.getKey();

                                                                        out.println("<tr height='80px'>");
                                                                        if (positionsTraffic.containsKey(k)) {
                                                                            if (entrySet.getKey().contains("emptySpace")) {
                                                                                out.println("<td style='width:30px;'>");
                                                                                out.println("");
                                                                                out.println("</td>");
                                                                            } else {
                                                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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

                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%
                                                                    LinkedHashMap<String, Object> e = positions.getE();

                                                                    for (Map.Entry<String, Object> entrySet : e.entrySet()) {

                                                                        String k = entrySet.getKey();

                                                                        out.println("<tr height='60px'>");

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
                                                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%                                    out.println("<tr height='800px'>");

                                                                    out.println("<td width='40px'>");
                                                                    out.println("    ");
                                                                    out.println("</td>");

                                                                    out.println("</tr>");

                                                                %>
                                                            </tbody>
                                                        </table>
                                                    </td>

                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%                                    LinkedHashMap<String, Object> z = positions.getZ();
                                                                    for (Map.Entry<String, Object> entrySet : z.entrySet()) {
                                                                        String k = entrySet.getKey();

                                                                        out.println("<tr height='80px'>");
                                                                        if (positionsTraffic.containsKey(k)) {
                                                                            if (entrySet.getKey().contains("emptySpace")) {
                                                                                out.println("<td style='width:30px;'>");
                                                                                out.println("");
                                                                                out.println("</td>");
                                                                            } else {
                                                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%
                                                                    LinkedHashMap<String, Object> h = positions.getH();

                                                                    for (Map.Entry<String, Object> entrySet : h.entrySet()) {

                                                                        String k = entrySet.getKey();

                                                                        out.println("<tr height='80px'>");

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
                                                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%                                    out.println("<tr height='800px'>");

                                                                    out.println("<td width='40px'>");
                                                                    out.println("    ");
                                                                    out.println("</td>");

                                                                    out.println("</tr>");

                                                                %>
                                                            </tbody>
                                                        </table>
                                                    </td>

                                                    <td valign='top'>
                                                        <table>
                                                            <tbody>
                                                                <%                                    LinkedHashMap<String, Object> th = positions.getTH();
                                                                    for (Map.Entry<String, Object> entrySet : th.entrySet()) {
                                                                        String k = entrySet.getKey();

                                                                        out.println("<tr height='60px'>");
                                                                        if (positionsTraffic.containsKey(k)) {
                                                                            if (entrySet.getKey().contains("emptySpace")) {
                                                                                out.println("<td style='width:30px;'>");
                                                                                out.println("");
                                                                                out.println("</td>");
                                                                            } else {
                                                                                out.println("<td style='width:30px; font-size: 25px; background-color: lightgreen;'>");
                                                                                out.println("<a  href = 'getAllDocsForItemBetweenTwoDatesWithThisBlockPosition.htm?blockPosition=" + entrySet.getKey() + "&itemCode=" + itemCode + "&startDate=" + startDate + "&endDate=" + endDate + "' target='_blank'>" + positionsTraffic.get(k) + "</a>");
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
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>

                                <!-- pame I -->
                                <tr>
                                    <td>
                                        <table>
                                            <tr>
                                                <!-- porta -->
                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td style='width:200px; '>");
                                                                out.println("<table>");

                                                                out.println("<tr>");

                                                                out.println("<td  style='width:200px; '>");
                                                                out.println(" ");
                                                                out.println("</td>");
                                                                out.println("</tr>");
                                                                //++++++++
                                                                out.println("<tr>");
                                                                out.println("<td  style='width:200px; '>");
                                                                out.println(" ");
                                                                out.println("</td>");

                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>

                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td width='40px'>");
                                                                out.println("<table>");

                                                                out.println("<tr>");

                                                                out.println("<td  style='40px; font-size: 25px; background-color: lightgreen;'>");
                                                                if (positionsTraffic.get("14Ι-00") == null) {
                                                                    out.println(0);
                                                                } else {
                                                                    out.println(positionsTraffic.get("14Ι-00"));
                                                                }

                                                                out.println("</td>");
                                                                out.println("</tr>");

                                                                //++++++++
                                                                out.println("<tr>");
                                                                out.println("<td width='40px'>");
                                                                out.println("14Ι-00");
                                                                out.println("</td>");

                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>


                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td width='90px'>");
                                                                out.println("<table>");

                                                                out.println("<tr>");
                                                                out.println("<td  style='90px; font-size: 25px; background-color: lightgreen;'>");
                                                                if (positionsTraffic.get("14Ι-01") == null) {
                                                                    out.println(0);
                                                                } else {
                                                                    out.println(positionsTraffic.get("14Ι-01"));
                                                                }

                                                                out.println("</td>");
                                                                out.println("</tr>");

                                                                //++++++++
                                                                out.println("<tr>");
                                                                if (itemBlockPosition.equals("14Ι-01")) {
                                                                    out.println("<td style='width:90px; background-color: red;'>");
                                                                } else {
                                                                    out.println("<td width='90px'>");
                                                                }
                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>


                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td width='90px'>");
                                                                out.println("<table>");

                                                                out.println("<tr>");
                                                                out.println("<td  style='90px; font-size: 25px; background-color: lightgreen;'>");
                                                                if (positionsTraffic.get("14Ι-02") == null) {
                                                                    out.println(0);
                                                                } else {
                                                                    out.println(positionsTraffic.get("14Ι-02"));
                                                                }

                                                                out.println("</td>");
                                                                out.println("</tr>");

                                                                //++++++++
                                                                out.println("<tr>");
                                                                if (itemBlockPosition.equals("14Ι-02")) {
                                                                    out.println("<td style='width:90px; background-color: red;'>");
                                                                } else {
                                                                    out.println("<td width='90px'>");
                                                                }
                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>

                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td width='90px'>");
                                                                out.println("<table>");

                                                                out.println("<tr>");
                                                                out.println("<td  style='90px; font-size: 25px; background-color: lightgreen;'>");
                                                                if (positionsTraffic.get("14Ι-03") == null) {
                                                                    out.println(0);
                                                                } else {
                                                                    out.println(positionsTraffic.get("14Ι-03"));
                                                                }

                                                                out.println("</td>");
                                                                out.println("</tr>");

                                                                //++++++++
                                                                out.println("<tr>");
                                                                if (itemBlockPosition.equals("14Ι-03")) {
                                                                    out.println("<td style='width:90px; background-color: red;'>");
                                                                } else {
                                                                    out.println("<td width='90px'>");
                                                                }
                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>

                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td width='90px'>");
                                                                out.println("<table>");

                                                                out.println("<tr>");
                                                                out.println("<td  style='90px; font-size: 25px; background-color: lightgreen;'>");
                                                                if (positionsTraffic.get("14Ι-04") == null) {
                                                                    out.println(0);
                                                                } else {
                                                                    out.println(positionsTraffic.get("14Ι-04"));
                                                                }

                                                                out.println("</td>");
                                                                out.println("</tr>");
                                                                //++++++++
                                                                out.println("<tr>");
                                                                if (itemBlockPosition.equals("14Ι-04")) {
                                                                    out.println("<td style='width:90px; background-color: red;'>");
                                                                } else {
                                                                    out.println("<td width='90px'>");
                                                                }
                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>

                                                <td valign='top'>
                                                    <table>
                                                        <tbody>
                                                            <%                                                                out.println("<tr>");

                                                                out.println("<td width='90px'>");
                                                                out.println("<table>");

                                                                out.println("<tr>");
                                                                out.println("<td  style='90px; font-size: 25px; background-color: lightgreen;'>");
                                                                if (positionsTraffic.get("14Ι-05") == null) {
                                                                    out.println(0);
                                                                } else {
                                                                    out.println(positionsTraffic.get("14Ι-05"));
                                                                }

                                                                out.println("</td>");
                                                                out.println("</tr>");
                                                                //++++++++
                                                                out.println("<tr>");

                                                                if (itemBlockPosition.equals("14Ι-05")) {
                                                                    out.println("<td style='width:90px; background-color: red;'>");
                                                                } else {
                                                                    out.println("<td width='90px'>");
                                                                }
                                                                out.println("14Ι-05");
                                                                out.println("</td>");

                                                                out.println("</tr>");

                                                                out.println("</table>");
                                                                out.println("</td>");

                                                                out.println("</tr>");


                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>

                </tr>
            </tbody>
        </table>  
        <hr><hr><hr><hr><hr><hr><hr><hr>
        <table style="font-size:20px">
            <thead>
                <tr> 
                    <th>Codes Quantity</th>
                    <th>Count</th>
                    <th>%</th>

                </tr>
            </thead>
            <tbody>


                <%                    for (Map.Entry<String, Integer> entrySet : positionsTraffic.entrySet()) {
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
        <hr><hr><hr><hr><hr><hr><hr><hr>
    </center>
</body>
</html>
