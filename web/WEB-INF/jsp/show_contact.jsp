<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Spring 3 MVC HashMap Form - viralpatel.net</title>
</head>
<body>
	<h2>Show Contact</h2>
	<table>
		<tr>
			<th>Key</th>
			<th>Value</th>
		</tr>
		<c:forEach items="${contactForm.contactMap}" var="contactMap"
			varStatus="status">
			<tr>
				<td>${contactMap.key}</td>
				<td>${contactMap.value}</td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<input type="button" value="Back" onclick="javascript:history.back()" />
</body>
</html>