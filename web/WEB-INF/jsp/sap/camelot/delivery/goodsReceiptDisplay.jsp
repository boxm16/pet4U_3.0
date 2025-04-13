<%-- 
    Document   : goodsReceiptView
    Created on : [Current Date]
    Author     : [Your Name]
    Purpose    : Display-only view of Goods Receipt (GRPO) information with item lookup
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Delivery.DeliveryItem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Goods Receipt View</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <style>
            .grpo-header {
                background-color: #f8f9fa;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 5px;
            }
            .table th {
                position: sticky;
                top: 0;
                background-color: #e9ecef;
            }
            .match {
                background-color: #d4edda;
            }
            .short {
                background-color: #fff3cd;
            }
            .over {
                background-color: #f8d7da;
            }
            #descriptionDisplay {
                display: none;
                margin-top: 10px;
            }
            .search-container {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid mt-3">
            <div class="grpo-header text-center">
                <h1>Goods Receipt (GRPO)</h1>
                <div class="row">
                    <div class="col-md-4">
                        <h5><span class="badge badge-secondary">GRPO Date: ${deliveryInvoice.insertionDate}</span></h5>
                    </div>
                    <div class="col-md-4">
                        <h5><span class="badge badge-info">Vendor: ${deliveryInvoice.supplier}</span></h5>
                    </div>
                    <div class="col-md-4">
                        <h5><span class="badge badge-primary">GRPO #: ${deliveryInvoice.number}</span></h5>
                    </div>
                </div>
              
            </div>

            <div class="search-container text-center">
                <div class="input-group" style="max-width: 500px; margin: 0 auto;">
                    <input type="text" class="form-control" id="itemLookup" placeholder="Scan item code or barcode..." onkeypress="handleItemLookup(event)">
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button" onclick="clearLookup()">
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
                </div>
                <div id="descriptionDisplay" class="alert alert-info mt-2"></div>
            </div>

            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="thead-light">
                        <tr>
                            <th>#</th>
                            <th>Item Code</th>
                            <th>Description</th>
                            <th>Ordered Qty</th>
                            <th>Received Qty</th>
                            <th>Status</th>

                        </tr>
                    </thead>
                    <tbody>

                        <c:forEach items="${deliveryInvoice.items}" var="itemEntry" varStatus="loop">
                            <%
                                Map.Entry<String, DeliveryItem> itemEntry = (Map.Entry<String, DeliveryItem>) pageContext.getAttribute("itemEntry");

                            %>

                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${itemEntry.key}</td>
                                <td>${item.description}</td>
                                <td>${item.quantity}</td>
                                <td>${item.deliveredQuantity}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.quantity == item.deliveredQuantity}">
                                            <span class="badge badge-success">Complete</span>
                                        </c:when>
                                        <c:when test="${item.quantity > item.deliveredQuantity}">
                                            <span class="badge badge-warning">Short</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">Over</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="text-center mt-3">
                <a href="javascript:window.print()" class="btn btn-primary mr-2">
                    <i class="fas fa-print"></i> Print
                </a>
                <a href="#" onclick="window.history.back()" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Back
                </a>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"></script>
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

        <script type="text/javascript">
                    // Item data from JSTL
                    var items = {
            <c:forEach items="${deliveryInvoice.items}" var="itemEntry">
                        "${itemEntry.key}": {
                            code: "${itemEntry.key}",
                                    description: "${itemEntry.value.description}",
                            quantity: "${itemEntry.value.quantity}",
                                    deliveredQuantity: "${itemEntry.value.deliveredQuantity}",
                            baseLine: "${itemEntry.value.baseLine}",
                                    grpoLineNum: "${itemEntry.value.grpoLineNum}"
                        },
            </c:forEach>
                    };

                    // Additional items from pet4UItemsRowByRow if needed
            <c:forEach items="${pet4UItemsRowByRow}" var="item">
                    items["${item.altercode}"] = items["${item.altercode}"] || {
                        code: "${item.code}",
                        description: "${item.description}",
                        quantity: "0",
                        deliveredQuantity: "0",
                        baseLine: "0",
                        grpoLineNum: "0"
                    };
            </c:forEach>

                    function handleItemLookup(event) {
                        if (event.keyCode === 13) { // Enter key
                            var lookupValue = document.getElementById("itemLookup").value.trim();
                            var display = document.getElementById("descriptionDisplay");

                            if (items[lookupValue]) {
                                var item = items[lookupValue];
                                display.innerHTML = "<strong>" + item.code + "</strong>: " + item.description +
                                        "<br>Ordered: " + item.quantity +
                                        ", Received: " + item.deliveredQuantity;
                                display.className = "alert alert-success mt-2";
                                display.style.display = "block";

                                // Highlight the row
                                var row = document.getElementById("row_" + item.code);
                                if (row) {
                                    row.style.backgroundColor = "#ffff99";
                                    setTimeout(function () {
                                        row.style.backgroundColor = "";
                                    }, 2000);
                                    row.scrollIntoView({behavior: "smooth", block: "center"});
                                }
                            } else {
                                display.innerHTML = "Item not found: " + lookupValue;
                                display.className = "alert alert-danger mt-2";
                                display.style.display = "block";
                            }

                            // Clear the input after a delay
                            setTimeout(function () {
                                document.getElementById("itemLookup").value = "";
                            }, 100);
                        }
                    }

                    function clearLookup() {
                        document.getElementById("itemLookup").value = "";
                        document.getElementById("descriptionDisplay").style.display = "none";
                    }
        </script>
    </body>
</html>