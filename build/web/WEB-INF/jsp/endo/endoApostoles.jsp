<%-- 
    Document   : endoApostoles
    Created on : Feb 4, 2024, 2:56:48 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="Endo.EndoApostolis"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="Endo.EndoOrder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Endo ΑΠΟΣΤΟΛΕΣ</title>
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
      <h1>ΑΠΟΣΤΟΛΕΣ ΕΝΔΟΔΙΑΚΙΝΗΣΗΣ</h1>
        <hr>
        <table>
            <tr>
                <td>
                    <table>
                        <thead>
                            <tr>
                                <th>Order Destination</th>
                                <th>Select</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%     LinkedHashMap<String, EndoOrder> endoOrdersTitles = (LinkedHashMap) request.getAttribute("endoOrdersTitles");

                                for (Map.Entry<String, EndoOrder> endoOrdersTitlesEntry : endoOrdersTitles.entrySet()) {
                                    out.println("<tr style='background-color: #E5B48D'>");

                                    out.println("<td>");
                                    out.println("<a href='showEndoOrder.htm?id=" + endoOrdersTitlesEntry.getValue().getId() + "' target='_blank'>" + endoOrdersTitlesEntry.getValue().getDestination() + "</a>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("<input type='checkbox' class='orderId' id='" + endoOrdersTitlesEntry.getValue().getId() + "' style='width:28px;height:28px' >");
                                    out.println("</td>");

                                    out.println("</tr>");
                                }
                            %>
                        </tbody>
                    </table>
                <td>
                <td>--------------</td>
                <td>
                    <table>
                        <thead>
                            <tr>
                                <th>Select</th>
                                <th>id</th>
                                <th>Number</th>
                                <th>Date</th>
                                <th>Destination</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%     LinkedHashMap<String, EndoApostolis> outgoingDeltioApostolisTitles = (LinkedHashMap) request.getAttribute("outgoingDeltioApostolisTitles");

                                for (Map.Entry<String, EndoApostolis> outgoingDeltioApostolisTitlesEntry : outgoingDeltioApostolisTitles.entrySet()) {
                                    out.println("<tr style='background-color: #E5B48D'>");

                                    out.println("<td>");
                                    out.println("<input type='checkbox' class='outgoingEndoId' id='" + outgoingDeltioApostolisTitlesEntry.getValue().getId() + "' style='width:28px;height:28px'>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("<a href='showDeltioApostolisVaribobis.htm?id=" + outgoingDeltioApostolisTitlesEntry.getValue().getId() + "' target='_blank'>" + outgoingDeltioApostolisTitlesEntry.getValue().getId() + "</a>");
                                    out.println("</td>");

                                    out.println("<td style='font-weight: bold;'>");
                                    out.println(outgoingDeltioApostolisTitlesEntry.getValue().getNumber());
                                    out.println("</td>");

                                    out.println("<td style='font-weight: bold;'>");
                                    out.println(outgoingDeltioApostolisTitlesEntry.getValue().getDateString());
                                    out.println("</td>");

                                    out.println("<td style='font-weight: bold;'>");
                                    out.println(outgoingDeltioApostolisTitlesEntry.getValue().getReceiver());
                                    out.println("</td>");

                                    out.println("</tr>");
                                }
                            %>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="4"><hr>
                </td>
            </tr>

            <tr>


                <td colspan="4">
            <center>
                <input style="background-color:lightblue; font-size:40px" type="button" value="ΣΥΓΚΡΙΣΗ" onclick="requestRouter('checkOrderWithEndo.htm')">
            </center>
            </td>
        </table>
    </center>
    <form id="form" action="#" method="POST">
        <input hidden type="text" id="orderIdInput" name="orderId">
        <input hidden type="text" id="outgoingEndoIdInput" name="outgoingEndoId">
    </form>
    <script>

        ////--------------------
        function requestRouter(requestTarget) {
            if (requestTarget == "checkOrderWithEndo.htm") {
                form.target = "_blank";
            } else {

            }
            form.action = requestTarget;

            orderIdInput.value = getSelectedOrderId();
            outgoingEndoIdInput.value = getOutgoingEndoId();
            console.log(form.action);
            form.submit();
        }

        function getSelectedOrderId() {
            var returnValue = "";
            var targetCheckBoxes = document.querySelectorAll(".orderId");
            for (x = 0; x < targetCheckBoxes.length; x++) {
                if (targetCheckBoxes[x].checked)
                    return targetCheckBoxes[x].id;
            }
            return returnValue;
        }

        function getOutgoingEndoId() {
            var returnValue = "";
            var targetCheckBoxes = document.querySelectorAll(".outgoingEndoId");
            for (x = 0; x < targetCheckBoxes.length; x++) {
                if (targetCheckBoxes[x].checked)
                    return targetCheckBoxes[x].id;
            }
            return returnValue;
        }
    </script>
</body>
</html>
