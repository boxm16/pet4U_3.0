
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
        <title>Inventory Display</title>
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
        <h1>Inventories Display</h1>
        <hr>

        <table>
            <thead>
            <th>ID</th>
            <th>Date</th>
            <th>Time</th>
            <th>Altercode</th>
            <th>Select</th>
            <th>Description</th>


            <th>Archivize Item Inventory</th>

            <th>System Stock</th>
            <th>Real Stock</th>
            <th>Note</th>
            <th>Item State</th>
            <th>Delete</th>

            </thead>
            <%
                ArrayList<InventoryItem> items = (ArrayList) request.getAttribute("inventories");
                for (InventoryItem inventoryItem : items) {

                    out.println("<tr>");

                    out.println("<td>");
                    out.println(inventoryItem.getId());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getDateStampString());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getTimeStampString());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getCode());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<input type='checkbox' class='inventoryItemId' id='" + inventoryItem.getId() + "' style='width:28px;height:28px'>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='archivizeInventoryItem.htm?id=" + inventoryItem.getId() + "'>Archivize Inventory Item</a>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getSystemStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getRealStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getNote());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(inventoryItem.getInventarizationState());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='deleteInventoryItem.htm?id=" + inventoryItem.getId() + "'>Delete Inventory Item</a>");
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
            %>
        </table>
        <hr>
        <form id="form" action="#" method="POST">
            <input hidden type="text" id="inventoryItemsInput" name="itemsIds">
        </form>

        <a href="#" onclick="requestRouter('printMode.htm')"><h4>Print Mode</h4></a>
        <hr><hr><hr><hr><hr><hr><hr><hr><hr><hr>
        <a href="#" onclick="requestRouter('archivizeItems.htm')"><h4>Archivize Selected Items</h4></a>

    </center>

    <script>

        ////--------------------
        function requestRouter(requestTarget) {
            if (requestTarget == "printMode.htm") {
                form.target = "_blank";
            } else {

            }
            form.action = requestTarget;
            inventoryItemsInput.value = collectSellectedCheckBoxes();
            console.log(form.action);
            form.submit();
        }
        //this function collects all checked checkbox values, concatinates them in one string and returns that string to send it after by POST method to server
        function collectSellectedCheckBoxes() {
            var returnValue = "";
            var targetCheckBoxes = document.querySelectorAll(".inventoryItemId");
            for (x = 0; x < targetCheckBoxes.length; x++) {
                if (targetCheckBoxes[x].checked)
                    returnValue += targetCheckBoxes[x].id + ",";
            }
            return returnValue;
        }
    </script>
</body>
</html>
