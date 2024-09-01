<%-- 
    Document   : positioning
    Created on : Aug 23, 2024, 8:47:42 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="BasicModel.WarehousePositioning"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Positioning</title>
    </head>
    <body>
        <%  WarehousePositioning positions = (WarehousePositioning) request.getAttribute("warehousePositioning");
        %>
    <center>
        <h1>Positioning</h1>
        <table>
            <tbody>
                <tr>
                    <td>
                        <table>
                            <tbody>
                                <%
                                    LinkedHashMap<String, Object> p = positions.getP();
                                    for (Map.Entry<String, Object> entrySet : p.entrySet()) {

                                        out.println("<tr>");
                                        out.println("<td>");
                                        out.println("--");
                                        out.println("</td>");
                                        out.println("<td>");
                                        out.println(entrySet.getKey());
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
                                    LinkedHashMap<String, Object> O = positions.getP();
                                    for (Map.Entry<String, Object> entrySet : p.entrySet()) {

                                        out.println("<tr>");

                                        out.println("<td>");
                                        out.println(entrySet.getKey());
                                        out.println("</td>");
                                        out.println("<td>");
                                        out.println("--");
                                        out.println("</td>");

                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        ++++
                    </td>
                    <td>
                        ___
                    </td>
                    <td>
                        Ξ
                    </td>
                    <td>
                        Ν
                    </td>
                    <td>
                        __
                    </td>
                    <td>
                        Μ
                    </td>
                    <td>

                    </td>
                </tr>
            </tbody>
        </table>
    </center>
</body>
</html>
