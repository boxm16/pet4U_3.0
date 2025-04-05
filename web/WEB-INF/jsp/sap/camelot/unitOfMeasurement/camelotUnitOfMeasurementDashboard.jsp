<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <table border="1">
            <tr>
                <th>Uom Entry</th>
                <th>Uom Code</th>
                <th>Uom Name</th>
                <th>Basic Quantity</th>
            </tr>
            <c:forEach var="unitOfMeasurementEntry" items="${allUnitsOfMeasurement}">
                <tr>
                    <td>${unitOfMeasurementEntry.value.uomEntry}</td> 
                    <td>${unitOfMeasurementEntry.value.uomCode}</td> 
                    <td>${unitOfMeasurementEntry.value.uomName}</td> 
                    <td>${unitOfMeasurementEntry.value.baseQuantity}</td>
                </tr>
            </c:forEach>
        </table>

        <hr> <hr> <hr>
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
    </body>
</html>
