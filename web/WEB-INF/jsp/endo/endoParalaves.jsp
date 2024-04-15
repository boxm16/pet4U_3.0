<%-- 
    Document   : endoParalaves
    Created on : Jan 28, 2024, 10:39:57 AM
    Author     : Michail Sitmalidis
--%>


<%@page import="Endo.EndoBinder"%>
<%@page import="Endo.EndoParalavis"%>
<%@page import="Endo.EndoApostolis"%>
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
        <title>ΕΝΔΟ ΠΑΡΑΛΑΒΕΣ</title>
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
        <a href="endoDashboard.htm"><h3>Go Endo Dashboard</h3></a>
        <hr>
        <table>
            <thead>
                <tr>
                    <th>A/A</th>
                    <th>Date</th>
                    <th>Creator</th>
                    <th>Number</th>
                </tr>
            </thead>
            <tbody>





                <%
                    EndoBinder proEndoBinder = (EndoBinder) request.getAttribute("proEndoBinder");
                    if (proEndoBinder != null) {
                        EndoParalavis endoParalavis = proEndoBinder.getEndoParalavis();
                        String anchorDate = endoParalavis.getDateString();

                        out.println("<tr style='background-color: #ADD8E6'>");

                        out.println("<td>");
                        out.println("<a href='showDeltioParalavis.htm?id=" + endoParalavis.getId() + "' target='_blank'>" + endoParalavis.getId() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endoParalavis.getDateString());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("ΒΑΡΙΜΠΟΜΠΗ");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endoParalavis.getNumber());
                        out.println("</td>");

                        out.println("</tr>");

                        LinkedHashMap<String, EndoApostolis> endoApostoliss = proEndoBinder.getEndoApostoliss();
                        for (Map.Entry<String, EndoApostolis> endoApostolisEntry : endoApostoliss.entrySet()) {
                            out.println("<tr style='background-color: #90EE90'>");

                            out.println("<td>");
                            out.println("<a href='showDeltioApostolis.htm?id=" + endoApostolisEntry.getValue().getId() + "' target='_blank'>" + endoApostolisEntry.getValue().getId() + "</a>");
                            out.println("</td>");

                            String comparingDate = endoApostolisEntry.getValue().getDateString();
                            if (anchorDate.equals(comparingDate)) {
                                out.println("<td>");
                            } else {
                                out.println("<td style='background-color:red'>");
                            }
                            out.println(endoApostolisEntry.getValue().getDateString());
                            out.println("</td>");

                            out.println("<td>");
                            out.println(endoApostolisEntry.getValue().getSender());
                            out.println("</td>");

                            out.println("<td>");
                            out.println(endoApostolisEntry.getValue().getNumber());
                            out.println("</td>");
                        }

                        out.println("</tr>");

                        out.println("<tr>");
                        if (proEndoBinder.isBinderOk()) {
                            out.println("<td colspan='5' >");
                            out.println("<center><a href='saveEndoBinder.htm' class='btn btn-primary' style='font-size:30px' target='_blank'>BIND ENDOS</a></center>");
                            out.println("</td>");
                        } else {
                            out.println("<td  colspan='5'  style='background-color: red'>");
                         //   out.println("<center><a href='checkSuggestedBinder.htm' class='btn btn-danger' style='font-size:30px' target='_blank'>SOMETHING WRONG. SEE WHY</a></center>");
                            out.println("SOMETHING WRONG. ΠΑΤΑ ΣΥΓΚΡΗΣΗ ΝΑ ΔΕΙΣ");
                           
                            out.println("</td>");
                        }
                        out.println("</tr>");
                    } else {
                        out.println("<tr>");
                        out.println("<td colspan='5' >");
                        out.println("<center>NO SUGGESTION FOR CHECKING AND BINDING</center>");
                        out.println("</td>");
                        out.println("</tr>");
                        
                             out.println("<tr>");
                        out.println("<td colspan='5' >");
                        out.println("<center> <a href='endoParalaves.htm' class='btn btn-primary' style='background-color: green' role='button'><h1>ΑΝΑΝΕΩΣΗ ΣΕΛΙΔΑΣ</h1></a></center>");
                        out.println("</td>");
                        out.println("</tr>");
                        
                       
                        
                    }
                %>
            </tbody>
        </table>

        <hr>    <hr>    <hr>
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


                            <%                                LinkedHashMap<String, EndoApostolis> incomingEndos = (LinkedHashMap) request.getAttribute("incomingEndos");
                                for (Map.Entry<String, EndoApostolis> entrySet : incomingEndos.entrySet()) {

                                    out.println("<tr>");
                                    if (entrySet.getValue().getSender().equals("ΠΕΡΙΣΤΕΡΙ")
                                            || entrySet.getValue().getSender().equals("Ν. ΙΩΝΙΑ")
                                            || entrySet.getValue().getSender().equals("ΧΑΛΚΗΔΟΝΑ")
                                            || entrySet.getValue().getSender().equals("ΚΑΛΛΙΘΕΑ")
                                            || entrySet.getValue().getSender().equals("ΠΕΤΡΟΥΠΟΛΗ")
                                            || entrySet.getValue().getSender().equals("MΕΝΙΔΙ")
                                            || entrySet.getValue().getSender().equals("ΚΟΥΚΑΚΙ")) {
                                        out.println("<tr style='background-color:#DFB4F9;'>");
                                    }

                                    if (entrySet.getValue().getSender().equals("ΑΓ_ΠΑΡΑΣΚΕΥΗ")
                                            || entrySet.getValue().getSender().equals("ΔΑΦΝΗ")
                                            || entrySet.getValue().getSender().equals("Π. ΦΑΛΗΡΟ")
                                            || entrySet.getValue().getSender().equals("ΑΛΙΜΟΣ")
                                            || entrySet.getValue().getSender().equals("ΧΑΛΑΝΔΡΙ")
                                            || entrySet.getValue().getSender().equals("ΑΡΓΥΡΟΥΠΟΛΗ")
                                            || entrySet.getValue().getSender().equals("ΜΙΧΑΛΑΚΟΠΟΥΛΟΥ")) {
                                        out.println("<tr style='background-color:#FAFAA5 ;'>");
                                    }

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
                                    String shortNumber = entrySet.getValue().getShortNumber();

                                    LinkedHashMap<String, EndoParalavis> receivingEndos = (LinkedHashMap) request.getAttribute("receivingEndos");
                                    if (receivingEndos.size() == 1) {
                                        Map.Entry<String, EndoParalavis> receivingEndoEntry = receivingEndos.entrySet()
                                                .stream()
                                                .findFirst()
                                                .get();

                                        if (receivingEndoEntry.getValue().getThreeLastDigitsArrayList().contains(shortNumber)) {
                                            out.println("<input type='checkbox' checked class='endoId' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px' >");
                                        } else {
                                            out.println("<input type='checkbox' class='endoId' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px' >");
                                        }
                                    } else {
                                        out.println("<input type='checkbox' class='endoId' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px' >");
                                    }
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
                                LinkedHashMap<String, EndoParalavis> receivingEndos = (LinkedHashMap) request.getAttribute("receivingEndos");

                                for (Map.Entry<String, EndoParalavis> entrySet : receivingEndos.entrySet()) {

                                    out.println("<tr>");

                                    out.println("<td>");
                                    if (receivingEndos.size() == 1) {
                                        out.println("<input type='checkbox' checked class='receivingEndoId' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px'>");
                                    } else {
                                        out.println("<input type='checkbox' class='receivingEndoId' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px'>");
                                    }
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

        <hr>
        <form id="form" action="#" method="POST">
            <input hidden type="text" id="endoIdsInput" name="endoIds">
            <input hidden type="text" id="receivingEndoIdsInput" name="receivingEndoIds">
        </form>
        <hr> <br> <hr> <br>   <hr>  <br>  <hr>
        <a href='seeLastEndoBinders.htm'><h1>SEE LAST ENDO BINDERS</h1></a>
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
