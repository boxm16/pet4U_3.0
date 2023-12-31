<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>DELTIO APOSOTLHS</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 20px;
            }
            th{
                font-size: 30px;
                font-weight: bold;
                text-align: center;
                background: #eee;
                position: sticky;
                top: 0px;
            }

        </style>
    </head>
    <body>

        <h2>DELTIO APOSOTLHS</h2>
        <form:form method="post" action="save.htm" modelAttribute="contactForm">
            <table>
                <tr>
                    <th>Abbrev</th>
                    <th>Description</th>
                    <th>QTY</th>

                </tr>
                <c:forEach items="${endo.items}" var="item" varStatus="status">
                    <tr>
                        <td><input name="item.code" value="${item.code}"/></td>
                    </tr>
                </c:forEach>
            </table>	
            <br/>
            <input type="submit" value="Save" />

        </form:form>
    </body>
</html>