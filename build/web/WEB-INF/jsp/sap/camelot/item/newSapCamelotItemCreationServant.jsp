<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Create New Camelot Item</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS for larger fonts and distinctive input fields -->
        <style>
            /* Custom styles for input fields */
            .form-control-lg {
                height: calc(2.5em + 1rem + 2px); /* Increase height */
                padding: 0.5rem 1rem; /* Increase padding */
                font-size: 2.25rem; /* Increase font size */
                line-height: 1.5; /* Adjust line height */
                border-radius: 0.5rem; /* Slightly larger border radius */
                border: 2px solid #007bff; /* Blue border */
                background-color: #f8f9fa; /* Light gray background */
                transition: border-color 0.3s ease, box-shadow 0.3s ease; /* Smooth transition */
            }

            /* Hover effect for input fields */
            .form-control-lg:hover {
                border-color: #0056b3; /* Darker blue on hover */
                box-shadow: 0 0 8px rgba(0, 123, 255, 0.5); /* Glow effect on hover */
            }

            /* Focus effect for input fields */
            .form-control-lg:focus {
                border-color: #0056b3; /* Darker blue on focus */
                box-shadow: 0 0 12px rgba(0, 123, 255, 0.75); /* Stronger glow on focus */
                background-color: #ffffff; /* White background on focus */
            }

            /* Custom width for the "code" input */
            #code {
                width: 200px; /* Adjust this value as needed */
            }

            /* Custom styles for labels */
            .form-label-lg {
                font-size: 2.25rem; /* Increase label font size */
                font-weight: bold; /* Make labels bold */
                color: #343a40; /* Dark gray color for labels */
            }

            /* Custom styles for radio buttons */
            .form-check-input {
                transform: scale(2); /* Increase size of radio buttons (2x) */
                margin-right: 15px; /* Add spacing between radio button and label */
                margin-bottom: 30px;   /* Add spacing between radio buttons */
            }
            /* Custom styles for radio button labels */
            .form-check-label {
                font-size: 2rem; /* Increase label font size */
                margin-left: 10px; /* Add spacing between radio button and label */
            }

            /* Custom styles for the submit button */
            .btn-primary {
                font-size: 2rem; /* Increase button font size */
                padding: 0.75rem 1.5rem; /* Increase button padding */
                border-radius: 0.5rem; /* Rounded corners for the button */
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center mb-4">Create New Camelot Item</h1>
            <h1>${message}</h1>
            <form:form action="createNewSapCamelotItem.htm" method="POST" modelAttribute="item" class="needs-validation">
                <h1>Mandatory Fields</h1>

                <!-- Row for Code and Description -->
                <div class="row mb-3">
                    <!-- Item Code -->
                    <div class="col-md-4"> <!-- Adjust the column size as needed -->
                        <label for="code" class="form-label form-label-lg">Item Code</label>
                        <form:input path="code" class="form-control form-control-lg" id="code" required="true" />
                        <div class="invalid-feedback" style="font-size: 1.1rem;">
                            Please provide an item code.
                        </div>
                    </div>

                    <!-- Items Group Dropdown -->
                    <div class="col-md-4">
                        <label for="itemsGroupCode" class="form-label form-label-lg">Items Group</label>
                        <form:select path="itemsGroupCode" class="form-control form-control-lg" id="itemsGroupCode" required="true">
                            <form:option value="" label="-- Select Items Group --" disabled="true" /> <!-- Disable the default option -->
                            <form:options items="${itemGroups}" />
                        </form:select>
                        <div class="invalid-feedback" style="font-size: 1.1rem;">
                            Please select an items group.
                        </div>
                    </div>

                    <!-- Radio Buttons for Item Type -->
                    <div class="col-md-4">
                        <label class="form-label form-label-lg">Item Type</label>
                        <div class="radio-group">
                            <div class="form-check">
                                <!-- Use the same path for both radio buttons -->
                                <form:radiobutton path="itemType" value="food" id="food" class="form-check-input" />
                                <label for="food" class="form-check-label">Food</label>
                            </div>
                            <div class="form-check">
                                <!-- Use the same path for both radio buttons -->
                                <form:radiobutton path="itemType" value="accessory" id="accessory" class="form-check-input" />
                                <label for="accessory" class="form-check-label">Accessory</label>
                            </div>
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
                <button type="submit" class="btn btn-primary btn-lg">Create Item</button>
            </form:form>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    </body>
</html>