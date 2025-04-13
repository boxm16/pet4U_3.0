<%-- 
    Document   : goodsReceiptView
    Created on : [Current Date]
    Author     : [Your Name]
    Purpose    : Display-only view of Goods Receipt (GRPO) information
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
                <c:if test="${not empty deliveryInvoice.poNumber}">
                    <div class="mt-2">
                        <h5><span class="badge badge-dark">PO #: ${deliveryInvoice.poNumber}</span></h5>
                    </div>
                </c:if>
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
                            <th>PO Line</th>
                            <th>GRPO Line</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${deliveryInvoice.items}" var="itemEntry" varStatus="loop">
                            <%
                                Map.Entry<String, DeliveryItem> itemEntry = (Map.Entry<String, DeliveryItem>) pageContext.getAttribute("itemEntry");
                                DeliveryItem item = itemEntry.getValue();
                                int orderedQty = Integer.parseInt(item.getQuantity());
                                int receivedQty = item.getDeliveredQuantity();
                                String rowClass = "";

                                if (orderedQty == receivedQty) {
                                    rowClass = "match";
                                } else if (orderedQty > receivedQty) {
                                    rowClass = "short";
                                } else {
                                    rowClass = "over";
                                }
                            %>
                            <tr class="<%= rowClass%>">
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
                                <td>${item.baseLine}</td>
                                <td>${item.grpoLineNum}</td>
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
    </body>
</html>