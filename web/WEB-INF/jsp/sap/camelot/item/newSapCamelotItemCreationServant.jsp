<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Create New Camelot Item</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS for larger fonts -->
        <style>
            .form-control-lg {
                height: calc(2.5em + 1rem + 2px); /* Increase height */
                padding: 0.5rem 1rem; /* Increase padding */
                font-size: 2.25rem; /* Increase font size */
                line-height: 1.5; /* Adjust line height */
                border-radius: 0.3rem; /* Slightly larger border radius */
            }
            .form-label-lg {
                font-size:2.25rem; /* Increase label font size */
                font-weight: bold; /* Optional: Make labels bold */
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center mb-4">Create New Camelot Item</h1>
            <form:form action="createNewSapCamelotItem.htm" method="POST" modelAttribute="item" class="needs-validation">
                <div class="mb-3">
                    <label for="code" class="form-label form-label-lg">Item Code</label>
                    <form:input path="code" class="form-control form-control-lg" id="code" required="true" />
                    <div class="invalid-feedback" style="font-size: 1.1rem;">
                        Please provide an item code.
                    </div>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label form-label-lg">Description</label>
                    <form:input path="description" class="form-control form-control-lg" id="description" required="true" />
                </div>
                <div class="mb-3">
                    <label for="mainBarcode" class="form-label form-label-lg">Main Barcode</label>
                    <form:input path="mainBarcode" class="form-control form-control-lg" id="mainBarcode" />
                </div>

                <div class="mb-3">
                    <label for="itemsGroup" class="form-label form-label-lg">Items Group</label>
                    <form:select path="itemsGroup" class="form-control form-control-lg" id="itemsGroup" required="true">
                        <form:option value="" label="-- Select Items Group --" />
                        <form:options items="${itemGroups}" />
                    </form:select>
                    <div class="invalid-feedback" style="font-size: 1.1rem;">
                        Please select an items group.
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-lg">Create Item</button>
            </form:form>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    </body>
</html>