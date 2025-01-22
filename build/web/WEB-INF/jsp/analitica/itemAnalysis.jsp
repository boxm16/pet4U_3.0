<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.List"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="DailySales.DailySale"%>
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
                    <h5>Pet4U Item Analysis</h5>

                    <table style='font-size: 25px;'> 
                        <tr><td>Code</td><td>${item.code}</td></tr>
                        <tr><td>Description</td><td>${item.description}</td></tr>
                        <tr>
                            <td>Position</td>
                            <td  ondblclick= 'redirect(${item.itemId})'>${item.position}</td>
                        </tr>
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
                                        || altercodeContainer.getStatus().equals("eshop-kat")
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
                            String itemCode = item.getCode();
                            if (item.getCode().contains(".-WE")) {
                                itemCode = item.getCode().replace(".-WE", "");
                            }
                            if (itemCode.contains("-WE")) {
                                itemCode = itemCode.replace("-WE", "");
                            }

                            out.println("<tr>");
                            out.println("<td colspan='2' >");
                            out.println("<a  href = 'camelotItemAnalysis.htm?code=" + item.getCode() + "' target='_blank'>Camelot Analysis </a>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td colspan='2' >");
                            out.println("<a  href = 'getItemForNote.htm?altercode=" + item.getCode() + "' target='_blank'>Make Note</a>");
                            out.println("</td>");
                            out.println("</tr>");
                        %>
                    </table>
                    <hr>
                    <center><h3>Offers</h3></center>
                    <table>
                        <th>Titel</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                            <%                                ArrayList<Offer> offers = (ArrayList<Offer>) request.getAttribute("offers");

                                for (Offer offer : offers) {

                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println(offer.getTitle());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(offer.getStartDateString());
                                    out.println("</td>");

                                    LocalDate endDate = offer.getEndDate();
                                    if (endDate == null) {
                                        out.println("<td>");
                                        out.println("<a href='endOfferDashboard.htm?id=" + offer.getId() + "'>End Offer</a>");
                                        out.println("</td>");
                                    } else {

                                        out.println("<td>");
                                        out.println(offer.getEndDateString());
                                        out.println("</td>");
                                    }

                                    out.println("</tr>");

                                }
                            %>
                    </table>
                    <hr>
                    <center><h3>Daily Sales</h3></center>
                    <table>

                        <th>Date</th>
                        <th>E-Shop Sales</th>
                        <th>Ενδοδιακ.</th>
                            <%
                                LinkedHashMap< LocalDate, DailySale> dailySalesA = (LinkedHashMap) request.getAttribute("dailySales");

                                double allDaysSalesA = 0;
                                int workingDays = 0;
                                String preSoldAlarm = "inherit";
                                for (Map.Entry<LocalDate, DailySale> daysSalesEntry : dailySalesA.entrySet()) {
                                    LocalDate date = daysSalesEntry.getKey();
                                    if (date.getDayOfWeek().toString().equals("SUNDAY")) {
                                        // do nothing;
                                    } else {
                                        allDaysSalesA = allDaysSalesA + daysSalesEntry.getValue().getSoldQuantiy();
                                        workingDays++;
                                    }
                                    if (daysSalesEntry.getValue().getPresoldQuantiy() > 0) {
                                        preSoldAlarm = "red";
                                    }
                                }

                                out.println("<tr>");
                                out.println("<td>");
                                out.println("Working Days");
                                out.println("</td>");
                                out.println("<td>");
                                out.println(workingDays);
                                out.println("</td>");

                                out.println("</tr>");

                                out.println("<tr style='background-color: #A6E2D0'>");
                                out.println("<td>");
                                out.println("Total Sales <br> For 24 <br>Working Days");
                                out.println("</td>");

                                out.println("<td>");
                                out.println(allDaysSalesA);
                                out.println("</td>");

                                out.println("</tr>");

                                out.println("<tr>");
                                out.println("<td>");
                                out.println("Average Sales <br> For One Day");
                                out.println("</td>");

                                out.println("<td>");
                                out.println(allDaysSalesA / workingDays);
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
                        <th>E-Shop Sales</th>
                        <th>Ενδοδιακ.</th>
                            <%                                MonthSales itemSales = (MonthSales) request.getAttribute("itemSales");
                                TreeMap<LocalDate, Sales> sales = itemSales.getSales();
                                int totalMonths = sales.size();
                                int currentMonth = 0;
                                double totalSales = 0;
                                double totalShopSupplies = 0;

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

                                    out.println("<td>");
                                    out.println(sale.getShopsSupply());
                                    out.println("</td>");

                                    out.println("</tr>");

                                    if (currentMonth >= (totalMonths - 6)) {
                                        totalSales += sale.getEshopSales();
                                        totalShopSupplies += sale.getShopsSupply();
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
                                out.println("<td>");
                                out.println(totalShopSupplies);
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("<tr style='color: blue'>");
                                out.println("<td colspan='3'>");
                                out.println("LAST 6 MONTHS GRAND TOTAL");
                                out.println("</td>");
                                out.println("<td>");
                                out.println(totalSales + totalShopSupplies);
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("<tr style='color: #BA4A00'>");
                                out.println("<td colspan='3'>");
                                out.println("One Month Average ΕΞΑΓΩΓΕΣ");
                                out.println("</td>");
                                out.println("<td>");
                                out.println((totalSales + totalShopSupplies) / 6);
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("</tr>");


                            %>
                    </table>
                </div>

                <div class=" col-sm-4">
                    <center><h3>Total Stock Analysis</h3></center>
                    <table>
                        <th>Name</th>
                        <th>Quantity</th>

                        <%                            StockAnalysis stockAnalysis = (StockAnalysis) request.getAttribute("stockAnalysis");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΒΑΡΥΜΠΟΜΗ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getVaribobiStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΧΑΛΚΗΔΟΝΑ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getXalkidonaStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΜΕΝΙΔΙ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getMenidiStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΚΑΛΛΙΘΕΑ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getKallitheaStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΑΛΙΜΟΣ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getAlimosStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΑΓ.ΠΑΡΑΣΚΕΥΗ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getAghiaParaskeviStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΔΑΦΝΗ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getDafniStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΚΟΥΚΑΚΙ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getKoukakiStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΜΙΧΑΛΑΚΟΠΟΥΛΟΥ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getMixalakopoulouStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΧΑΛΑΝΔΡΙ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getXalandriStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΝΕΑ ΙΩΝΙΑ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getNeaIoniaStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΑΡΓΥΡΟΥΠΟΛΗ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getArghiroupoliStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΠΕΡΙΣΤΕΡΙ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getPeristeriStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("ΠΕΤΡΟΥΠΟΛΗ");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(stockAnalysis.getPetroupoliStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("Π. ΦΑΛΗΡΟ");
                            out.println("</td>");

                            out.println("<td>");
                            out.println(stockAnalysis.getPalioFaliroStock());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("Προς Καταστρ.");
                            out.println("</td>");

                            out.println("<td>");
                            out.println(stockAnalysis.getKatastrofi());
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td>");
                            out.println("Ενδο");
                            out.println("</td>");

                            out.println("<td>");
                            out.println(stockAnalysis.getEndo());
                            out.println("</td>");
                            out.println("</tr>");


                        %>
                    </table>  
                    <hr>
                    <%         out.println("<a href='showItemTotalStockSnapshots.htm?item_code=" + stockAnalysis.getCode() + "' target='_blank'><h4>Show Item Total Stock Snapshots</h4></a>"); %>
                    <hr>
                </div>

            </div>
            <hr>
            <div class="row">
                <div class=" col-sm-4">
                    <center><h3>Daily Sales</h3></center>
                    <table>

                        <th>Date</th>
                        <th>E-Shop Sales</th>
                        <th>Ενδοδιακ.</th>
                            <%
                                /*
                                LinkedHashMap<String, Double> daysSales = (LinkedHashMap) request.getAttribute("daysSales");

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

                                out.println("<tr style='background-color: #A6E2D0'>");
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
                                 */

//-----------------
                                LinkedHashMap<LocalDate, DailySale> daysSales = (LinkedHashMap) request.getAttribute("dailySales");

                                double allDaysSales = 0;
                                int days = 0;

                                for (Map.Entry<LocalDate, DailySale> daysSalesEntry : daysSales.entrySet()) {
                                    LocalDate date = daysSalesEntry.getKey();

                                    if (date.getDayOfWeek().toString().equals("SUNDAY")) {
                                        out.println("<tr style='background-color: #90EE90;'>");

                                    } else {
                                        out.println("<tr >");

                                    }
                                    String[] weekdays = {"", "Δευτερα.", "Τρίτη", "Τετάρτη", "Πέμπτη.", "Παρασκεύη.", "Σάββατο.", "Κυριακη."};
                                    int day = date.getDayOfWeek().getValue();

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    out.println("<td>");
                                    out.println(date.format(formatter) + "<br>" + weekdays[day]);
                                    out.println("</td>");

                                    if (daysSalesEntry.getValue().getPresoldQuantiy() > 0) {
                                        out.println("<td style='background-color: red;'>");
                                        String bb = daysSalesEntry.getValue().getSoldQuantiy() + "/" + daysSalesEntry.getValue().getPresoldQuantiy();
                                        out.println("<a  href = 'getAllSalesDocsOfDateAndItem.htm?itemCode=" + item.getCode() + "&date=" + date + "' target='_blank'>" + bb + "</a>");

                                    } else {
                                        out.println("<td>");

                                        out.println("<a  href = 'getAllSalesDocsOfDateAndItem.htm?itemCode=" + item.getCode() + "&date=" + date + "' target='_blank'>" + daysSalesEntry.getValue().getSoldQuantiy() + "</a>");

                                    }

                                    out.println("</td>");

                                    out.println("</tr>");

                                    allDaysSales = allDaysSales + daysSalesEntry.getValue().getSoldQuantiy();
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

                                out.println("<tr style='background-color: #A6E2D0'>");
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
                    <center><h3>Varibobi Stock Analysis</h3></center>
                    <table>
                        <th>Date Stamp</th>
                        <th>State</th>
                        <th>Positon</th>
                        <th>Quantity</th>
                            <% /*                               ArrayList<ItemSnapshot> itemSnapshots = (ArrayList) request.getAttribute("itemSnapshots");
                                double stockBefore = 0.0;

                                for (int x = 0; x < itemSnapshots.size() - 1; x++) {
                                    ItemSnapshot itemSnapshot = itemSnapshots.get(x);
                                    stockBefore = Double.parseDouble(itemSnapshots.get(x + 1).getQuantity());
                                    Double stock = Double.parseDouble(itemSnapshot.getQuantity());

                                    String date = itemSnapshot.getDateStamp();
                                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

                                    String[] weekdays = {"Κυριακη.", "Δευτερα.", "Τρίτη", "Τετάρτη", "Πέμπτη.", "Παρασκεύη.", "Σάββατο."};
                                    int day = date1.getDay();

                                    if (day == 0) {
                                        out.println("<tr style='background-color: #90EE90;'>");

                                    } else {
                                        out.println("<tr >");

                                    }
                                    out.println("<td>");
                                    out.println(itemSnapshot.getReformatedDateStamp() + "<br>" + weekdays[day]);
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(itemSnapshot.getState());
                                    out.println("</td>");

                                    if (itemSnapshot.getQuantity().equals("0") || itemSnapshot.getQuantity().equals("0.000000")) {
                                        out.println("<td style='background-color: #F7B2F7'>");
                                    } else {
                                        out.println("<td>");
                                    }
                                    out.println(itemSnapshot.getQuantity());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(stock - stockBefore);
                                    out.println("</td>");
                                    out.println("</tr>");
                                    stockBefore = stock;

                                }
                                 */
                                LinkedHashMap<LocalDate, ItemSnapshot> itemSnapshots = (LinkedHashMap) request.getAttribute("last100DaysSnapshots");
                                List<LocalDate> keys = new ArrayList<>(itemSnapshots.keySet());
                                for (int k = 0; k < keys.size() - 1; k++) {
                                    LocalDate currentDate = keys.get(k);
                                    LocalDate previousDate = keys.get(k + 1);;   // Will fail if there isn't another element.

                                    ItemSnapshot currentItem = itemSnapshots.get(currentDate);
                                    ItemSnapshot previousItem = itemSnapshots.get(previousDate);

                                    // ...
                                    if (currentDate.getDayOfWeek().toString().equals("SUNDAY")) {
                                        out.println("<tr style='background-color: #90EE90;'>");

                                    } else {
                                        out.println("<tr >");

                                    }
                                    String[] weekdays = {"", "Δευτερα.", "Τρίτη", "Τετάρτη", "Πέμπτη.", "Παρασκεύη.", "Σάββατο.", "Κυριακη."};
                                    int day = currentDate.getDayOfWeek().getValue();

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    out.println("<td>");
                                    out.println(currentDate.format(formatter) + "<br>" + weekdays[day]);
                                    out.println("</td>");

                                    if (currentItem == null) {
                                        out.println("<td style='background-color: #F1BFB2 ;'>");
                                        out.println("N/A");
                                        out.println("</td>");
                                        out.println("<td style='background-color: #F1BFB2 ;'>");
                                        out.println("N/A");
                                        out.println("</td>");
                                        out.println("<td style='background-color: #F1BFB2 ;'>");
                                        out.println("N/A");
                                        out.println("</td>");
                                    } else {
                                        Double stock = Double.parseDouble(currentItem.getQuantity());

                                        out.println("<td>");
                                        out.println(currentItem.getState());
                                        out.println("</td>");

                                        out.println("<td>");
                                        out.println(currentItem.getPosition());
                                        out.println("</td>");

                                        if (currentItem.getQuantity().equals("0") || currentItem.getQuantity().equals("0.000000")) {
                                            out.println("<td style='background-color: #F7B2F7'>");
                                        } else {
                                            out.println("<td>");
                                        }
                                        out.println(currentItem.getQuantity());
                                        out.println("</td>");

                                        out.println("<td>");
                                        if (previousItem == null) {
                                            out.println("-");
                                        } else {
                                            out.println(stock - Double.parseDouble(previousItem.getQuantity()));
                                        }
                                        out.println("</td>");

                                    }
                                    out.println("</tr>");
                                }
                            %>
                    </table>
                </div>
                <div class=" col-sm-4">
                    <center><h3>Camelot Stock Analysis</h3></center>
                    <table>
                        <th>Date Stamp</th>
                        <th>State</th>
                        <th>Quantity</th>
                            <%
                                LinkedHashMap<LocalDate, ItemSnapshot> camelotItemSnapshots = (LinkedHashMap) request.getAttribute("camelotLast100DaysSnapshots");
                                List<LocalDate> camelotKeys = new ArrayList<>(camelotItemSnapshots.keySet());
                                for (int k = 0; k < camelotKeys.size() - 1; k++) {
                                    LocalDate currentDate = camelotKeys.get(k);
                                    LocalDate previousDate = camelotKeys.get(k + 1);;   // Will fail if there isn't another element.

                                    ItemSnapshot currentItem = camelotItemSnapshots.get(currentDate);
                                    ItemSnapshot previousItem = camelotItemSnapshots.get(previousDate);

                                    // ...
                                    if (currentDate.getDayOfWeek().toString().equals("SUNDAY")) {
                                        out.println("<tr style='background-color: #90EE90;'>");

                                    } else {
                                        out.println("<tr >");

                                    }
                                    String[] weekdays = {"", "Δευτερα.", "Τρίτη", "Τετάρτη", "Πέμπτη.", "Παρασκεύη.", "Σάββατο.", "Κυριακη."};
                                    int day = currentDate.getDayOfWeek().getValue();

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    out.println("<td>");
                                    out.println(currentDate.format(formatter) + "<br>" + weekdays[day]);
                                    out.println("</td>");

                                    if (currentItem == null) {
                                        out.println("<td style='background-color: #F1BFB2 ;'>");
                                        out.println("N/A");
                                        out.println("</td>");
                                        out.println("<td style='background-color: #F1BFB2 ;'>");
                                        out.println("N/A");
                                        out.println("</td>");
                                        out.println("<td style='background-color: #F1BFB2 ;'>");
                                        out.println("N/A");
                                        out.println("</td>");
                                    } else {
                                        String currentItemQuantit = currentItem.getQuantity();
                                        if (currentItemQuantit == null || currentItemQuantit.isEmpty()) {
                                            currentItemQuantit = "0.0";
                                        }
                                        Double stock = Double.parseDouble(currentItemQuantit);

                                        out.println("<td>");
                                        out.println(currentItem.getState());
                                        out.println("</td>");

                                        if (currentItem.getQuantity().equals("0") || currentItem.getQuantity().equals("0.000000")) {
                                            out.println("<td style='background-color: #F7B2F7'>");
                                        } else {
                                            out.println("<td>");
                                        }
                                        out.println(currentItem.getQuantity());
                                        out.println("</td>");

                                        out.println("<td>");
                                        if (previousItem == null || previousItem.getQuantity() == null || previousItem.getQuantity().isEmpty()) {
                                            out.println("-");
                                        } else {
                                            out.println(stock - Double.parseDouble(previousItem.getQuantity()));
                                        }
                                        out.println("</td>");

                                    }
                                    out.println("</tr>");
                                }

                                /*
                                ArrayList<ItemSnapshot> camelotItemSnapshots = (ArrayList) request.getAttribute("camelotItemSnapshots");

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
                                 */
                            %>
                    </table>
                </div>
            </div>
            <div class="row">
                <hr> 
                <%                        out.println("<center><h1>");
                    out.println("<a  href = 'getFullItemAnalysis.htm?code=" + item.getCode() + "' target='_blank'>Full Analysis </a>");
                    out.println("</h1></center>");
                %> 
            </div>

            <div class="row">
                <div class="col-sm-4">
                    <form action="getItemCodeSenderStore.htm" method="POST" target="_blank">
                        <h3>  <input type="text" hidden name="itemCode" value="${item.code}"></h3>
                        <h3>  <input type="date"  name="startDate" value="${before10DaysDate}"></h3>
                        <h3>  <input type="date"  name="endDate" value="${nowDate}"></h3>
                        <hr>
                        <button type="submit" class="btn btn-success"> <h5>კოდის ენდოდან შემოსვლა</h5></button>
                    </form>
                </div>

                <div class="col-sm-4">
                    <form action="inputOutput.htm" method="POST" target="_blank">
                        <h3>  <input type="text" hidden name="itemCode" value="${item.code}"></h3>
                        <h3>  <input type="date"  name="startDate" value="${before10DaysDate}"></h3>
                        <h3>  <input type="date"  name="endDate" value="${nowDate}"></h3>
                        <hr>
                        <button type="submit" class="btn btn-secondary"> <h1>კოდის  მოძრაობა (შემოსვლა/გასვლა)</h1></button>
                    </form>      
                </div>

                <div class="col-sm-4">

                </div>
            </div>


        </div>
        <script>

            function  redirect(itemId) {
                window.location.href = "goForPositionDeleting.htm?itemId=" + itemId;
            }
        </script>
    </body>
</html>
