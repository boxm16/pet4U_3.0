<%-- 
    Document   : stockManagement
    Created on : Aug 15, 2023, 6:35:42 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.ArrayList"%>
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

            input[type='number']{
                width: 100px;
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

            <th>Six<br> Months<br>Sales</th>
            <th>2 Week`s <br> Sales</th>
            <th>Stock</th>
            <th>Minimal <br>Stock</th>
            <th>Order</th>


            <th>Order <br>Unit</th>
            <th>Order <br>Unit<br>Capacity</th>
            <th>Note</th>
            <th> MAKE <br>ORDER<br>BY <br>ORDER <br>UNIT </th>
            <th> ORDERED<br> ITEMS </th>

            </thead>
            <%
                LinkedHashMap<String, SuppliersItem> items = (LinkedHashMap) request.getAttribute("supplierItems");
                for (Map.Entry<String, SuppliersItem> entrySet : items.entrySet()) {
                    SuppliersItem item = entrySet.getValue();

                    String alarmColor = "";
                    int minimalStock = item.getMinimalStock();
                    Double pet4uStock = 0.0;
                    if (item.getQuantity() == null) {
                    } else {
                        pet4uStock = Double.parseDouble(item.getQuantity());
                    }
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

                    if (needOrder) {
                        out.println("<td>");
                        out.println("<input class='itemId' type='checkbox'  id='" + item.getCode() + "' style='width:28px;height:28px' checked>");
                        out.println("</td>");
                    } else {
                        out.println("<td>");
                        out.println("<input class='itemId' type='checkbox'  id='" + item.getCode() + "' style='width:28px;height:28px'>");
                        out.println("</td>");
                    }
                    out.println("<td>");
                    out.println(item.getOrderUnit());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getOrderUnitCapacity());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getNote());
                    out.println("</td>");

                    out.println("<td style='background-color:white'>");
                    out.println("<input onkeyup='recalculateItems(event)' id='" + item.getCode() + ":" + item.getOrderUnitCapacity() + "' class='unitsOrdered' style='font-size:20px' type='number' ");
                    out.println("</td>");

                    out.println("<td style='background-color:white'>");
                    out.println("<input id='" + item.getCode() + ":receiver' class='itemsOrdered' style='font-size:20px' type='number'");
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

    <script>
        function recalculateItems(event) {
            let id = event.target.id;
            let value = event.target.value;
            const idArray = id.split(":");
            id = idArray[0];
            let capacity = idArray[1];
            let receiver = document.getElementById(id+":receiver");
          receiver.value=value*capacity;
            console.log(receiver);
        }
    </script>
</body>
</html>
