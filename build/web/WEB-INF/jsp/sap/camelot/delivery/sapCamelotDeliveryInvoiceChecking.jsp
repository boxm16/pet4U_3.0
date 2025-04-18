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
// Data structures
                                const appData = {
                                items: {},
                                        altercodeContainers: {},
                                        elements: {
                                        tableBody: document.getElementById("tableBody"),
                                                descriptionDisplay: document.getElementById("descriptionDisplay"),
                                                barcodeInput: document.querySelector("input[type='text']"),
                                                form: document.getElementById("form")
                                        }
                                };
// Initialize data from JSP
        <c:forEach items="${pet4UItemsRowByRow}" var="item">
                                appData.items["${item.altercode}"] = {
                                code: "${item.code}",
                                        description: "${item.description}"
                                        };
        </c:forEach>

        <c:forEach items="${pet4UAllAltercodeContainers}" var="altercodeContainer">
                                appData.altercodeContainers["${altercodeContainer.altercode}"] = {
                                packageBarcode: "${altercodeContainer.packageBarcode}" === "true",
                                        itemsInPackage: parseInt("${altercodeContainer.itemsInPackage}") || 1
                                        };
        </c:forEach>

// Core functions
                                function updateRowColor(code) {
                                const deliveredEl = document.getElementById(`${code}_delivered`);
                                if (!deliveredEl) return;
                                const sent = parseFloat(document.getElementById(`${code}_sent`).value) || 0;
                                const delivered = parseFloat(deliveredEl.value) || 0;
                                const diff = sent - delivered;
                                // Remove all highlight classes first
                                deliveredEl.classList.remove('highlight-red', 'highlight-yellow', 'highlight-green');
                                // Add appropriate class
                                if (diff > 0) {
                                deliveredEl.classList.add('highlight-red');
                                } else if (diff < 0) {
                                deliveredEl.classList.add('highlight-yellow');
                                } else {
                                deliveredEl.classList.add('highlight-green');
                                }
                                }

                                function addRow(code, description) {
                                const rowHTML = `
        <tr id="row_${code}">
            <td>----</td>
            <td><a href="itemAnalysis.htm?code=${code}" target="_blank">${code}</a></td>
            <td style="padding-left: 5px;">${description}</td>
            <td>1</td>
            <td><input class="deliveredPackages" type="number" id="${code}_deliveredPackages" value="0"></td>
            <td><input class="delivered" type="number" id="${code}_delivered" value="0"></td>
            <td><input class="sent" type="number" id="${code}_sent" value="0" readonly></td>
            <td class="po-line">-1</td>
        </tr>
    `;
                                appData.elements.tableBody.insertAdjacentHTML('beforeend', rowHTML);
                                updateRowColor(code);
                                }

// Delivery calculation functions
                                function updateDeliveryValues(code, increment = 1) {
                                const deliveredEl = document.getElementById(`${code}_delivered`);
                                if (!deliveredEl) return false;
                                deliveredEl.value = (parseFloat(deliveredEl.value) || 0) + increment;
                                updateRowColor(code);
                                return true;
                                }

                                function handleQuantityInput(input, isPackageField) {
                                const row = input.closest('tr');
                                const code = input.id.replace(isPackageField ? '_deliveredPackages' : '_delivered', '');
                                const itemsInPackage = parseFloat(row.cells[3].textContent) || 1;
                                if (isPackageField) {
                                // Package field changed - update items
                                document.getElementById(`${code}_delivered`).value = (parseFloat(input.value) || 0) * itemsInPackage;
                                } else {
                                // Items field changed - update packages if whole number
                                const packages = (parseFloat(input.value) || 0) / itemsInPackage;
                                if (Number.isInteger(packages)) {
                                document.getElementById(`${code}_deliveredPackages`).value = packages;
                                }
                                }

                                updateRowColor(code);
                                moveToNextField(input, isPackageField);
                                }

// Navigation functions
                                function moveToNextField(currentInput, isPackageField) {
                                const inputs = document.querySelectorAll(isPackageField ? '.deliveredPackages' : '.delivered');
                                for (let i = 0; i < inputs.length; i++) {
                                if (inputs[i] === currentInput && i < inputs.length - 1) {
                                inputs[i + 1].focus();
                                inputs[i + 1].select();
                                break;
                                }
                                }
                                }

// Event handlers
                                function handleBarcodeInput(event) {
                                if (event.keyCode !== 13) return;
                                const altercode = event.target.value;
                                const item = appData.items[altercode];
                                const container = appData.altercodeContainers[altercode];
                                if (!item) {
                                playBeep();
                                if (!document.getElementById(`${altercode}_delivered`)) {
                                appData.elements.descriptionDisplay.textContent = `${altercode} : UNKNOWN ALTERCODE`;
                                addRow(altercode, "UNKNOWN ALTERCODE");
                                }
                                updateDeliveryValues(altercode);
                                } else {
                                appData.elements.descriptionDisplay.textContent = `${altercode} : ${item.description}`;
                                    const increment = container?.packageBarcode ? container.itemsInPackage : 1;
                                    if (!updateDeliveryValues(item.code, increment)) {
                                    playBeep();
                                    addRow(item.code, item.description);
                                    updateDeliveryValues(item.code, increment);
                                    }
                                    }

                                    event.target.value = "";
                                    event.preventDefault();
                                    }

// Form handling
                                    function collectFormData() {
                                    const data = {
                                    sentItems: "",
                                            deliveredItems: "",
                                            baseLines: ""
                                    };
                                    document.querySelectorAll(".sent").forEach(el => {
                                    data.sentItems += `${el.id.replace("_sent", "")}:${el.value},`;
                                    });
                                    document.querySelectorAll(".delivered").forEach(el => {
                                    data.deliveredItems += `${el.id.replace("_delivered", "")}:${el.value},`;
                                    });
                                    document.querySelectorAll("tbody tr").forEach(row => {
                                    data.baseLines += `${row.cells[1].textContent.trim()}:${row.cells[7].textContent.trim()},`;
                                    });
                                    return data;
                                    }

                                    function requestRouter(requestTarget) {
                                    const formData = collectFormData();
                                    document.getElementById("sentItems").value = formData.sentItems;
                                    document.getElementById("deliveredItems").value = formData.deliveredItems;
                                    document.getElementById("baseLines").value = formData.baseLines;
                                    appData.elements.form.action = requestTarget;
                                    appData.elements.form.submit();
                                    }

// Utility functions
                                    function playBeep() {
                                    const audioCtx = new (window.AudioContext || window.webkitAudioContext)();
                                    const oscillator = audioCtx.createOscillator();
                                    const gainNode = audioCtx.createGain();
                                    oscillator.type = "sine";
                                    oscillator.frequency.setValueAtTime(1000, audioCtx.currentTime);
                                    gainNode.gain.setValueAtTime(1, audioCtx.currentTime);
                                    oscillator.connect(gainNode);
                                    gainNode.connect(audioCtx.destination);
                                    oscillator.start();
                                    setTimeout(() => oscillator.stop(), 500);
                                    }

                                    function hideLoadingOverlay() {
                                    const overlay = document.getElementById('loading-overlay');
                                    overlay.style.opacity = '0';
                                    setTimeout(() => overlay.style.display = 'none', 500);
                                    }

// Initialization
                                    function initializePage() {
                                    // Set up event listeners
                                    appData.elements.barcodeInput.addEventListener('keypress', handleBarcodeInput);
                                    document.addEventListener('keypress', function(e) {
                                    if (e.target.classList.contains('deliveredPackages')) {
                                    handleQuantityInput(e.target, true);
                                    } else if (e.target.classList.contains('delivered')) {
                                    handleQuantityInput(e.target, false);
                                    }
                                    });
                                    document.addEventListener('blur', function(e) {
                                    if (e.target.classList.contains('delivered')) {
                                    handleQuantityInput(e.target, false);
                                    }
                                    }, true);
                                    // Validate existing rows
                                    document.querySelectorAll("tbody tr").forEach(row => {
                                    const code = row.cells[1].textContent.trim();
                                    if (code) updateRowColor(code);
                                    });
                                    // Focus barcode input
                                    appData.elements.barcodeInput?.focus();
                                    }

// Start the application
                                    window.addEventListener('load', function() {
                                    initializePage();
                                    setTimeout(hideLoadingOverlay, 300);
                                    });
    </script>
</body>
</html>