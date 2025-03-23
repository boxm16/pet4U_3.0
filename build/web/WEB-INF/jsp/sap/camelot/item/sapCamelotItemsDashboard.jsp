<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="SAP.SapBasicModel.SapItem"%>
<%@ page import="java.util.Map, java.util.LinkedHashMap, your.package.name.SapItem, your.package.name.AltercodeContainer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Display Items</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Item List</h1>
        <table class="table table-bordered table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>Item Code</th>
                    <th>Item Name</th>
                    <th>Pick Location</th>
                    <th>Stock</th>
                    <th>Alternate Codes</th>
                </tr>
            </thead>
            <tbody>
                <%
                    LinkedHashMap<String, SapItem> items = (LinkedHashMap<String, SapItem>) request.getAttribute("items");
                    if (items != null) {
                        for (Map.Entry<String, SapItem> entry : items.entrySet()) {
                            SapItem item = entry.getValue();
                %>
                <tr>
                    <td><%= item.getCode() %></td>
                    <td><%= item.getDescription() %></td>
                    <td><%= item.getPosition() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td>
                        <ul>
                            <%
                                for (AltercodeContainer altercode : item.getAltercodes()) {
                            %>
                            <li><%= altercode.getAltercode() %> - <%= altercode.getStatus() %></li>
                            <%
                                }
                            %>
                        </ul>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">No items found</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>