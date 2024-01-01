<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>DELTIO APOSOTLHS DISPLAY</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


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
    <center>
        <h4><a href="index.htm">Index</a></h4>
        <h4>DELTIO APOSOTLHS DISPLAY</h4>
        <hr>
        <center> <input type="text" onkeypress="addItem(event, this)"></center>
        <center> <p id="descriptionDisplay"></center>

        <hr>

        <input name="id" value="${endo.id}"/>
        <input name="dateString" type="date">
        <input name="type" value="APOSTOLI" />
        <input name="sender" value="XALKIDONA" />
        <input name="receiver" value="VARIBOBI"/>
        <table>
            <thead> 
                <tr>
                    <th>Abbrev</th>
                    <th>Description</th>
                    <th>QTY</th>
                </tr>
            </thead>
            <tbody id='tableBody'>
                <c:forEach items="${endo.items}" var="itemEntry" varStatus="status">
                    <tr>
                        <td><input name="items['${itemEntry.key}'].code" value="${itemEntry.value.code}"/></td>
                        <td><input name="items['${itemEntry.key}'].description" value="${itemEntry.value.description}"/></td>
                        <td><input name="items['${itemEntry.key}'].quantity" value="${itemEntry.value.quantity}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>	
        <br/>


    </center>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


</body>
</html>