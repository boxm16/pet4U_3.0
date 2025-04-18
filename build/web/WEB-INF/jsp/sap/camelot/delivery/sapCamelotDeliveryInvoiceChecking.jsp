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
                                // Define reusable classes
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
                                this.itemsInPackage = parseFloat(itemsInPackage);
                                }
                                }

                                const items = {};
        <c:forEach items="${pet4UItemsRowByRow}" var="item">
                                items["${item.altercode}"] = new Item("${item.altercode}", "${item.code}", "${item.description}");
        </c:forEach>

                                const altercodeContainers = {};
        <c:forEach items="${pet4UAllAltercodeContainers}" var="altercodeContainer">
                                altercodeContainers["${altercodeContainer.altercode}"] = new AltercodeContainer("${altercodeContainer.altercode}", "${altercodeContainer.packageBarcode}", "${altercodeContainer.itemsInPackage}");
        </c:forEach>

                                window.onload = () => {
                                document.querySelectorAll("tbody tr").forEach(row => {
                                const code = row.cells[1]?.textContent.trim();
                                if (code) updateRowColor(code);
                                });
                                document.querySelector("input[type='text']")?.focus();
                                };
                                function check(event, input) {
                                if (event.key !== "Enter") return;
                                const altercode = input.value;
                                const item = items[altercode];
                                const altercodeContainer = altercodeContainers[altercode];
                                if (!item) {
                                playBeep();
                                document.getElementById("descriptionDisplay").innerHTML = `${altercode} : UNKNOWN ALTERCODE : ${altercode}`;
                                    if (!document.getElementById(`${altercode}_sent`)) {
                                    addRow(altercode, `UNKNOWN ALTERCODE ${altercode}`);
                                    }

                                    const deliveredInput = document.getElementById(`${altercode}_delivered`);
                                    deliveredInput.value = parseInt(deliveredInput.value) + 1;
                                    updateRowColor(altercode);
                                    } else {
                                    document.getElementById("descriptionDisplay").innerHTML = `${altercode} : ${item.description}`;
                                        if (!document.getElementById(`${item.code}_sent`)) {
                                        playBeep();
                                        addRow(item.code, item.description);
                                        }

                                        const deliveredInput = document.getElementById(`${item.code}_delivered`);
                                        deliveredInput.value = parseFloat(deliveredInput.value) + (altercodeContainer?.packageBarcode === "true" ? altercodeContainer.itemsInPackage : 1);
                                        updateRowColor(item.code);
                                        }

                                        input.value = "";
                                        }

                                        function addRow(code, description) {
                                        const row = document.createElement("tr");
                                        row.id = `row_${code}`;
                                        row.innerHTML = `
        <td>----</td>
        <td><a href='itemAnalysis.htm?code=${code}' target='_blank'>${code}</a></td>
        <td>${description}</td>
        <td>1</td>
        <td><input class='deliveredPackages' type='number' id='${code}_sent' value='0'></td>
        <td><input class='delivered' type='number' id='${code}_delivered' value='0'></td>
        <td><input class='sent' type='number' id='${code}_sent' value='0' readonly></td>
        <td><span class='po-line'>-1</span></td>
    `;
                                        document.getElementById("tableBody").appendChild(row);
                                        updateRowColor(code);
                                        }

                                        function updateRowColor(code) {
                                        const sentEl = document.getElementById(`${code}_sent`);
                                        const deliveredEl = document.getElementById(`${code}_delivered`);
                                        if (!sentEl || !deliveredEl) return;
                                        const sent = parseFloat(sentEl.value) || 0;
                                        const delivered = parseFloat(deliveredEl.value) || 0;
                                        const diff = sent - delivered;
                                        deliveredEl.classList.remove('highlight-red', 'highlight-yellow', 'highlight-green');
                                        if (diff > 0) {
                                        deliveredEl.classList.add('highlight-red');
                                        } else if (diff < 0) {
                                        deliveredEl.classList.add('highlight-yellow');
                                        } else {
                                        deliveredEl.classList.add('highlight-green');
                                        }
                                        }

                                        function collectData(className, suffix) {
                                        return Array.from(document.querySelectorAll(`.${className}`))
                                                .map(el => `${el.id.replace(suffix, '')}:${el.value}`)
                                                            .join(',');
                                                    }

                                                    function collectSentData() {
                                                    return collectData("sent", "_sent");
                                                    }

                                                    function collectDeliveredData() {
                                                    return collectData("delivered", "_delivered");
                                                    }

                                                    function collectBaseLines() {
                                                    return Array.from(document.querySelectorAll("tbody tr"))
                                                            .map(row => `${row.cells[1].textContent.trim()}:${row.cells[7].textContent.trim()}`)
                                                                        .join(',');
                                                                }

                                                                function requestRouter(requestTarget) {
                                                                form.action = requestTarget;
                                                                document.getElementById("sentItems").value = collectSentData();
                                                                document.getElementById("deliveredItems").value = collectDeliveredData();
                                                                document.getElementById("baseLines").value = collectBaseLines();
                                                                form.submit();
                                                                }

                                                                function playBeep() {
                                                                const ctx = new (window.AudioContext || window.webkitAudioContext)();
                                                                const oscillator = ctx.createOscillator();
                                                                const gain = ctx.createGain();
                                                                oscillator.type = 'sine';
                                                                oscillator.frequency.setValueAtTime(1000, ctx.currentTime);
                                                                gain.gain.setValueAtTime(1, ctx.currentTime);
                                                                oscillator.connect(gain);
                                                                gain.connect(ctx.destination);
                                                                oscillator.start();
                                                                setTimeout(() => oscillator.stop(), 500);
                                                                }

                                                                function handlePackageEnter(event, input) {
                                                                if (event.key === "Enter") {
                                                                event.preventDefault();
                                                                const row = input.closest('tr');
                                                                const itemsPerPackage = parseFloat(row.cells[3].textContent) || 1;
                                                                const deliveredPackages = parseFloat(input.value) || 0;
                                                                const deliveredItems = deliveredPackages * itemsPerPackage;
                                                                const deliveredInput = row.querySelector('.delivered');
                                                                if (deliveredInput) {
                                                                deliveredInput.value = deliveredItems;
                                                                updateRowColor(deliveredInput.id.replace('_delivered', ''));
                                                                }

                                                                moveToNextInput(input, '.deliveredPackages');
                                                                }
                                                                }

                                                                function handleDeliveredEnter(event, input) {
                                                                if (event.key === "Enter") {
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
                                                                const itemsPerPackage = parseFloat(row.cells[3].textContent) || 1;
                                                                const deliveredItems = parseFloat(input.value) || 0;
                                                                const deliveredPackages = deliveredItems / itemsPerPackage;
                                                                if (Number.isInteger(deliveredPackages)) {
                                                                const packageInput = row.querySelector('.deliveredPackages');
                                                                packageInput.value = deliveredPackages;
                                                                }

                                                                updateRowColor(code);
                                                                moveToNextInput(input, '.delivered');
                                                                }

                                                                function moveToNextInput(current, selector) {
                                                                const inputs = Array.from(document.querySelectorAll(selector));
                                                                const idx = inputs.indexOf(current);
                                                                if (idx !== - 1 && idx < inputs.length - 1) {
                                                                const next = inputs[idx + 1];
                                                                next.focus();
                                                                next.select();
                                                                }
                                                                }

                                                                function hideLoadingOverlay() {
                                                                const overlay = document.getElementById('loading-overlay');
                                                                overlay.style.opacity = '0';
                                                                setTimeout(() => overlay.style.display = 'none', 500);
                                                                }

                                                                window.addEventListener('load', () => setTimeout(hideLoadingOverlay, 300));

    </script>
</body>
</html>