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
        <title>Input Output Alarms Short Version</title>
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

            <hr>
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">
                    <center><h3>Daily Input Output</h3></center>
                    <table>
                        <th>Code</th>
                        <th>Description</th>
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
                            LinkedHashMap<String, InputOutputContainer> inputOutputContainers = (LinkedHashMap) request.getAttribute("inputOutputContainers");
                            int index = 1;

                            for (Map.Entry<String, InputOutputContainer> inputOutputContainersEntry : inputOutputContainers.entrySet()) {
                                InputOutputContainer inputOutputContainer = inputOutputContainersEntry.getValue();
                                LinkedHashMap<LocalDate, InputOutput> inputOutputs = inputOutputContainer.getInputOutputs();
                                Item item = inputOutputContainer.getItem();
                                double previousDayStock = 0;
                                int snapshotIndex = 0;

                                for (Map.Entry<LocalDate, InputOutput> inputOutputsEntry : inputOutputs.entrySet()) {
                                    LocalDate date = inputOutputsEntry.getKey();
                                    InputOutput inputOutput = inputOutputsEntry.getValue();

                                    DailySale dailySale = inputOutput.getDailySale();

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                                    ItemSnapshot itemSnapshot = inputOutput.getItemSnapshot();

                                    if (itemSnapshot == null) {

                                    } else {
                                        double stock = Double.parseDouble(inputOutput.getItemSnapshot().getQuantity());
                                        double snapshotDiff = stock - previousDayStock;
                                        double inputOutputDiff = inputOutput.getDelivery()
                                                + inputOutput.getEndoParalavi()
                                                - inputOutput.getEndoApostoli()
                                                - inputOutput.getDailySale().getSoldQuantiy();

                                        if (snapshotDiff == inputOutputDiff || snapshotIndex == 0) {
                                            snapshotIndex++;
                                            continue;
                                        }
                                        out.println("<tr>");
                                        out.println("<td>");
                                        out.println(item.getCode());
                                        out.println("</td>");
                                        out.println("<td>");
                                        out.println(item.getDescription());
                                        out.println("</td>");
                                        out.println("<td>");
                                        out.println(date.format(formatter));
                                        out.println("</td>");

                                        out.println("<td>");
                                        out.println(inputOutput.getItemSnapshot().getPosition());
                                        out.println("</td>");

                                        out.println("<td>");
                                        out.println(inputOutput.getItemSnapshot().getState());
                                        out.println("</td>");

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

                                        out.println("<td>");
                                        out.println(stock);
                                        out.println("</td>");
                                        out.println("<td>");

                                        out.println(snapshotDiff);
                                        out.println("</td>");
                                        out.println("<td>");

                                        out.println(inputOutputDiff);
                                        out.println("</td>");

                                        out.println("<td style='background-color:red'>");
                                        out.println("ALARM");
                                        out.println("</td>");

                                        previousDayStock = stock;
                                    }

                                    out.println("</tr>");
                                    snapshotIndex++;
                                }

                            }
                        %>
                    </table>
                </div>
                <div class=" col-sm-4">
                </div>
            </div>
        </div>
    </body>
</html>
