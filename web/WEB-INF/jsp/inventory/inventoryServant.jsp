<%-- 
    Document   : orderServant
    Created on : Dec 10, 2022, 11:32:35 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Servant</title>
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
                                <td>
                                    ${item.description}
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <%
                                        Item item = (Item) request.getAttribute("item");

                                        ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
                                        for (AltercodeContainer altercode : altercodes) {
                                            if (altercode.getStatus().equals("eshop")) {
                                                out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercode.getAltercode() + "' target='_blank'>" + altercode.getAltercode() + "</a><br>");
                                            } else {
                                                out.println(altercode.getAltercode());
                                                out.println("<br>");
                                            }
                                        }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td style='background-color: lightblue; font-size: 20px' >
                                    ${item.position}
                                </td>
                            </tr>
                            <tr>
                                <td style='background-color: lightblue;'>
                                    <form action='saveItemInventory.htm' method="POST">

                                        <center>    <input type='text' readonly name='systemStock' style='font-size:30px; background-color: lightcoral' value='${item.quantity}'></center>
                                        <hr>
                                        <center>   <h3>  Real Stock</h3></center> 
                                        <input type='number' name='realStock' style='font-size:30px'>
                                        <hr>
                                        <input name='altercode' hidden value='${altercode}'>
                                        Make notes - 500 char. max.
                                        <input type='text' name='note'>
                                        <hr>

                                        <input style="font-size:30px" class='btn btn-primary' type='submit' value='SAVE ITEM INVENTORY'>

                                    </form>
                                </td>
                            </tr>
                        </table>
                        <br><br><br><br> <br><br><br><br>
                        <a href='index.htm'>Index</a>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>

        </div>
    </body>
</html>