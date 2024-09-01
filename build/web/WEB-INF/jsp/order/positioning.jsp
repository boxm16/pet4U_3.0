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

                                        out.println("<tr height='20px'>");
                                        out.println("<td>");
                                        out.println(entrySet.getValue());
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
                                    LinkedHashMap<String, Object> o = positions.getP();
                                    for (Map.Entry<String, Object> entrySet : o.entrySet()) {

                                        out.println("<tr height='20px'>");

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
                            <tbody>
                                <%
                                    for (int x = 0; x < 29; x++) {

                                        out.println("<tr height='20px'>");

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

                                        out.println("<tr height='20px'>");

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
