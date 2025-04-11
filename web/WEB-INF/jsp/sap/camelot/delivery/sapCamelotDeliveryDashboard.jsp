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
            .invoice-link { 
                color: #0066cc; 
                text-decoration: none; 
                cursor: pointer;
            }
            .invoice-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <h1>Due Purchase Orders by Supplier</h1>

        <c:if test="${not empty duePurchaseOrders}">
            <form id="invoiceForm" action="sapCamelotDeliveryInvoiceChecking.htm" method="POST">
                <input type="hidden" id="selectedInvoice" name="invoiceId" value="">

                <c:forEach var="entry" items="${duePurchaseOrders}">
                    <div class="supplier-header">
                        <h2>${entry.key}</h2>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Invoice Number</th>
                                <th>Document Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="invoice" items="${entry.value}">
                                <tr>
                                    <td>
                                        <a class="invoice-link" 
                                           onclick="document.getElementById('selectedInvoice').value = '${invoice.invoiceId}';
                                                   document.getElementById('invoiceForm').submit();">
                                            ${invoice.invoiceId}
                                        </a>
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

        <script>
            // Alternative JavaScript function if you prefer
            function submitInvoice(invoiceId) {
                document.getElementById('selectedInvoice').value = invoiceId;
                document.getElementById('invoiceForm').submit();
            }
        </script>
    </body>
</html>