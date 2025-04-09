<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Update Camelot Item</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <!-- Custom CSS -->
        <style>
            .form-control-lg {
                height: calc(2.5em + 1rem + 2px);
                padding: 0.5rem 1rem;
                font-size: 2.25rem;
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
            #code {
                width: 400px;
            }
            .form-label-lg {
                font-size: 2.25rem;
                font-weight: bold;
                color: #343a40;
            }
            .readonly-field {
                background-color: #e9ecef !important;
                cursor: not-allowed;
            }
            .uom-group-actions {
                margin-top: 1rem;
                display: flex;
                gap: 1rem;
            }
            .no-uom-group {
                font-style: italic;
                color: #6c757d;
            }
            .uom-table {
                width: 100%;
                margin: 20px 0;
                border-collapse: collapse;
            }
            .uom-table th, .uom-table td {
                padding: 12px;
                border: 1px solid #dee2e6;
                text-align: left;
            }
            .uom-table th {
                background-color: #f8f9fa;
            }
            .pallet-row {
                background-color: #e8f5e9;
            }
            .base-quantity-input {
                width: 100px;
            }
            .barcode-input {
                width: 200px;
                display: inline-block;
                margin-right: 10px;
            }
            .barcode-badge {
                display: inline-block;
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
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center mb-5">Update Camelot Item</h1>
            <h1 style="background-color:${alertColor}">${message}</h1>
            
            <form:form action="updateSapCamelotItem.htm" method="POST" modelAttribute="item" class="needs-validation">
                <h1>Item Information</h1>

                <!-- Row for Code and Description -->
                <div class="row mb-3">
                    <!-- Item Code (readonly) -->
                    <div class="col-md-4">
                        <label for="code" class="form-label form-label-lg">Item Code</label>
                        <form:input path="code" class="form-control form-control-lg readonly-field" id="code" readonly="true" />
                    </div>

                    <!-- Items Group Dropdown -->
                    <div class="col-md-4">
                        <label for="itemsGroupCode" class="form-label form-label-lg">Items Group</label>
                        <form:select path="itemsGroupCode" class="form-select form-select-lg" id="itemsGroupCode" required="true">
                            <form:option value="" label="-- Select Items Group --" disabled="true" />
                            <form:options items="${itemGroups}" />
                        </form:select>
                        <div class="invalid-feedback" style="font-size: 1.1rem;">
                            Please select an items group.
                        </div>
                    </div>
                </div>

                <!-- Description -->
                <div class="mb-3">
                    <label for="description" class="form-label form-label-lg">Description</label>
                    <form:input path="description" class="form-control form-control-lg" id="description" required="true" />
                </div>

                <hr>
                <h1>Optional Fields</h1>
                <!-- Main Barcode -->
                <div class="mb-3">
                    <label for="mainBarcode" class="form-label form-label-lg">Main Barcode</label>
                    <form:input path="mainBarcode" class="form-control form-control-lg" id="mainBarcode" />
                </div>

                <!-- Submit Button -->
                <button type="submit" class="btn btn-primary btn-lg">Update Item</button>
            </form:form>
            <hr>
            
            <!-- UoM Group Section -->
            <div class="mb-4">
                <h2>Current Unit of Measurement Group</h2>

                <!-- Current UoM Group Display -->
                <div class="card mb-3" id="currentUomGroupCard" style="${empty item.unitOfMeasurementGroup.ugpEntry ? 'display:none;' : ''}">
                    <div class="card-body">
                        <h5 class="card-title">Current UoM Group</h5>
                        <p class="card-text" id="currentUomGroupText">
                            <c:if test="${not empty item.unitOfMeasurementGroup.ugpEntry}">
                                ${item.unitOfMeasurementGroup.ugpName} (${item.unitOfMeasurementGroup.ugpCode})
                            </c:if>
                        </p>
                        
                        <!-- UoM Table with Base Quantities and Barcodes -->
                        <table class="uom-table">
                            <thead>
                                <tr>
                                    <th>UoM Code</th>
                                    <th>UoM Name</th>
                                    <th>Base Quantity</th>
                                    <th>Barcodes</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${item.unitOfMeasurementGroup.unitOfMeasurements}" var="uom">
                                    <tr class="${uom.value.baseQuantity == 120 ? 'pallet-row' : ''}">
                                        <td>${uom.value.uomCode}</td>
                                        <td>${uom.value.uomName}</td>
                                        <td>
                                            <input type="number" name="baseQuantity" 
                                                   value="${uom.value.baseQuantity}" 
                                                   class="form-control base-quantity-input" 
                                                   min="1" step="1" readonly />
                                        </td>
                                        <td>
                                            <!-- Display existing barcodes -->
                                            <div class="mb-2">
                                                <c:forEach items="${uom.value.barcodes}" var="barcode">
                                                    <span class="barcode-badge">
                                                        ${barcode.value}
                                                        <form method="POST" action="removeUomBarcode.htm" class="d-inline">
                                                            <input type="hidden" name="itemCode" value="${item.code}" />
                                                            <input type="hidden" name="uomEntry" value="${uom.value.uomEntry}" />
                                                            <input type="hidden" name="barcode" value="${barcode.value}" />
                                                            <button type="submit" class="barcode-remove-btn">
                                                                <i class="fas fa-times"></i>
                                                            </button>
                                                        </form>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                            
                                            <!-- Add new barcode form -->
                                            <form method="POST" action="addUomBarcode.htm" class="mt-2" >
                                                <input type="hidden" name="itemCode" value="${item.code}" />
                                                <input type="hidden" name="uomEntry" value="${uom.value.uomEntry}" />
                                                <div class="input-group">
                                                    <input type="text" name="barcode" class="form-control barcode-input" 
                                                           placeholder="New barcode" required />
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="fas fa-plus"></i> Add Barcode
                                                    </button>
                                                </div>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        
                        <!-- Unassign UoM Group Button -->
                        <form method="POST" action="unassignUomGroupFromItem.htm" class="uom-group-actions">
                            <input type="hidden" name="itemCode" value="${item.code}" />
                            <button type="submit" class="btn btn-danger btn-lg" 
                                    onclick="return confirm('Are you sure you want to remove this UoM Group from the item?')">
                                <i class="fas fa-unlink me-2"></i>Remove UoM Group
                            </button>
                        </form>
                    </div>
                </div>
                
                <!-- Message when no UoM Group is assigned -->
                <div id="noUomGroupMessage" class="no-uom-group" style="${not empty item.unitOfMeasurementGroup.ugpEntry ? 'display:none;' : ''}">
                    No UoM Group currently assigned to this item.
                </div>
            </div>
            
            <!-- UoM Group Assignment Section -->
            <div class="mb-4">
                <h2>Assign New Unit of Measurement Group</h2>

                <!-- Add UoM Group Button and Dropdown -->
                <form method="POST" action="assignUomGroupToItem.htm">
                    <!-- Hidden field for itemCode -->
                    <input type="hidden" name="itemCode" value="${item.code}" />
                    <input type="hidden" name="originalUgpEntry" value="${item.unitOfMeasurementGroup.ugpEntry}" />

                    <div class="row">
                        <div class="col-md-8">
                            <select name="ugpEntry" class="form-select form-select-lg">
                                <option value="">-- Select UoM Group --</option>
                                <c:forEach items="${allUnitOfMeasurementGroups}" var="groupEntry">
                                    <option value="${groupEntry.value.ugpEntry}"
                                            <c:if test="${not empty item.unitOfMeasurementGroup && item.unitOfMeasurementGroup.ugpEntry == groupEntry.value.ugpEntry}">selected</c:if>>
                                        ${groupEntry.value.ugpName} (${groupEntry.value.ugpCode})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-success btn-lg">
                                <i class="fas fa-link me-2"></i>Assign UoM Group
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        
        <script>
            // Show/hide UoM Group sections based on assignment
            function toggleUomGroupDisplay(hasGroup) {
                document.getElementById('currentUomGroupCard').style.display = hasGroup ? 'block' : 'none';
                document.getElementById('noUomGroupMessage').style.display = hasGroup ? 'none' : 'block';
            }
        </script>
    </body>
</html>