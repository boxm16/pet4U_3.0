<%-- 
    Document   : searchErrorPage
    Created on : Dec 5, 2024, 11:46:30 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Error Page</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">
                    <center>
                        <h3 style='color:red'>Target Altercode: ${target}); </h3>
                        <hr>
                        <br> <h3 style='color:red'>Item with that altercode<br>could not be found. </h3>
                        <hr>
                        <br> <h3 style='color:red'>Try again  </h3>

                        <a href="camelotSearchDashboard.htm"><h3>New Search</h3></a>
                        <hr>
                        <a href="index.htm"><h3>INDEX</h3></a>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>
        </div>
    </body>
</html>
