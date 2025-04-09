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
            /* Your existing styles remain the same */
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
            /* ... keep all your existing styles ... */

            /* New styles for barcode management */
            .barcode-section {
                background-color: #f8f9fa;
                padding: 20px;
                border-radius: 8px;
                margin-top: 20px;
                border: 1px solid #dee2e6;
            }
            .barcode-item {
                display: flex;
                align-items: center;
                padding: 8px;
                border-bottom: 1px solid #eee;
            }
            .barcode-item:last-child {
                border-bottom: none;
            }
            .pallet-badge {
                background-color: #28a745;
                color: white;
                padding: 3px 8px;
                border-radius: 4px;
                font-size: 0.9rem;
                margin-left: 10px;
            }
            .add-barcode-form {
                margin-top: 15px;
                display: flex;
                gap: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center mb-5">Update Camelot Item</h1>
            <h1 style='background-color:${alertColor}'>${message}</h1>

            <form:form action="updateSapCamelotItem.htm" method="POST" modelAttribute="item" class="needs-validation">
                <!-- Your existing form fields remain the same -->
                <h1>Item Information</h1>
                <div class="row mb-3">
                    <div class="col-md-4">
                        <label for="code" class="form-label form-label-lg">Item Code</label>
                        <form:input path="code" class="form-control form-control-lg readonly-field" id="code" readonly="true" />
                    </div>
                    <div class="col-md-4">
                        <label for="itemsGroupCode" class="form-label form-label-lg">Items Group</label>
                        <form:select path="itemsGroupCode" class="form-select form-select-lg" id="itemsGroupCode" required="true">
                            <form:option value="" label="-- Select Items Group --" disabled="true" />
                            <form:options items="${itemGroups}" />
                        </form:select>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label form-label-lg">Description</label>
                    <form:input path="description" class="form-control form-control-lg" id="description" required="true" />
                </div>
                <hr>
                <h1>Optional Fields</h1>
                <div class="mb-3">
                    <label for="mainBarcode" class="form-label form-label-lg">Main Barcode</label>
                    <form:input path="mainBarcode" class="form-control form-control-lg" id="mainBarcode" />
                </div>
                <button type="submit" class="btn btn-primary btn-lg">Update Item</button>
            </form:form>
            <hr>

            <!-- Enhanced UoM Group Section with Barcode Management -->
            <div class="mb-4">
                <h2>Current Unit of Measurement Group</h2>

                <div class="card mb-3" id="currentUomGroupCard" 
                     style="${empty item.unitOfMeasurementGroup.ugpEntry ? 'display:none;' : ''}">
                    <div class="card-body">
                        <h5 class="card-title">Current UoM Group</h5>
                        <p class="card-text" id="currentUomGroupText">
                            <c:if test="${not empty item.unitOfMeasurementGroup.ugpEntry}">
                                ${item.unitOfMeasurementGroup.ugpName} (${item.unitOfMeasurementGroup.ugpCode})
                            </c:if>
                        </p>

                        <!-- Barcode Management Section -->
                        <div class="barcode-section">
                            <h4>UoM Barcodes</h4>

                            <c:forEach items="${item.unitOfMeasurementGroup.unitOfMeasurements}" var="uom">
                                <div class="mb-3">
                                    <h5>
                                        ${uom.value.uomName} (${uom.value.uomCode})
                                        <c:if test="${uom.value.baseQuantity == 120}">
                                            <span class="pallet-badge">PALLET</span>
                                        </c:if>
                                    </h5>

                                    <div class="barcode-list">
                                        <c:forEach items="${uom.value.barcodes}" var="barcode">
                                            <div class="barcode-item">
                                                <span>${barcode}</span>
                                                <form method="POST" action="removeUomBarcode.htm" style="margin-left: auto;">
                                                    <input type="hidden" name="itemCode" value="${item.code}">
                                                    <input type="hidden" name="uomEntry" value="${uom.value.uomEntry}">
                                                    <input type="hidden" name="barcode" value="${barcode}">
                                                    <button type="submit" class="btn btn-sm btn-outline-danger">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                </form>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <form method="POST" action="addUomBarcode.htm" class="add-barcode-form">
                                        <input type="hidden" name="itemCode" value="${item.code}">
                                        <input type="hidden" name="uomEntry" value="${uom.value.uomEntry}">
                                        <input type="text" name="barcode" class="form-control" placeholder="New barcode" required>
                                        <button type="submit" class="btn btn-success">Add Barcode</button>
                                    </form>
                                </div>
                            </c:forEach>

                            <!-- Pallet Scan Action for UoMs with 120 pieces -->
                            <c:if test="${not empty palletUom}">
                                <div class="mt-4">
                                    <h4>Pallet Operations</h4>
                                    <form method="POST" action="processPalletScan.htm">
                                        <input type="hidden" name="itemCode" value="${item.code}">
                                        <input type="hidden" name="uomEntry" value="${palletUom.uomEntry}">
                                        <button type="submit" class="btn btn-primary btn-lg">
                                            <i class="fas fa-barcode me-2"></i> Process Pallet Scan (+120)
                                        </button>
                                    </form>
                                </div>
                            </c:if>
                        </div>

                        <!-- Unassign UoM Group Button -->
                        <form method="POST" action="unassignUomGroupFromItem.htm" class="uom-group-actions">
                            <input type="hidden" name="itemCode" value="${item.code}">
                            <button type="submit" class="btn btn-danger btn-lg" 
                                    onclick="return confirm('Are you sure you want to remove this UoM Group from the item?')">
                                <i class="fas fa-unlink me-2"></i>Remove UoM Group
                            </button>
                        </form>
                    </div>
                </div>

                <div id="noUomGroupMessage" class="no-uom-group" 
                     style="${not empty item.unitOfMeasurementGroup.ugpEntry ? 'display:none;' : ''}">
                    No UoM Group currently assigned to this item.
                </div>
            </div>

            <!-- UoM Group Assignment Section -->
            <div class="mb-4">
                <h2>Assign New Unit of Measurement Group</h2>
                <form method="POST" action="assignUomGroupToItem.htm">
                    <input type="hidden" name="itemCode" value="${item.code}">
                    <input type="hidden" name="originalUgpEntry" value="${item.unitOfMeasurementGroup.ugpEntry}">

                    <div class="row">
                        <div class="col-md-8">
                            <select name="ugpEntry" class="form-select form-select-lg">
                                <option value="">-- Select UoM Group --</option>
                                <c:forEach items="${allUnitOfMeasurementGroups}" var="groupEntry">
                                    <option value="${groupEntry.value.ugpEntry}"
                                            <c:if test="${not empty item.unitOfMeasurementGroup && item.unitOfMeasurementGroup.ugpEntry == groupEntry.value.ugpEntry}">
                                                selected
                                            </c:if>
                                            >
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
                                        // Your existing JavaScript remains the same
                                        function toggleUomGroupDisplay(hasGroup) {
                                            document.getElementById('currentUomGroupCard').style.display = hasGroup ? 'block' : 'none';
                                            document.getElementById('noUomGroupMessage').style.display = hasGroup ? 'none' : 'block';
                                        }

                                        // New function to focus on barcode input when adding
                                        document.querySelectorAll('.add-barcode-form').forEach(form => {
                                            form.addEventListener('submit', function () {
                                                const input = this.querySelector('input[name="barcode"]');
                                                if (input.value.trim() === '') {
                                                    input.focus();
                                                    return false;
                                                }
                                                return true;
                                            });
                                        });
        </script>
    </body>
</html>