<%-- 
    Document   : endoDashboard
    Created on : Jan 1, 2024, 8:32:46 PM
    Author     : Michail Sitmalidis
--%>

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
        <h1>Hello World!</h1>
        <hr>
        <a href="deltioApostolis.htm"><h1>Δελτιο Αποστολης</h1></a> 
        <table>
            <tr>

                <td>
                    <table>
                        <tbody>
                            <tr><td colspan="4">ΔΕΛΤΙΑ ΑΠΟΣΤΟΛΗΣ ΑΠΟ ΜΑΓΑΖΙΑ</td></tr>
                            <tr>
                                <th>Select</th>
                                <th>A/A</th>
                                <th>Date</th>
                                <th>Sender</th>
                            </tr>


                            <%
                                LinkedHashMap<String, Endo> incomingEndos = (LinkedHashMap) request.getAttribute("incomingEndos");
                                for (Map.Entry<String, Endo> entrySet : incomingEndos.entrySet()) {

                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println("<input type='checkbox' class='endoId' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px'>");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<a href='showDeltioApostolis.htm?id=" + entrySet.getValue().getId() + "'>" + entrySet.getValue().getId() + "</a>");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println(entrySet.getValue().getDateString());
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println(entrySet.getValue().getSender());
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
                                <th>Sender</th>
                            </tr>
                            <tr>


                                <%
                                    LinkedHashMap<String, Endo> receivingEndos = (LinkedHashMap) request.getAttribute("biden");
                                    String biden = (String) request.getAttribute("biden");
                                    if (!biden.equals("yes")) {

                                        for (Map.Entry<String, Endo> entrySet : receivingEndos.entrySet()) {

                                            out.println("<tr>");

                                            out.println("<td>");
                                            out.println("<input type='checkbox'class='endoId777' id='" + entrySet.getValue().getId() + "' style='width:28px;height:28px'>");
                                            out.println("</td>");
                                            out.println("<td>");
                                            out.println("<a href='showDeltioApostolis.htm?id=" + entrySet.getValue().getId() + "'>" + entrySet.getValue().getId() + "</a>");
                                            out.println("</td>");
                                            out.println("<td>");
                                            out.println(entrySet.getValue().getDateString());
                                            out.println("</td>");
                                            out.println("<td>");
                                            out.println(entrySet.getValue().getSender());
                                            out.println("</td>");

                                            out.println("</tr>");
                                        }
                                    }
                                %> 
                            <tr><td colspan="4">.</td></tr>
                            <tr><td colspan="4">.</td></tr>
                            <tr><td colspan="4">.</td></tr>
                            <tr><td colspan="4">.</td></tr>
                            <tr><td colspan="4">ΑΦΟΡΑ ΔΕΛΤΙΑ ΑΠΟΣΤΟΛΗΣ ΤΩΝ ΜΑΓΑΖΙΩΝ</td></tr>
                            <tr><td colspan="4">4323423, 4323424, 4323429</td></tr>
                            <tr><td colspan="4">1271254, 1271255</td></tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr><td colspan="3"><center><input style="background-color:lightblue; font-size:40px" type="button" value="ΣΥΓΚΡΙΣΗ" onclick="requestRouter('compareEndo.htm')"></center></tr>
    </table>

    <form id="form" action="#" method="POST">
        <input hidden type="text" id="endoIdsInput" name="endoIds">
    </form>
    <script>

        ////--------------------
        function requestRouter(requestTarget) {
            if (requestTarget == "compareEndo.htm") {
                form.target = "_blank";
            } else {

            }
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
    </script>
</body>
</html>
