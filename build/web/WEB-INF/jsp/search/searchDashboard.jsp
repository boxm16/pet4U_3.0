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
        <title>Item Search Dashboard</title>
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
        <div class="container" style="background-color: #35B62F">
            <div class="row">
                <div class=" col-sm-4">

                </div>
                <div class=" col-sm-4">
                    <center>
                        <h3>Pet4u Item Search</h3>
                        <h3><a href='index.htm'>INDEX</a></h3>

                        <hr>
                        <h5>Find Multiple Items Using <strong>ALTERCODE MASK</strong></h5>
                        <h6>Use % as wildcard</h6>
                        <h1>
                            <form action="findItemsByAltercodeMask.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='altercodeMask' value="%">
                                <br>
                                <input class="btn btn-primary btn-lg btn-block"  type="submit" value="Submit">
                            </form>
                        </h1>
                        <hr>
                        <div STYLE="background-color:#000000; height:10px; width:100%;"></div>

                        <hr>
                        <h5>Find Multiple Items Using <strong>DESCRIPTION MASK</strong></h5>
                        <h6>Use % as wildcard</h6>
                        <h1>
                            <form action="findItemsByADescriptionMask.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='descriptionMask' value="%">
                                <br>
                                <input class="btn btn-warning btn-lg btn-block"  type="submit" value="Submit">
                            </form>
                        </h1>
                        <hr>
                        <div STYLE="background-color:#000000; height:10px; width:100%;"></div>


                        <h5>Find Single Item By <strong>Exact Altercode/Barcode</strong></h5>
                        <h1>
                            <form action="findItemByAltercode.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='altercode'>
                                <br>
                                <input class="btn btn-success btn-lg btn-block"  type="submit" value="Submit">
                            </form>
                        </h1>

                        <div STYLE="background-color:#000000; height:10px; width:100%;"></div>


                        <h5><strong>Find Single Line <br>From Microsoft Database<br> Using Altercode Mask</strong></h5>
                        <h1>
                            <form action="deepSearchForAltercodeMask.htm" method="GET" >
                                <input type="text" class="form-control input-lg" name='altercodeMask' value="%">
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
