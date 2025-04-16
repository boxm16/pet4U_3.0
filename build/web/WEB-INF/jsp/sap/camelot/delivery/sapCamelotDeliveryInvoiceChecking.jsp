<%-- 
    Document   : deliveryInvoiceChecking
    Created on : Jun 25, 2023, 6:30:28 PM
    Author     : Michail Sitmalidis
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Delivery.DeliveryItem"%>
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
                border: 1px solid;
                border-collapse: collapse;
            }
            td {
                font-size: 20px;
            }
            th {
                font-size: 30px;
                font-weight: bold;
                text-align: center;
                background: #eee;
                position: sticky;
                top: 0px;
            }
            .po-line {
                font-weight: bold;
                color: #333;
            }
            .unknown-item {
                background-color: #ffe6e6;
            }
        </style>
    </head>
    <body>
    <center>
        <h1>Delivery Checking</h1>
        <h3>
            Ημερομηνία Παραστατικού: ${deliveryInvoice.insertionDate} 
            &nbsp;&nbsp;&nbsp; 
            Προμηθευτής: ${deliveryInvoice.supplier} 
            &nbsp;&nbsp;&nbsp; 
            Αριθμός Παραστατικού: ${deliveryInvoice.number}
        </h3>
        <hr>
        <button onclick="recheckAll()" class="btn-lg btn-warning">ReCheck All Items</button>
        <hr>

        <table>
            <thead>
                <tr>
                    <th colspan="7">
                        <h3>  
                            <center><input type="text" id="barcodeInput" onkeypress="checkBarcode(event, this)"></center>
                            <center><p id="descriptionDisplay"></p></center>
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
                    <th>PO Line</th>
                </tr>
            </thead>
            <tbody id="tableBody">
                <%
                    DeliveryInvoice deliveryInvoice = (DeliveryInvoice) request.getAttribute("deliveryInvoice");
                    LinkedHashMap<String, DeliveryItem> items = deliveryInvoice.getItems();
                    int x = 1;
                    for (Map.Entry<String, DeliveryItem> deliveryItemEntry : items.entrySet()) {
                        DeliveryItem item = deliveryItemEntry.getValue();
                %>
                <tr>
                    <td><%=x%></td>
                    <td><%=item.getCode()%></td>
                    <td><%=item.getDescription()%></td>
                    <td><input class="sent" type="number" id="<%=item.getCode()%>_sent" value="<%=item.getQuantity()%>" readonly></td>
                    <td><input class="delivered" type="number" id="<%=item.getCode()%>_delivered" value="<%=item.getDeliveredQuantity()%>"></td>
                    <td><div id="<%=item.getCode()%>_colorDisplay">____</div></td>
                    <td class="po-line"><%=item.getBaseLine()%></td>
                </tr>
                <%
                        x++;
                    }
                %>
            </tbody>
        </table>
        <hr>
        ${tempoSaveButton}
        <hr><hr><hr><hr><hr><hr>
        ${saveButton}

        <!-- Bootstrap Modal -->
        <div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header" id="modalHeader">
                        <h5 class="modal-title" id="modalTitle">Modal title</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p id="modalMessage"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
                    </div>
                </div>
            </div>
        </div>

        <form id="form" action="#" method="POST">
            <input hidden type="text" name="invoiceId" value="${deliveryInvoice.invoiceId}">
            <input hidden type="text" name="supplier" value="${deliveryInvoice.supplier}">
            <input hidden type="text" name="invoiceNumber" value="${deliveryInvoice.number}">
            <input hidden type="text" id="deliveredItems" name="deliveredItems">
            <input hidden type="text" id="sentItems" name="sentItems">
            <input hidden type="text" id="baseLines" name="baseLines">
        </form>

    </center>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <script type="text/javascript">
                                // Item class to store product information
                                class Item {
                                    constructor(altercode, code, description) {
                                        this.altercode = altercode;
                                        this.code = code;
                                        this.description = description;
                                    }
                                }

                                // Initialize items array from JSTL
                                var items = new Array();
        <c:forEach items="${pet4UItemsRowByRow}" var="item">
                                var altercode = "${item.altercode}";
                                var code = "${item.code}";
                                var description = "${item.description}";
                                items[altercode] = new Item(altercode, code, description);
        </c:forEach>

                                // Main barcode checking function
                                function checkBarcode(event, input) {
                                    if (event.keyCode === 13) { // Enter key pressed
                                        var barcode = input.value.trim();
                                        console.log("Scanned barcode: " + barcode);

                                        var item = items[barcode];
                                        if (item == null) {
                                            handleUnknownBarcode(barcode);
                                        } else {
                                            handleKnownBarcode(item);
                                        }

                                        input.value = ""; // Clear the input field
                                        input.focus(); // Keep focus on input
                                    }
                                }

                                function handleUnknownBarcode(barcode) {
                                    playBeep();
                                    document.getElementById("descriptionDisplay").innerHTML =
                                            "Unknown Barcode: " + barcode;

                                    // Check if this unknown barcode already exists in the table
                                    let deliveredInput = document.getElementById(barcode + "_delivered");
                                    if (deliveredInput == null) {
                                        addRow(barcode, "UNKNOWN BARCODE: " + barcode, "-1");
                                    }

                                    // Increment delivered quantity
                                    deliveredInput = document.getElementById(barcode + "_delivered");
                                    deliveredInput.value = parseInt(deliveredInput.value) + 1;

                                    // Mark with yellow background
                                    let colorDisplay = document.getElementById(barcode + "_colorDisplay");
                                    if (colorDisplay) {
                                        colorDisplay.style.backgroundColor = 'yellow';
                                    }
                                }

                                function handleKnownBarcode(item) {
                                    document.getElementById("descriptionDisplay").innerHTML =
                                            item.altercode + " : " + item.description;

                                    // Check if item exists in table
                                    let sentInput = document.getElementById(item.code + "_sent");
                                    if (sentInput == null) {
                                        playBeep();
                                        addRow(item.code, item.description, "-1");
                                        sentInput = document.getElementById(item.code + "_sent");
                                    }

                                    // Increment delivered quantity
                                    let deliveredInput = document.getElementById(item.code + "_delivered");
                                    deliveredInput.value = parseInt(deliveredInput.value) + 1;

                                    // Update row color
                                    updateRowColor(item.code);
                                }

                                function addRow(code, description, baseLine) {
                                    let table = document.getElementById("tableBody");
                                    let row = document.createElement("tr");
                                    if (description.includes("UNKNOWN")) {
                                        row.classList.add("unknown-item");
                                    }

                                    row.innerHTML = `
                <td>----</td>
                <td>${code}</td>
                <td>${description}</td>
                <td><input class="sent" type="number" id="${code}_sent" value="0" readonly></td>
                <td><input class="delivered" type="number" id="${code}_delivered" value="0"></td>
                <td><div id="${code}_colorDisplay">____</div></td>
                <td class="po-line">${baseLine}</td>
            `;

                                    table.appendChild(row);
                                }

                                function updateRowColor(code) {
                                    let colorDisplay = document.getElementById(code + "_colorDisplay");
                                    let sent = parseInt(document.getElementById(code + "_sent").value) || 0;
                                    let delivered = parseInt(document.getElementById(code + "_delivered").value) || 0;
                                    let diff = sent - delivered;

                                    if (diff > 0) {
                                        colorDisplay.style.backgroundColor = 'red';
                                    } else if (diff < 0) {
                                        colorDisplay.style.backgroundColor = 'yellow';
                                    } else {
                                        colorDisplay.style.backgroundColor = 'green';
                                    }
                                }

                                function recheckAll() {
                                    let deliveredInputs = document.querySelectorAll(".delivered");
                                    deliveredInputs.forEach(input => {
                                        let code = input.id.replace("_delivered", "");
                                        updateRowColor(code);
                                    });
                                }

                                function playBeep() {
                                    // Simple beep sound
                                    let audioCtx = new (window.AudioContext || window.webkitAudioContext)();
                                    let oscillator = audioCtx.createOscillator();
                                    oscillator.type = "sine";
                                    oscillator.frequency.value = 800;
                                    oscillator.connect(audioCtx.destination);
                                    oscillator.start();
                                    oscillator.stop(audioCtx.currentTime + 0.1);
                                }

                                function collectSentData() {
                                    let result = "";
                                    document.querySelectorAll(".sent").forEach(input => {
                                        let code = input.id.replace("_sent", "");
                                        result += code + ":" + input.value + ",";
                                    });
                                    return result;
                                }

                                function collectDeliveredData() {
                                    let result = "";
                                    document.querySelectorAll(".delivered").forEach(input => {
                                        let code = input.id.replace("_delivered", "");
                                        result += code + ":" + input.value + ",";
                                    });
                                    return result;
                                }

                                function collectBaseLines() {
                                    let result = "";
                                    document.querySelectorAll("tbody tr").forEach(row => {
                                        let code = row.cells[1].textContent;
                                        let baseLine = row.cells[6].textContent;
                                        result += code + ":" + baseLine + ",";
                                    });
                                    return result;
                                }

                                function requestRouter(requestTarget) {
                                    document.getElementById("sentItems").value = collectSentData();
                                    document.getElementById("deliveredItems").value = collectDeliveredData();
                                    document.getElementById("baseLines").value = collectBaseLines();
                                    document.getElementById("form").action = requestTarget;
                                    document.getElementById("form").submit();
                                }

                                function saveAndUpload() {
                                    if (validateDelivery()) {
                                        showModal("Success!", "✅ Delivery is correct! You can proceed with the upload.", "green");
                                    } else {
                                        showModal("Error!", "⚠️ Sent and Delivered items do not match! Please check.", "red");
                                    }
                                }

                                function validateDelivery() {
                                    let allMatch = true;
                                    document.querySelectorAll(".sent").forEach(sentInput => {
                                        let code = sentInput.id.replace("_sent", "");
                                        let sent = parseInt(sentInput.value) || 0;
                                        let delivered = parseInt(document.getElementById(code + "_delivered").value) || 0;

                                        if (sent !== delivered) {
                                            console.log("Mismatch found: " + code + " → Sent: " + sent + ", Delivered: " + delivered);
                                            allMatch = false;
                                        }
                                    });
                                    return allMatch;
                                }

                                function showModal(title, message, color) {
                                    document.getElementById("modalTitle").innerHTML = title;
                                    document.getElementById("modalMessage").innerHTML = message;
                                    document.getElementById("modalHeader").style.backgroundColor = color;
                                    $('#confirmationModal').modal('show');
                                }

                                // Focus on barcode input when page loads
                                window.onload = function () {
                                    document.getElementById("barcodeInput").focus();
                                };
    </script>
</body>
</html>