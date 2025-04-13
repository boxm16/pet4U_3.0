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
            .supplier-header {
                margin-top: 20px;
                padding: 8px;
                background-color: #f0f0f0;
                border-radius: 4px;
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
                    <c:set var="currentSupplier" value="" />
                    <c:forEach var="po" items="${duePurchaseOrders}">
                        <c:if test="${currentSupplier ne po.supplier}">
                            <c:set var="currentSupplier" value="${po.supplier}" />
                            <h3 class="supplier-header">${currentSupplier}</h3>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Select</th>
                                        <th>PO Number</th>
                                        <th>Date</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    </c:if>
                                    <tr>
                                        <td>
                                            <input type="checkbox" 
                                                   name="selectedPOs" 
                                                   value="${po.invoiceId}">
                                        </td>
                                        <td>
                                            <button type="button" class="invoice-btn"
                                                    onclick="window.open('sapCamelotDeliveryInvoiceChecking.htm?invoiceId=${po.invoiceId}', '_blank')">
                                                ${po.number}
                                            </button>
                                        </td>
                                        <td>${po.insertionDate}</td>
                                        <td>${po.status}</td>
                                    </tr>
                                <c:if test="${empty duePurchaseOrders[status.index + 1] or duePurchaseOrders[status.index + 1].supplier ne currentSupplier}">
                                    </tbody>
                            </table>
                        </c:if>
                    </c:forEach>
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
                    <c:set var="currentSupplier" value="" />
                    <c:forEach var="gr" items="${todaysGoodsReceipts}">
                        <c:if test="${currentSupplier ne gr.supplier}">
                            <c:set var="currentSupplier" value="${gr.supplier}" />
                            <h3 class="supplier-header">${currentSupplier}</h3>
                            <table>
                                <thead>
                                    <tr>
                                        <th>GR Number</th>
                                        <th>PO Reference</th>
                                        <th>Items</th>
                                        <th>Time</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    </c:if>
                                    <tr>
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
                                <c:if test="${empty todaysGoodsReceipts[status.index + 1] or todaysGoodsReceipts[status.index + 1].supplier ne currentSupplier}">
                                    </tbody>
                            </table>
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${empty todaysGoodsReceipts}">
                    <div class="no-data">No goods receipts today</div>
                </c:if>
            </div>
        </div>
    </body>
</html>