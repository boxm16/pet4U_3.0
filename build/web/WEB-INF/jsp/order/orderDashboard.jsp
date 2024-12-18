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
        <form action="ordersTimeStructureOfDate.htm" method="POST" target="_blank">
            <h1>  <input type="date"  name="date" value="${date}"></h1>
            <hr>
            <button type="submit" class="btn btn-info"> <h1>Show orders Time Structure FOR DATE</h1></button>
        </form>
        <hr><hr>
        <form action="ordersForDate.htm" method="POST">
            <h1>  <input type="date"  name="date" value="${date}"></h1>
            <hr>
            <button type="submit" class="btn btn-danger"> <h1>Show orders FOR DATE</h1></button>
        </form>
        <hr>
        <form action="ordersOfDate.htm" method="POST">
            <h1>  <input type="date"  name="date" value="${date}"></h1>
            <hr>
            <button type="submit" class="btn btn-primary"> <h1>Show orders OF DATE</h1></button>
        </form>
        <hr>
        <hr>
        <form action="getOrder.htm" method="POST">
            <h1>  <input type="text" name='orderNumber'></h1>
            <hr>
            <button type="submit" class="btn btn-primary"> <h1> Get  Order </h1></button>
        </form>
        <hr>
        <form action="getOrderById.htm" method="POST">
            <h1>  <input type="text" name='orderId'></h1>
            <hr>
            <button type="submit" class="btn btn-secondary"> <h1> Get  Order By Id </h1></button>
        </form>
        <hr>

        <hr>
        <h1>Statistics after 01.01.2024 till today</h1>
        <hr>
        <a href="codeQuantityInOrders.htm" class="btn btn-primary" role="button" style='background-color: green;'><h1>Code Quantity In Orders - Analysis</h1></a>
        <hr>
        <hr>

        <a href="positionsTraffic.htm" class="btn btn-primary" role="button" style='background-color: blue;'><h1>Positions Traffic - Analysis</h1></a>
        <hr>
        <hr>
        <a href="positionsBlockTraffic.htm" class="btn btn-primary" role="button" style='background-color: blue;' target="_blank"><h1>Positions Block Traffic - Analysis</h1></a>
        <hr>
        <hr>
        <form action="positionsBlockTrafficForPeriod.htm" method="POST" target="_blank">
            <h1>  <input type="date"  name="startDate" value="${startDate}"></h1>
            <h1>  <input type="date"  name="endDate" value="${nowDate}"></h1>
            <hr>
            <button type="submit" class="btn btn-primary"> <h1>Positions Block Traffic For Period Of Time</h1></button>
        </form>
        <hr>
        <hr>
        <form action="positionsBlockTrafficForPeriodOneOrderOneVisit.htm" method="POST" target="_blank">
            <h1>  <input type="date"  name="startDate" value="${startDate}"></h1>
            <h1>  <input type="date"  name="endDate" value="${nowDate}"></h1>
            <hr>
            <button type="submit" class="btn btn-secondary"> <h1>ბლოკების ტრაფიკინგიგ ანალიზი დროის მონაკვეთის მიხედვით-ერთი შეკვეთა ერთი ვიზიტი </h1></button>
        </form>
        <hr>
        <hr>
        <form action="getAllDocsForItemBetweenTwoDates.htm" method="POST" target="_blank">
            <h1>  <input type="text"  name="itemCode" value=""></h1>
            <h1>  <input type="date"  name="startDate" value="${before10DaysDate}"></h1>
            <h1>  <input type="date"  name="endDate" value="${nowDate}"></h1>
            <hr>
            <button type="submit" class="btn btn-info"> <h1>Ολα τα παραστατικά για τον κωδικό </h1></button>
        </form>
        <hr>
        <hr>
        <form action="itemsCollateralPositions.htm" method="POST" target="_blank">
            <h1>  <input type="text"  name="itemCode" value=""></h1>
            <h1>  <input type="date"  name="startDate" value="${before10DaysDate}"></h1>
            <h1>  <input type="date"  name="endDate" value="${nowDate}"></h1>
            <hr>
            <button type="submit" class="btn btn-success"> <h1>Items Collateral Positions </h1></button>
        </form>
        <hr>
        <h1>შედარებითი ანალიტიკა</h1>
        <a href="ordersQuantityComparingAnalysis.htm" class="btn btn-primary" role="button" style='background-color: blue;'><h1>Orders Quantity Comparison</h1></a>
        <hr> <hr> <hr> <hr> <hr> <hr> <hr> <hr>
        <h1>--------------</h1>
        <a href="endoAnalysis.htm" class="btn btn-primary" role="button" style='background-color: blue;' target="_blank"><h1>ენდოდიაკინისის ანალიზი</h1></a>
        <hr>

        <a href="endoAnalysisVMD.htm" class="btn btn-primary" role="button" style='background-color: blue;' target="_blank"><h1>ენდოდიაკინისის ანალიზი VMDGL</h1></a>
        <hr> <hr>
        <h1>POSITIONING</h1>
        <a href="positioning.htm" class="btn btn-primary" role="button" style='background-color: blue;'><h1>Positioning</h1></a>
        <hr>        <hr> <hr>
        <form action="inputOutput.htm" method="POST" target="_blank">
            <h1>  <input type="text"  name="itemCode" value=""></h1>
            <h1>  <input type="date"  name="startDate" value="${before10DaysDate}"></h1>
            <h1>  <input type="date"  name="endDate" value="${nowDate}"></h1>
            <hr>
            <button type="submit" class="btn btn-secondary"> <h1>კოდის  მოძრაობა (შემოსვლა/გასვლა)</h1></button>
        </form>

        <hr>        <hr> <hr>
        <form action="inputOutputAlarms.htm" method="POST" target="_blank">

            <h1>  <input type="date"  name="startDate" value="${before10DaysDate}"></h1>
            <h1>  <input type="date"  name="endDate" value="${nowDate}"></h1>
            SHORT VERSION<input type="checkbox"  name="shortVersion" checked value="shortVersion" style="width:28px;height:28px">
            <hr>
            <button type="submit" class="btn btn-danger"> <h1>კოდების ალარმები</h1></button>
        </form>

        <hr>
        <form action="getItemCodeSenderStore.htm" method="POST" target="_blank">
            <h1>  <input type="text"  name="itemCode" value=""></h1>
            <h1>  <input type="date"  name="startDate" value="${before10DaysDate}"></h1>
            <h1>  <input type="date"  name="endDate" value="${nowDate}"></h1>
            <hr>
            <button type="submit" class="btn btn-success"> <h1>კოდის ენდოდან შემოსვლა</h1></button>
        </form>
        <hr>

    </center>
</body>
</html>
