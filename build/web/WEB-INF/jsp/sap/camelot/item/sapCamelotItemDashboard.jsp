
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>ForEach Example</title>
    </head>
    <body>
        <h1>Item Dashboard
            Item Name : ${item.code} <br>
            Item Description : ${item.description} <br>  
        </h1>
        <ul>
            <c:forEach items="${altercodes}" var="AltercodeContainer">
                <li>--</li>
                </c:forEach>
        </ul>

    </body>
</html>