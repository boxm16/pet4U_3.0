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
        <title>Endo Analysis</title>
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
        <h3>Endo Analysis</h3>
        <hr>


        <hr>

        <table>

            <thead>
                <tr>

                    <th>A/A</th>
                    <th>Date</th>
                    <th>Number</th>
                    <th>DESTINATION</th>
                    <th>User</th>
                    <th>DateTime</th>
                    <th>Show  Endo Apostolis</th>

                </tr>
            </thead>
            <tbody>
                <%     LinkedHashMap<String, EndoApostolis> endoApostoliss = (LinkedHashMap) request.getAttribute("endoApostolissVaribobis");

                    // reverse order of keys
                    // iterate LHM using reverse order of keys
                    for (Map.Entry<String, EndoApostolis> endoApostolissEntry : endoApostoliss.entrySet()) {
                        EndoApostolis endoApostolis = endoApostolissEntry.getValue();
                        if (endoApostolis.getReceiver().equals("ΠΕΡΙΣΤΕΡΙ")
                                || endoApostolis.getReceiver().equals("Ν. ΙΩΝΙΑ")
                                || endoApostolis.getReceiver().equals("ΧΑΛΚΗΔΟΝΑ")
                                || endoApostolis.getReceiver().equals("ΚΑΛΛΙΘΕΑ")
                                || endoApostolis.getReceiver().equals("ΠΕΤΡΟΥΠΟΛΗ")
                                || endoApostolis.getReceiver().equals("MΕΝΙΔΙ")
                                || endoApostolis.getReceiver().equals("ΚΟΥΚΑΚΙ")) {
                            out.println("<tr style='background-color:#DFB4F9;'>");
                        }

                        if (endoApostolis.getReceiver().equals("ΑΓ_ΠΑΡΑΣΚΕΥΗ")
                                || endoApostolis.getReceiver().equals("ΔΑΦΝΗ")
                                || endoApostolis.getReceiver().equals("Π. ΦΑΛΗΡΟ")
                                || endoApostolis.getReceiver().equals("ΑΛΙΜΟΣ")
                                || endoApostolis.getReceiver().equals("ΧΑΛΑΝΔΡΙ")
                                || endoApostolis.getReceiver().equals("ΑΡΓΥΡΟΥΠΟΛΗ")
                                || endoApostolis.getReceiver().equals("ΜΙΧΑΛΑΚΟΠΟΥΛΟΥ")) {
                            out.println("<tr style='background-color:#FAFAA5 ;'>");
                        }

                        out.println("<td>");
                        out.println(endoApostolis.getId());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endoApostolis.getDateString());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endoApostolis.getNumber());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endoApostolis.getReceiver());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endoApostolis.getCreationUser());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endoApostolis.getCreationDateTime());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(endoApostolis.getCreationUser());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<center><a href='showBindedEndoOrder.htm?id=" + endoApostolis.getId() + "' class='btn btn-primary' style='font-size:30px' target='_blank'>Show Binded Endo Order</a></center>");
                        out.println("</td>");

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

</body>
</html>
