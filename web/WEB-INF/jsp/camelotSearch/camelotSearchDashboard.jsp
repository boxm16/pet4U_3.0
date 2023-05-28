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
        <title>Camelot Item Search Dashboard</title>
        <Style>
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
        <div class="container" style="background-color: #D052DB ">
            <div class="row">
                <div class=" col-sm-4">

                </div>
                <div class=" col-sm-4">
                    <center>
                        <h1>Camelot Item Search</h1>
                        <h1><a href='index.htm'>INDEX</a></h1>
                        <hr>
                        <h3>Find Multiple Camelot Items Using <strong>ALTERCODE MASK</strong></h3>
                        <h4>Use % as wildcard</h4>
                        <h1>
                            <form action="findCamelotItemsByAltercodeMask.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='altercodeMask' value="%">
                                <br>
                                <input class="btn btn-primary btn-lg btn-block"  type="submit" value="Submit">
                            </form>
                        </h1>
                        <hr>
                        <div STYLE="background-color:#000000; height:10px; width:100%;"></div>

                        <hr>
                        <h3>Find Multiple Camelot Items Using <strong>DESCRIPTION MASK</strong></h3>
                        <h4>Use % as wildcard</h4>
                        <h1>
                            <form action="findCamelotItemsByADescriptionMask.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='descriptionMask' value="%">
                                <br>
                                <input class="btn btn-warning btn-lg btn-block"  type="submit" value="Submit">
                            </form>
                        </h1>
                        <hr>
                        <div STYLE="background-color:#000000; height:10px; width:100%;"></div>


                        <h3>Find Single Camelot Item By <strong>Exact Camelot Item Altercode/Barcode</strong></h3>
                        <h1>
                            <form action="findCamelotItemByAltercode.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='altercode'>
                                <br>
                                <input class="btn btn-success btn-lg btn-block"  type="submit" value="Submit">
                            </form>
                        </h1>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>
        </div>
    </center>
</body>
</html>
