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
        <title>ENDO  Checking</title>
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
        <h1>ΕΛΕΓΧΟΣ ΠΑΡΑΛΑΒΗΣ ΕΝΔΟΔΙΑΚΙΝΙΣΗΣ</h1>
        <hr>
        <button onclick="rechechAll()" class="btn-lg btn-warning">ReCheck All Items </button>

        <hr>
        <table>
            <thead>
                <tr>
                    <th colspan="7">
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
                    <th>Remove</th>
                </tr>
            </thead>
            <tbody id="tableBody">
                <%

                    DeliveryInvoice deliveryInvoice = (DeliveryInvoice) request.getAttribute("deliveryInvoice");
                    LinkedHashMap<String, DeliveryItem> items = deliveryInvoice.getItems();

                    int x = 1;
                    for (Map.Entry<String, DeliveryItem> deliveryItemEntry : items.entrySet()) {
                        DeliveryItem item = deliveryItemEntry.getValue();

                        out.println("<tr>");
                        out.println("<td>");
                        out.println(x);
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<a href='showDeltiaApostolisOfItem.htm?itemCode=" + item.getCode() + "' target='_blank'>" + item.getCode() + "</a>");
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("<a>" + item.getDescription() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input  class='sent' type='number' id='" + item.getCode() + "@sent' value='" + item.getSentQuantity() + "' readonly width='10px'>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input class='delivered' type='number' id='" + item.getCode() + "@delivered' value='" + item.getDeliveredQuantity() + "'>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<dev id='" + item.getCode() + "@colorDisplay'>____</dev>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<button onclick=\"removeRow(this)\">Remove</button>");
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }
                %>
            </tbody>
        </table>
        <hr>
        ${saveButton}
        <hr><hr><br><br><hr><hr>
        ${uploadButton}
        <form id="form" action="#" method="POST">
            <input hidden type="text"  name="invoiceNumber" value="${deliveryInvoice.getNumber()}">
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

                                class AltercodeContainer {
                                    constructor(altercode, packageBarcode, itemsInPackage) {
                                        this.altercode = altercode;
                                        this.packageBarcode = packageBarcode;
                                        this.itemsInPackage = itemsInPackage;
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




                                var altercodeContainers = new Array();
        <c:forEach items="${pet4UAllAltercodeContainers}" var="altercodeContainer">

                                var altercodeBarcode = "${altercodeContainer.altercode}";
                                var packageBarcode = "${altercodeContainer.packageBarcode}";
                                var itemsInPackage = "${altercodeContainer.itemsInPackage}";
                                var altercodeContainer = new AltercodeContainer(altercodeBarcode, packageBarcode, itemsInPackage);

                                altercodeContainers[altercodeBarcode] = altercodeContainer;
        </c:forEach>


                                function check(event, input) {
                                    if (event.keyCode === 13) {
                                        var altercode = input.value.trim();
                                        console.log("altercode:" + altercode);
                                        var item = items[altercode];
                                        var altercodeContainer = altercodeContainers[altercode];

                                        if (item == null) {
                                            playBeep();
                                            if (altercodeContainer != null) {
                                                console.log("Something Wrong, Item is null, but barocede is not" + altercode);
                                            }
                                            let unknownBarcodeSent = document.getElementById(altercode + "@sent");
                                            let unknownBarcodeDelivered = document.getElementById(altercode + "@delivered");

                                            if (unknownBarcodeSent == null) {
                                                document.getElementById("descriptionDisplay").innerHTML = altercode + " : NKNOWN ALTERCODE : " + altercode;
                                                addRow(altercode, "UNKNOWN ALTERCODE " + altercode);
                                                let unknownBarcodeDelivered = document.getElementById(altercode + "@delivered");
                                                let v = unknownBarcodeDelivered.value;
                                                v++;
                                                unknownBarcodeDelivered.value = v;
                                            } else {
                                                let v = unknownBarcodeDelivered.value;
                                                v++;
                                                unknownBarcodeDelivered.value = v;
                                            }

                                            let colorDisplay = document.getElementById(altercode + "@colorDisplay");
                                            colorDisplay.style.backgroundColor = 'yellow';


                                        } else {
                                            var code = item.code;
                                            console.log(code);
                                            var description = item.description;
                                            document.getElementById("descriptionDisplay").innerHTML = altercode + " : " + description;


                                            //----------
                                            if (altercodeContainer == null) {
                                                console.log("Something Wrong, while Item is not null, barcode is null" + altercode);
                                            }
                                            //-------------

                                            let sent = document.getElementById(code + "@sent");
                                            if (sent == null) {
                                                playBeep();
                                                addRow(item.code, item.description);
                                            } else {
                                                sent = sent.value * 1;// here
                                            }

                                            let delivered = document.getElementById(code + "@delivered").value * 1;

                                            if (altercodeContainer.packageBarcode == "true") {
                                                delivered += altercodeContainer.itemsInPackage * 1;
                                            } else {
                                                delivered++;
                                            }

                                            document.getElementById(code + "@delivered").value = delivered;

                                            let colorDisplay = document.getElementById(code + "@colorDisplay");

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
                                    let c7 = document.createElement("td")
                                    // Insert data to cells
                                    c1.innerText = "----";
                                    c2.innerText = code;
                                    c3.innerText = description;
                                    c4.innerHTML = "<input class='sent' type='number' id='" + code + "@sent' value='0' readonly width='10px'>";
                                    c5.innerHTML = "<input class='delivered' type='number' id='" + code + "@delivered' value='0'>";
                                    c6.innerHTML = "<dev id='" + code + "@colorDisplay'>____</dev>";
                                    c7.innerHTML = "<button onclick=\"removeRow(this)\">Remove</button>";


                                    // Append cells to row
                                    row.appendChild(c1);
                                    row.appendChild(c2);
                                    row.appendChild(c3);
                                    row.appendChild(c4);
                                    row.appendChild(c5);
                                    row.appendChild(c6);
                                    row.appendChild(c7);


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


                                function removeRow(button) {
                                    // Get the parent row of the clicked button and remove it
                                    let row = button.parentNode.parentNode;
                                    row.parentNode.removeChild(row);
                                }


                                //---------------------------------
                                function rechechAll() {
                                    var deliveredItems = document.querySelectorAll(".delivered");

                                    for (x = 0; x < deliveredItems.length; x++) {
                                        let deliveredItem = deliveredItems[x];
                                        const deliveredItemArrayed = deliveredItem.id.split("@");
                                        let itemtemCode = deliveredItemArrayed[0];

                                        let sent = document.getElementById(itemtemCode + "@sent");
                                        if (sent == null) {
                                            addRow(item.code, item.description);
                                        } else {
                                            sent = sent.value * 1;
                                        }

                                        let delivered = document.getElementById(itemtemCode + "@delivered").value * 1;



                                        let colorDisplay = document.getElementById(itemtemCode + "@colorDisplay");

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
                                }

                                //-------------------
                                //----------------------------
                                function playBeep() {
                                    let audioCtx = new (window.AudioContext || window.webkitAudioContext)();
                                    let oscillator = audioCtx.createOscillator();
                                    let gainNode = audioCtx.createGain();

                                    oscillator.type = "sine";  // You can use 'square' for a harsher sound
                                    oscillator.frequency.setValueAtTime(1000, audioCtx.currentTime); // 1000 Hz = Beep sound
                                    gainNode.gain.setValueAtTime(1, audioCtx.currentTime);

                                    oscillator.connect(gainNode);
                                    gainNode.connect(audioCtx.destination);

                                    oscillator.start();
                                    setTimeout(() => {
                                        oscillator.stop();
                                    }, 500); // Beep duration: 500ms
                                }
    </script>

</body>
</html>
