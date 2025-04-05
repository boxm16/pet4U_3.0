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
            body {
                font-size: 1.05rem; /* Increased base font size */
            }
            .card {
                margin-bottom: 30px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }
            .card-header {
                background-color: #f8f9fa;
                font-weight: 600;
                font-size: 1.1rem; /* Larger card headers */
            }
            .table-responsive {
                margin-bottom: 0;
            }
            .table {
                font-size: 1rem; /* Slightly larger table text */
            }
            .inner-table {
                margin-bottom: 0;
                background-color: #f8f9fa;
                font-size: 0.95rem; /* Slightly smaller for nested tables */
            }
            .inner-table thead th {
                background-color: #e9ecef;
            }
            h2 {
                color: #343a40;
                margin-bottom: 20px;
                padding-bottom: 10px;
                border-bottom: 1px solid #eee;
                font-size: 1.8rem; /* Larger main heading */
            }
            .group-row {
                border-left: 4px solid #0d6efd;
            }
            .group-header {
                background-color: #e7f1ff !important;
                font-weight: 600;
            }
            .settings-btn {
                font-size: 0.9rem;
                padding: 0.25rem 0.5rem;
            }
            .action-col {
                width: 120px; /* Fixed width for action column */
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
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="unitOfMeasurementEntry" items="${allUnitsOfMeasurement}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td>${unitOfMeasurementEntry.value.uomCode}</td>
                                                <td>${unitOfMeasurementEntry.value.uomName}</td>
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
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-bordered mb-0">
                                    <thead class="table-light">
                                        <tr>
                                            <th>#</th>
                                            <th>UGP Code</th>
                                            <th>UGP Name</th>
                                            <th>Unit of Measurements</th>
                                            <th class="action-col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="entry" items="${allUnitOfMeasurementGroups}" varStatus="loop">
                                            <tr class="group-row">
                                                <td class="group-header">${loop.index + 1}</td>
                                                <td class="group-header">${entry.value.ugpCode}</td>
                                                <td class="group-header">${entry.value.ugpName}</td>
                                                <td class="p-0">
                                                    <div class="table-responsive">
                                                        <table class="table inner-table table-sm mb-0">
                                                            <thead>
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
                                                </td>
                                                <td class="group-header text-center">
                                                    <a href="/settings/unit-group/${entry.key}" 
                                                       class="btn btn-sm btn-outline-primary settings-btn"
                                                       title="Group Settings">
                                                        <i class="fas fa-cog"></i> Settings
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
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