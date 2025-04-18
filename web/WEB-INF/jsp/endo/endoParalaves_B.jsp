<%-- 
    Document   : endoParalaves
    Created on : Jan 28, 2024, 10:39:57 AM
    Author     : Michail Sitmalidis
--%>


<%@page import="Delivery.DeliveryInvoice"%>
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
        <h1 style='background-color:red'>ENDO PARALAVES B</h1>


        <hr>    
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
                                ArrayList<String> lastEndoDeliveryInvoiceBindedsEndoApostolisIds = (ArrayList) request.getAttribute("lastEndoDeliveryInvoiceBindedsEndoApostolisIds");
                                LinkedHashMap<String, EndoApostolis> incomingEndos = (LinkedHashMap) request.getAttribute("incomingEndos");
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

                                    String id = entrySet.getValue().getId();
                                    out.println("<td>");
                                    if (lastEndoDeliveryInvoiceBindedsEndoApostolisIds.contains(id)) {

                                        out.println("<input type='checkbox' class='endoId' id='" + id + "' style='width:28px;height:28px' checked >");
                                    } else {
                                        out.println("<input type='checkbox' class='endoId' id='" + id + "' style='width:28px;height:28px' >");

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
                                DeliveryInvoice deliveryInvoice = (DeliveryInvoice) request.getAttribute("endoDelivery");

                                out.println("<tr>");

                                out.println("<td>");
                                out.println("<a href='showEndoDelivery.htm?id=" + deliveryInvoice.getId() + "' target='_blank'>" + deliveryInvoice.getId() + "</a>");
                                out.println("</td>");

                                out.println("</tr>");


                            %> 

                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    ---------
                </td>
                <td colspan="2">
            <center>
                <input style="background-color: blueviolet; font-size:40px" type="button" value="Joint Load" onclick="requestRouter('endoDeliveryJointLoad.htm')">
            </center>
            </td>
            </tr>
        </table>

        <hr>
        <form id="form" action="#" method="POST">
            <input hidden type="text" id="endoIdsInput" name="endoIds">
        </form>
        <hr> <br> <hr> <br>   <hr>  <br>  <hr>
        <a href='seeLastEndoBinders.htm'><h1>SEE LAST ENDO BINDERS</h1></a>
    </center>
    <script>

        ////--------------------
        function requestRouter(requestTarget) {
            form.target = "_blank";
            form.action = requestTarget;
            endoIdsInput.value = collectSellectedCheckBoxes();

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
