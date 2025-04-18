<%-- 
    Optimized Delivery Invoice Checking Page
    Maintains all original functionality with performance improvements
--%>
<%@page import="java.util.Map"%>
<%@page import="Delivery.DeliveryItem"%>
<%@page import="Delivery.DeliveryInvoice"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Delivery Invoice Checking</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" crossorigin="anonymous">
    <style>
        table, th, td { border: 1px solid; border-collapse: collapse; }
        td { font-size: 20px; }
        th { 
            font-size: 30px; font-weight: bold; text-align: center; 
            background: #eee; position: sticky; top: 0px; 
        }
        .po-line { font-weight: bold; color: #333; }
        input[type="number"] { 
            text-align: center; width: 100%; background-color: inherit; 
            font-size: 22px; font-weight: bold; padding: 8px 4px; 
        }
        input[type="number"][readonly] { 
            font-weight: normal; cursor: default; border: 1px solid #ced4da; 
        }
        input[type="number"]:focus { 
            box-shadow: 0 0 0 2px rgba(0,123,255,.25); outline: none; 
        }
        tr.highlight-red { background-color: #ff6666; }
        tr.highlight-red input { background-color: #ff4d4d; color: #000; }
        tr.highlight-yellow { background-color: #ffff66; }
        tr.highlight-yellow input { background-color: #ffff33; color: #000; }
        tr.highlight-green { background-color: #66ff66; }
        tr.highlight-green input { background-color: #33ff33; color: #000; }
        tr.highlight-red, tr.highlight-yellow, tr.highlight-green { color: #000; }
        input.delivered { transition: background-color 0.3s ease; }
    </style>
</head>
<body>
<center>
    <h1>Delivery Checking</h1>
    <h3>
        Ημερομηνία Παραστατικού: ${deliveryInvoice.insertionDate} &nbsp;&nbsp;&nbsp; 
        Προμηθευτής: ${deliveryInvoice.supplier} &nbsp;&nbsp;&nbsp; 
        Αριθμός Παραστατικού: ${deliveryInvoice.number}
    </h3>
    <hr>

    <table>
        <thead>
            <tr>
                <th colspan="8">
                    <h3>  
                        <center><input type="text" id="barcodeInput"></center>
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
                DeliveryInvoice deliveryInvoice = (DeliveryInvoice) request.getAttribute("deliveryInvoice");
                LinkedHashMap<String, DeliveryItem> items = deliveryInvoice.getItems();
                int rowIndex = 1;
                for (Map.Entry<String, DeliveryItem> entry : items.entrySet()) {
                    DeliveryItem item = entry.getValue();
                    Double itemsInPackage = item.getPackQuantity() == 0.0 ? 1.0 : item.getPackQuantity();
            %>
            <tr id="row_<%= item.getCode() %>">
                <td><%= rowIndex++ %></td>
                <td><a href="itemAnalysis.htm?code=<%= item.getCode() %>" target="_blank"><%= item.getCode() %></a></td>
                <td style="padding-left: 5px;"><%= item.getDescription() %></td>
                <td><%= itemsInPackage %></td>
                <td><input class="deliveredPackages" type="number" id="<%= item.getCode() %>_deliveredPackages" value="0"></td>
                <td><input class="delivered" type="number" id="<%= item.getCode() %>_delivered" value="<%= item.getDeliveredQuantity() %>"></td>
                <td><input class="sent" type="number" id="<%= item.getCode() %>_sent" value="<%= item.getQuantity() %>" readonly></td>
                <td class="po-line"><%= item.getBaseLine() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
    
    <hr>
    ${tempoSaveButton}
    <hr><hr><hr><hr><hr><hr>
    ${saveButton}
    
    <form id="form" action="#" method="POST">
        <input type="hidden" name="invoiceId" value="${deliveryInvoice.invoiceId}">
        <input type="hidden" name="supplier" value="${deliveryInvoice.supplier}">
        <input type="hidden" name="invoiceNumber" value="${deliveryInvoice.number}">
        <input type="hidden" id="deliveredItems" name="deliveredItems">
        <input type="hidden" id="sentItems" name="sentItems">
        <input type="hidden" id="baseLines" name="baseLines">
    </form>
</center>

<!-- Deferred JavaScript loading -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" defer></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" defer></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" defer></script>

<script>
// Cache DOM elements
const elements = {
    tableBody: document.getElementById("tableBody"),
    descriptionDisplay: document.getElementById("descriptionDisplay"),
    barcodeInput: document.getElementById("barcodeInput"),
    form: document.getElementById("form")
};

// Data structures
const data = {
    items: {},
    altercodeContainers: {}
};

// Initialize data from JSP
<c:forEach items="${pet4UItemsRowByRow}" var="item">
data.items["${item.altercode}"] = {
    code: "${item.code}",
    description: "${item.description}"
};
</c:forEach>

<c:forEach items="${pet4UAllAltercodeContainers}" var="altercodeContainer">
data.altercodeContainers["${altercodeContainer.altercode}"] = {
    packageBarcode: "${altercodeContainer.packageBarcode}" === "true",
    itemsInPackage: parseInt("${altercodeContainer.itemsInPackage}") || 1
};
</c:forEach>

// Core functions
function updateRowColor(code) {
    const row = document.getElementById(`row_${code}`);
    if (!row) return;
    
    const sent = parseFloat(document.getElementById(`${code}_sent`).value) || 0;
    const delivered = parseFloat(document.getElementById(`${code}_delivered`).value) || 0;
    const diff = sent - delivered;
    
    row.className = row.className.replace(/(^|\s)highlight-\S+/g, '');
    
    if (diff > 0) row.classList.add('highlight-red');
    else if (diff < 0) row.classList.add('highlight-yellow');
    else row.classList.add('highlight-green');
}

function addRow(code, description) {
    const rowHTML = `
        <tr id="row_${code}">
            <td>----</td>
            <td><a href="itemAnalysis.htm?code=${code}" target="_blank">${code}</a></td>
            <td style="padding-left: 5px;">${description}</td>
            <td>1</td>
            <td><input class="deliveredPackages" type="number" id="${code}_sent" value="0"></td>
            <td><input class="delivered" type="number" id="${code}_delivered" value="0"></td>
            <td><input class="sent" type="number" id="${code}_sent" value="0" readonly></td>
            <td class="po-line">-1</td>
        </tr>
    `;
    
    elements.tableBody.insertAdjacentHTML('beforeend', rowHTML);
    updateRowColor(code);
}

// Event handlers
function handleBarcodeInput(event) {
    if (event.keyCode !== 13) return;
    
    const altercode = event.target.value;
    const item = data.items[altercode];
    const container = data.altercodeContainers[altercode];
    
    if (!item) {
        playBeep();
        const deliveredField = document.getElementById(`${altercode}_delivered`);
        
        if (!deliveredField) {
            elements.descriptionDisplay.textContent = `${altercode} : UNKNOWN ALTERCODE : ${altercode}`;
            addRow(altercode, `UNKNOWN ALTERCODE ${altercode}`);
            document.getElementById(`${altercode}_delivered`).value++;
        } else {
            deliveredField.value++;
        }
        updateRowColor(altercode);
    } else {
        elements.descriptionDisplay.textContent = `${altercode} : ${item.description}`;
        const deliveredField = document.getElementById(`${item.code}_delivered`);
        
        if (!deliveredField) {
            playBeep();
            addRow(item.code, item.description);
        }
        
        const increment = container?.packageBarcode ? 
            (container.itemsInPackage || 1) : 1;
        
        document.getElementById(`${item.code}_delivered`).value = 
            (parseFloat(document.getElementById(`${item.code}_delivered`).value) || 0) + increment;
        updateRowColor(item.code);
    }
    
    event.target.value = "";
    event.preventDefault();
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

// Initialize the page
document.addEventListener('DOMContentLoaded', function() {
    // Focus barcode input immediately
    elements.barcodeInput.focus();
    
    // Set up event listeners
    elements.barcodeInput.addEventListener('keypress', handleBarcodeInput);
    
    // Defer initial validation to avoid blocking render
    setTimeout(() => {
        const rows = elements.tableBody.querySelectorAll("tr");
        rows.forEach(row => {
            const code = row.cells[1].textContent.trim();
            if (code) updateRowColor(code);
        });
    }, 50);
});
</script>
</body>
</html>