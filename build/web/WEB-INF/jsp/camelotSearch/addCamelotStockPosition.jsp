<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Set Camelot Stock Position</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-auto">
                    <center>
                        <h2>Set Camelot Stock Position</h2>

                        <h3> <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Find Position" title="Type in a name">  </h3>
                            <form:form method="post" action="add.htm" modelAttribute="contactForm">
                             <table id="myTable" class="table table-bordered" style="width:700px; font-size:25px" >
                                <tr class="header">
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
                    </center>
                </div>
            </div>
        </div>
        <script>
            function myFunction() {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("myInput");
                filter = input.value.toUpperCase();
                table = document.getElementById("myTable");
                tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[0];
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
        </script>

    </body>
</html>