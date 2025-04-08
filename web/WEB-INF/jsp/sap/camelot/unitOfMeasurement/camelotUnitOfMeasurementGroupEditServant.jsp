<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Edit Unit of Measurement Group</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            body {
                font-size: 1.15rem;
            }
            .form-control-lg {
                height: calc(2.5em + 1rem + 2px);
                padding: 0.5rem 1rem;
                font-size: 1.5rem;
                line-height: 1.5;
                border-radius: 0.5rem;
                border: 2px solid #007bff;
                background-color: #f8f9fa;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .form-control-lg:hover {
                border-color: #0056b3;
                box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
            }
            .form-control-lg:focus {
                border-color: #0056b3;
                box-shadow: 0 0 12px rgba(0, 123, 255, 0.75);
                background-color: #ffffff;
            }
            .form-label-lg {
                font-size: 1.5rem;
                font-weight: bold;
                color: #343a40;
            }
            .btn-lg {
                font-size: 1.5rem;
                padding: 0.75rem 1.5rem;
                border-radius: 0.5rem;
            }
            .card {
                margin-bottom: 30px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }
            .card-header {
                background-color: #f8f9fa;
                font-weight: 600;
                font-size: 1.3rem;
            }
            h1, h2 {
                color: #343a40;
                margin-bottom: 20px;
                padding-bottom: 10px;
                border-bottom: 1px solid #eee;
            }
            h1 {
                font-size: 2.2rem;
            }
            h2 {
                font-size: 1.8rem;
            }
            .inner-table {
                background-color: #f8f9fa;
            }
            .inner-table thead th {
                background-color: #e9ecef;
            }
            .action-col {
                width: 150px;
            }
            .readonly-field {
                background-color: #e9ecef !important;
                cursor: not-allowed;
            }
            .unassign-section {
                border-top: 2px dashed #dc3545;
                padding-top: 1.5rem;
                margin-top: 1.5rem;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center mb-5">
                <i class="fas fa-layer-group me-2"></i>Edit Unit of Measurement Group
            </h1>

            <center> <h1 style='background-color:${alertColor}'>${message}</h1></center>

            <!-- Main Form -->
            <form:form action="updateUnitOfMeasurementGroup.htm" method="POST" modelAttribute="unitOfMeasurementGroup" class="needs-validation">
                <input type="hidden" name="ugpEntry" value="${unitOfMeasurementGroup.ugpEntry}">

                <div class="card mb-4">
                    <div class="card-header">Group Information</div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="ugpCode" class="form-label form-label-lg">Group Code</label>
                                <form:input path="ugpCode" class="form-control form-control-lg" id="ugpCode" required="true" />
                                <div class="invalid-feedback">Please provide a group code.</div>
                            </div>
                            <div class="col-md-6">
                                <label for="ugpName" class="form-label form-label-lg">Group Name</label>
                                <form:input path="ugpName" class="form-control form-control-lg" id="ugpName" required="true" />
                                <div class="invalid-feedback">Please provide a group name.</div>
                            </div>
                        </div>
                        
                        <!-- Unassign UoM Group Section -->
                        <div class="unassign-section">
                            <div class="alert alert-warning">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <i class="fas fa-exclamation-triangle me-2"></i>
                                        <strong>Unassign Group from Items</strong>
                                        <p class="mb-0 mt-1">This will remove this UoM Group from all items currently using it</p>
                                    </div>
                                    <form action="unassignUomGroupFromAllItems.htm" method="post" 
                                          onsubmit="return confirm('WARNING: This will remove this UoM Group from ALL items using it. Continue?');">
                                        <input type="hidden" name="ugpEntry" value="${unitOfMeasurementGroup.ugpEntry}" />
                                        <button type="submit" class="btn btn-danger btn-lg">
                                            <i class="fas fa-unlink me-2"></i>Unassign from All Items
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Form Actions -->
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <a href="camelotUnitOfMeasurementDashboard.htm" class="btn btn-secondary btn-lg me-md-2">
                        <i class="fas fa-arrow-left"></i> Back to List
                    </a>
                    <button type="submit" name="action" value="update" class="btn btn-primary btn-lg">
                        <i class="fas fa-save"></i> Save Changes
                    </button>
                </div>
            </form:form>

            <!-- Unit of Measurements Section -->
            <div class="card mb-4">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <span>Unit of Measurements in this Group</span>
                    <span class="badge bg-primary">${unitOfMeasurementGroup.unitOfMeasurements.size()} units</span>
                </div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-bordered mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>#</th>
                                    <th>UOM Code</th>
                                    <th>UOM Name</th>
                                    <th>Base Quantity</th>
                                    <th class="action-col">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="uomEntry" items="${unitOfMeasurementGroup.unitOfMeasurements}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${uomEntry.value.uomCode}</td>
                                        <td>${uomEntry.value.uomName}</td>
                                        <td>
                                            <form action="updateBaseQuantity.htm" method="post" style="display:inline;">
                                                <input type="hidden" name="unitOfMeasurementGroupEntry" value="${unitOfMeasurementGroup.ugpEntry}" />
                                                <input type="hidden" name="uomEntry" value="${uomEntry.value.uomEntry}" />
                                                <div class="input-group input-group-sm">
                                                    <input type="number" 
                                                           class="form-control form-control-sm" 
                                                           name="baseQuantity" 
                                                           value="${uomEntry.value.baseQuantity}" 
                                                           step="0.0001" 
                                                           min="0"
                                                           required>
                                                    <button type="submit" class="btn btn-sm btn-outline-primary" title="Update Quantity">
                                                        <i class="fas fa-save"></i>
                                                    </button>
                                                </div>
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <form action="removeUomFromGroup.htm" method="post" style="display:inline;" 
                                                  onsubmit="return confirm('Are you sure you want to remove this unit from the group?');">
                                                <input type="hidden" name="unitOfMeasurementGroupEntry" value="${unitOfMeasurementGroup.ugpEntry}" />
                                                <input type="hidden" name="uomEntryToRemove" value="${uomEntry.value.uomEntry}" />
                                                <button type="submit" class="btn btn-sm btn-outline-danger" title="Remove from Group">
                                                    <i class="fas fa-trash-alt"></i> Remove
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Add Unit to Group Section -->
            <div class="card mb-4">
                <div class="card-header">Add Unit to Group</div>
                <div class="card-body">
                    <form action="addUomToGroup.htm" method="POST">
                        <!-- Include hidden field for group ID if needed -->
                        <input type="hidden" name="unitOfMeasurementGroupEntry" value="${unitOfMeasurementGroup.ugpEntry}">

                        <div class="row">
                            <div class="col-md-8">
                                <label for="newUomEntry" class="form-label form-label-lg">Select Unit to Add</label>
                                <select name="newUomEntry" class="form-select form-select-lg" id="newUomEntry" required>
                                    <option value="">-- Select Unit of Measurement --</option>
                                    <c:forEach items="${allUnitsOfMeasurement}" var="uom">
                                        <option value="${uom.value.uomEntry}">
                                            ${uom.value.uomName} (${uom.value.uomCode})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4 d-flex align-items-end">
                                <button type="submit" class="btn btn-primary btn-lg w-100">
                                    <i class="fas fa-plus"></i> Add Unit
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

        <script>
            // Enable Bootstrap form validation
            (function () {
                'use strict';
                const forms = document.querySelectorAll('.needs-validation');

                Array.from(forms).forEach(form => {
                    form.addEventListener('submit', event => {
                        if (!form.checkValidity()) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            })();
            
            // Additional confirmation for unassign action
            document.querySelectorAll('[data-unassign-confirm]').forEach(button => {
                button.addEventListener('click', (e) => {
                    if (!confirm('This will affect ALL items using this group. Continue?')) {
                        e.preventDefault();
                    }
                });
            });
        </script>
    </body>
</html>