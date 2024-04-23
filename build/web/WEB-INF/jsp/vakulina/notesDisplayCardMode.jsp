<%@page import="Inventory.InventoryItem"%>
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
        <title>Notes:Card Mode</title>
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
                            ArrayList<InventoryItem> items = (ArrayList) request.getAttribute("notes");

                            if (items.size() > 0) {
                                for (InventoryItem item : items) {

                                    out.println("<table class='table'>");
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
                                    out.println("<td colspan='2' style='font-size:20px'>");
                                    out.println("<strong>" + item.getNote() + "</strong>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td colspan='2'>");
                                    out.println("<a href='deleteNoteCardMode.htm?id=" + item.getId() + "'>Delete</a>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("</tbody>");
                                    out.println("</table>");
                                    out.println("<div STYLE=\"background-color:lightblue; height:10px; width:100%;\"></div>");
                                }
                            }
                        %>

                        <hr>

                        <a href="index.htm"><h4>INDEX</h4></a>

                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>
        </div>

    </body>
</html>

