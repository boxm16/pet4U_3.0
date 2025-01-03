<%@page import="Service.Scheduler"%>
<%@page import="Service.StaticsDispatcher"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pet4U 3.0 VI</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
            hr {
                border-top: 1px solid red;
            }

        </style>
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

                <hr> 
                <hr>
                <h1 style="color: blue"> <a href="endoDashboard.htm">Endo Dashboard</a></h1> 
                <a href="endoParalaves.htm" class="btn btn-primary" role="button" style='background-color: #0C3CDD'> <h1>ΕΝΔΟ ΠΑΡΑΛΑΒΕΣ</h1></a>
                <a href="endoApostoles.htm" class="btn btn-primary" role="button" style='background-color: #55DD0C'> <h1>ΕΝΔΟ ΑΠΟΣΤΟΛΕΣ</h1></a>
                <hr>
                <a href="orderAlert.htm" class="btn btn-primary" style='background-color: #762276' role="button"><h1>CAMELOT ORDER ALERT</h1></a>
                <hr>
                <a href="camelotOrderAlert.htm" class="btn btn-primary" style='background-color: #762276' role="button"><h1>CAMELOT ORDER ALERT FULL VERSION</h1></a>
                <hr>
                <a href="deliveryDashboard.htm" class="btn btn-primary" style='background-color: red' role="button"><h1>Delivery Dashboard</h1></a>
                <hr>
                <a href='pet4uNegativeStock.htm'><h1 style="color: gray">Pet4u Negative Stock</h1></a>
                <hr>
                <a href="inventoryDashboard.htm"><h1 style="color: #999900">Inventory Dashboard</h1></a>
                <hr>
                <a href="bestBeforeDashboard.htm"><h1 style="color: #CB230F">Best Before Dashboard</h1></a>
                <hr>
                <a href="bestBeforeDashboardCardMode.htm"><h1 style="color: #CB230F">Best Before Dashboard Card Mode</h1></a>
                <hr>
                <!--  <a href="dayStockShortageAlert.htm"><h1 style="color: green">Day Stock Shortage Alert</h1></a>
                  <hr>
                -->

                <a href="itemsStateUpdates.htm"><h1 style="color: #63E32C">Show Items State Updates</h1></a>
                <hr>

                <h1><a href='sixMonthsSalesY.htm'>Pet4u:Last Six Months Sales Y</a></h1>
                <hr><hr>
                <hr>
                <a href="notesDisplay.htm"><h1>Notes</h1></a>
                <hr>
                <a href="notesDisplayCardMode.htm"><h1>Notes: Card Mode</h1></a>
                <hr>
                <a href="camelotNotesCardMode.htm"><h1>Camelot Notes:Card Mode</h1></a>
                <hr>
                <a href="camelotNotesDisplay.htm"><h1>Camelot Notes</h1></a>
                <hr>
                <a href="camelotStockPositionsDisplay.htm"><h1>Camelot Stock Positions Display</h1></a>


                <!--  <hr><hr> 
                   <h1><a href='monthSales.htm'>Pet4u:Last Six Months Sales By Month</a></h1>
                   <hr><hr> -->

                <hr><hr><hr>
                <h1><a href='pet4uAllItems.htm'>Pet4u: All Items</a></h1>
                <hr>

                <h1><a href='camelotAllItems.htm'>Camelot: All Items</a></h1>
                <hr>
                <h1><a href='camelotSixMonthsSales.htm'>Camelot 6 Months Sales</a></h1>
                <hr>

                <h1><a href='itemsFromCamelot.htm'>Pet4U: All Items From Camelot</a></h1>
                <hr>

                <h1><a href='camelotItemsWithPossitionDifference.htm'>Show Camelot Items With Position`s Difference</a></h1>
                <hr>
                <a href="weightItems.htm"><h1>Show Items With Weight</h1></a>





                <a href="scanninger.htm"><h1>Go for Scanning</h1></a>
                <hr>


                <a href="index.htm"><h1>INDEX</h1></a>
                <hr>   <hr>
                <a href="signOut.htm"><h1>Sign Out</h1></a>
            </center>
        </div>
    </body>
</html>
