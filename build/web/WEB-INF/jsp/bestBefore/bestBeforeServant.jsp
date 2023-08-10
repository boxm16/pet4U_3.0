

<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Best Before Servant</title>
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
                                <td style='background-color: lightblue; font-size: 20px' >
                                    ${item.position}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form action='saveBestBeforeStatment.htm' method="POST">

                                        <center> 
                                            <h4>BEST BEFORE</h4>
                                            <input type='date' onchange='setAlertDate(event)' name='bestBefore' style='font-size:20px; '>
                                        </center>
                                        <hr>
                                        <h4>ALERT</h4>
                                        <input type='date' id='alert' name='alert' style='font-size:20px; '></center>

                                        <hr>
                                        <input name='altercode' hidden value='${altercode}'>
                                        Make notes - 500 char. max.
                                        <input type='text' name='note'>
                                        <hr>

                                        <input class='btn btn-primary' type='submit' value='SAVE BEST BEFORE STATEMENT'>
                                        <hr>
                                    </form>
                                </td>
                            </tr>
                        </table>
                        <a href='index.htm'>Index</a>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>

        </div>
        <script>
            function setAlertDate(event) {
                var bestBeforeDate = event.srcElement.valueAsDate;
                bestBeforeDate.setMonth(bestBeforeDate.getMonth() - 1);
                console.log(bestBeforeDate);
                console.log(document.getElementById("alert").value);

                document.getElementById("alert").valueAsDate = new Date(Date.UTC(bestBeforeDate.getFullYear(), bestBeforeDate.getMonth(), bestBeforeDate.getDate()));
                console.log(document.getElementById("alert").value);
            }
        </script>
    </body>
</html>