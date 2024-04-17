<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="CamelotItemsOfInterest.ItemSnapshot"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="StockAnalysis.StockAnalysis"%>
<%@page import="MonthSales.MonthSales"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="Offer.Offer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.time.LocalDate"%>
<%@page import="MonthSales.Sales"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="BasicModel.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Item Analysis</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 15px;
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
                    <h5><a href="index.htm">INDEX</a></h5>
                    <h5>Camelot Item Analysis</h5>

                    <table style='background-color: #DB98EF'> 
                        <tr><td>Code</td><td>${item.code}</td></tr>
                        <tr><td>Description</td><td>${item.description}</td></tr>
                        <tr><td>Position</td><td>${item.position}</td></tr>
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

                                    out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercodeContainer.getAltercode() + "' target='_blank'>" + "<strong>" + altercodeContainer.getAltercode() + " : " + altercodeContainer.getStatus() + "</strong>" + "</a>");
                                    out.println("<br>");
                                } else {
                                    if (altercodeContainer.getStatus().isEmpty()) {
                                        out.println("<strong>" + altercodeContainer.getAltercode() + "</strong>");
                                    } else {
                                        out.println("<strong>" + altercodeContainer.getAltercode() + " : " + altercodeContainer.getStatus() + "</strong>");
                                    }
                                    out.println("<br>");
                                }
                            }
                            out.println("</td>");
                            out.println("</tr>");
                        %>
                    </table>
                </div>

                <div class=" col-sm-4">
                    <center><h3>Sales</h3></center>
                    <table>
                        <th>-</th>
                        <th>Month Name</th>
                        <th>Sales</th>

                        <%                                MonthSales itemSales = (MonthSales) request.getAttribute("itemSales");
                            TreeMap<LocalDate, Sales> sales = itemSales.getSales();
                            int totalMonths = sales.size();
                            int currentMonth = 0;
                            double totalSales = 0;

                            for (Map.Entry<LocalDate, Sales> salesEntry : sales.entrySet()) {
                                LocalDate date = salesEntry.getKey();
                                Sales sale = salesEntry.getValue();

                                if (currentMonth >= (totalMonths - 6)) {
                                    out.println("<tr style='background-color:#D0D0D0'>");
                                } else {
                                    out.println("<tr>");
                                }
                                out.println("<td>");
                                out.println(date.getMonthValue());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(date.getMonth());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(sale.getEshopSales());
                                out.println("</td>");

                                out.println("</tr>");

                                if (currentMonth >= (totalMonths - 6)) {
                                    totalSales += sale.getEshopSales();

                                } else {
                                    //do nothing
                                }
                                currentMonth++;

                            }
                            out.println("<tr style='color: green'>");
                            out.println("<td colspan='2'>");
                            out.println("LAST 6 MONTHS TOTALS");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(totalSales);
                            out.println("</td>");

                            out.println("</tr>");

                            out.println("<tr style='color: #BA4A00'>");
                            out.println("<td colspan='2'>");
                            out.println("One Month Average ΕΞΑΓΩΓΕΣ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(totalSales / 6);
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("</tr>");


                        %>
                    </table>
                </div>

                <div class=" col-sm-4">

                </div>

            </div>
            <hr>
            <div class="row">
                <div class=" col-sm-4">

                </div>


                <div class=" col-sm-4">
                    <center><h3>Day Sales</h3></center>
                    <table>

                        <th>Date</th>
                        <th>E-Shop Sales</th>

                        <%                                LinkedHashMap<String, Double> daysSales = (LinkedHashMap) request.getAttribute("daysSales");

                            double allDaysSales = 0;
                            int days = 0;

                            for (Map.Entry<String, Double> daysSalesEntry : daysSales.entrySet()) {
                                String date = daysSalesEntry.getKey();

                                Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

                                String[] weekdays = {"Κυριακη.", "Δευτερα.", "Τρίτη", "Τετάρτη", "Πέμπτη.", "Παρασκεύη.", "Σάββατο."};
                                int day = date0.getDay();

                                if (day == 0) {
                                    out.println("<tr style='background-color: #90EE90;'>");

                                } else {
                                    out.println("<tr >");

                                }

                                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");

                                out.println("<td>");
                                out.println(format2.format(date0) + "<br>" + weekdays[day]);
                                out.println("</td>");

                                out.println("<td>");
                                out.println(daysSalesEntry.getValue());
                                out.println("</td>");

                                out.println("</tr>");
                                
                                allDaysSales = allDaysSales + daysSalesEntry.getValue();
                                days++;
                            }
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("Days");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(days);
                            out.println("</td>");

                            out.println("</tr>");

                            out.println("<tr style='font-size:30px'>");
                            out.println("<td>");
                            out.println("Total Sales <br> For 30 Day");
                            out.println("</td>");

                            out.println("<td>");
                            out.println(allDaysSales);
                            out.println("</td>");

                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("Average Sales <br> For One Day");
                            out.println("</td>");

                            out.println("<td>");
                            out.println(allDaysSales / days);
                            out.println("</td>");

                            out.println("</tr>");


                        %>
                    </table>
                </div>
                <div class=" col-sm-4">
                    <center><h3>Camelot Stock Analysis</h3></center>
                    <table>
                        <th>Date Stamp</th>
                        <th>State</th>
                        <th>Quantity</th>
                            <%                                ArrayList<ItemSnapshot> camelotItemSnapshots = (ArrayList) request.getAttribute("camelotItemSnapshots");

                                double camelotStockBefore = 0.0;
                                if (camelotItemSnapshots.size() == 1) {
                                    ItemSnapshot camelotItemSnapshot = camelotItemSnapshots.get(0);

                                    Double camelotStock = Double.parseDouble(camelotItemSnapshot.getQuantity());

                                    String date = camelotItemSnapshot.getDateStamp();
                                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

                                    String[] weekdays = {"Κυριακη.", "Δευτερα.", "Τρίτη", "Τετάρτη", "Πέμπτη.", "Παρασκεύη.", "Σάββατο."};
                                    int day = date1.getDay();

                                    if (day == 0) {
                                        out.println("<tr style='background-color: #90EE90;'>");

                                    } else {
                                        out.println("<tr >");

                                    }
                                    out.println("<td>");
                                    out.println(camelotItemSnapshot.getReformatedDateStamp() + "<br>" + weekdays[day]);
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(camelotItemSnapshot.getState());
                                    out.println("</td>");

                                    if (camelotItemSnapshot.getQuantity().equals("0") || camelotItemSnapshot.getQuantity().equals("0.000000")) {
                                        out.println("<td style='background-color: #F7B2F7'>");
                                    } else {
                                        out.println("<td>");
                                    }
                                    out.println(camelotItemSnapshot.getQuantity());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println();
                                    out.println("</td>");
                                    out.println("</tr>");

                                } else {

                                    for (int y = 0; y < camelotItemSnapshots.size() - 1; y++) {
                                        ItemSnapshot camelotItemSnapshot = camelotItemSnapshots.get(y);

                                        camelotStockBefore = Double.parseDouble(camelotItemSnapshots.get(y + 1).getQuantity());

                                        Double camelotStock = Double.parseDouble(camelotItemSnapshot.getQuantity());

                                        String date = camelotItemSnapshot.getDateStamp();
                                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

                                        String[] weekdays = {"Κυριακη.", "Δευτερα.", "Τρίτη", "Τετάρτη", "Πέμπτη.", "Παρασκεύη.", "Σάββατο."};
                                        int day = date1.getDay();

                                        if (day == 0) {
                                            out.println("<tr style='background-color: #90EE90;'>");

                                        } else {
                                            out.println("<tr >");

                                        }
                                        out.println("<td>");
                                        out.println(camelotItemSnapshot.getReformatedDateStamp() + "<br>" + weekdays[day]);
                                        out.println("</td>");

                                        out.println("<td>");
                                        out.println(camelotItemSnapshot.getState());
                                        out.println("</td>");

                                        if (camelotItemSnapshot.getQuantity().equals("0") || camelotItemSnapshot.getQuantity().equals("0.000000")) {
                                            out.println("<td style='background-color: #F7B2F7'>");
                                        } else {
                                            out.println("<td>");
                                        }
                                        out.println(camelotItemSnapshot.getQuantity());
                                        out.println("</td>");

                                        out.println("<td>");
                                        out.println(camelotStock - camelotStockBefore);
                                        out.println("</td>");
                                        out.println("</tr>");
                                        camelotStockBefore = camelotStock;
                                    }
                                }
                            %>
                    </table>
                </div>

            </div>
        </div>

    </body>
</html>
