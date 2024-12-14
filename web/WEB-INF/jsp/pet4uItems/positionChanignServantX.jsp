<%-- 
    Document   : positionChanignServantX
    Created on : Dec 14, 2024, 11:44:44 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <body>
        <h1>Hello World!</h1>

        <form action="changePet4uItemPosition.htm"  method="POST">
            <input hidden name="itemId" value="${itemId}">
            <input type="text" name="row" value="">
            <input type="number" name="blockNumber" value="">
            <input type="number" name="subBlock" value="">
            <input type="number" name="positionNumber" value="">
            <button type="submit" class="btn btn-primary">
                Set Position
            </button>
        </form>
    </body>
</html>
