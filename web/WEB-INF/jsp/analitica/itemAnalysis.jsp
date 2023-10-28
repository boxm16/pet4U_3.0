<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="Offer.Offer"%>
<%@page import="java.util.ArrayList"%>
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

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
        <div class="container" >
            <div class="row">
                <div class=" col-sm-4">

                </div>
                <div class=" col-sm-4">
                    <h5><a href="index.htm">INDEX</a></h5>
                    <h5>Pet4U Item Analysis</h5>

                    <table> 
                        <tr><td>Code</td><td>${item.code}</td></tr>
                        <tr><td>Description</td><td>${item.description}</td></tr>
                        <tr><td>State</td><td>${item.state}</td></tr>
                        <tr><td>Stock</td><td>${item.quantity}</td></tr>
                        <%
                            Item item = (Item) request.getAttribute("item");
                            out.println("<tr>");
                            out.println("<td colspan='2' >");

                            ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
                            for (AltercodeContainer altercodeContainer : altercodes) {
                                if (altercodeContainer.getStatus().equals("eshop")
                                        || altercodeContainer.getStatus().equals("eshop-on")
                                        || altercodeContainer.getStatus().equals("eshop-barf")
                                        || altercodeContainer.getStatus().equals("eshop-pro")) {

                                    out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercodeContainer.getAltercode() + "' target='_blank'>" + "<strong>" + altercodeContainer.getAltercode() + "</strong>" + "</a>");
                                    out.println("<br>");
                                } else {
                                    out.println("<strong>" + altercodeContainer.getAltercode() + "</strong>");
                                    out.println("<br>");
                                }
                            }
                            out.println("</td>");
                            out.println("</tr>");
                        %>
                    </table>
                </div>
                <div class=" col-sm-4">

                </div>
                <hr>
            </div>
            <hr>
            <div class="row">
                <div class=" col-sm-4">
                    <center><h3>Stock Analysis</h3></center>
                    <table>
                        <th>Date Stamp</th>
                        <th>State</th>
                        <th>Quantity</th>
                            <%
                                LinkedHashMap<String, Item> itemSnapshots = (LinkedHashMap) request.getAttribute("itemSnapshots");
                                double stockBefore = 0.0;
                                for (Map.Entry<String, Item> itemSnapshotEntry : itemSnapshots.entrySet()) {
                                    Item itemSnapshot = itemSnapshotEntry.getValue();
                                    Double stock = Double.parseDouble(itemSnapshot.getQuantity());

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
                                    out.println(stock - stockBefore);
                                    out.println("</td>");
                                    out.println("</tr>");
                                    stockBefore = stock;

                                }
                            %>
                    </table>
                </div>
                <div class=" col-sm-4">
                    <center><h3>Sales</h3></center>
                    <table>
                        <th>Date Stamp</th>
                        <th>E-Shop Sales</th>
                        <th>Ενδοδιακ.</th>
                            <%
                                ItemSales itemSales = (ItemSales) request.getAttribute("itemSales");
                                TreeMap<LocalDate, Sales> sales = itemSales.getSales();
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
                </div>
                <hr>
                <div class=" col-sm-4">
                    <center><h3>Offers</h3></center>
                    <table>
                        <th>Titel</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                            <%
                                ArrayList<Offer> offers = (ArrayList<Offer>) request.getAttribute("offers");
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                                for (Offer offer : offers) {

                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println(offer.getTitle());
                                    out.println("</td>");
                                    String startDateString = formatter.format(offer.getStartDate());
                                    out.println("<td>");
                                    out.println(startDateString);
                                    out.println("</td>");

                                    Date endDate = offer.getEndDate();
                                    if (endDate == null) {
                                        out.println("<td>");
                                        out.println("<a href='endOfferDashboard.htm?id=" + offer.getId() + "'>End Offer</a>");
                                        out.println("</td>");
                                    } else {
                                        String endDateString = formatter.format(endDate);
                                        out.println("<td>");
                                        out.println(endDateString);
                                        out.println("</td>");
                                    }

                                    out.println("</tr>");

                                }

                            %>
                    </table>
                    <hr>
                    <a href="offerDashboard.htm?code=${item.getCode()}" class="btn btn-info btn-lg" role="button" aria-disabled="true">Add Offer</a>

                </div>
            </div>
        </div>
    </body>
</html>
