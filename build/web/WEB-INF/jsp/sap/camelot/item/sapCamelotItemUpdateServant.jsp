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
                <h2>Unit of Measurement Group</h2>

                <!-- Current UoM Group Display -->
                <div class="card mb-3" id="currentUomGroupCard" 
                     style="${empty item.unitOfMeasurementGroup.ugpEntry ? 'display:none;' : ''}">
                    <div class="card-body">
                        <h5 class="card-title">Current UoM Group</h5>
                        <p class="card-text" id="currentUomGroupText">
                        <c:if test="${not empty item.unitOfMeasurementGroup.ugpEntry}">
                            ${item.unitOfMeasurementGroup.ugpName} (${item.unitOfMeasurementGroup.ugpCode})
                        </c:if>
                        </p>

                    </div>
                </div>



            </div>
            <table border="1">
                <thead>
                    <tr>
                        <th>Ugp Entry</th>
                        <th>Ugp Code</th>
                        <th>Ugp Name </th>
                        <th>Unit Of Measurement </th>

                    </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${allUnitOfMeasurementGroups}">
                    <tr>
                        <td>${entry.key}</td> <!-- Key of the LinkedHashMap (Short type) -->
                        <td>${entry.value.ugpCode}</td> <!-- Property of SapUnitOfMeasurementGroup -->
                        <td>${entry.value.ugpName}</td> <!-- Property of SapUnitOfMeasurementGroup -->
                        <td>
                            <table border="1">
                                <tr>
                                    <th>Uom Entry</th>
                                    <th>Uom Code</th>
                                    <th>Uom Name</th>
                                    <th>Basic Quantity</th>
                                </tr>
                                <c:forEach var="unitOfMeasurementEntry" items="${entry.value.unitOfMeasurements}">

                                    <tr>

                                        <td>${unitOfMeasurementEntry.value.uomEntry}</td> 
                                        <td>${unitOfMeasurementEntry.value.uomCode}</td> 
                                        <td>${unitOfMeasurementEntry.value.uomName}</td> 
                                        <td>${unitOfMeasurementEntry.value.baseQuantity}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    </body>
</html>