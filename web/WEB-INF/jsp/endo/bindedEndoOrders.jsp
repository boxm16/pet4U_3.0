<%-- 
    Document   : endoDashboard
    Created on : Jan 1, 2024, 8:32:46 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="Endo.EndoApostolis"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="Endo.BindedEndos"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="Endo.Endo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Endo Dashboard</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 30px;
            }
            th{
                font-size: 30px;
                font-weight: bold;
            }





        </style>
    </head>
    <body>
    <center>
        <a href="index.htm"><h3>INDEX</h3></a>
        <a href="endoParalaves.htm"><h3>Endo ΠΑΡΑΛΑΒΕΣ</h3></a>
        <hr>

        <h2>ΔΕΣΜΕΥΜΕΝΑ ΔΕΛΤΙΑ ΑΠΟΣΤΟΛΗΣ ΠΡΟΣ ΜΑΓΑΖΙΑ </h2>
        <hr>
        <table>

            <thead>
                <tr>

                    <th>A/A</th>
                    <th>Date</th>
                    <th>Number</th>
                    <th>DESTINATION</th>
                </tr>
            </thead>
            <tbody>
                <%     ArrayList<EndoApostolis> bindedOutgoindDeltioApostolis = (ArrayList) request.getAttribute("bindedOutgoindDeltioApostolis");

                    // reverse order of keys
                    Collections.reverse(bindedOutgoindDeltioApostolis);

                    // iterate LHM using reverse order of keys
                    for (EndoApostolis endoApostolis : bindedOutgoindDeltioApostolis) {

                        out.println("<tr style='background-color: #ADD8E6'>");

                        out.println("<tr>");
                        out.println("<td colspan='4'>");
                        out.println("<center><a href='showBindedEndoOrder.htm?id=" + endoApostolis.getId() + "' class='btn btn-primary' style='font-size:30px' target='_blank'>" + endoApostolis.getReceiver() + "</a></center>");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println("</tr>");
                    }
                %>

            </tbody>
        </table>
        <hr>
        <form id="form" action="#" method="POST">
            <input hidden type="text" id="endoIdsInput" name="endoIds">
            <input hidden type="text" id="receivingEndoIdsInput" name="receivingEndoIds">
        </form>
    </center>
    <script>

        ////--------------------
        function requestRouter(requestTarget) {
            if (requestTarget == "compareEndos.htm" || requestTarget == "endosChecking.htm") {
                form.target = "_blank";
            } else {

            }
            form.action = requestTarget;
            endoIdsInput.value = collectSellectedCheckBoxes();
            receivingEndoIdsInput.value = collectSellectedReceivingCheckBoxes();
            console.log(form.action);
            form.submit();
        }
        //this function collects all checked checkbox values, concatinates them in one string and returns that string to send it after by POST method to server
        function collectSellectedCheckBoxes() {
            var returnValue = "";
            var targetCheckBoxes = document.querySelectorAll(".endoId");
            for (x = 0; x < targetCheckBoxes.length; x++) {
                if (targetCheckBoxes[x].checked)
                    returnValue += targetCheckBoxes[x].id + ",";
            }
            return returnValue;
        }

        function collectSellectedReceivingCheckBoxes() {
            var returnValue = "";
            var targetCheckBoxes = document.querySelectorAll(".receivingEndoId");
            for (x = 0; x < targetCheckBoxes.length; x++) {
                if (targetCheckBoxes[x].checked)
                    returnValue += targetCheckBoxes[x].id + ",";
            }
            return returnValue;
        }
    </script>
</body>
</html>
