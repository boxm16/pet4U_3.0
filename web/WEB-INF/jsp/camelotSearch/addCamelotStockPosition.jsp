<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Spring 3 MVC HashMap Form - viralpatel.net</title>
    </head>
    <body>

        <h2>Spring 3 MVC HashMap Form</h2>
        <form:form method="post" action="add.htm" modelAttribute="contactForm">
            <table>
                <tr>
                    <th>Key</th>
                    <th>Value</th>
                </tr>
                <c:forEach items="${stockPositions}" var="contactMap" varStatus="status">
                    <tr>
                        <td>${contactMap.key}</td>
                        <td><input name="contactMap['${contactMap.key}']" value="${contactMap.value}"/></td>
                    </tr>
                </c:forEach>
            </table>	
            <br/>

            <input type="submit" value="Save" />

        </form:form>
    </body>
</html>