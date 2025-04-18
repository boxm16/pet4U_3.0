<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Map, java.util.LinkedHashMap, Delivery.DeliveryItem, Delivery.DeliveryInvoice" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Delivery Invoice Checking</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" />
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            table, th, td { border: 1px solid; border-collapse: collapse; }
            th, td { padding: 8px; font-size: 18px; }
            th { background-color: #f2f2f2; position: sticky; top: 0; font-size: 22px; }
            .po-line { font-weight: bold; color: #333; }
            input[type="number"] {
                width: 100%; text-align: center; font-size: 18px; font-weight: bold;
                background-color: inherit; border: none;
            }
            input[type="number"][readonly] {
                font-weight: normal; background-color: #f9f9f9; border: 1px solid #ddd;
            }
            tr.highlight-red { background-color: #ffcccc; }
            tr.highlight-yellow { background-color: #fffab3; }
            tr.highlight-green { background-color: #ccffcc; }
        </style>
    </head>
    <body>
    <center>
        <h2>Delivery Invoice Checking</h2>
        <p>
            Date: ${deliveryInvoice.insertionDate} |
            Supplier: ${deliveryInvoice.supplier} |
            Invoice #: ${deliveryInvoice.number}
        </p>
        <hr/>

        <input type="text" id="barcodeInput" onkeypress="checkBarcode(event)" placeholder="Scan barcode..." class="form-control w-50 mb-3" />
        <p id="descriptionDisplay"></p>

        <table id="deliveryTable" class="table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Sent</th>
                    <th>Delivered</th>
                    <th>Alert</th>
                    <th>PO Line</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int x = 1;
                    DeliveryInvoice deliveryInvoice = (DeliveryInvoice) request.getAttribute("deliveryInvoice");
                    LinkedHashMap<String, DeliveryItem> items = deliveryInvoice.getItems();
                    for (Map.Entry<String, DeliveryItem> entry : items.entrySet()) {
                        DeliveryItem item = entry.getValue();
                %>
                <tr id="row-<%=item.getCode()%>">
                    <td><%=x++%></td>
                    <td><%=item.getCode()%></td>
                    <td><%=item.getDescription()%></td>
                    <td><input type="number" id="<%=item.getCode()%>_sent" value="<%=item.getQuantity()%>" readonly class="sent"/></td>
                    <td><input type="number" id="<%=item.getCode()%>_delivered" value="<%=item.getDeliveredQuantity()%>" class="delivered" onchange="updateRowColor('<%=item.getCode()%>')"/></td>
                    <td><div id="<%=item.getCode()%>_colorDisplay">____</div></td>
                    <td class="po-line"><%=item.getBaseLine()%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <form id="form" method="POST" action="#">
            <input type="hidden" name="invoiceId" value="${deliveryInvoice.invoiceId}">
            <input type="hidden" name="supplier" value="${deliveryInvoice.supplier}">
            <input type="hidden" name="invoiceNumber" value="${deliveryInvoice.number}">
            <input type="hidden" id="deliveredItems" name="deliveredItems">
            <input type="hidden" id="sentItems" name="sentItems">
            <input type="hidden" id="baseLines" name="baseLines">
        </form>
    </center>

    <script>
        const items = {};
        <c:forEach items="${pet4UItemsRowByRow}" var="item">
    items["${item.altercode}"] = {
        code: "${item.code}",
        description: "${item.description}"
        };
        </c:forEach>

        function checkBarcode(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                const input = document.getElementById("barcodeInput");
                const altercode = input.value.trim();
                const item = items[altercode];

                if (!item) {
                    const existing = document.getElementById(altercode + "_sent");
                    if (!existing) {
                        addRow(altercode, "Unknown item: " + altercode);
                        document.getElementById("descriptionDisplay").textContent = "Unknown barcode: " + altercode;
                    } else {
                        document.getElementById(altercode + "_delivered").value++;
                    }
                } else {
                    const code = item.code;
                    document.getElementById("descriptionDisplay").textContent = `${altercode}: ${item.description}`;
                                    const delivered = document.getElementById(code + "_delivered");
                                    delivered.value = parseInt(delivered.value || 0) + 1;
                                    updateRowColor(code);
                                }
                                input.value = "";
                            }
                        }

                        function addRow(code, description) {
                            const tbody = document.querySelector("#deliveryTable tbody");
                            const row = document.createElement("tr");
                            row.id = `row-${code}`;
                            row.innerHTML = `
                <td>---</td>
                <td>${code}</td>
                <td>${description}</td>
                <td><input type="number" id="${code}_sent" value="0" readonly class="sent"/></td>
                <td><input type="number" id="${code}_delivered" value="1" class="delivered" onchange="updateRowColor('${code}')"/></td>
                <td><div id="${code}_colorDisplay">____</div></td>
                <td class="po-line">0</td>
            `;
                            tbody.appendChild(row);
                            updateRowColor(code);
                        }

                        function updateRowColor(code) {
                            const sent = parseFloat(document.getElementById(code + "_sent").value || 0);
                            const delivered = parseFloat(document.getElementById(code + "_delivered").value || 0);
                            const row = document.getElementById("row-" + code);

                            row.classList.remove("highlight-red", "highlight-yellow", "highlight-green");

                            if (delivered < sent) {
                                row.classList.add("highlight-red");
                            } else if (delivered > sent) {
                                row.classList.add("highlight-yellow");
                            } else {
                                row.classList.add("highlight-green");
                            }
                        }
    </script>
</body>
</html>
