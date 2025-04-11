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
        </style>
    </head>
    <body>
        <h1>Due Purchase Orders by Supplier</h1>

        <c:if test="${not empty duePurchaseOrders}">
            <c:forEach var="entry" items="${duePurchaseOrders}">
                <div class="supplier-header">
                    <h2>Supplier: ${entry.key}</h2>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>PO Number</th>
                            <th>Supplier Name</th>
                            <th>Document Date</th>
                            <th>Status</th>
                            <th>Warehouse</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="invoice" items="${entry.value}">
                            <tr>
                                <td>${invoice.invoiceId}</td>
                                <td>${invoice.supplier}</td>
                                <td>${invoice.docDate}</td>
                                <td>${invoice.docStatus}</td>
                                <td>${invoice.whsCode}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>

        <c:if test="${empty duePurchaseOrders}">
            <p>No due purchase orders found.</p>
        </c:if>
    </body>
</html>