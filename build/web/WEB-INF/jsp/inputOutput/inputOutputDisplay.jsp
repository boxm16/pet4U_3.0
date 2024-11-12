<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="InputOutput.InputOutputContainer"%>
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

                <div class=" col-sm-12">
                    <h5><a href="index.htm">INDEX</a></h5>
                    <h5>Pet4U Item Input Output</h5>



                    <center><h3>Daily Input Output</h3></center>
                    <table>

                        <th>Date</th>
                        <th>Position</th>
                        <th>State</th>

                        <th>Delivery</th>
                        <th>Ενδο Παραλαβη</th>
                        <th>Ενδο Αποστολη</th>
                        <th>E-Shop Sales</th>
                        <th>Stock</th>
                        <th>Snapshot<br>Stock<br>Diff</th>
                        <th>Input Output<br>Diff</th>
                        <th>Alarms</th>


                        <%
                            Item item = (Item) request.getAttribute("item");
                            LinkedHashMap<LocalDate, InputOutput> inputOutputs = (LinkedHashMap) request.getAttribute("inputOutputs");
                            int index = 1;

                            for (Map.Entry<LocalDate, InputOutput> inputOutputsEntry : inputOutputs.entrySet()) {
                                InputOutput inputOutput = inputOutputsEntry.getValue();

                                double previousDayStock = 0;
                                int snapshotIndex = 0;

                                LocalDate date = inputOutputsEntry.getKey();

                                DailySale dailySale = inputOutput.getDailySale();
                                if (date.getDayOfWeek().toString().equals("SUNDAY")) {
                                    out.println("<tr style='background-color: #90EE90;'>");

                                } else {
                                    out.println("<tr >");

                                }
                                String[] weekdays = {"", "Δευτερα.", "Τρίτη", "Τετάρτη", "Πέμπτη.", "Παρασκεύη.", "Σάββατο.", "Κυριακη."};
                                int day = date.getDayOfWeek().getValue();

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                                ItemSnapshot itemSnapshot = inputOutput.getItemSnapshot();

                                out.println("<td>");
                                out.println(date.format(formatter) + "<br>" + weekdays[day]);
                                out.println("</td>");

                                if (itemSnapshot == null) {

                                    out.println("<td>");
                                    out.println("--");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("--");
                                    out.println("</td>");
                                } else {

                                    out.println("<td>");
                                    out.println(inputOutput.getItemSnapshot().getPosition());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(inputOutput.getItemSnapshot().getState());
                                    out.println("</td>");
                                }

                                out.println("<td>");
                                out.println(inputOutput.getDelivery());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(inputOutput.getEndoParalavi());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(inputOutput.getEndoApostoli());
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

                                if (itemSnapshot == null) {

                                    out.println("<td>");
                                    out.println("--");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("--");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("--");
                                    out.println("</td>");

                                } else {
                                    double stock = Double.parseDouble(inputOutput.getItemSnapshot().getQuantity());
                                    out.println("<td>");
                                    out.println(stock);
                                    out.println("</td>");
                                    out.println("<td>");
                                    double snapshotDiff = stock - previousDayStock;
                                    out.println(snapshotDiff);
                                    out.println("</td>");
                                    out.println("<td>");
                                    double inputOutputDiff = inputOutput.getDelivery()
                                            + inputOutput.getEndoParalavi()
                                            - inputOutput.getEndoApostoli()
                                            - inputOutput.getDailySale().getSoldQuantiy();
                                    out.println(inputOutputDiff);
                                    out.println("</td>");

                                    if (snapshotDiff == inputOutputDiff || snapshotIndex == 0) {
                                        out.println("<td>");
                                        out.println();
                                        out.println("</td>");
                                    } else {
                                        out.println("<td style='background-color:red'>");
                                        out.println("ALARM");
                                        out.println("</td>");
                                    }

                                    previousDayStock = stock;
                                }

                                out.println("</tr>");
                                snapshotIndex++;
                            }
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("-------");
                            out.println("</td>");
                            out.println("</tr>");
                        %>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
