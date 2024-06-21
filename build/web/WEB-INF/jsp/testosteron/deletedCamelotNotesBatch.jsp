
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
        <title>Camelot Deleted Notes Batch</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 25px;
            }
            th{
                font-size: 25px;
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
            <th>Item Code</th>

            <th>Notes Count</th>
            <th>Insertion Time</th>
            <th>Deletion Time</th>

            </thead>
            <%
                ArrayList<InventoryItem> deletedCamelotNotesBatch = (ArrayList) request.getAttribute("notes");
                if (deletedCamelotNotesBatch.size() > 0) {
                    int index = 0;
                    for (InventoryItem inventoryItem : deletedCamelotNotesBatch) {

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(index);
                        out.println("</td>");

                        out.println("<td>");
                        out.println(inventoryItem.getCode());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(inventoryItem.getNote());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(inventoryItem.getTimeStampString());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(inventoryItem.getDateStampString());
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
