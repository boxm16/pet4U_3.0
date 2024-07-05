<%-- 
    Document   : deliveryInvoiceChecking
    Created on : Jun 25, 2023, 6:30:28 PM
    Author     : Michail Sitmalidis
--%>
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

        </style>
    </head>
    <body onload="rechechAll()">
    <center>


        <h1>Endo Order Checking</h1>
        Order Id <input type="text" readonly  value="${endoOrder.id}" style="width:350px"/>
        Order Date  <input name="dateString" readonly type="text" value="${endoOrder.dateString}" style="width:100px">
        Order Destination  <input type="text" readonly name="receiver" value="${endoOrder.destination}" />
        <hr>
        Invoice Id <input type="text" readonly  value="${endoApostolis.id}" style="width:80px"/>
        Invoice Date  <input name="dateString" readonly type="text" value="${endoApostolis.dateString}" style="width:100px">
        Invoice Number  <input name="dateString" readonly type="text" value="${endoApostolis.number}"  style="width:80px">
        Invoice Destination  <input type="text" readonly name="receiver" value="${endoApostolis.receiver}" style="width:350px" />
        <hr>
        <table>
            <thead> 
                <tr>
                    <th>A/A</th>

                    <th>Code</th>
                    <th>Ordered<br>Altercode</th>
                    <th>Ordered<br>Barcode</th>

                    <th>Description</th>
                    <th>Ordered</th>
                    <th>Invoiced</th>
                    <th>Alert</th>
                    <th>Comment</th>
                </tr>
            </thead>
            <tbody id="tableBody">
                <%
                    int x = 1;

                    EndoApostolis endoApostolis = (EndoApostolis) request.getAttribute("endoApostolis");
                    LinkedHashMap<String, Item> invoicedItems = endoApostolis.getItems();

                    EndoOrder endoOrder = (EndoOrder) request.getAttribute("endoOrder");
                    LinkedHashMap<String, EndoOrderItem> orderedItems = endoOrder.getOrderedItems();
                    for (Map.Entry<String, EndoOrderItem> orderedItemEntry : orderedItems.entrySet()) {
                        EndoOrderItem orderedItem = orderedItemEntry.getValue();
                        Item invoicedItem = invoicedItems.remove(orderedItemEntry.getKey());

                        out.println("<tr>");
                        out.println("<td>");
                        out.println(x);
                        out.println("</td>");
                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("<a href='itemAnalysis.htm?code=" + orderedItem.getCode() + "' target='_blank'>" + orderedItem.getCode() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(orderedItem.getOrderedAltercode());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(orderedItem.getItemBarcode());
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
                        if (orderedItem.getComment().isEmpty()) {
                            out.println("<td>");
                        } else if (!orderedItem.getComment().equals("ΤΕΜΑΧΙΑ")) {
                            out.println("<td style='background-color:red'>");
                        } else {
                            out.println("<td>");
                        }
                        out.println(orderedItem.getComment());
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }

                    for (Map.Entry<String, Item> invoicedItemsEntry : invoicedItems.entrySet()) {
                        Item invoicedItem = invoicedItemsEntry.getValue();

                        out.println("<tr>");
                        out.println("<td>");
                        out.println(x);
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("<a href='itemAnalysis.htm?code=" + invoicedItem.getCode() + "' target='_blank'>" + invoicedItem.getCode() + "</a>");
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("-------");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(invoicedItem.getDescription());
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("-------");
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

                        out.println("<td>");
                        out.println("");
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }
                %>
            </tbody>
        </table>
        <hr>

        <form id="form" action="#" method="POST">
            <input hidden type="text"  name="invoiceNumber" value="${deliveryInvoice.getNumber()}">
            <input hidden type="text" id="deliveredItems" name="deliveredItems">
            <input hidden type="text" id="sentItems" name="sentItems">
        </form>
        <hr>
        <h1>  <a href="unbindOrderWithEndo.htm?orderId=${endoOrder.id}&outgoingEndoId=${endoApostolis.id}">UNBIND ENDO ORDER AND ENDO APOSTOLIS</a></h1>
        <hr>
        <hr>

        ${lockerButton}
    </center>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <script type="text/javascript">



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
    </script>
</body>
</html>
