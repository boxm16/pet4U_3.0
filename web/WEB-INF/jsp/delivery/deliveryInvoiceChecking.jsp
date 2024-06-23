<%-- 
    Document   : deliveryInvoiceChecking
    Created on : Jun 25, 2023, 6:30:28 PM
    Author     : Michail Sitmalidis
--%>
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
        <title>Delivery Invoice Checking</title>
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
    <body>
    <center>

        <h1>Delivery Checking</h1>
        <h3>Ημερομηνία Παραστατικού:  ${deliveryInvoice.insertionDate} 
            &nbsp;&nbsp;&nbsp; 
            Προμηθευτής:  ${deliveryInvoice.supplier} 
            &nbsp;&nbsp;&nbsp; 
            Αριθμός Παραστατικού:  ${deliveryInvoice.number} </h3>
        <hr>

        <table>
            <thead>
                <tr>
                    <th colspan="6">
                        <h3>  
                            <center> <input type="text" onkeypress="check(event, this)"></center>
                            <center> <p id="descriptionDisplay"></center>
                        </h3>
                    </th>
                </tr>

                <tr>
                    <th>A/A</th>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Sent</th>
                    <th>Delivered</th>
                    <th>Alert</th>
                </tr>
            </thead>
            <tbody id="tableBody">
                <%
                    int x = 1;
                    DeliveryInvoice deliveryInvoice = (DeliveryInvoice) request.getAttribute("deliveryInvoice");
                    LinkedHashMap<String, DeliveryItem> items = deliveryInvoice.getItems();
                    for (Map.Entry<String, DeliveryItem> deliveryItemEntry : items.entrySet()) {
                        DeliveryItem item = deliveryItemEntry.getValue();

                        out.println("<tr>");
                        out.println("<td>");
                        out.println(x);
                        out.println("</td>");
                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println(item.getCode());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input  class='sent' type='number' id='" + item.getCode() + "_sent' value='" + item.getQuantity() + "' readonly width='10px'>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input class='delivered' type='number' id='" + item.getCode() + "_delivered' value='" + item.getDeliveredQuantity() + "'>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<dev id='" + item.getCode() + "_colorDisplay'>____</dev>");
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }
                %>
            </tbody>
        </table>
        <hr>
        ${saveButton}
        <form id="form" action="#" method="POST">
            <input hidden type="text"  name="invoiceId" value="${deliveryInvoice.invoiceId()}">
            <input hidden type="text"  name="supplier" value="${deliveryInvoice.supplier()}">
            <input hidden type="text"  name="invoiceNumber" value="${deliveryInvoice.number()}">
            <input hidden type="text" id="deliveredItems" name="deliveredItems">
            <input hidden type="text" id="sentItems" name="sentItems">
        </form>


    </center>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <script type="text/javascript">

                                class Item {
                                    constructor(altercode, code, description) {
                                        this.altercode = altercode;
                                        this.code = code;
                                        this.description = description;
                                    }
                                }

                                var items = new Array();
        <c:forEach items="${pet4UItemsRowByRow}" var="item">
                                var altercode = "${item.altercode}";
                                var code = "${item.code}";
                                var description = "${item.description}";
                                var item = new Item(altercode, code, description);
                                items[altercode] = item;
        </c:forEach>




                                function check(event, input) {
                                    if (event.keyCode === 13) {
                                        var altercode = input.value;
                                        console.log("altercode:" + altercode);
                                        var item = items[altercode];
                                        if (item == null) {
                                            let unknownBarcodeX = document.getElementById(altercode + "_sent");

                                            if (unknownBarcodeX == null) {
                                                document.getElementById("descriptionDisplay").innerHTML = altercode + " : Unkown Barcode: " + altercode;
                                                addRow(altercode, "Unkown Barcode: " + altercode);
                                            } else {
                                                let unknownBarcodeD = document.getElementById(altercode + "_delivered");
                                                let v = unknownBarcodeD.value;
                                                v++;
                                                unknownBarcodeD.value = v;
                                            }
                                        } else {
                                            var code = item.code;
                                            console.log(code);
                                            var description = item.description;
                                            document.getElementById("descriptionDisplay").innerHTML = altercode + " : " + description;


                                            let sent = document.getElementById(code + "_sent");
                                            if (sent == null) {
                                                addRow(item.code, item.description);
                                            } else {
                                                sent = sent.value * 1;
                                            }

                                            let delivered = document.getElementById(code + "_delivered").value * 1;
                                            delivered++;

                                            document.getElementById(code + "_delivered").value = delivered;

                                            let colorDisplay = document.getElementById(code + "_colorDisplay");

                                            let diff = sent - delivered;
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

                                        input.value = "";

                                    }
                                }

                                function addRow(code, description) {
                                    // Get the table body element in which you want to add row
                                    let table = document.getElementById("tableBody");

                                    // Create row element
                                    let row = document.createElement("tr")

                                    // Create cells
                                    let c1 = document.createElement("td")
                                    let c2 = document.createElement("td")
                                    let c3 = document.createElement("td")
                                    let c4 = document.createElement("td")
                                    let c5 = document.createElement("td")
                                    let c6 = document.createElement("td")
                                    // Insert data to cells
                                    c1.innerText = "----";
                                    c2.innerText = code;
                                    c3.innerText = description;
                                    c4.innerHTML = "<input class='sent' type='number' id='" + code + "_sent' value='0' readonly width='10px'>";
                                    c5.innerHTML = "<input class='delivered' type='number' id='" + code + "_delivered' value='1'>";
                                    c6.innerHTML = "<dev id='" + code + "_colorDisplay'>____</dev>";


                                    // Append cells to row
                                    row.appendChild(c1);
                                    row.appendChild(c2);
                                    row.appendChild(c3);
                                    row.appendChild(c4);
                                    row.appendChild(c5);
                                    row.appendChild(c6);


                                    // Append row to table body
                                    table.appendChild(row)
                                }

                                //---------------------------------
                                //--------------------------------
                                //---------------------------------
                                function requestRouter(requestTarget) {
                                    form.action = requestTarget;

                                    let sent = collectSentData();
                                    sentItems.value = sent;

                                    let delivered = collectDeliveredData();
                                    deliveredItems.value = delivered;


                                    // console.log(data);
                                    form.submit();
                                }

                                function collectSentData() {
                                    var returnValue = "";
                                    var sentItems = document.querySelectorAll(".sent");

                                    for (x = 0; x < sentItems.length; x++) {

                                        returnValue += sentItems[x].id + ":" + sentItems[x].value + ",";
                                    }
                                    return returnValue;
                                }

                                function collectDeliveredData() {
                                    var returnValue = "";
                                    var deliveredItems = document.querySelectorAll(".delivered");

                                    for (x = 0; x < deliveredItems.length; x++) {

                                        returnValue += deliveredItems[x].id + ":" + deliveredItems[x].value + ",";
                                    }
                                    return returnValue;
                                }
    </script>
</body>
</html>
