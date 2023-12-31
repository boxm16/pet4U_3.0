<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Show contacts</title>
    </head>
    <body>

        <h2>Show contacts</h2>
  
        <table>
            <tr>
                <th>No.</th>
                <th>Name</th>
                <th>Lastname</th>
                <th>Email</th>
                <th>Phone</th>
            </tr>
            <c:forEach items="${contactForm.contacts}" var="contact" varStatus="status">
                <tr>
                    <td align="center">${status.count}</td>
                    <td><input name="contacts[${status.index}].firstname" value="${contact.firstname}"/></td>
                    <td><input name="contacts[${status.index}].lastname" value="${contact.lastname}"/></td>
                    <td><input name="contacts[${status.index}].email" value="${contact.email}"/></td>
                    <td><input name="contacts[${status.index}].phone" value="${contact.phone}"/></td>
                </tr>
            </c:forEach>
        </table>	
        <br/>
      

  
</body>
</html>