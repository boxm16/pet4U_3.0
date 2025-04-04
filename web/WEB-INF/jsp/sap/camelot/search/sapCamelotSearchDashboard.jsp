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
        <div class="container" style="background-color: #3a6fd8 ">
            <div class="row">
                <div class=" col-sm-4">

                </div>
                <div class=" col-sm-4">
                    <center>
                        <h1>Camelot Item Search SAP</h1>
                        <h1><a href='sapIndex.htm'>INDEX</a></h1>
                        <hr>
                        <h5>Find Single Item By <strong>Exact Altercode/Barcode FROM SAP VIEW</strong></h5>
                        <h1>
                            <form action="findCamelotItemByAltercodeFromSapView.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='altercode'>
                                <br>
                                <input class="btn btn-success btn-lg btn-block"  type="submit" value="Submit">
                            </form>
                        </h1>
                        <hr>
                        <div STYLE="background-color:#000000; height:10px; width:100%;"></div>


                        <h5>Find Single Item By <strong>EXACT ITEM CODE FROM SAP DATABASE</strong></h5>
                        <h1>
                            <form action="findCamelotItemByItemCodeFromSapDB.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='itemCode'>
                                <br>
                                <input class="btn btn-success btn-lg btn-block"  type="submit" value="Submit">
                            </form>
                        </h1>

                        <div STYLE="background-color:#000000; height:10px; width:100%;"></div>

                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>
        </div>
    </center>
</body>
</html>
