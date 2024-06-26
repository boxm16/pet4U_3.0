<%-- 
    Document   : orderServant
    Created on : Dec 10, 2022, 11:32:35 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Stock Positions</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>

            input[type="text"] {
                font-size: 30px;
            }

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
                        <table>
                            <tr>
                                <td style='background-color: lightblue; font-size: 20px' colspan="2">
                                    ${item.description}
                                </td>
                            </tr>

                            <tr>
                                <td style='background-color: lightblue; font-size: 20px'  colspan="2">
                                    <%
                                        Item item = (Item) request.getAttribute("item");

                                        ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
                                        for (AltercodeContainer altercode : altercodes) {
                                            if (altercode.getStatus().equals("eshop")) {
                                                out.println("<br><a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercode.getAltercode() + "' target='_blank'>" + altercode.getAltercode() + "</a><br>");
                                            } else {
                                                out.println(altercode.getAltercode());
                                                out.println("<br>");
                                            }
                                        }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td style='background-color: lightblue; font-size: 20px' colspan="2">
                                    ${item.position}
                                </td>
                            </tr>
                            <tr>
                                <td style='background-color: lightblue; font-size: 20px' colspan="2" >
                                    ${item.quantity}
                                </td>
                            </tr>
                            <tr style="font-size:30px; background-color: #E58DE2">
                                <td colspan="2"> Stock Positions </td>
                            </tr>

                            <%
                                LinkedHashMap<Integer, String> positions = (LinkedHashMap) request.getAttribute("stockPositions");

                                for (Map.Entry<Integer, String> positionsEntry : positions.entrySet()) {
                                    out.println("<tr style='font-size:30px; background-color: #E58DE2'>");
                                    out.println("<td>");
                                    out.println(positionsEntry.getValue());
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<a href='camelotStockPositionDeletion.htm?itemCode=" + item.getCode() + "&id=" + positionsEntry.getKey() + "'>X</a>");
                                    out.println("</td>");
                                    out.println("</tr>");
                                }

                            %>  

                            <tr>
                                <td colspan="2">
                                    <form action='addCamelotStockPosition.htm' method="POST">
                                        <hr>
                                        <input name='itemCode' hidden value='${item.code}'>
                                        <input class='btn btn-lg btn-primary' type='submit' value='ADD NEW STOCK POSITION'>

                                    </form>
                                </td>
                            </tr>
                        </table>

                        <hr><hr><hr><hr>
                        <hr>
                        <a href="camelotSearchDashboard.htm"><h3>New Search</h3></a>
                        <hr>
                        <a href="index.htm"><h3>INDEX</h3></a>

                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>

        </div>
    </body>
</html>