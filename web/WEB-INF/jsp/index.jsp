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
                <a href="searchDashboard.htm"><h1 style="color: #35B62F">Find Pet4u Items</h1></a>
                <hr>
                <a href="camelotSearchDashboard.htm"><h1 style="color: #D052DB ">Find Camelot Items</h1></a>
                <hr>
                <a href='camelotItemsOfOurInterestDashboard.htm'><h1 style="color: blue">Camelot: Items Of Our Interest</h1></a>
                <hr>
                <a href='pet4uNegativeStock.htm'><h1 style="color: gray">Pet4u Negative Stock</h1></a>
                <hr>
                <a href="deliveryDashboard.htm"><h1 style="color: red">Delivery Dashboard</h1></a>
                <hr>
                <a href="inventoryDashboard.htm"><h1 style="color: #999900">Inventory Dashboard</h1></a>
                <hr>
                <a href="bestBeforeDashboard.htm"><h1 style="color: #CB230F">Best Before Dashboard</h1></a>
                <hr>
                <a href="dayStockShortageAlert.htm"><h1 style="color: green">Day Stock Shortage Alert</h1></a>
                <hr>
                <a href="suppliersAndStockDashboard.htm"><h1 style="color: #5F74BC">Suppliers And Stock Dashboard</h1></a>
                <hr>
                <a href="itemsStateUpdates.htm"><h1 style="color: #63E32C">Show Items State Updates</h1></a>
                <hr>
                <a href="allOffers.htm"><h1 style="color: #E38D2C">All Offers</h1></a>
                <hr>
                <a href="activeOffers.htm"><h1 style="color: #C4E32C">Active Offers</h1></a>
                <hr>
                <h1><a href='sixMonthsSalesY.htm'>Pet4u:Last Six Months Sales Y</a></h1>
                <hr><hr>
                <hr>
                <a href="notesDisplay.htm"><h1>Notes</h1></a>




                <!--  <hr><hr> 
                   <h1><a href='monthSales.htm'>Pet4u:Last Six Months Sales By Month</a></h1>
                   <hr><hr> -->

                <hr><hr><hr>
                <h1><a href='pet4uAllItems.htm'>Pet4u: All Items</a></h1>
                <hr>

                <h1><a href='camelotAllItems.htm'>Camelot: All Items</a></h1>
                <hr>
                <h1><a href='itemsFromCamelot.htm'>Pet4U: All Items From Camelot</a></h1>
                <hr>

                <h1><a href='camelotItemsWithPossitionDifference.htm'>Show Camelot Items With Position`s Difference</a></h1>
                <hr>
                <a href="weightItems.htm"><h1>Show Items With Weight</h1></a>


                <!--
                 <hr>
                <a href="prosfores.htm"><h1 style="color: #3059D8">ΠΡΟΣΦΟΡΕΣ</h1></a>
                <hr>
                <a href="downloadPet4UExcelData.htm"><h1>Download Pet4u Data In Excel Format</h1></a>
                <hr>
                <hr>
                <a href="goForSalesUpload.htm"><h1>Go For Sales Upload</h1></a>

                <hr>
                <a href="deliveryDemo.htm"><h1>Delivery Demo</h1></a>

                <hr>
                <a href="bestBeforeDashboard.htm"><h1>Go For Best Before Dashboard</h1></a>
                
                <h1><a href='sixMonthsSales.htm'>Pet4u:Last Six Months Sales</a></h1>
                <hr>
                <h1><a href='sixMonthsSalesX.htm'>Pet4u:Last Six Months Sales X</a></h1>
                <hr>
                <hr>
                <a href="goForSalesUploadX.htm"><h1>Go For ----NEW---- Sales Upload</h1></a>
                -->

                <hr>
                <a href="goForMonthSalesUpload.htm"><h1>Go For Month Sales  Upload</h1></a>



            </center>
        </div>
    </body>
</html>
