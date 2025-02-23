<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Delivery.DeliveryItem"%>
<%@page import="Delivery.DeliveryInvoice"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>ENDO Checking</title>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">

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
        </style>
    </head>
    <body>
    <center>
        <h1>ΕΛΕΓΧΟΣ ΠΑΡΑΛΑΒΗΣ ΕΝΔΟΔΙΑΚΙΝΙΣΗΣ</h1>
        <hr>
        <button onclick="recheckAll()" class="btn-lg btn-warning">ReCheck All Items</button>
        <hr>

        <!-- Input Field for Scanning -->
        <h3>
            <input type="text" id="scanInput" class="form-control text-center" onkeypress="check(event, this)" placeholder="Scan barcode...">
            <p id="descriptionDisplay"></p>
        </h3>

        <!-- Table for Delivery Items -->
        <table class="table">
            <thead>
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
                    for (Map.Entry<String, DeliveryItem> entry : items.entrySet()) {
                        DeliveryItem item = entry.getValue();
                %>
                <tr>
                    <td><%= x %></td>
                    <td>
                        <a href="showDeltiaApostolisOfItem.htm?itemCode=<%= item.getCode() %>" target="_blank">
                            <%= item.getCode() %>
                        </a>
                    </td>
                    <td><%= item.getDescription() %></td>
                    <td><input class="sent form-control" type="number" id="<%= item.getCode() %>@sent" value="<%= item.getSentQuantity() %>" readonly></td>
                    <td><input class="delivered form-control" type="number" id="<%= item.getCode() %>@delivered" value="<%= item.getDeliveredQuantity() %>"></td>
                    <td><div id="<%= item.getCode() %>@colorDisplay">____</div></td>
                    <td><button class="btn btn-danger btn-sm" onclick="removeRow(this)">Remove</button></td>
                </tr>
                <%
                        x++;
                    }
                %>
            </tbody>
        </table>

        <hr>
        ${saveButton}
        <hr>
        ${uploadButton}

        <!-- Hidden Form for Submission -->
        <form id="form" action="#" method="POST">
            <input type="hidden" name="invoiceNumber" value="${deliveryInvoice.getNumber()}">
            <input type="hidden" id="deliveredItems" name="deliveredItems">
            <input type="hidden" id="sentItems" name="sentItems">
        </form>
    </center>

    <!-- Bootstrap Modal for Confirmation -->
    <div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header" id="modalHeader">
                    <h5 class="modal-title" id="modalTitle">Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
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

    <!-- Required JavaScript -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"></script>

    <script>
                        function check(event, input) {
                            if (event.keyCode === 13) {
                                let altercode = input.value.trim();
                                console.log("Scanned altercode:", altercode);
                                input.value = "";
                            }
                        }

                        function deliveryOk() {
                            let sentItems = document.querySelectorAll(".sent");
                            let deliveredItems = document.querySelectorAll(".delivered");

                            for (let i = 0; i < sentItems.length; i++) {
                                let sent = parseInt(sentItems[i].value) || 0;
                                let delivered = parseInt(deliveredItems[i].value) || 0;
                                if (sent !== delivered) {
                                    console.log(`Mismatch: ${sentItems[i].id} → Sent: ${sent}, Delivered: ${delivered}`);
                                    return false;
                                }
                            }
                            console.log("✅ All sent items match delivered items!");
                            return true;
                        }

                        function saveAndUpload() {
                            if (deliveryOk()) {
                                showModal("✅ Delivery is correct! You can proceed with the upload.", "Success!", "green");
                            } else {
                                showModal("⚠️ Sent and Delivered items do not match! Please check.", "Error!", "red");
                            }
                        }

                        function showModal(message, title, color) {
                            document.getElementById("modalMessage").innerHTML = message;
                            document.getElementById("modalTitle").innerHTML = title;
                            document.getElementById("modalHeader").style.backgroundColor = color;
                            $('#confirmationModal').modal('show');
                        }

                        function removeRow(button) {
                            let row = button.parentNode.parentNode;
                            row.parentNode.removeChild(row);
                        }

                        function recheckAll() {
                            document.querySelectorAll(".delivered").forEach(item => {
                                let itemCode = item.id.split("@")[0];
                                let sent = parseInt(document.getElementById(itemCode + "@sent").value) || 0;
                                let delivered = parseInt(item.value) || 0;
                                let colorDisplay = document.getElementById(itemCode + "@colorDisplay");

                                if (sent > delivered)
                                    colorDisplay.style.backgroundColor = 'red';
                                else if (sent < delivered)
                                    colorDisplay.style.backgroundColor = 'yellow';
                                else
                                    colorDisplay.style.backgroundColor = 'green';
                            });
                        }
    </script>
</body>
</html>
