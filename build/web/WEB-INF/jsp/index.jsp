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
                <a href="camelotSearchDashboard.htm" class="btn btn-primary" role="button" style='background-color: #762276;'><h2>Find Camelot Items</h2></a>
                <hr>
                <hr>
                <a href="pet4uDashboard.htm" class="btn btn-outline" role="button" style='background-color:  #baf2ba ; color: green;'><h1 style="font-size: 50px; font-weight :bold">Pet4u<br>Dashboard</h1></a>
                <hr><hr>
                <a href="camelotDashboard.htm" class="btn btn-outline" role="button" style='background-color: #dbb4c9 ; color: #762276;'><h1 style="font-size: 50px; font-weight : bold">Camelot<br>Dashboard</h1></a>
                <hr><hr>
                <hr><hr>
                <a href="goForAuthorization.htm" class="btn btn-outline" role="button" style='background-color: #f2f218 ; '><h2 style="font-size: 50px; font-weight : bold">Authorization</h2></a>
                <hr><hr>
                 <hr><hr>
                <a href="sapIndex.htm" class="btn btn-outline" role="button" style='background-color: #3a6fd8 ; '><h2 style="font-size: 50px; font-weight : bold">SAP INDEX</h2></a>
                <hr><hr>
            </center>
        </div>
    </body>
</html>
