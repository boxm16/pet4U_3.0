<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="InputOutput.InputOutput"%>
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
        <title>Input Output Display</title>
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
                    <h5>Pet4U Item Input Output</h5>


                    <hr>
                    <center><h3>Offers</h3></center>

                </div>

                <div class=" col-sm-4">
                    <center><h3>Sales</h3></center>

                </div>

                <div class=" col-sm-4">
                    <center><h3>Total Stock Analysis</h3></center>


                </div>

            </div>
            <hr>
            <div class="row">
                <div class=" col-sm-4">
                    <center><h3>Daily Input Output</h3></center>
                    <table>

                        <th>Date</th>
                        <th>Delivery</th>
                        <th>Ενδο Παραλαβη</th>
                        <th>E-Shop Sales</th>

                        <%

                            Item item = (Item) request.getAttribute("item");

//-----------------
                            LinkedHashMap<LocalDate, InputOutput> inputOutputs = (LinkedHashMap) request.getAttribute("inputOutputs");

                            double allDaysSales = 0;
                            int days = 0;

                            for (Map.Entry<LocalDate, InputOutput> inputOutputsEntry : inputOutputs.entrySet()) {
                                LocalDate date = inputOutputsEntry.getKey();
                                InputOutput inputOutput = inputOutputsEntry.getValue();
                                DailySale dailySale = inputOutput.getDailySale();
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

                                out.println("<td>");
                                out.println(inputOutput.getDelivery());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(inputOutput.getEndoParalavi());
                                out.println("</td>");
                                
                                  out.println("<td>");
                                out.println(inputOutput.getEn());
                                out.println("</td>");
                                

                                if (dailySale.getPresoldQuantiy() > 0) {
                                    out.println("<td style='background-color: red;'>");
                                    String bb = dailySale.getSoldQuantiy() + "/" + dailySale.getPresoldQuantiy();
                                    out.println("<a  href = 'getAllSalesDocsOfDateAndItem.htm?itemCode=" + item.getCode() + "&date=" + date + "' target='_blank'>" + bb + "</a>");

                                } else {
                                    out.println("<td>");

                                    out.println("<a  href = 'getAllSalesDocsOfDateAndItem.htm?itemCode=" + item.getCode() + "&date=" + date + "' target='_blank'>" + dailySale.getSoldQuantiy() + "</a>");

                                }

                                out.println("</td>");
                                
                                

                                out.println("</tr>");

                                allDaysSales = allDaysSales + dailySale.getSoldQuantiy();
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
                                LinkedHashMap<LocalDate, ItemSnapshot> itemSnapshots = (LinkedHashMap) request.getAttribute("allSnapshots");
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

                </div>


            </div>

        </div>

    </body>
</html>
