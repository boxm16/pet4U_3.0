<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Update Camelot Item</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS (same as create form) -->
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
            .form-check-input {
                transform: scale(2);
                margin-right: 15px;
                margin-bottom: 30px;
            }
            .form-check-label {
                font-size: 2rem;
                margin-left: 10px;
            }
            .btn-primary {
                font-size: 2rem;
                padding: 0.75rem 1.5rem;
                border-radius: 0.5rem;
            }
            .form-select-lg:hover {
                border-color: #0056b3;
                box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
            }
            .form-select-lg:focus {
                border-color: #0056b3;
                box-shadow: 0 0 12px rgba(0, 123, 255, 0.75);
                background-color: #ffffff;
            }
            .readonly-field {
                background-color: #e9ecef !important;
                cursor: not-allowed;
            }
            /* New style for UoM Group section */
            .uom-group-section {
                margin-bottom: 2rem;
                border: 1px solid #dee2e6;
                border-radius: 0.5rem;
                padding: 1.5rem;
                background-color: #f8f9fa;
            }
            .uom-group-title {
                font-size: 1.8rem;
                margin-bottom: 1rem;
                color: #495057;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center mb-5">Update Camelot Item</h1>
            <h1 style='background-color:${alertColor}'>${message}</h1>
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

                    <!-- UoM Group Dropdown -->
                    <div class="col-md-4">
                        <label for="uomGroup" class="form-label form-label-lg">UoM Group</label>
                        <form:select path="uomGroup" class="form-select form-select-lg" id="uomGroup">
                            <form:option value="" label="-- Select UoM Group --" />
                            <form:options items="${unitOfMeasurementGroups}" itemValue="ugpEntry" itemLabel="ugpName" />
                        </form:select>
                    </div>
                </div>

                <!-- Description -->
                <div class="mb-3">
                    <label for="description" class="form-label form-label-lg">Description</label>
                    <form:input path="description" class="form-control form-control-lg" id="description" required="true" />
                </div>

                <!-- UoM Group Details Section -->
                <div class="uom-group-section" id="uomGroupDetails" style="display: none;">
                    <h3 class="uom-group-title">UoM Group Details</h3>
                    <div id="uomGroupContent">
                        <!-- Content will be populated by JavaScript -->
                    </div>
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
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        
        <!-- JavaScript to handle UoM Group selection -->
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const uomGroupSelect = document.getElementById('uomGroup');
                const uomGroupDetails = document.getElementById('uomGroupDetails');
                const uomGroupContent = document.getElementById('uomGroupContent');
                
                // Get the UoM groups data passed from the controller
                const uomGroupsData = ${unitOfMeasurementGroupsJson};
                
                uomGroupSelect.addEventListener('change', function() {
                    const selectedUgpEntry = this.value;
                    
                    if (selectedUgpEntry) {
                        const selectedGroup = uomGroupsData.find(group => group.ugpEntry == selectedUgpEntry);
                        
                        if (selectedGroup) {
                            let html = `
                                <div class="mb-2">
                                    <strong>Group Code:</strong> ${selectedGroup.ugpCode}
                                </div>
                                <div class="mb-3">
                                    <strong>Base Unit:</strong> ${selectedGroup.unitOfMeasurements[0].uomName}
                                </div>
                                <h4>Available Units:</h4>
                                <ul class="list-group">`;
                            
                            selectedGroup.unitOfMeasurements.forEach(uom => {
                                html += `
                                    <li class="list-group-item">
                                        <strong>${uom.uomName}</strong> (${uom.uomCode}): 
                                        Base Quantity = ${uom.baseQuantity}
                                    </li>`;
                            });
                            
                            html += `</ul>`;
                            uomGroupContent.innerHTML = html;
                            uomGroupDetails.style.display = 'block';
                        }
                    } else {
                        uomGroupDetails.style.display = 'none';
                    }
                });
                
                // Trigger change event if there's a pre-selected value
                if (uomGroupSelect.value) {
                    uomGroupSelect.dispatchEvent(new Event('change'));
                }
            });
        </script>
    </body>
</html>