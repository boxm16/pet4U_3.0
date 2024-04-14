<%-- 
    Document   : authorization
    Created on : Apr 14, 2024, 5:22:42 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Authorization Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body>
        <div class="container">
            <form action="authorize.htm">
                <label for="psw">Password</label>
                <input type="password" id="password" name="password">
                <input type="submit" value="Submit">
            </form>
        </div>


    </body>
</html>
