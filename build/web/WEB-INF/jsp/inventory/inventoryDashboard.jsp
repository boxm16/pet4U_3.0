<%-- 
    Document   : inventoryDashboard
    Created on : May 14, 2023, 2:40:28 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Dashboard</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body> 
    <center>
        <h1>Inventory Dashboard</h1>
        <hr>
        ${result}
        <br>
        <form action="getItemForInventory.htm" method="GET"> 
            <input type="text" name="altercode">
            <br>
            <br>
            <button type="submint">Get Item  For Inventory</button>
        </form>
        <hr>


        <a href="showInventories.htm" class="btn btn-primary" role="button"><h1>ΔΕΣ ΑΠΟΓΡΑΦΕΣ</h1></a>

    </center>
</body>
</html>
