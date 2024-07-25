<%-- 
    Document   : orderDashboard
    Created on : Jul 24, 2024, 12:25:18 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Order Dashboard</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


    </head>
    <body>
    <center>
        <hr>
        <form action="ordersForDate.htm" method="POST">
            <h1>  <input type="date"  name="date" value="${date}"></h1>
            <hr>
            <button type="submit" class="btn btn-danger"> <h1> Show  Orders </h1></button>
        </form>
        <hr>
        <hr>
        <hr>
        <a href="ordersSixMonthsStatistics.htm" class="btn btn-primary" role="button" style='background-color: green;'><h1>Στατιστικά για πρώτο εξάμηνο</h1></a>
    </center>
</body>
</html>
