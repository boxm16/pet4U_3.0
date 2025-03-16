
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>ForEach Example</title>
    </head>
    <body>
        <h2>Item Dashboard</h2>
        Item Name : ${item.code} <br>
        Item Description : ${item.description} <br>
        <ul>
            <c:forEach items="${altercodes}" var="AltercodeContainer">
                <li>${AltercodeContainer.altercode}</li>
                </c:forEach>
        </ul>
    </body>
</html>