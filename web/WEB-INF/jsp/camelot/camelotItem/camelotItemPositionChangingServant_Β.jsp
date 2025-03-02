<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Set  New Position</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body onload="markAsFullyLoaded()">
        <div class="container">
            <div class="row">
                <div class="col-md-auto">
                    <center>
                        <h2 id="title" style="background-color:red">Change  Position</h2>

                        <h3> <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Find Position" title="Type in a name">  </h3>
                        <table id="myTable" class="table table-bordered" style="width:700px; font-size:25px" >
                            <c:forEach items="${camelotPickingPositions}" var="position" varStatus="status">
                                <tr>
                                    <td style="width: 200px"> 
                                        ${position}
                                    </td>
                                    <td style="width: 50px">  
                                <center>  
                                    <form action="confirmCamelotItemPositionChanging.htm"  method="POST">
                                        <input hidden name="itemCode" value="${itemCode}">
                                        <input hidden name="pickingPositionName" value="${position}">
                                        <button type="submit" class="btn btn-primary">
                                            Set Position
                                        </button>
                                    </form>
                                </center>
                                </td>
                                </tr>
                            </c:forEach>
                        </table>	
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

            function markAsFullyLoaded() {
                let title = document.getElementById("title");
                title.style.backgroundColor = "green";
            }

        </script>

    </body>
</html>