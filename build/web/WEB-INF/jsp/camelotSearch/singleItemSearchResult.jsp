<%@page import="java.util.ArrayList"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="BasicModel.Item"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Single Item Search Result</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;

            }
            td{
                text-align: center;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">
                    <center>
                        <%
                            Item item = (Item) request.getAttribute("item");
                            out.println("<table class='table' style='background-color: #D052DB'>");
                            out.println("<tbody>");
                            out.println("<tr style='background-color:lightblue'>");
                            out.println("<td style='width:70px;'>");
                            out.println("Πε/φη");
                            out.println("</td>");
                            out.println("<td style='font-size: 20px;'>");
                            out.println("<strong>" + item.getDescription() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr style='background-color:lightgreen'>>");
                            out.println("<td style='width:70px;'>");
                            out.println("Θεση");
                            out.println("</td>");
                            out.println("<td style='font-size: 30px;'>");
                            out.println("<strong>" + item.getPosition() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td style='width:70px'>");
                            out.println("Υπλ.");
                            out.println("</td>");
                            out.println("<td style='font-size: 30px;'>");
                            out.println("<strong>" + item.getQuantity() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td style='width:70px'>");
                            out.println("Κατ.");
                            out.println("</td>");
                            out.println("<td style='font-size: 30px;'>");
                            out.println("<strong>" + item.getState() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr style='background-color:#F1F1F1'>");
                            out.println("<td colspan='2' style='font-size: 30px;'>");
                            ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
                            for (AltercodeContainer altercodeContainer : altercodes) {
                                String color = "";
                                if (altercodeContainer.isMainBarcode()) {
                                    color = "red";
                                }
                                if (altercodeContainer.isPackageBarcode()) {
                                    color = "green";
                                }

                                if (altercodeContainer.getAltercode().equals(item.getCode())) {
                                    color = "brown";
                                }
                                if (altercodeContainer.getStatus().isEmpty()) {
                                    out.println("<strong style='color:" + color + "'>" + altercodeContainer.getAltercode() + "</strong>");
                                } else {
                                    out.println("<strong style='color:" + color + "'>" + altercodeContainer.getAltercode() + " : " + altercodeContainer.getStatus() + "</strong>");
                                }
                                out.println("<br>");
                            }
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td style='width:70px' colspan='2'>");
                            out.println("<a href='https://www.petcamelot.gr/search-products-el.html?match=all&subcats=Y&pcode_from_q=Y&pshort=Y&pfull=Y&pname=Y&pkeywords=Y&search_performed=Y&q=" + item.getCode() + "' target='_blank'>" + "<strong>SITE LINK</strong>" + "</a>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("</tbody>");
                            out.println("</table>");
                        %>
                        <hr>
                        <a href="getCamelotItemForNote.htm?altercode=${item.getCode()}" class="btn btn-warning btn-lg" role="button" aria-disabled="true"><h3>Make Note</h3></a>
                        <hr>
                        <a href="camelotStockPositions.htm?itemCode=${item.getCode()}" class="btn btn-success btn-lg" role="button" aria-disabled="true"><h3>ΘΕΣΕΙΣ ΣΤΟΚ</h3></a>
                        <hr>

                        <a href='goForCamelotReplenishment.htm?altercode=${item.getCode()}' class='btn btn-danger btn-lg' role='button' aria-disabled='true' style='background-color: #f955d4'><h3>ΚΑΝΕ ΑΝΑΠΛΗΡΩΣΗ</h3></a>
                        <hr> 
                        <a href='printCamelotBarcode.htm?altercode=${target}' class='btn btn-danger btn-lg' role='button' aria-disabled='true'><h3>PRINT BARCODE LABEL</h3></a>
                        <hr>
                        <hr> 
                        <a href='printCamelotMainBarcode.htm?altercode=${target}' class='btn btn-info btn-lg' role='button' aria-disabled='true'><h3>FOR TRIAL ONLY</h3></a>
                        <hr>
                        <%
                            String userName = (String) session.getAttribute("userName");
                            if (userName != null) {
                                if (userName.equals("me") || userName.equals("super")) {
                                    out.println("<a  href = 'camelotItemAnalysis.htm?code=" + item.getCode() + "'  class ='btn btn-info btn-lg' role='button' aria-disabled='true'><h3>Show Item Analysis</h3></a>");
                                }
                            }

                        %>
                        <hr>
                        <a href="camelotSearchDashboard.htm"><h3>New Search</h3></a>
                        <hr>
                        <a href="index.htm"><h3>INDEX</h3></a>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>


    </body>
</html>

