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

                <a href="searchDashboard.htm" class="btn btn-success" role="button" style='background-color: white; color:green'><h1>Find Pet4u Items</h1></a>
                <hr><hr>
                <a href="camelotSearchDashboard.htm" class="btn btn-light" role="button" style='background-color: white; color: #D052DB'><h1>Find Pet4u Items</h1></a>
                <hr><hr>
                <a href="orderAlert.htm" class="btn btn-primary" style='background-color: #762276' role="button"><h1>CAMELOT ORDER ALERT</h1></a>
                <hr> 
                <hr>
                <a href="endoParalaves.htm" class="btn btn-primary" role="button"><h1>ΕΝΔΟ ΠΑΡΑΛΑΒΕΣ</h1></a>
                <a href="endoApostoles.htm" class="btn btn-primary" style='background-color: green' role="button"><h1>ΕΝΔΟ ΑΠΟΣΤΟΛΕΣ</h1></a>
                <hr>
                <a href="deliveryDashboard.htm" class="btn btn-primary" style='background-color: red' role="button"><h1>Delivery Dashboard</h1></a>
                <hr>
                <a href="inventoryDashboard.htm"><h1 style="color: #999900">Inventory Dashboard</h1></a>
                <hr>
                <a href="notesDisplay.htm"><h1>Notes</h1></a>

                <hr>
                <a href="camelotNotesCardMode.htm"><h1>Camelot Notes:Card Mode</h1></a>
                <hr>
                <a href="camelotNotesDisplay.htm"><h1>Camelot Notes</h1></a>
                <hr>
                <a href="camelotStockPositionsDisplay.htm"><h1>Camelot Stock Positions Display</h1></a>

                <hr>
                <a href="adminIndex.htm"><h1>Admin Index</h1></a>

            </center>
        </div>
    </body>
</html>
