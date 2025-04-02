<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Create New Unit of Measurement Group</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS matching your item creation style -->
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

        .form-label-lg {
            font-size: 2.25rem;
            font-weight: bold;
            color: #343a40;
        }

        .btn-primary {
            font-size: 2rem;
            padding: 0.75rem 1.5rem;
            border-radius: 0.5rem;
        }

        .uom-entry {
            border: 1px solid #dee2e6;
            border-radius: 0.5rem;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            background-color: #f8f9fa;
        }

        .add-uom-btn {
            font-size: 1.75rem;
            padding: 0.5rem 1rem;
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-5">Create New Unit of Measurement Group</h1>
        
        <form:form action="camelotUnitOfMeasurementGroupCreationServant" method="POST" modelAttribute="uomGroup" class="needs-validation">
            <h1>Basic Information</h1>
            
            <!-- Row for UgpEntry and UgpCode -->
            <div class="row mb-3">
                <!-- UgpEntry -->
                <div class="col-md-6">
                    <label for="ugpEntry" class="form-label form-label-lg">Group Entry ID</label>
                    <form:input path="ugpEntry" type="number" class="form-control form-control-lg" id="ugpEntry" required="true" />
                </div>
                
                <!-- UgpCode -->
                <div class="col-md-6">
                    <label for="ugpCode" class="form-label form-label-lg">Group Code</label>
                    <form:input path="ugpCode" class="form-control form-control-lg" id="ugpCode" required="true" />
                </div>
            </div>
            
            <!-- UgpName -->
            <div class="mb-3">
                <label for="ugpName" class="form-label form-label-lg">Group Name</label>
                <form:input path="ugpName" class="form-control form-control-lg" id="ugpName" required="true" />
            </div>
            
            <hr>
            <h1>Units of Measurement</h1>
            
            <div id="uom-container">
                <!-- First UoM entry -->
                <div class="uom-entry">
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label for="unitOfMeasurements[0].uomCode" class="form-label form-label-lg">UoM Code</label>
                            <form:input path="unitOfMeasurements[0].uomCode" class="form-control form-control-lg" required="true" />
                        </div>
                        <div class="col-md-4">
                            <label for="unitOfMeasurements[0].uomName" class="form-label form-label-lg">UoM Name</label>
                            <form:input path="unitOfMeasurements[0].uomName" class="form-control form-control-lg" required="true" />
                        </div>
                        <div class="col-md-4">
                            <label for="unitOfMeasurements[0].basicQuantity" class="form-label form-label-lg">Basic Quantity</label>
                            <form:input path="unitOfMeasurements[0].basicQuantity" type="number" step="0.01" class="form-control form-control-lg" required="true" />
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Button to add more UoM entries -->
            <button type="button" class="btn btn-secondary add-uom-btn" onclick="addUomEntry()">
                + Add Another Unit
            </button>
            
            <!-- Submit Button -->
            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary btn-lg">Create UoM Group</button>
            </div>
        </form:form>
    </div>

    <!-- JavaScript for dynamic UoM entry addition -->
    <script>
        let uomCounter = 1;
        
        function addUomEntry() {
            const container = document.getElementById('uom-container');
            const newEntry = document.createElement('div');
            newEntry.className = 'uom-entry';
            newEntry.innerHTML = `
                <div class="row mb-3">
                    <div class="col-md-4">
                        <label for="unitOfMeasurements[${uomCounter}].uomCode" class="form-label form-label-lg">UoM Code</label>
                        <input type="text" name="unitOfMeasurements[${uomCounter}].uomCode" class="form-control form-control-lg" required />
                    </div>
                    <div class="col-md-4">
                        <label for="unitOfMeasurements[${uomCounter}].uomName" class="form-label form-label-lg">UoM Name</label>
                        <input type="text" name="unitOfMeasurements[${uomCounter}].uomName" class="form-control form-control-lg" required />
                    </div>
                    <div class="col-md-4">
                        <label for="unitOfMeasurements[${uomCounter}].basicQuantity" class="form-label form-label-lg">Basic Quantity</label>
                        <input type="number" step="0.01" name="unitOfMeasurements[${uomCounter}].basicQuantity" class="form-control form-control-lg" required />
                    </div>
                </div>
                <button type="button" class="btn btn-danger btn-sm" onclick="this.parentNode.remove()">Remove</button>
            `;
            container.appendChild(newEntry);
            uomCounter++;
        }
    </script>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>