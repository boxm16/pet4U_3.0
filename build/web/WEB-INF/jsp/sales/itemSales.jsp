<%@page import="SalesX.SoldItem"%>
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
                        SoldItem item = (SoldItem) request.getAttribute("soldItem");
                        out.println("<table class='table' style='background-color: #FFD700'>");
                        out.println("<tbody>");
                        out.println("<tr style='background-color:lightblue'>");
                        out.println("<td style='width:70px;'>");
                        out.println("Πε/φη");
                        out.println("</td>");
                        out.println("<td style='font-size: 20px;'>");
                        out.println("<strong>" + item.getDescription() + "</strong>");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println("<tr>");
                        out.println("<td style='width:70px;'>");
                        out.println("Θεση");
                        out.println("</td>");
                        out.println("<td style='font-size: 30px;'>");
                        out.println("<strong>" + item.getPosition() + "</strong>");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println("<tr>");
                        out.println("<td style='width:70px'>");
                        out.println("Πώληση");
                        out.println("</td>");
                        out.println("<td style='font-size: 20px;'>");
                        out.println("<strong>" + item.getEshopSoldPieces() + "</strong>");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println("<tr>");
                        out.println("<td style='width:70px'>");
                        out.println("Ενδο.");
                        out.println("</td>");
                        out.println("<td style='font-size: 20px;'>");
                        out.println("<strong>" + item.getShopsSuppliedPieces() + "</strong>");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println("<tr>");
                        out.println("<td style='width:70px'>");
                        out.println("Σύνολο");
                        out.println("</td>");
                        out.println("<td style='font-size: 40px;'>");
                        out.println("<strong>" + item.getTotalShippedPieces() + "</strong>");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println("<tr>");
                        out.println("<td style='width:70px'>");
                        out.println("Εβδομαδ.");
                        out.println("</td>");
                        out.println("<td style='font-size: 30px;'>");
                        Double weekSales = item.getTotalShippedPieces() / 26;
                        out.println("<strong>" + weekSales.intValue() + "</strong>");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println("</tbody>");
                        out.println("</table>");
                    %>
                    <hr>
                    <a href="index.htm"><h3>INDEX</h3></a>
                </center>
            </div>
            <div class=" col-sm-4">
            </div>
        </div>
    </body>
</html>

