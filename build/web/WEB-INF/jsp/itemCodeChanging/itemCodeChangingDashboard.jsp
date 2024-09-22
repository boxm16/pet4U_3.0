<%-- 
    Document   : itemCodeChangingDashboard
    Created on : Sep 22, 2024, 4:39:20 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Item Code</title>
    </head>
    <body>
    <center>
                <h1>Change Item Code</h1>
        <hr>
        <form action="changeItemCode.htm" method="POST">
            <h1>  <input type="text" name='oldItemCode'></h1>
            <hr>
            <h1>  <input type="text" name='newItemCode'></h1>
            <button type="submit" class="btn btn-secondary"> <h1> Change Item Code In MySQL DataBase Tables </h1></button>
        </form>
        <hr><hr>
    </center>
</body>
</html>
