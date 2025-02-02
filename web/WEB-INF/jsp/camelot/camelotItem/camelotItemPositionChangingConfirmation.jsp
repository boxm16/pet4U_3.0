<%-- 
    Document   : searchDashboard
    Created on : Feb 25, 2023, 5:14:04 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Item Position Changing Confirmation Page</title>
        <style>
            input[type="text"]
            {
                font-size:30px;
            }
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body>
    <center>
        <div class="container" style="background-color: #D052DB">
            <div class="row">
                <div class=" col-sm-4">

                </div>
                <div class=" col-sm-4">
                    <center>
                        <h3>Camelot Item Position Changing Confirmation Page</h3>
                        <h3><a href='index.htm'>INDEX</a></h3>
                        <hr>
                        <h3>Confirm Position Changing
                            Item Code ${itemCode}<br>
                            New Position: ${pickingPositionName}<br>

                        </h3>
                        <hr>
                        <form action="changeCamelotItemPosition.htm" method="POST" >
                            <input hidden name="itemId" value="${itemCode}">
                            <input hidden name="newPositionId" value="${pickingPositionName}">
                            <br>
                            <input class="btn btn-primary btn-lg btn-block"  type="submit" value="Submit">
                        </form>
                    </center>
                </div>

                <div class=" col-sm-4">

                </div>
            </div>
        </div>
    </center>
</body>
</html>
