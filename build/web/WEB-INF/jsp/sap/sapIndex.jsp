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
                <a href="---.htm" class="btn btn-primary" role="button" style='background-color: green;'><h1>-------</h1></a>
                <hr><hr>
                <a href="sapCamelotSearchDashboard.htm" class="btn btn-primary" role="button" style='background-color: #762276;'><h2>Find Camelot Items</h2></a>
                <hr><hr><hr><hr>
                <a href="camelotItemsDashboard.htm"><h1>Camelot Items Dashboard</h1></a>
                <hr><hr><hr><hr>
                <a href="camelotUnitOfMeasurementDashboard.htm"><h1>Unit Of Measurement Dashboard</h1></a>
                <hr><hr><hr><hr>
                <hr><hr><hr><hr>
                <a href="camelotDeliveryDashboard.htm"><h1>Delivery Dashboard</h1></a>
                <hr><hr><hr><hr>
                <hr><hr><hr><hr>
                <a href="camelotDeliveryDashboardX.htm"><h1>Delivery Dashboard X</h1></a>
                <hr><hr><hr><hr>


            </center>
        </div>
    </body>
</html>
