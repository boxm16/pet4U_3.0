<%-- 
    Document   : orderServant
    Created on : Dec 10, 2022, 11:32:35 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Stock Positions Deletion</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>

        </style>
    </head>
    <body>
        <div class="container" >
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">

                    <center>
                        <h1 style="background-color: red"> ΠΡΟΣΟΧΗ </h1>
                        <h1 style="background-color: red"> ΠΑΣ ΝΑ ΣΒΗΣΕΙΣ ΘΕΣΗ ΣΤΟΚ. Η ΠΡΑΞΗ ΕΙΝΑΙ ΑΜΕΤΑΚΛΗΤΗ</h1>

                        <a href="deleteCameltoStockPosition.htm?itemCode=${itemCode}&id=${id}" class="btn btn-danger" role="button"><h1>ΚΙ ΟΜΩΣ, ΣΒΗΝΩ</h1></a>
                        <hr>
                        <hr>
                        <a href="camelotStockPositions.htm?itemCode=${itemCode}" class="btn btn-info" role="button"><h1>ΔΕ ΣΒΗΝΩ</h1></a>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>

        </div>
    </body>
</html>