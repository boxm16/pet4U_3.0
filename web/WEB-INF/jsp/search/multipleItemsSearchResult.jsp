<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="BasicModel.Item"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Multiple Item Search Result</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

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


                    <center>
                        <a href="index.htm"><h4>INDEX</h4></a>
                        <hr>
                        <a href="searchDashboard.htm"><h4>New Search</h4></a>
                        <h6>Search Target : ${target} </h6>
                        <h6> Items found: ${items.size()} </h6>
                        <%
                            LinkedHashMap<Integer, Item> items = (LinkedHashMap) request.getAttribute("items");
                            for (Map.Entry<Integer, Item> entrySet : items.entrySet()) {
                                Item item = entrySet.getValue();
                                out.println("<table class='table' style='background-color: #35B62F'>");
                                out.println("</tbody>");
                                out.println("<tr>");
                                out.println("<td style='width:70px'>");
                                out.println("Πε/φη");
                                out.println("</td>");
                                out.println("<td>");
                                out.println("<strong>" + item.getDescription() + "</strong>");
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("<tr>");
                                out.println("<td style='width:70px'>");
                                out.println("Θεση");
                                out.println("</td>");
                                out.println("<td>");
                                out.println("<strong>" + item.getPosition() + "</strong>");
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("<tr>");
                                out.println("<td style='width:70px'>");
                                out.println("Υπλ.");
                                out.println("</td>");
                                out.println("<td>");
                                out.println("<strong>" + item.getQuantity() + "</strong>");
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("<tr>");
                                out.println("<td style='width:70px'>");
                                out.println("Κατ.");
                                out.println("</td>");
                                out.println("<td>");
                                out.println("<strong>" + item.getState() + "</strong>");
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("<tr>");
                                out.println("<td colspan='2'>");
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

                                out.println("<tr >");
                                out.println("<td colspan='2'>");
                                out.println("<a  href = 'itemAnalysis.htm?code=" + item.getCode() + "' </a>");
                                out.println("</td>");
                                out.println("</tr>");

                                out.println("</tbody>");
                                out.println("</table>");
                                out.println("<div STYLE=\"background-color:lightblue; height:10px; width:100%;\"></div>");
                            }
                        %>





                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

    </body>
</html>

