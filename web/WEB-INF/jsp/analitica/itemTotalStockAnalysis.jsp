<%-- 
    Document   : itemAnalysis
    Created on : Oct 22, 2023, 2:59:26 PM
    Author     : Michail Sitmalidis
--%>

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
        <title>Item Total Stock Analysis</title>
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
                <div class=" col-sm-4"></div>

                <div class=" col-sm-4">
                    <h5><a href="index.htm">INDEX</a></h5>
                    <h5>Pet4U Item Total Stock Analysis</h5>

                    <table> 
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
                <div class=" col-sm-4"></div>
            </div>

            <div class="row">
                <div class=" col-sm-12">
                    <center><h3>Total Stock Analysis</h3></center>
                    <table>
                        <th>Date</th>
                        <th>Χαλκ.</th>
                        <th>Μεν.</th>
                        <th>Καλλ.</th> 
                        <th>Αλιμ.</th>
                        <th>Αγ.Π.</th> 
                        <th>Δαφν.</th>
                        <th>Κουκ.</th>
                        <th>Μιχαλ.</th>
                        <th>Βαρ.</th>
                        <th>Χλνδ.</th> 
                        <th>Ν.Ιων.</th>
                        <th>Αργ.</th> 
                        <th>Περ.</th>
                        <th>Πετρ.</th>
                        <th>Χαλκ.</th>
                        <th>Π.Φαλ.</th> 
                        <th>Κατ.</th>
                        <th>Ενδο.</th> 



                        <%
                            LinkedHashMap<String, StockAnalysis> stockAnalysis = (LinkedHashMap) request.getAttribute("stockAnalysis");
                            for (Map.Entry<String, StockAnalysis> stockAnalysisEntry : stockAnalysis.entrySet()) {
                                //   out.println("<tr>");

                                /*out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getDate());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getXalkidonaStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getMenidiStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getKallitheaStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getAlimosStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getAghiaParaskeviStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getDafniStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getKoukakiStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getMixalakopoulouStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getVaribobiStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getXalandriStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getNeaIoniaStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getArghiroupoliStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getPeristeriStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getPetroupoliStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getPalioFaliroStock());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getKatastrofi());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(stockAnalysisEntry.getValue().getEndo());
                                out.println("</td>");
                                 */
                                //   out.println("</tr>");
                            }

                        %>
                    </table>  
                    <hr>

                </div>
            </div>
        </div>


    </body>
</html>
