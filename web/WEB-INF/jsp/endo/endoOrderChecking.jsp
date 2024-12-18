<%-- 
    Document   : deliveryInvoiceChecking
    Created on : Jun 25, 2023, 6:30:28 PM
    Author     : Michail Sitmalidis
--%>
<%@page import="java.time.LocalDate"%>
<%@page import="Endo.EndoPackaging"%>
<%@page import="BasicModel.Item"%>
<%@page import="Endo.EndoApostolis"%>
<%@page import="Endo.EndoOrderItem"%>
<%@page import="Endo.EndoOrder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Delivery.DeliveryItem"%>
<%@page import="java.util.HashMap"%>
<%@page import="Delivery.DeliveryInvoice"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Endo Order Checking</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

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
                text-align: center;
                background: #eee;
                position: sticky;
                top: 0px;
            }
            #packagesCount    {
                width: 3.5em;
                font-size: 40px;
                font-weight: bold;
                background: lightgreen;
            }
            #labelsCount    {
                width: 3.5em;
                font-size: 40px;
                font-weight: bold;
                background: lightgreen;
            }


        </style>
    </head>
    <body onload="rechechAll()">
    <center>

        <h1>Endo Order Checking</h1>

        <table>
            <tr>
                <th style=" font-size: 20px;">ΚΑΤΑΣΤΗΜΑ</th>
                <th style=" font-size: 20px;">Order Date</th>
                <th style=" font-size: 20px;">Delivery Date</th>
            </tr>
            <tr >
                <td style=" font-size: 30px;">${endoApostolis.receiver}</td>
                <td style=" font-size: 30px;">${endoOrder.dateString}</td>
                <td style="font-size: 30px;background-color:${dateCheckColor}">${endoApostolis.dateString}</td>
            </tr>
        </table>
        <hr>
        <table>
            <thead> 
                <tr>
                    <th>A/A</th>
                    <th>Ordered<br>Altercode</th>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Ordered</th>
                    <th>Invoiced</th>
                    <th>Alert</th>
                </tr>
            </thead>
            <tbody id="tableBody">
                <%
                    int x = 1;
                    int y = 0;
                    EndoApostolis endoApostolis = (EndoApostolis) request.getAttribute("endoApostolis");

                    LinkedHashMap<String, Item> invoicedItems = endoApostolis.getItems();

                    EndoOrder endoOrder = (EndoOrder) request.getAttribute("endoOrder");
                    LinkedHashMap<String, EndoOrderItem> orderedItems = endoOrder.getOrderedItems();
                    LinkedHashMap<String, EndoPackaging> allEndoPackaging = (LinkedHashMap) request.getAttribute("allEndoPackaging");
                    for (Map.Entry<String, EndoOrderItem> orderedItemEntry : orderedItems.entrySet()) {
                        EndoOrderItem orderedItem = orderedItemEntry.getValue();
                        Item invoicedItem = invoicedItems.remove(orderedItemEntry.getKey());

                        String rowColor = "inherited";
                        if (invoicedItem != null) {
                            EndoPackaging endoPackaging = allEndoPackaging.get(invoicedItem.getCode());
                            if (endoPackaging != null) {
                                double q = Double.valueOf(invoicedItem.getQuantity());
                                int qi = (int) q;

                                rowColor = "#C2F2D7";
                                y = y + (qi / endoPackaging.getItem() * endoPackaging.getLabel());
                            }
                        }

                        out.println("<tr style='background-color:" + rowColor + "'>");
                        out.println("<td>");
                        out.println(x);
                        out.println("</td>");

                        out.println("<td>");
                        out.println(orderedItem.getOrderedAltercode());
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("<a href='findItemByAltercode.htm?altercode=" + orderedItem.getCode() + "' target='_blank'>" + orderedItem.getCode() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(orderedItem.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input  class='ordered' type='number' id='" + orderedItem.getCode() + "@ordered' value='" + orderedItem.getOrderedQuantity() + "' readonly width='10px'>");
                        out.println("</td>");

                        out.println("<td>");
                        if (invoicedItem == null) {

                            out.println("<input class='invoiced' type='number' id='" + orderedItem.getCode() + "@invoiced' value='0.0'>");
                        } else {
                            out.println("<input class='invoiced' type='number' id='" + invoicedItem.getCode() + "@invoiced' value='" + invoicedItem.getQuantity() + "'>");
                        }
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<dev id='" + orderedItem.getCode() + "@colorDisplay'>____</dev>");
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }

                    for (Map.Entry<String, Item> invoicedItemsEntry : invoicedItems.entrySet()) {
                        Item invoicedItem = invoicedItemsEntry.getValue();

                        String rowColor = "inherited";
                        if (invoicedItem != null) {
                            EndoPackaging endoPackaging = allEndoPackaging.get(invoicedItem.getCode());
                            if (endoPackaging != null) {
                                double q = Double.valueOf(invoicedItem.getQuantity());
                                int qi = (int) q;

                                rowColor = "#C2F2D7";
                                y = y + (qi / endoPackaging.getItem() * endoPackaging.getLabel());
                            }
                        }

                        out.println("<tr>");
                        out.println("<td>");
                        out.println(x);
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("-------");
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("<a href='itemAnalysis.htm?code=" + invoicedItem.getCode() + "' target='_blank'>" + invoicedItem.getCode() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(invoicedItem.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input  class='ordered' type='number' id='" + invoicedItem.getCode() + "@ordered' value='0.0' readonly width='10px'>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input class='invoiced' type='number' id='" + invoicedItem.getCode() + "@invoiced' value='" + invoicedItem.getQuantity() + "'>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<dev id='" + invoicedItem.getCode() + "@colorDisplay'>____</dev>");
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }
                %>
            </tbody>
        </table>
        <hr>

        <h3 style='background-color: #C2F2D7'> ΜΟΝΟΚΟΜΜΑΤΑ(τσουβάλια, κλουβιά, catsan): <%   out.println(y);%></h3>
        <input hidden id="monokommata"  value="<%   out.println(y);%>" >
        <h3> ΔΕΜΑΤΑ(κουτιά)   <input  type="number" id="packagesCount" name="packagesCount" value="0"  onkeyup="sumUp()" onchange="sumUp()">
            ΣΥΝΟΛΟ ΕΤΙΚΕΤΩΝ   <input  type="number" id="labelsCount" name="labelsCount" <%  out.println("value='" + y + "'");%> > </h3>
        <br>
        <button id="printerButton" style='font-size: 40px; width:250px;' class="btn btn-warning" onclick="printLabels()"> PRINT LABELS</button>


        <div id='printingResponseDisplay'></div>
        <hr>
        <h1>  <a href="bindOrderWithEndo.htm?orderId=${endoOrder.id}&outgoingEndoId=${endoApostolis.id}">SAVE (BIND) ENDO ORDER AND ENDO APOSTOLIS</a></h1>

        <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr>
    </center>


    <!-- jQuery CDN -  -->
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <!-- Popper.JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    <!-- jQuery Custom Scroller CDN -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>

    <script type="text/javascript">

            packagesCount.addEventListener(`focus`, () => packagesCount.select());
            labelsCount.addEventListener(`focus`, () => labelsCount.select());

            document.getElementById("packagesCount")
                    .addEventListener("keyup", function (event) {
                        event.preventDefault();
                        if (event.keyCode === 13) {
                            document.getElementById("printerButton").focus();
                        }
                    });
            document.getElementById("labelsCount")
                    .addEventListener("keyup", function (event) {
                        event.preventDefault();
                        if (event.keyCode === 13) {
                            document.getElementById("printerButton").focus();
                        }
                    });


            function rechechAll() {
                var orderedItems = document.querySelectorAll(".ordered");
                for (x = 0; x < orderedItems.length; x++) {
                    let orderedItem = orderedItems[x];
                    //  console.log(orderedItem);
                    const orderedItemArrayed = orderedItem.id.split("@");
                    let itemtemCode = orderedItemArrayed[0];
                    //   console.log("Item Code : " + itemtemCode);
                    let invoiced = document.getElementById(itemtemCode + "@invoiced");
                    if (invoiced == null) {
                        console.log("WHOPA");
                    } else {
                        invoiced = invoiced.value * 1;
                    }

                    let ordered = document.getElementById(itemtemCode + "@ordered").value * 1;
                    let colorDisplay = document.getElementById(itemtemCode + "@colorDisplay");
                    let diff = invoiced - ordered;
                    if (diff > 0) {
                        colorDisplay.style.backgroundColor = 'red';
                    }
                    if (diff < 0) {
                        colorDisplay.style.backgroundColor = 'yellow';
                    }
                    if (diff === 0) {
                        colorDisplay.style.backgroundColor = 'green';
                    }
                }
            }

            //----------------------- PRINTING AJAX------------


            function sumUp() {
                labelsCount.value = monokommata.value * 1 + packagesCount.value * 1;
            }

            function printLabels() {

                ajax(labelsCount.value, "${endoApostolis.receiver}");
            }



            function ajax(labelsCount, storeName) {
                $("#printingResponseDisplay").html("Printig......");
                $.ajax({
                    url: 'printLabel.htm?labelsCount=' + labelsCount + '&storeName=' + storeName,
                    //  contentType: "application/x-www-form-urlencoded;charset=UTF-8",

                    success: function (status) {
                        $("#printingResponseDisplay").html(status);
                        console.log(status)
                    }
                });
            }
    </script>
</body>
</html>
