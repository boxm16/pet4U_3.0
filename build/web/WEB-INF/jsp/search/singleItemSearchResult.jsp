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
        <div class="container" >
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">
                </div>
                <center>
                    <%
                        Item item = (Item) request.getAttribute("item");
                        out.println("<table class='table' style='background-color: #35B62F'>");
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

                        out.println("</tbody>");
                        out.println("</table>");
                    %>
                    <hr>
                    <a href="searchDashboard.htm"><h3>New Search</h3></a>
                    <hr>
                    <a href="index.htm"><h3>INDEX</h3></a>
                    <br><br><br><br><br><br><br><br><br><br><br><br>
                    <hr>
                    <a href="getItemForInventory.htm?altercode=${item.getCode()}" class="btn btn-primary btn-lg" role="button" aria-disabled="true"><h3>Inventory</h3></a>
                    <hr>
                    <a href="makeBestBeforeStatement.htm?altercode=${item.getCode()}" class="btn btn-danger btn-lg" role="button" aria-disabled="true"><h3>Best Before</h3></a>
                    <hr>
                    <a href="showItemSales.htm?altercode=${item.getCode()}" class="btn btn-warning btn-lg" role="button" aria-disabled="true"><h3>Show Item Sales</h3></a>
                    <hr>
                    <a href="pet4uItemSnapshots.htm?code=${item.getCode()}" class="btn btn-light btn-lg" role="button" aria-disabled="true"><h3>Show Item Snapshots</h3></a>
                    <hr>

                </center>
            </div>
            <div class=" col-sm-4">

            </div>
        </div>


    </body>
</html>

