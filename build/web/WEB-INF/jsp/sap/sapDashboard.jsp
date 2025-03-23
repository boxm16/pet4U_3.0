<%-- 
    Document   : sapDashboard
    Created on : Mar 3, 2025, 9:37:26 PM
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
        <h1>Hello To Sap!</h1>
        ${message}
        <hr>   <hr>
        <a href="sapLogin.htm"><h1>Sap Login</h1></a>
        <hr>
        <a href="createSupplier.htm"><h1>Create Supplier</h1></a>
        <hr>
        <a href="createItem.htm"><h1>Create Item</h1></a>
        <hr>
        <a href="addBarcode.htm"><h1>Add Barcode</h1></a>
        <hr>
        <a href="addBarcodeWithUoM1.htm"><h1>Add Barcode With UoM</h1></a>
        <hr>
        <a href="addPalletBarcode.htm"><h1>Add  Pallet Barcode</h1></a>
        <hr>



        <a href="getAllCamelotItemsFromSapHanaView.htm" target="_blank"><h1>Get All Camelot Items</h1></a>
        <hr>
        <a href="getApiCallResponse.htm" target="_blank"><h1>Get Api Call Response</h1></a>




        <form action="getApiCallResponseGET.htm" method="GET">
            <input type="text" name="apiText" style="width: 700px; font-size: 40px;">
            <button type="submit" style="font-size: 40px;">SUBMIT</button>

        </form>

        <hr><hr><hr>

        <a href="createUoMGroup.htm"><h1>Create UoM Group</h1></a>
        <hr><hr><hr><hr>
        <a href="deleteUoMGroup.htm"><h1>Delete UoM Group</h1></a>
        <hr><hr><hr><hr>
        <a href="assignUoMGroupToItem.htm"><h1>Assign UoM Group To Item</h1></a>
        <hr><hr><hr><hr>
        <a href="assignUoM9.htm"><h1>Assign UoM_9 To Item</h1></a>
        <hr><hr><hr><hr>
        <a href="assignUoMToItem.htm"><h1>Assign UoM To Item ---------------</h1></a>
        <hr><hr><hr><hr>

        <hr><hr><hr><hr>
        <a href="createPurchaseOrder.htm"><h1>CREATE PURCHASE ORDER</h1></a>
        <hr><hr><hr><hr>


        <hr><hr><hr><hr><hr><hr><hr><hr>
        <a href="goForCamelotItemsDashboard.htm"><h1>Go For Camelot Items Dashboard</h1></a>
        <hr><hr><hr><hr>



    </body>
</html>
