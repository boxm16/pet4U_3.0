<%-- 
    Document   : deliveryInvoiceChecking
    Created on : Jun 25, 2023, 6:30:28 PM
    Author     : Michail Sitmalidis
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="BasicModel.AltercodeContainer"%>
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
            input[type="number"] {
                text-align: center;
                width: 100%;
                background-color: inherit;
                font-size: 22px;
                font-weight: bold;
                padding: 8px 4px;
            }
            input[type="number"][readonly] {
                font-weight: normal;
                cursor: default;
                border: 1px solid #ced4da;
            }
            input[type="number"]:focus {
                box-shadow: 0 0 0 2px rgba(0,123,255,.25);
                outline: none;
            }
            input[type="number"]::-webkit-inner-spin-button, 
            input[type="number"]::-webkit-outer-spin-button { 
                opacity: 1;
                height: 30px;
            }



            /* Add these new styles */
            .delivered.highlight-red {
                background-color: #ff4d4d !important;
                color: #000;
            }
            .delivered.highlight-yellow {
                background-color: #ffff33 !important;
                color: #000;
            }
            .delivered.highlight-green {
                background-color: #33ff33 !important;
                color: #000;
            }

            td:nth-child(4) {
                text-align: center;
            }
            tr.highlight-red {
                background-color: #ff6666;
            }
            tr.highlight-red input {
                background-color: #ff4d4d;
                color: #000;
            }
            tr.highlight-yellow {
                background-color: #ffff66;
            }
            tr.highlight-yellow input {
                background-color: #ffff33;
                color: #000;
            }
            tr.highlight-green {
                background-color: #66ff66;
            }
            tr.highlight-green input {
                background-color: #33ff33;
                color: #000;
            }

            input.delivered {
                transition: background-color 0.3s ease;
            }

            /* Loading animation styles */
            #loading-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(255, 255, 255, 0.9);
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                z-index: 9999;
                transition: opacity 0.5s ease-out;
            }

            .loading-spinner {
                width: 50px;
                height: 50px;
                border: 5px solid #f3f3f3;
                border-top: 5px solid #3498db;
                border-radius: 50%;
                animation: spin 1s linear infinite;
                margin-bottom: 20px;
            }

            .loading-text {
                font-size: 24px;
                color: #333;
                font-weight: bold;
            }

            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
        </style>
    </head>
    <body>
        <div id="loading-overlay">
            <div class="loading-spinner"></div>
            <div class="loading-text">Loading Delivery Invoice...</div>
        </div>
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

        <table>
            <thead>
                <tr>
                    <th colspan="8">
                        <h3>  
                            <center><input type="text" onkeypress="check(event, this)"></center>
                            <center><p id="descriptionDisplay"></p></center>
                        </h3>
                    </th>
                </tr>

                <tr>
                    <th>A/A</th>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Items In<br>Package</th> 
                    <th>Delivered<br>Packages</th>
                    <th>Delivered<br>Items</th>
                    <th>Sent<br>Items</th>
                    <th>PO Line</th>
                </tr>
            </thead>
            <tbody id="tableBody">
                <%

                    int x = 1;
                    DeliveryInvoice deliveryInvoice = (DeliveryInvoice) request.getAttribute("deliveryInvoice");
                    LinkedHashMap<String, DeliveryItem> items = deliveryInvoice.getItems();
                    for (Map.Entry<String, DeliveryItem> deliveryItemEntry : items.entrySet()) {
                        DeliveryItem item = deliveryItemEntry.getValue();
                        Double itemsInPackage = item.getPackQuantity(); // Default value
                        if (itemsInPackage == 0.0) {
                            itemsInPackage = 1.0;
                        }
                        out.println("<tr id='row_" + item.getCode() + "'>");

                        out.println("<td>");
                        out.println(x);
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<a href='itemAnalysis.htm?code=" + item.getCode() + "' target='_blank'>" + item.getCode() + "</a>");
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("<a>" + item.getDescription() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(itemsInPackage);
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input class='deliveredPackages' type='number' id='" + item.getCode() + "_deliveredPackages' value='0' onkeypress='handlePackageEnter(event, this)'>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input class='delivered' type='number' id='" + item.getCode() + "_delivered' value='" + item.getDeliveredQuantity() + "' onkeypress='handleDeliveredEnter(event, this)' onblur='handleDeliveredBlur(this)'>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input class='sent' type='number' id='" + item.getCode() + "_sent' value='" + item.getQuantity() + "' readonly>");
                        out.println("</td>");

                        out.println("<td class='po-line'>");
                        out.println(item.getBaseLine());
                        out.println("</td>");

                        out.println("</tr>");
                        x++;
                    }
                %>
            </tbody>
        </table>
        <hr>
        ${tempoSaveButton}
        <hr><hr><hr><hr><hr><hr>
        ${saveButton}
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
                                window.onload = function () {
                                    // Existing row validation
                                    const rows = document.querySelectorAll("tbody tr");
                                    rows.forEach(row => {
                                        const code = row.cells[1].textContent.trim();
                                        if (code)
                                            updateRowColor(code);
                                    });

                                    // Auto-focus barcode input (if needed)
                                    document.querySelector("input[type='text']")?.focus();
                                };

                                function check(event, input) {
                                    if (event.keyCode === 13) {
                                        var altercode = input.value;
                                        console.log("altercode:" + altercode);
                                        var item = items[altercode];
                                        if (item == null) {
                                            playBeep();
                                            if (altercodeContainer != null) {
                                                console.log("Something Wrong, Item is null, but barocede is not" + altercode);
                                            }

                                            let unknownBarcodeSent = document.getElementById(altercode + "_sent");
                                            let unknownBarcodeDelivered = document.getElementById(altercode + "_delivered");

                                            if (unknownBarcodeSent == null) {
                                                document.getElementById("descriptionDisplay").innerHTML = altercode + " : UNKNOWN ALTERCODE : " + altercode;
                                                addRow(altercode, "UNKNOWN ALTERCODE " + altercode);
                                                let unknownBarcodeDelivered = document.getElementById(altercode + "_delivered");
                                                let v = unknownBarcodeDelivered.value;
                                                v++;
                                                unknownBarcodeDelivered.value = v;

                                            } else {
                                                let v = unknownBarcodeDelivered.value;
                                                v++;
                                                unknownBarcodeDelivered.value = v;
                                            }
                                            updateRowColor(altercode);
                                        } else {
                                            var code = item.code;
                                            console.log(code);
                                            var description = item.description;
                                            document.getElementById("descriptionDisplay").innerHTML = altercode + " : " + description;

                                            if (altercodeContainer == null) {
                                                console.log("Something Wrong, while Item is not null, barcode is null" + altercode);
                                            }

                                            let sent = document.getElementById(code + "_sent");
                                            if (sent == null) {
                                                playBeep();
                                                addRow(item.code, item.description);
                                            } else {
                                                sent = sent.value * 1;
                                            }

                                            let delivered = document.getElementById(code + "_delivered").value * 1;

                                            if (altercodeContainer.packageBarcode == "true") {
                                                delivered += altercodeContainer.itemsInPackage * 1;
                                            } else {
                                                delivered++;
                                            }

                                            document.getElementById(code + "_delivered").value = delivered;
                                            //--------------------
                                            // Get items per package (default to 1 if not specified)
                                            const itemsInPackage = parseFloat(row.cells[3].textContent) || 1;
                                            const deliveredItems = parseFloat(input.value) || 0;

                                            // Calculate complete packages (only counts full boxes)
                                            const deliveredPackages = Math.floor(deliveredItems / itemsInPackage);

                                            // Update packages field
                                            const packagesField = row.querySelector('.deliveredPackages');
                                            packagesField.value = deliveredPackages;
                                            //--------------------
                                            updateRowColor(code);
                                        }
                                        input.value = "";
                                    }
                                }

                                function addRow(code, description) {
                                    let table = document.getElementById("tableBody");
                                    let row = document.createElement("tr");
                                    row.id = "row_" + code;

                                    let c1 = document.createElement("td");
                                    let c2 = document.createElement("td");
                                    let c3 = document.createElement("td");
                                    let c4 = document.createElement("td");
                                    let c5 = document.createElement("td");
                                    let c6 = document.createElement("td");
                                    let c7 = document.createElement("td");
                                    let c8 = document.createElement("td");

                                    c1.innerText = "----";
                                    c2.innerHTML = "<a href='itemAnalysis.htm?code=" + code + "' target='_blank'>" + code + "</a>";
                                    c3.innerText = description;
                                    c4.innerText = "1";
                                    c5.innerHTML = "<input class='deliveredPackages' type='number' id='" + code + "_sent' value='0'>";
                                    c6.innerHTML = "<input class='delivered' type='number' id='" + code + "_delivered' value='0'>";
                                    c7.innerHTML = "<input class='sent' type='number' id='" + code + "_sent' value='0' readonly>";
                                    c8.innerHTML = "<span class='po-line'>-1</span>";

                                    row.appendChild(c1);
                                    row.appendChild(c2);
                                    row.appendChild(c3);
                                    row.appendChild(c4);
                                    row.appendChild(c5);
                                    row.appendChild(c6);
                                    row.appendChild(c7);
                                    row.appendChild(c8);
                                    table.appendChild(row);

                                    updateRowColor(code);
                                }

                                function updateRowColor(code) {
                                    const deliveredEl = document.getElementById(code + "_delivered");
                                    if (!deliveredEl)
                                        return; // safety check

                                    const sent = parseFloat(document.getElementById(code + "_sent").value) || 0;
                                    const delivered = parseFloat(deliveredEl.value) || 0;
                                    const diff = sent - delivered;

                                    // Remove all highlight classes first
                                    deliveredEl.classList.remove('highlight-red', 'highlight-yellow', 'highlight-green');

                                    // Add appropriate class based on comparison
                                    if (diff > 0) {
                                        deliveredEl.classList.add('highlight-red');
                                    } else if (diff < 0) {
                                        deliveredEl.classList.add('highlight-yellow');
                                    } else {
                                        deliveredEl.classList.add('highlight-green');
                                    }
                                }


                                function collectSentData() {
                                    var returnValue = "";
                                    var sentItems = document.querySelectorAll(".sent");
                                    for (x = 0; x < sentItems.length; x++) {
                                        let code = sentItems[x].id.replace("_sent", "");
                                        returnValue += code + ":" + sentItems[x].value + ",";
                                    }
                                    return returnValue;
                                }

                                function collectDeliveredData() {
                                    var returnValue = "";
                                    var deliveredItems = document.querySelectorAll(".delivered");
                                    for (x = 0; x < deliveredItems.length; x++) {
                                        let code = deliveredItems[x].id.replace("_delivered", "");
                                        returnValue += code + ":" + deliveredItems[x].value + ",";
                                    }
                                    return returnValue;
                                }

                                function collectBaseLines() {
                                    var returnValue = "";
                                    var rows = document.querySelectorAll("tbody tr");
                                    for (var x = 0; x < rows.length; x++) {
                                        var code = rows[x].cells[1].textContent.trim();
                                        var baseLine = rows[x].cells[7].textContent.trim();
                                        returnValue += code + ":" + baseLine + ",";
                                    }
                                    return returnValue;
                                }

                                function requestRouter(requestTarget) {
                                    form.action = requestTarget;
                                    document.getElementById("sentItems").value = collectSentData();
                                    document.getElementById("deliveredItems").value = collectDeliveredData();
                                    document.getElementById("baseLines").value = collectBaseLines();
                                    form.submit();
                                }

                                function playBeep() {
                                    let audioCtx = new (window.AudioContext || window.webkitAudioContext)();
                                    let oscillator = audioCtx.createOscillator();
                                    let gainNode = audioCtx.createGain();
                                    oscillator.type = "sine";
                                    oscillator.frequency.setValueAtTime(1000, audioCtx.currentTime);
                                    gainNode.gain.setValueAtTime(1, audioCtx.currentTime);
                                    oscillator.connect(gainNode);
                                    gainNode.connect(audioCtx.destination);
                                    oscillator.start();
                                    setTimeout(() => {
                                        oscillator.stop();
                                    }, 500);
                                }

                                function handlePackageEnter(event, input) {
                                    if (event.keyCode === 13) {
                                        event.preventDefault();

                                        const row = input.closest('tr');
                                        const itemsInPackage = parseFloat(row.cells[3].textContent) || 1;
                                        const deliveredPackages = parseFloat(input.value) || 0;
                                        const deliveredItems = itemsInPackage * deliveredPackages;

                                        const deliveredField = row.querySelector('.delivered');
                                        if (deliveredField) {
                                            deliveredField.value = deliveredItems;
                                            const code = deliveredField.id.replace('_delivered', '');
                                            updateRowColor(code);
                                        }

                                        moveToNextInput(input);
                                    }
                                }

                                function moveToNextInput(currentInput) {
                                    const inputs = document.querySelectorAll('.deliveredPackages');
                                    let nextInput = null;

                                    for (let i = 0; i < inputs.length; i++) {
                                        if (inputs[i] === currentInput) {
                                            if (i < inputs.length - 1) {
                                                nextInput = inputs[i + 1];
                                            }
                                            break;
                                        }
                                    }

                                    if (nextInput) {
                                        nextInput.focus();
                                        nextInput.select();
                                    }
                                }
                                function handleDeliveredEnter(event, input) {
                                    if (event.keyCode === 13) {
                                        event.preventDefault();
                                        updateRowFromDelivered(input);
                                    }
                                }

                                function handleDeliveredBlur(input) {
                                    updateRowFromDelivered(input);
                                }

                                function updateRowFromDelivered(input) {
                                    const row = input.closest('tr');
                                    const code = input.id.replace('_delivered', '');

                                    // Get items per package (default to 1 if not specified)
                                    const itemsInPackage = parseFloat(row.cells[3].textContent) || 1;
                                    const deliveredItems = parseFloat(input.value) || 0;

                                    // Calculate complete packages (only counts full boxes)
                                    const deliveredPackages = Math.floor(deliveredItems / itemsInPackage);

                                    // Update packages field
                                    const packagesField = row.querySelector('.deliveredPackages');
                                    packagesField.value = deliveredPackages;

                                    updateRowColor(code);
                                    moveToNextDeliveredInput(input);
                                }

                                function moveToNextDeliveredInput(currentInput) {
                                    const inputs = document.querySelectorAll('.delivered');
                                    let nextInput = null;

                                    for (let i = 0; i < inputs.length; i++) {
                                        if (inputs[i] === currentInput && i < inputs.length - 1) {
                                            nextInput = inputs[i + 1];
                                            break;
                                        }
                                    }

                                    if (nextInput) {
                                        nextInput.focus();
                                        nextInput.select();
                                    }
                                }
                                function hideLoadingOverlay() {
                                    const overlay = document.getElementById('loading-overlay');
                                    overlay.style.opacity = '0';
                                    setTimeout(() => {
                                        overlay.style.display = 'none';
                                    }, 500); // Match this with the CSS transition duration
                                }

                                // When everything is fully loaded
                                window.addEventListener('load', function () {
                                    // Add a small delay to ensure everything is really ready
                                    setTimeout(hideLoadingOverlay, 300);
                                });
    </script>
</body>
</html>