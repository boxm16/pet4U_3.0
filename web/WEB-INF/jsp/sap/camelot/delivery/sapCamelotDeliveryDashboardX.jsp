<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Purchase Orders and Goods Receipts</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }

            .container {
                display: flex;
                gap: 20px;
            }

            .column {
                flex: 1;
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 15px;
                background-color: #f9f9f9;
                overflow: auto;
            }

            table {
                border-collapse: collapse;
                width: 100%;
                margin-top: 10px;
            }

            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
                position: sticky;
                top: 0;
            }

            .section-header {
                background-color: #e6e6e6;
                padding: 10px;
                margin: -15px -15px 15px -15px;
                border-radius: 5px 5px 0 0;
            }

            .invoice-btn {
                background-color: #52d83a;
                border: none;
                color: white;
                padding: 6px 12px;
                text-align: center;
                font-size: 26px;
                margin: 2px 1px;
                cursor: pointer;
                border-radius: 4px;
            }

            .goods-receipt-btn {
                background-color: #4285f4;
            }

            .tempo-btn {
                background-color: #f5a623;
            }

            .no-data {
                text-align: center;
                padding: 20px;
                color: #666;
            }

            .supplier-cell {
                font-size: 30px;
                font-weight: bold;
                color: #333;
            }

            /* Highlight canceled GRs */
            .canceled-row {
                background-color: #ffe6e6; /* light red */
                color: #a10000;           /* dark red */
                font-weight: bold;
            }
        </style>
    </head>
    <body>
    <center>
        <h1>Purchase Orders and Goods Receipts</h1>
        <a href="createPurchaseOrder.htm?supplierCode=ΠΡΟ-000115"><h3>Create Purchase Order For LAVIOSA</h3></a>
        <a href="createPurchaseOrder.htm?supplierCode=ΠΡΟ-000062"><h3>Create Purchase Order For CILESIZ</h3></a>

    </center>

    <div class="container">
        <!-- Left Column - Due Purchase Orders -->
        <div class="column">
            <div class="section-header">
                <h2>Due Purchase Orders</h2>
            </div>

            <c:if test="${not empty duePurchaseOrders}">
                <table>
                    <thead>
                        <tr>
                            <th>Supplier</th>
                            <th>PO Number</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="po" items="${duePurchaseOrders}">
                            <tr>
                                <td class="supplier-cell">${po.supplier}</td>
                                <td>
                                    <button class="invoice-btn"
                                            style="background-color:${po.status eq 'Partially Delivered' ? '#AECA20' : ''}"
                                            onclick="window.open('sapCamelotDeliveryInvoiceChecking.htm?invoiceId=${po.invoiceId}', '_blank')">
                                        ${po.number}  ${po.status}
                                    </button>

                                </td>
                                <td>${po.insertionDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty duePurchaseOrders}">
                <div class="no-data">No due purchase orders found</div>
            </c:if>
        </div>

        <!-- Middle Column - SAP Tempo Deliveries -->
        <div class="column">
            <div class="section-header">
                <h2>Open SAP Tempo Deliveries</h2>
            </div>

            <c:if test="${not empty tempos}">
                <table>
                    <thead>
                        <tr>
                            <th>Supplier</th>
                            <th>Delivery No</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tempo" items="${tempos}">
                            <tr>
                                <td class="supplier-cell">${tempo.supplier}</td>
                                <td>
                                    <button class="invoice-btn tempo-btn"
                                            onclick="window.open('sapCamelotTempoDeliveryInvoiceChecking.htm?invoiceId=${tempo.invoiceId}', '_blank')">
                                        ${tempo.number}
                                    </button>
                                </td>
                                <td>${tempo.insertionDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty tempos}">
                <div class="no-data">No SAP tempo deliveries</div>
            </c:if>
        </div>

        <!-- Right Column - Today's Goods Receipts -->
        <div class="column">
            <div class="section-header">
                <h2>Today's Goods Receipts (<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd"/>)</h2>
            </div>

            <c:if test="${not empty todaysGoodsReceipts}">
                <table>
                    <thead>
                        <tr>
                            <th>Supplier</th>
                            <th>GR Number</th>
                            <th>PO Ref</th>
                            <th>Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="gr" items="${todaysGoodsReceipts}">
                            <tr>
                                <td class="supplier-cell">${gr.supplier}</td>
                                <td>
                                    <button class="invoice-btn goods-receipt-btn"
                                            style="background-color:${gr.status eq 'CANCELED' ? 'red' : ''}"
                                            onclick="window.open('showGoodsReceipt.htm?invoiceId=${gr.invoiceId}', '_blank')">
                                        ${gr.number}
                                    </button>
                                </td>
                                <td>
                                    <button class="invoice-btn goods-receipt-btn"
                                            onclick="window.open('showGoodsReceipt.htm?invoiceId=${gr.invoiceId}', '_blank')">
                                        ${gr.referencedPO}
                                    </button>
                                </td>
                                <td>${gr.insertionDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty todaysGoodsReceipts}">
                <div class="no-data">No goods receipts today</div>
            </c:if>
        </div>
    </div>
</body>
</html>
