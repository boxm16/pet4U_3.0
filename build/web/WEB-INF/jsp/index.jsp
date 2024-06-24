<%@page import="Service.Scheduler"%>
<%@page import="Service.StaticsDispatcher"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pet4U 3.0</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>

    <body>
        <%
            if (!StaticsDispatcher.isTimerOn()) {
                Scheduler scheduler = new Scheduler();
                scheduler.startScheduledTasks();
                StaticsDispatcher.setTimerOn();
            }
        %>
        <div class="container">
            <center>
                <hr>
                <a href="searchDashboard.htm" class="btn btn-primary" role="button" style='background-color: green;'><h1>Find Pet4u Items</h1></a>
                <hr><hr>
                <a href="camelotSearchDashboard.htm" class="btn btn-primary" role="button" style='background-color: #762276;'><h1>Find Camelot Items</h1></a>
                <hr><hr>
                <a href="endoParalaves.htm" class="btn btn-primary" role="button" style='background-color: #0C3CDD'><h1>ΕΝΔΟ ΠΑΡΑΛΑΒΕΣ</h1></a>
                <a href="endoApostoles.htm" class="btn btn-primary" role="button" style='background-color: #55DD0C'><h1>ΕΝΔΟ ΑΠΟΣΤΟΛΕΣ</h1></a>
                <hr>
                <a href="camelotOrderAlertSV.htm" class="btn btn-primary" style='background-color: #762276' role="button"><h1>CAMELOT ORDER ALERT</h1></a>
                <hr> 
                <a href="deliveryDashboard.htm" class="btn btn-primary" style='background-color: red' role="button"><h1>Delivery Dashboard</h1></a>
                <hr> <hr> <hr>
                <a href="deliveryDashboard_X.htm" class="btn btn-primary" style='background-color: red' role="button"><h1>Delivery Dashboard _ X</h1></a>
                <hr> <hr> <hr>
                <a href="camelotNotesDisplay.htm" class="btn btn-primary" role="button" style='background-color: #B48199'><h1>Camelot Notes</h1></a>
                <hr>
                <a href="camelotNotesCardMode.htm"><h1>Camelot Notes:Card Mode</h1></a>

                <hr>
                <a href="notesDisplay.htm" class="btn btn-primary" role="button" style='background-color: #2DEE0F'><h1>Pet4U Notes</h1></a>
                <hr>
                <a href="notesDisplayCardMode.htm"><h1>Pet4U Notes: Card Mode</h1></a>
                <hr>
                <a href="camelotStockPositionsDisplay.htm"><h1>Camelot Stock Positions By Position </h1></a>
                <hr>
                <a href="camelotStockPositionsByItemCodeDisplay.htm"><h1>Camelot Stock Positions By Item Code </h1></a>
                <hr>
                <a href="inventoryDashboard.htm"><h1 style="color: #999900">Inventory Dashboard</h1></a>
                <hr>
                <a href="adminIndex.htm"><h1>Admin Index</h1></a>

            </center>
        </div>
    </body>
</html>
