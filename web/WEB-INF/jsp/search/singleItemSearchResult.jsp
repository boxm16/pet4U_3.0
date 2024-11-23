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

            input[type="number"] {
                width: 2.5em;
                font-size: 55px;
            }

        </style>
    </head>
    <body>
        <div class="container" >
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">

                    <center>
                        <%
                            Item item = (Item) request.getAttribute("item");
                            String target = (String) request.getAttribute("target");
                            if (item == null) {
                                out.println("<h3 style='color:red'>Target Altercode: " + target + "");
                                out.println("<hr><br>Item with that altercode<br>could not be found. ");
                                out.println("<hr><br>Try again  </h3>");
                            } else {
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
                                   
                                    if (altercodeContainer.getStatus().equals("eshop")
                                            || altercodeContainer.getStatus().equals("eshop-on")
                                            || altercodeContainer.getStatus().equals("eshop-barf")
                                            || altercodeContainer.getStatus().equals("eshop-kat")
                                            || altercodeContainer.getStatus().equals("eshop-pro")) {

                                        out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercodeContainer.getAltercode() + "' target='_blank'>" + "<strong>" + altercodeContainer.getAltercode() + " : " + altercodeContainer.getStatus() + "</strong>" + "</a>");

                                    } else {
                                        if (altercodeContainer.getStatus().isEmpty()) {
                                            out.println("<strong style='color:" + color + "'>" + altercodeContainer.getAltercode() + "</strong>");
                                        } else {
                                            out.println("<strong style='color:" + color + "'>" + altercodeContainer.getAltercode() + " : " + altercodeContainer.getStatus() + "</strong>");
                                        }

                                    }

                                    out.println("<br>");

                                }
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("</tbody>");
                                out.println("</table>");
                            }
                        %>
                        <hr>
                        <a href="searchDashboard.htm"><h3>New Search</h3></a>
                        <hr>
                        <a href="index.htm"><h3>INDEX</h3></a>
                        <br><br><br><br><br><br>
                        <hr>

                        <% if (item != null) {
                                out.println(" <a href='getItemForInventory.htm?altercode=" + item.getCode() + "' class='btn btn-primary btn-lg' role='button' aria-disabled='true'><h3>Inventory</h3></a>");
                                out.println("<hr>");
                                out.println(" <a href='goForReplenishment.htm?altercode=" + item.getCode() + "' class='btn btn-danger btn-lg' role='button' aria-disabled='true' style='background-color: #f955d4'><h3>ΚΑΝΕ ΑΝΑΠΛΗΡΩΣΗ</h3></a>");
                                // out.println(" <a href='makeBestBeforeStatement.htm?altercode=" + item.getCode() + "' class='btn btn-danger btn-lg' role='button' aria-disabled='true'><h3>Best Before</h3></a>");
                                out.println("<hr>");

                                out.println("<hr>");
                                out.println(" <a href='getItemForNote.htm?altercode=" + item.getCode() + "' class='btn btn-warning btn-lg' role='button' aria-disabled='true'><h3>Make Note</h3></a>");
                                out.println("<hr>");

                                out.println("<hr>");
                                out.println(" <a href='getItemForNotForEndo.htm?altercode=" + item.getCode() + "' class='btn btn-info btn-lg' role='button' aria-disabled='true'><h3>Mark As 'Not For Endo'</h3></a>");
                                out.println("<hr>");
                                out.println("<hr>");
                                out.println(" <a href='printBarcode.htm?altercode=" + target + "' class='btn btn-danger btn-lg' role='button' aria-disabled='true'><h3>PRINT BARCODE LABEL</h3></a>");
                                out.println("<hr>");
                                out.println(" <a href='printQRcode.htm?altercode=" + target + "' class='btn btn-danger btn-lg' role='button' aria-disabled='true' style='background-color: #d69f93'><h3>PRINT EAN<br>BARCODE LABEL</h3></a>");
                                out.println("<hr>");

                                out.println("<form action='printSmallLabels.htm' method='POST' target='_blank'>");
                                out.println("<h1>  <input type='text' hidden  name='altercode' value='" + target + "'></h1>");
                                out.println("<h1>  <input type='number'  name='labelsQuantity' value='1'></h1>");
                                out.println("<button type='submit' class='btn btn-success'> <h1>Print Small Labels</h1></button>");
                                out.println("</form>");

                                String userName = (String) session.getAttribute("userName");
                                if (userName != null) {
                                    if (userName.equals("me") || userName.equals("vasilis") || userName.equals("super")) {
                                        out.println("<hr>");
                                        out.println("<hr>");
                                        out.println("<hr>");
                                        out.println(" <a href='itemAnalysis.htm?code=" + item.getCode() + "' class='btn btn-info btn-lg' role='button' aria-disabled='true'><h3>Show Item Analysis</h3></a>");

                                        out.println("<hr>");
                                    }
                                }

                            }%>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>

        </div>


    </body>
</html>

