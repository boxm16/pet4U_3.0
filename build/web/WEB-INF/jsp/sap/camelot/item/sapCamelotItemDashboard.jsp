<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Sap Camelot Item Dashboard</title>
    </head>
    <body>
        <h1>Sap Camelot Item Dashboard</h1>
        <hr> 
        <h1>
            Item Code : ${item.code} <br>
            Item Description : ${item.description} <br> 
            <br> 
            Item Unit Of Measurement Entry - Code : ${item.unitOfMeasurementGroup.ugpEntry} <br> 
            Item Unit Of Measurement Group - Code : ${item.unitOfMeasurementGroup.ugpCode} <br> 
            Item Unit Of Measurement Group - Name: ${item.unitOfMeasurementGroup.ugpName} <br>  
        </h1>



        <table border="1">
            <tr>
                <th>UoM Entry</th>
                <th>UoM Name</th>
                <th>Altercode</th>
                <th>Text</th>
                <th>Items in Package</th>
            </tr>
            <c:forEach items="${item.unitOfMeasurementGroup.unitOfMeasurements}" var="unitOfMeasurement">
                <c:forEach items="${unitOfMeasurement.value.altercodeContainers}" var="altercodeContainer">
                    <tr>
                        <td>${unitOfMeasurement.value.uomEntry}</td>
                        <td>${unitOfMeasurement.value.uomName}</td>
                        <td>${altercodeContainer.altercode}</td>
                        <td>${altercodeContainer.altercodeName}</td>
                        <td>${unitOfMeasurement.value.baseQuantity}</td>
                    </tr>

                </c:forEach>
                <tr>
                    <td>-----</td>
                </tr>
            </c:forEach>
        </table>

        <hr>
        <table border="1">
            <thead>
                <tr>
                    <th>Ugp Entry</th>
                    <th>Ugp Code</th>
                    <th>Ugp Name </th>
                    <th>Uom Entry</th>
                    <th>Uom Code</th>
                    <th>Uom Name</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="entry" items="${allUnitOfMeasurementGroups}">
                    <tr>
                        <td>${entry.key}</td> <!-- Key of the LinkedHashMap (Short type) -->
                        <td>${entry.value.ugpCode}</td> <!-- Property of SapUnitOfMeasurementGroup -->
                        <td>${entry.value.ugpName}</td> <!-- Property of SapUnitOfMeasurementGroup -->
                        <td>
                            <table>
                                <c:forEach var="unit" items="${entry.value.unitOfMeasurements}">
                                    <tr>
                                        <td>${unit.value.uomEntry}</td> 
                                        <td>${unit.value.uomCode}</td> 
                                        <td>${unit.value.uomName}</td> 
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