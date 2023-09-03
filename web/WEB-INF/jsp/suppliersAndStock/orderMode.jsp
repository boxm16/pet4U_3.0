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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

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

        <h1>${supplier.name} Order</h1>
        <hr>
        <table class="table">
            <thead>
            <th>ΚΩΔΙΚΟΣ</th>
            <th>Description</th>

            <th>Six<br> Months<br>Sales</th>
            <th>2 Week`s <br> Sales</th>
            <th>Stock</th>
            <th>Minimal <br>Stock</th>



            <th>Order <br>Unit</th>
            <th>Order <br>Unit<br>Capacity</th>
            <th>Note</th>
            <th>Order<br> Suggestion<br>BY <br>ORDER <br>UNIT </th>
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

                    out.println("<td>");
                    out.println(item.getOrderUnit());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getOrderUnitCapacity());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getNote());
                    out.println("</td>");

                    Double suggestion = item.getTotalShippedPieces() / 6 / item.getOrderUnitCapacity();
                    out.println("<td style='background-color:white;  text-align: center;' >");
                    out.println(suggestion.intValue());
                    out.println("</td>");

                    out.println("<td style='background-color:white'>");
                    out.println("<input onkeyup='recalculateItems(event)' id='" + item.getCode() + ":" + item.getOrderUnitCapacity() + "' class='unitsOrdered' style='font-size:20px' type='number'>");
                    out.println("</td>");

                    out.println("<td style='background-color:white'>");
                    out.println("<input id='" + item.getCode() + ":receiver' class='orderedItem' style='font-size:20px' type='number' >");
                    out.println("</td>");

                    out.println("</tr>");

                }
            %>
        </table>
        <hr>
        <form id="form" action="downlodOrderInExcelFormat.htm" target="_blank" method="POST">
            <input hidden type="text" id="supplierId" name="supplierId" value="${supplier.id}">
            <input hidden type="text" id="orderedItems" name="orderedItems" >
        </form>
        <button class="btn btn-success " onclick="downloadInExcelFormat()">DOWNLOD IN EXCEL FORMAT</button>
        <hr>  <hr>
    </center>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    <script>
            function recalculateItems(event) {

                let id = event.target.id;
                let value = event.target.value;
                const idArray = id.split(":");
                id = idArray[0];
                let capacity = idArray[1];
                let receiver = document.getElementById(id + ":receiver");
                receiver.value = value * capacity;

            }
//--------down is function to change focus. Not mine, just copied from stackover :)
            function getTabStops(o, a, el) {
                // Check if this element is a tab stop
                if (el.tabIndex > 0) {
                    if (o[el.tabIndex]) {
                        o[el.tabIndex].push(el);
                    } else {
                        o[el.tabIndex] = [el];
                    }
                } else if (el.tabIndex === 0) {
                    // Tab index "0" comes last so we accumulate it seperately
                    a.push(el);
                }
                // Check if children are tab stops
                for (var i = 0, l = el.children.length; i < l; i++) {
                    getTabStops(o, a, el.children[i]);
                }
            }

            function focusNext() {
                var o = [],
                        a = [],
                        stops = [],
                        active = document.activeElement;
                getTabStops(o, a, document.body);
                // Use simple loops for maximum browser support
                for (var i = 0, l = o.length; i < l; i++) {
                    if (o[i]) {
                        for (var j = 0, m = o[i].length; j < m; j++) {
                            stops.push(o[i][j]);
                        }
                    }
                }
                for (var i = 0, l = a.length; i < l; i++) {
                    stops.push(a[i]);
                }
                // If no items are focusable, then blur
                if (stops.length === 0) {
                    active.blur();
                    return;
                }
                // Shortcut if current element is not focusable
                if (active.tabIndex < 0) {
                    stops[0].focus();
                    return;
                }
                // Attempt to find the current element in the stops
                for (var i = 0, l = stops.length; i < l; i++) {
                    if (stops[i] === active) {
                        if (i + 1 === stops.length) {
                            active.blur();
                            return;
                        }
                        stops[i + 1].focus();
                        return;
                    }
                }
                // We shouldn't make it this far
                active.blur();
            }

            document.addEventListener('keypress', function (e) {
                if (e.which === 13) {
                    e.preventDefault();
                    focusNext();
                }
            });

            //-------------


            function downloadInExcelFormat() {

                orderItemsInput.value = collectOrderData();

                form.submit();
            }
            //this function collects data
            function collectOrderData() {
                var returnValue = "";
                var collectedItems = document.querySelectorAll(".orderedItems");
                for (x = 0; x < collectedItems.length; x++) {

                    returnValue += collectedItems[x].id + ":" + collectedItems[x].id +",";
                }
                return returnValue;
            }
    </script>
</body>
</html>
