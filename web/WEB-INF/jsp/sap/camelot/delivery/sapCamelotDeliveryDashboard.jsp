<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Due Purchase Orders</title>
        <style>
            table { border-collapse: collapse; width: 100%; }
            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
            th { background-color: #f2f2f2; }
            .supplier-header { background-color: #e6e6e6; margin-top: 20px; }
            .invoice-btn {
                background-color: #4CAF50;
                border: none;
                color: white;
                padding: 6px 12px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 14px;
                margin: 2px 1px;
                cursor: pointer;
                border-radius: 4px;
            }
            .invoice-btn:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <h1>Due Purchase Orders by Supplier</h1>

        <c:if test="${not empty duePurchaseOrders}">
            <form id="invoiceForm" method="post">
                <c:forEach var="entry" items="${duePurchaseOrders}">
                    <div class="supplier-header">
                        <h2>${entry.key}</h2>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Select</th>
                                <th>Document Id</th>
                                <th>Document Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="invoice" items="${entry.value}">
                                <tr>
                                    <td>
                                        <input type="checkbox" 
                                               name="selectedInvoices" 
                                               value="${invoice.invoiceId}" 
                                               id="invoice_${invoice.invoiceId}">
                                        <label for="invoice_${invoice.invoiceId}"></label>
                                    </td>
                                    <td>
                                        <button type="button"
                                                onclick="window.location.href = 'sapCamelotDeliveryInvoiceChecking?invoiceId=${invoice.invoiceId}'">
                                            ${invoice.invoiceId}
                                        </button>
                                    </td>
                                    <td>${invoice.insertionDate}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:forEach>
            </form>
        </c:if>

        <c:if test="${empty duePurchaseOrders}">
            <p>No due purchase orders found.</p>
        </c:if>
    </body>
</html>