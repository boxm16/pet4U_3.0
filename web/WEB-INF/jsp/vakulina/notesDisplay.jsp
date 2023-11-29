
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
        <title>Notes Display</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 20px;
            }
            th{
                font-size: 30px;
                font-weight: bold;
                text-align: left;
                background: #eee;
                position: sticky;
                top: 0px;
            }

        </style>
    </head>
    <body>
    <center>
        <h1><a href="index.htm">INDEX</a></h1>
        <h1>${title}</h1>
        <hr>

        <table>
            <thead>
            <th>ID</th>

            <th>Altercode</th>
            <th>Position</th>

            <th>Description</th>

            <th>Note</th>

            <th>Delete</th>


            </thead>
            <%
                ArrayList<InventoryItem> items = (ArrayList) request.getAttribute("notes");
                if (items.size() > 0) {
                    for (InventoryItem inventoryItem : items) {

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(inventoryItem.getId());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<a href='findItemByAltercode.htm?altercode=" + inventoryItem.getCode() + "'>" + inventoryItem.getCode() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(inventoryItem.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(inventoryItem.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(inventoryItem.getNote());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<a href='deleteNote.htm?id=" + inventoryItem.getId() + "'>Delete</a>");
                        out.println("</td>");

                        /*

                    out.println("<td>");
                    out.println("<a href='goForEditingCamelotItemOfInterest.htm?code=" + camelotItemOfInterest.getCode() + "'>Edit</a>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='itemSnapshots.htm?code=" + camelotItemOfInterest.getCode() + "' target='_blank'>Show Day Rest Snapshots</a>");
                    out.println("</td>");
                         */
                        out.println("</tr>");

                    }
                }
            %>
        </table>
        <hr>



    </center>




</body>
</html>
