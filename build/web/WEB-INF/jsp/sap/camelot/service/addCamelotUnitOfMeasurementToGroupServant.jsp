<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Add UoM to Group</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center mb-5">Add Unit of Measurement to Group</h1>

            <div class="alert alert-info">
                <h4>Current Group: ${groupCode} - ${groupName}</h4>
            </div>

            <form:form action="addUoMToGroup" method="POST" modelAttribute="uom" class="needs-validation">
                <input type="hidden" name="groupCode" value="${groupCode}" />

                <div class="row mb-3">
                    <!-- UoM Entry -->
                    <div class="col-md-6">
                        <label for="uomEntry" class="form-label form-label-lg">UoM Entry ID</label>
                        <form:input path="uomEntry" type="number" class="form-control form-control-lg" 
                                    id="uomEntry" required="true" />
                    </div>

                    <!-- Basic Quantity -->
                    <div class="col-md-6">
                        <label for="basicQuantity" class="form-label form-label-lg">Basic Quantity</label>
                        <form:input path="basicQuantity" type="number" step="0.01" 
                                    class="form-control form-control-lg" id="basicQuantity" required="true" />
                    </div>
                </div>

                <!-- UoM Code and Name (if needed) -->
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="uomCode" class="form-label form-label-lg">UoM Code</label>
                        <form:input path="uomCode" class="form-control form-control-lg" 
                                    id="uomCode" required="true" />
                    </div>
                    <div class="col-md-6">
                        <label for="uomName" class="form-label form-label-lg">UoM Name</label>
                        <form:input path="uomName" class="form-control form-control-lg" 
                                    id="uomName" required="true" />
                    </div>
                </div>

                <!-- Submit Button -->
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary btn-lg">Add to UoM Group</button>
                </div>
            </form:form>

            <!-- Back to Group Details -->
            <div class="mt-4 text-center">
                <a href="uomGroupDetails.htm?groupCode=${groupCode}" 
                   class="btn btn-secondary btn-lg">Back to Group Details</a>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Simple validation -->
        <script>
            (function () {
                'use strict';
                const forms = document.querySelectorAll('.needs-validation');

                Array.from(forms).forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            })();
        </script>
    </body>
</html>