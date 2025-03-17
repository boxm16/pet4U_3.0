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
            Item Unit Of Measurement Code : ${item.unitOfMeasurementGroup.ugpCode} <br> 
            Item Unit Of Measurement Group : ${item.unitOfMeasurementGroup.ugpName} <br>  
        </h1>


        <table border="1">
            <tr>
                <th>Alternate Code</th>
                <th>Status</th>
                <th>Items in Package</th>
            </tr>
            <c:forEach items="${item.altercodes}" var="altercode">
                <tr>
                    <td>${altercode.altercode}</td>
                    <td>${altercode.status}</td>
                    <td>${altercode.itemsInPackage}</td>
                </tr>
            </c:forEach>
        </table>

        <ul>
            <c:forEach items="${item.altercodes}" var="altercode">
                <li>${altercode.altercode} : ${altercode.status} : ${altercode.itemsInPackage}</li>
                </c:forEach>
        </ul>

    </body>
</html>