<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Sap Camelot Item Dashboard</title>
    </head>
    <body>
        <h1>Sap Camelot Item Dashboard</h1>
        <hr> 
        <h1>
            Item Name : ${item.code} <br>
            Item Description : ${item.description} <br>  
        </h1>

        <c:if test="${empty altercodes}">
            <p>No altercodes found.</p>
        </c:if>

        <ul>
            <c:forEach items="${altercodes}" var="altercode">
                <li>${altercode}</li>
            </c:forEach>
        </ul>

        <!-- Debugging -->
        <p>Item: ${item != null ? item : "Item is null"}</p>
        <p>Altercodes: ${altercodes != null ? altercodes : "Altercodes is null"}</p>
    </body>
</html>