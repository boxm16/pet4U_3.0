<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Update Camelot Item</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            /* Base Styles */
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
            }
            .container {
                max-width: 1200px;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 0 15px rgba(0,0,0,0.1);
                padding: 30px;
                margin-top: 20px;
                margin-bottom: 40px;
            }
            h1, h2, h3, h4, h5 {
                color: #2c3e50;
            }

            /* Form Styles */
            .form-control-lg {
                height: calc(2.5em + 1rem + 2px);
                padding: 0.5rem 1rem;
                font-size: 1.25rem;
                line-height: 1.5;
                border-radius: 0.5rem;
                border: 2px solid #ced4da;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .form-control-lg:focus {
                border-color: #007bff;
                box-shadow: 0 0 8px rgba(0, 123, 255, 0.25);
            }
            .form-label-lg {
                font-size: 1.25rem;
                font-weight: 500;
                margin-bottom: 0.5rem;
            }
            .readonly-field {
                background-color: #e9ecef;
                cursor: not-allowed;
            }

            /* UoM Group Styles */
            .uom-group-card {
                border: 1px solid #dee2e6;
                border-radius: 8px;
                margin-bottom: 20px;
            }
            .uom-group-header {
                background-color: #f8f9fa;
                padding: 15px;
                border-bottom: 1px solid #dee2e6;
            }
            .uom-group-body {
                padding: 20px;
            }

            /* Barcode Management Styles */
            .barcode-table {
                width: 100%;
                margin-bottom: 20px;
            }
            .barcode-table th {
                background-color: #f8f9fa;
                padding: 12px;
                text-align: left;
            }
            .barcode-table td {
                padding: 12px;
                vertical-align: middle;
                border-top: 1px solid #dee2e6;
            }
            .pallet-row {
                background-color: #e8f5e9;
                border-left: 4px solid #28a745;
            }
            .barcode-badge {
                display: inline-flex;
                align-items: center;
                background-color: #e2f0fd;
                color: #004085;
                padding: 5px 10px;
                border-radius: 20px;
                margin-right: 5px;
                margin-bottom: 5px;
            }
            .barcode-remove-btn {
                color: #dc3545;
                background: none;
                border: none;
                padding: 0 5px;
                margin-left: 5px;
            }
            .barcode-remove-btn:hover {
                color: #b02a37;
            }

            /* Action Buttons */
            .btn-action {
                padding: 8px 15px;
                font-size: 0.9rem;
                border-radius: 4px;
                display: inline-flex;
                align-items: center;
            }
            .btn-action i {
                margin-right: 5px;
            }
            .btn-pallet {
                background-color: #28a745;
                color: white;
            }
            .btn-pallet:hover {
                background-color: #218838;
                color: white;
            }

            /* Alert Messages */
            .alert-message {
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 4px;
                font-size: 1.1rem;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="text-center mb-4">Update SAP Camelot Item</h1>

            <!-- Alert Message -->
            <c:if test="${not empty message}">
                <div class="alert-message" style="background-color:${alertColor}">
                    ${message}
                </div>
            </c:if>

            <!-- Main Item Form -->
            <form:form action="updateSapCamelotItem.htm" method="POST" modelAttribute="item" class="needs-validation" novalidate>
                <div class="card mb-4">
                    <div class="card-header bg-primary text-white">
                        <h3 class="mb-0">Item Information</h3>
                    </div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <!-- Item Code -->
                            <div class="col-md-4">
                                <label for="code" class="form-label form-label-lg">Item Code</label>
                                <form:input path="code" class="form-control form-control-lg readonly-field" id="code" readonly="true" />
                            </div>

                            <!-- Items Group -->
                            <div class="col-md-4">
                                <label for="itemsGroupCode" class="form-label form-label-lg">Items Group</label>
                                <form:select path="itemsGroupCode" class="form-select form-select-lg" id="itemsGroupCode" required="true">
                                    <form:option value="" label="-- Select Items Group --" disabled="true" />
                                    <form:options items="${itemGroups}" />
                                </form:select>
                                <div class="invalid-feedback">
                                    Please select an items group.
                                </div>
                            </div>
                        </div>

                        <!-- Description -->
                        <div class="mb-3">
                            <label for="description" class="form-label form-label-lg">Description</label>
                            <form:input path="description" class="form-control form-control-lg" id="description" required="true" />
                            <div class="invalid-feedback">
                                Please provide a description.
                            </div>
                        </div>

                        <!-- Main Barcode -->
                        <div class="mb-3">
                            <label for="mainBarcode" class="form-label form-label-lg">Main Barcode</label>
                            <form:input path="mainBarcode" class="form-control form-control-lg" id="mainBarcode" />
                        </div>

                        <!-- Submit Button -->
                        <div class="text-end">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-save me-2"></i>Update Item
                            </button>
                        </div>
                    </div>
                </div>
            </form:form>

            <!-- UoM Group Management Section -->
            <div class="card mb-4">
                <div class="card-header bg-primary text-white">
                    <h3 class="mb-0">Unit of Measurement Group</h3>
                </div>

                <!-- Current UoM Group -->
                <div id="currentUomGroupCard" class="${empty item.unitOfMeasurementGroup.ugpEntry ? 'd-none' : ''}">
                    <div class="card-body">
                        <h4 class="mb-4">
                            Current Group: 
                            <span class="text-primary">${item.unitOfMeasurementGroup.ugpName}</span>
                            <span class="badge bg-secondary ms-2">${item.unitOfMeasurementGroup.ugpCode}</span>
                        </h4>

                        <!-- UoM Barcode Management Table -->
                        <div class="table-responsive">
                            <table class="barcode-table">
                                <thead>
                                    <tr>
                                        <th>UoM</th>
                                        <th>Base Quantity</th>
                                        <th>Assigned Barcodes</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${item.unitOfMeasurementGroup.unitOfMeasurements}" var="uom">
                                        <tr class="${uom.value.baseQuantity == 120 ? 'pallet-row' : ''}">
                                            <td>
                                                <strong>${uom.value.uomCode}</strong>
                                                <div class="text-muted">${uom.value.uomName}</div>
                                            </td>
                                            <td>
                                                <span class="badge ${uom.value.baseQuantity == 120 ? 'bg-success' : 'bg-info'}">
                                                    ${uom.value.baseQuantity}
                                                </span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty uom.value.barcodes}">
                                                        <div class="d-flex flex-wrap">
                                                            <c:forEach items="${uom.value.barcodes}" var="barcode">
                                                                <span class="barcode-badge">
                                                                    ${barcode.value}
                                                                    <form method="POST" action="removeUomBarcode.htm" class="d-inline">
                                                                        <input type="hidden" name="itemCode" value="${item.code}">
                                                                        <input type="hidden" name="uomEntry" value="${uom.value.uomEntry}">
                                                                        <input type="hidden" name="barcode" value="${barcode.value}">
                                                                        <button type="submit" class="barcode-remove-btn">
                                                                            <i class="fas fa-times"></i>
                                                                        </button>
                                                                    </form>
                                                                </span>
                                                            </c:forEach>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">No barcodes assigned</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <!-- Add Barcode Form -->
                                                <form method="POST" action="addUomBarcode.htm" class="d-flex gap-2">
                                                    <input type="hidden" name="itemCode" value="${item.code}">
                                                    <input type="hidden" name="uomEntry" value="${uom.value.uomEntry}">
                                                    <input type="text" name="barcode" class="form-control form-control-sm" 
                                                           placeholder="New barcode" required style="width: 150px;">
                                                    <button type="submit" class="btn btn-sm btn-primary">
                                                        <i class="fas fa-plus"></i> Add
                                                    </button>

                                                    <!-- Pallet Scan Button -->
                                                    <c:if test="${uom.value.baseQuantity == 120}">
                                                        <button type="button" class="btn btn-sm btn-pallet" 
                                                                onclick="processPalletScan('${item.code}', ${uom.value.uomEntry})">
                                                            <i class="fas fa-barcode"></i> +120
                                                        </button>
                                                    </c:if>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Unassign UoM Group Button -->
                        <div class="text-end mt-4">
                            <form method="POST" action="unassignUomGroupFromItem.htm">
                                <input type="hidden" name="itemCode" value="${item.code}">
                                <button type="submit" class="btn btn-danger btn-lg" 
                                        onclick="return confirm('Are you sure you want to remove this UoM Group from the item?')">
                                    <i class="fas fa-unlink me-2"></i>Remove UoM Group
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- No UoM Group Message -->
                <div id="noUomGroupMessage" class="card-body ${not empty item.unitOfMeasurementGroup.ugpEntry ? 'd-none' : ''}">
                    <div class="alert alert-info">
                        <i class="fas fa-info-circle me-2"></i>
                        No UoM Group currently assigned to this item.
                    </div>
                </div>

                <!-- Assign New UoM Group -->
                <div class="card-body border-top">
                    <h4 class="mb-3">Assign New UoM Group</h4>
                    <form method="POST" action="assignUomGroupToItem.htm" class="row g-3 align-items-end">
                        <input type="hidden" name="itemCode" value="${item.code}">
                        <input type="hidden" name="originalUgpEntry" value="${item.unitOfMeasurementGroup.ugpEntry}">

                        <div class="col-md-8">
                            <label for="ugpEntry" class="form-label">Select UoM Group</label>
                            <select name="ugpEntry" id="ugpEntry" class="form-select form-select-lg">
                                <option value="">-- Select UoM Group --</option>
                                <c:forEach items="${allUnitOfMeasurementGroups}" var="groupEntry">
                                    <option value="${groupEntry.value.ugpEntry}"
                                            ${not empty item.unitOfMeasurementGroup && item.unitOfMeasurementGroup.ugpEntry == groupEntry.value.ugpEntry ? 'selected' : ''}>
                                        ${groupEntry.value.ugpName} (${groupEntry.value.ugpCode})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-success btn-lg w-100">
                                <i class="fas fa-link me-2"></i>Assign Group
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Navigation Links -->
            <div class="d-flex justify-content-between">
                <a href="sapCamelotItemDashboard.htm" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-2"></i>Back to Dashboard
                </a>
                <a href="camelotUnitOfMeasurementGroupCreationServant.htm" class="btn btn-outline-primary">
                    <i class="fas fa-plus-circle me-2"></i>Create New UoM Group
                </a>
            </div>
        </div>

        <!-- Bootstrap & jQuery JS -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Custom Scripts -->
        <script>
                                            // Form validation
                                            (function () {
                                                'use strict';
                                                window.addEventListener('load', function () {
                                                    var forms = document.getElementsByClassName('needs-validation');
                                                    Array.prototype.filter.call(forms, function (form) {
                                                        form.addEventListener('submit', function (event) {
                                                            if (form.checkValidity() === false) {
                                                                event.preventDefault();
                                                                event.stopPropagation();
                                                            }
                                                            form.classList.add('was-validated');
                                                        }, false);
                                                    });
                                                }, false);
                                            })();

                                            // Process pallet scan
                                            function processPalletScan(itemCode, uomEntry) {
                                                if (confirm('Process pallet scan for +120 pieces?')) {
                                                    $.ajax({
                                                        url: 'processPalletScan.htm',
                                                        type: 'POST',
                                                        data: {
                                                            itemCode: itemCode,
                                                            uomEntry: uomEntry
                                                        },
                                                        success: function (response) {
                                                            window.location.reload();
                                                        },
                                                        error: function (xhr) {
                                                            alert('Error processing pallet scan: ' + xhr.responseText);
                                                        }
                                                    });
                                                }
                                            }

                                            // Toggle UoM group display
                                            function toggleUomGroupDisplay(hasGroup) {
                                                if (hasGroup) {
                                                    $('#currentUomGroupCard').removeClass('d-none');
                                                    $('#noUomGroupMessage').addClass('d-none');
                                                } else {
                                                    $('#currentUomGroupCard').addClass('d-none');
                                                    $('#noUomGroupMessage').removeClass('d-none');
                                                }
                                            }
        </script>
    </body>
</html>