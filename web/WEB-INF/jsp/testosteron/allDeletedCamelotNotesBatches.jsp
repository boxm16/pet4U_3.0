
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Inventory.InventoryItem"%>
<%@page import="java.util.TreeMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Deleted Notes</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 15px;
            }
            th{
                font-size: 15px;
                font-weight: bold;
                text-align: left;
                background: #eee;
                top: 0px;
            }

        </style>
    </head>
    <body>
    <center>
        <h5>Camelot Deleted Notes</h5>

        <a href="index.htm"><h4>INDEX</h4></a>


        <table>
            <thead>
            <th>A/A</th>
            <th>Batch (Deletion Time)</th>
            <th>Notes Count</th>

            </thead>
            <%
                LinkedHashMap<String, Integer> deletedCamelotNotesBatches = (LinkedHashMap) request.getAttribute("deletedCamelotNotesBatches");
                if (deletedCamelotNotesBatches.size() > 0) {
                    int index = 0;
                    for (Map.Entry<String, Integer> deletedCamelotNotesBatchesEntrySet : deletedCamelotNotesBatches.entrySet()) {
                        {

                            out.println("<tr>");
                            out.println("<td>");
                            out.println(index);
                            out.println("</td>");

                            out.println("<td>");
                            out.println(deletedCamelotNotesBatchesEntrySet.getKey());
                            out.println("</td>");

                            out.println("<td>");
                            out.println(deletedCamelotNotesBatchesEntrySet.getValue());
                            out.println("</td>");

                            out.println("</tr>");

                            index++;

                        }
                    }
            %>
        </table>






    </center>

</body>
</html>
