<%-- 
    Document   : endoDashboard
    Created on : Jan 1, 2024, 8:32:46 PM
    Author     : Michail Sitmalidis
--%>

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
        <hr>
        <!--  <a href="deltioApostolis.htm"><h1>Δελτιο Αποστολης</h1></a>  -->
        <table>
            <tr>

                <td>
                    <table>
                        <tbody>
                            <tr><td colspan="5">ΔΕΛΤΙΑ ΑΠΟΣΤΟΛΗΣ ΑΠΟ ΜΑΓΑΖΙΑ</td></tr>
                            <tr>

                                <th>A/A</th>
                                <th>Date</th>
                                <th>Sender</th>
                                <th>Number</th>
                                <th>Select</th>
                            </tr>


                            <%
                                LinkedHashMap<String, Endo> incomingEndos = (LinkedHashMap) request.getAttribute("incomingEndos");
                                for (Map.Entry<String, Endo> entrySet : incomingEndos.entrySet()) {

                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println("<a href='showDeltioApostolis.htm?id=" + entrySet.getValue().getId() + "' target='_blank'>" + entrySet.getValue().getId() + "</a>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(entrySet.getValue().getDateString());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(entrySet.getValue().getSender());
                                    out.println("</td>");

                                    out.println("<td style='font-weight: bold;'>");
                                    out.println(entrySet.getValue().getNumber());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("<input type='checkbox' class='endoId' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px' >");
                                    out.println("</td>");

                                    out.println("</tr>");
                                }
                            %>    
                        </tbody>
                    </table>
                </td>
                <td>--------------</td>
                <td>

                    <table>
                        <tbody>
                            <tr><td colspan="4">ΔΕΛΤΙΑ ΠΑΡΑΛΑΒΗΣ ΒΑΡΙΜΠΟΜΠΗΣ</td></tr>
                            <tr>
                                <th>Select</th>
                                <th>A/A</th>
                                <th>Date</th>
                                <th>Number</th>

                            </tr>



                            <%
                                LinkedHashMap<String, Endo> receivingEndos = (LinkedHashMap) request.getAttribute("receivingEndos");

                                for (Map.Entry<String, Endo> entrySet : receivingEndos.entrySet()) {

                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println("<input type='checkbox'class='receivingEndoId' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px'>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("<a href='showDeltioParalavis.htm?id=" + entrySet.getValue().getId() + "' target='_blank'>" + entrySet.getValue().getId() + "</a>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(entrySet.getValue().getDateString());
                                    out.println("</td>");

                                    out.println("<td >");
                                    out.println(entrySet.getValue().getNumber());
                                    out.println("</td>");

                                    out.println("</tr>");
                                }

                            %> 

                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <input style="background-color:lightgreen; font-size:40px" type="button" value="ΕΛΕΓΧΟΣ" onclick="requestRouter('endosChecking.htm')">
                </td>
                <td colspan="2">
            <center>
                <input style="background-color:lightblue; font-size:40px" type="button" value="ΣΥΓΚΡΙΣΗ" onclick="requestRouter('compareEndos.htm')">
            </center>
            </td>
            </tr>
        </table>

        <hr><hr>
        <h2>ΔΕΣΜΕΥΜΕΝΑ ΔΕΛΤΙΑ</h2>
        <hr>
        <table>

            <thead>
                <tr>

                    <th>A/A</th>
                    <th>Date</th>
                    <th>Creator</th>
                </tr>
            </thead>
            <tbody>
                <%     LinkedHashMap<String, BindedEndos> bindedEndos = (LinkedHashMap) request.getAttribute("bindedEndos");

                    List<String> alKeys = new ArrayList<String>(bindedEndos.keySet());

                    // reverse order of keys
                    Collections.reverse(alKeys);

                    // iterate LHM using reverse order of keys
                    for (String strKey : alKeys) {

                        out.println("<tr style='background-color: #ADD8E6'>");

                        out.println("<td>");
                        out.println("<a href='showDeltioApostolis.htm?id=" + strKey + "'>" + strKey + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(bindedEndos.get(strKey).getBindingReceivingEndo().getDateString());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("ΒΑΡΙΜΠΟΜΠΗ");
                        out.println("</td>");

                        out.println("</tr>");

                        ArrayList<Endo> sendingEndos = bindedEndos.get(strKey).getBindedSendingEndos();
                        for (Endo sendingEndo : sendingEndos) {
                            out.println("<tr style='background-color: #90EE90'>");

                            out.println("<td>");
                            out.println("<a href='showDeltioApostolis.htm?id=" + sendingEndo.getId() + "'>" + sendingEndo.getId() + "</a>");
                            out.println("</td>");

                            out.println("<td>");
                            out.println(sendingEndo.getDateString());
                            out.println("</td>");

                            out.println("<td>");
                            out.println(sendingEndo.getSender());
                            out.println("</td>");

                            out.println("</tr>");

                            out.println("<tr>");

                            out.println("<td colspan='3'>");
                            out.println("----------------------");
                            out.println("</td>");

                            out.println("</tr>");

                        }
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
