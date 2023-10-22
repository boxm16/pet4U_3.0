<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.TreeMap"%>
<%@page import="java.time.LocalDate"%>
<%@page import="MonthSales.Sales"%>
<%@page import="MonthSales.ItemSales"%>
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
            <tr><td>Description</td><td>${item.description}</td></tr>
            <tr><td>State</td><td>${item.state}</td></tr>
            <tr><td>Stocl</td><td>${item.quantity}</td></tr>
        </table>
        <hr>
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
                                     out.println("<td>");
                                    out.println("|");
                                    out.println("</td>");

                                    out.println("</tr>");

                                }
                            %>
                    </table>
                </td>
                <td>
                    <table>
                        <th>Date Stamp</th>
                        <th>E-Shop Sales</th>
                        <th>Ενδοδιακ.</th>
                            <%
                                ItemSales item = (ItemSales) request.getAttribute("itemSales");
                                TreeMap<LocalDate, Sales> sales = item.getSales();
                                double totalSales = 0;
                                double totalShopSupplies = 0;

                                for (Map.Entry<LocalDate, Sales> salesEntry : sales.entrySet()) {
                                    LocalDate date = salesEntry.getKey();
                                    Sales sale = salesEntry.getValue();
                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println(date.getMonth());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(sale.getEshopSales());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(sale.getShopsSupply());
                                    out.println("</td>");

                                    out.println("</tr>");
                                    totalSales += sale.getEshopSales();
                                    totalShopSupplies += sale.getShopsSupply();

                                }
                                out.println("<tr>");
                                out.println("<td>");
                                out.println("TOTALS");
                                out.println("</td>");
                                out.println("<td>");
                                out.println(totalSales);
                                out.println("</td>");
                                out.println("<td>");
                                out.println(totalShopSupplies);
                                out.println("</td>");

                                out.println("</tr>");
                                out.println("<td>");
                                out.println("GRAND TOTAL");
                                out.println("</td>");
                                out.println("<td colspan='2'>");
                                out.println(totalSales + totalShopSupplies);
                                out.println("</td>");

                                out.println("</tr>");
                            %>
                    </table>
                </td>
            </tr>
        </table>
    </center>
</body>
</html>
