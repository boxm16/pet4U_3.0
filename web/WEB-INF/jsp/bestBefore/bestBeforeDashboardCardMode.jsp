<%@page import="BestBefore.BestBeforeStatement"%>
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
        <title>Best Before:Card Mode</title>
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
                            ArrayList<BestBeforeStatement> statements = (ArrayList) request.getAttribute("bestBeforeStatements");

                            if (statements.size() > 0) {
                                for (BestBeforeStatement statement : statements) {

                                    out.println("<table class='table'  style='background-color:" + statement.getAlertColor() + "'>");
                                    out.println("</tbody>");
                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("ID");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<strong>" + statement.getId() + "</strong>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Altercode");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println(" <a href='itemAnalysis.htm?code=" + statement.getAltercode() + "' ><h3>"+statement.getAltercode()+"</h3></a>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Position");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<strong>" + statement.getPosition() + "</strong>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Description");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<strong>" + statement.getDescription() + "</strong>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Alert");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<input id='" + statement.getId() + "_alert' style='font-size:25px' type='date' value='" + statement.getAlert() + "'");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Best Before");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<input id='" + statement.getId() + "_bestBefore' style='font-size:25px'  type='date' value='" + statement.getBestBefore() + "'");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Delete");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<a href='deleteBestBeforeStatementCardMode.htm?id=" + statement.getId() + "'>Delete Best Before Statement</a>");
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

