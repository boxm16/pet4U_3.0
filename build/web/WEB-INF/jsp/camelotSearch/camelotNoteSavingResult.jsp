<%-- 
    Document   : camelotNoteSavingResult
    Created on : Jun 20, 2024, 9:08:12 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class=" col-sm-4">

                </div>
                <div class=" col-sm-4">


                    <center>
                        <hr>

                        <a href="index.htm"><h4>INDEX</h4></a>
                        <hr>
                        <hr>
                        <h1 style="background-color: ${resultColor}"> ${result} </h1>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>
        </div>
    </body>
</html>
