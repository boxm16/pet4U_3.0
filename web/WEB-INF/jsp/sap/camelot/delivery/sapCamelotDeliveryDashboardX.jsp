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
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 2px 1px;
                cursor: pointer;
                border-radius: 4px;
            }
            .goods-receipt-btn {
                background-color: #4285f4;
            }
            .no-data {
                text-align: center;
                padding: 20px;
                color: #666;
            }
            .supplier-cell {
                font-weight: bold;
                color: #333;
            }
        </style>
    </head>
    <body>
        <h1>Purchase Orders and Goods Receipts</h1>
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
                                        <button type="button" class="invoice-btn"
                                                onclick="window.open('sapCamelotDeliveryInvoiceChecking.htm?invoiceId=${po.invoiceId}', '_blank')">
                                            ${po.number}
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
                                <th>PO Reference</th>
                                <th>Items</th>
                                <th>Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="gr" items="${todaysGoodsReceipts}">
                                <tr>
                                    <td class="supplier-cell">${gr.supplier}</td>
                                    <td>
                                        <button type="button" class="invoice-btn goods-receipt-btn"
                                                onclick="window.open('showGoodsReceipt.htm?invoiceId=${gr.invoiceId}', '_blank')">
                                            ${gr.number}
                                        </button>
                                    </td>
                                    <td>
                                        <c:if test="${not empty gr.baseEntry}">
                                            PO: ${gr.baseEntry}
                                        </c:if>
                                    </td>
                                    <td>${gr.items.size()} items</td>
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