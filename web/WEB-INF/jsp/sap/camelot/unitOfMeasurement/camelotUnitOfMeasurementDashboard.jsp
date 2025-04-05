<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Unit of Measurement Management</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .card {
                margin-bottom: 30px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }
            .card-header {
                background-color: #f8f9fa;
                font-weight: 600;
            }
            .table-responsive {
                margin-bottom: 0;
            }
            .inner-table {
                margin-bottom: 0;
            }
            .inner-table thead th {
                background-color: #e9ecef;
            }
            h2 {
                color: #343a40;
                margin-bottom: 20px;
                padding-bottom: 10px;
                border-bottom: 1px solid #eee;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid py-4">
            <div class="row">
                <div class="col-12">
                    <h2 class="text-center mb-4">
                        <i class="fas fa-ruler-combined me-2"></i>Unit of Measurement Management
                    </h2>
                </div>
            </div>
            
            <!-- Units of Measurement Card -->
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <span><i class="fas fa-list-alt me-2"></i>All Units of Measurement</span>
                            <span class="badge bg-primary">${allUnitsOfMeasurement.size()} items</span>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover table-bordered">
                                    <thead class="table-light">
                                        <tr>
                                            <th>#</th>
                                            <th>UOM Code</th>
                                            <th>UOM Name</th>
                                            <th>Base Quantity</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="unitOfMeasurementEntry" items="${allUnitsOfMeasurement}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td>${unitOfMeasurementEntry.value.uomCode}</td>
                                                <td>${unitOfMeasurementEntry.value.uomName}</td>
                                                <td>${unitOfMeasurementEntry.value.baseQuantity}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Unit of Measurement Groups Card -->
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <span><i class="fas fa-layer-group me-2"></i>Unit of Measurement Groups</span>
                            <span class="badge bg-primary">${allUnitOfMeasurementGroups.size()} groups</span>
                        </div>
                        <div class="card-body">
                            <div class="accordion" id="ugpAccordion">
                                <c:forEach var="entry" items="${allUnitOfMeasurementGroups}" varStatus="loop">
                                    <div class="accordion-item">
                                        <h2 class="accordion-header" id="heading${loop.index}">
                                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" 
                                                    data-bs-target="#collapse${loop.index}" aria-expanded="false" 
                                                    aria-controls="collapse${loop.index}">
                                                <span class="me-3 fw-bold">${entry.value.ugpCode}</span>
                                                <span class="text-muted">${entry.value.ugpName}</span>
                                            </button>
                                        </h2>
                                        <div id="collapse${loop.index}" class="accordion-collapse collapse" 
                                             aria-labelledby="heading${loop.index}" data-bs-parent="#ugpAccordion">
                                            <div class="accordion-body p-0">
                                                <div class="table-responsive">
                                                    <table class="table inner-table table-sm table-bordered mb-0">
                                                        <thead class="table-light">
                                                            <tr>
                                                                <th>#</th>
                                                                <th>UOM Code</th>
                                                                <th>UOM Name</th>
                                                                <th>Base Quantity</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="unitOfMeasurementEntry" items="${entry.value.unitOfMeasurements}" varStatus="innerLoop">
                                                                <tr>
                                                                    <td>${innerLoop.index + 1}</td>
                                                                    <td>${unitOfMeasurementEntry.value.uomCode}</td>
                                                                    <td>${unitOfMeasurementEntry.value.uomName}</td>
                                                                    <td>${unitOfMeasurementEntry.value.baseQuantity}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>