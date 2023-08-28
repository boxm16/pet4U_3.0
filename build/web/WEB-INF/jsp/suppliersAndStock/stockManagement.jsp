<%-- 
    Document   : stockManagement
    Created on : Aug 15, 2023, 6:35:42 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="SuppliersAndStock.SuppliersItem"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock Management</title>
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

        <h1>${supplier.name} Stock Management</h1>
        <form action="goForAddingItemToSupplier.htm" method="POST">
            <input hidden name="supplierId" value="${supplier.id}">
            <input type="text" name="altercode">
            <button type="submit">Add New Item</button>
        </form>

        <hr>
        <table>
            <thead>
            <th>ΚΩΔΙΚΟΣ</th>
            <th>Description</th>

            <th>Six Months<br>Sales</th>
            <th>Period`s Sales</th>
            <th>Stock</th>
            <th>Minimal <br>Stock</th>
            <th>Order</th>


            <th>Order <br>Unit</th>
            <th>Order <br>Unit<br>Capacity</th>
            <th>Note</th>
            <th>  Edit  </th>

            </thead>
            <%
                LinkedHashMap<String, SuppliersItem> items = (LinkedHashMap) request.getAttribute("supplierItems");
                for (Map.Entry<String, SuppliersItem> entrySet : items.entrySet()) {
                    SuppliersItem item = entrySet.getValue();
                    String alarmColor = "";
                    int minimalStock = item.getMinimalStock();
                    Double pet4uStock = Double.parseDouble(item.getQuantity());
                    boolean needOrder = false;
                    if (pet4uStock < minimalStock * 2) {
                        alarmColor = "yellow";
                        needOrder = true;
                    }
                    if (pet4uStock < minimalStock) {
                        alarmColor = "#F33A6A";
                        needOrder = true;
                    }

                    out.println("<tr style='background-color: " + alarmColor + "'>");

                    out.println("<td>");
                    out.println(item.getCode());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getTotalShippedPieces());
                    out.println("</td>");

                    int shippedPiecesForPeriod = item.getTotalShippedPiecesForPeriod();
                    out.println("<td>");
                    out.println(shippedPiecesForPeriod);
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getQuantity());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getMinimalStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getMinimalStock());
                    out.println("</td>");

                    if (needOrder) {
                        out.println("<td>");
                        out.println("<input class='orderItemid' type='checkbox' class='code' id='" + item.getCode() + "' style='width:28px;height:28px' checked>");
                        out.println("</td>");
                    } else {
                        out.println("<td>");
                        out.println("<input class='orderItemId' type='checkbox' class='code' id='" + item.getCode() + "' style='width:28px;height:28px'>");
                        out.println("</td>");
                    }

                    out.println("<td>");
                    out.println(item.getOrderUnitCapacity());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getNote());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='goForEditingSuppliersItem.htm?supplierId=" + item.getSupplierId() + "&code=" + item.getCode() + "'>Edit</a>");
                    out.println("</td>");

                    out.println("</tr>");

                }
            %>
        </table>

    </center>
    <form id="form" action="#" method="POST">
        <input hidden type="text" id="supplierId" name="supplierId" value="${supplier.id}">
        <input hidden type="text" id="orderItemsInput" name="itemsIds">
    </form>

    <a href="#" onclick="requestRouter('orderMode.htm')"><h4>Order Mode</h4></a>
    <script>
        ////--------------------
        function requestRouter(requestTarget) {
            if (requestTarget == "orderMode.htm") {
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
            var targetCheckBoxes = document.querySelectorAll(".orderItemId");
            for (x = 0; x < targetCheckBoxes.length; x++) {
                if (targetCheckBoxes[x].checked)
                    returnValue += targetCheckBoxes[x].id + ",";
            }
            return returnValue;
        }
    </script>
</body>
</html>
