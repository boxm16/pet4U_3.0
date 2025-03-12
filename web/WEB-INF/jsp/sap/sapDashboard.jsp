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
        <a href="addBarcodeWithUoM.htm"><h1>Add Barcode With UoM</h1></a>
        <hr>
        <a href="getAllCamelotItemsFromSapHanaView.htm" target="_blank"><h1>Get All Camelot Items</h1></a>

    </body>
</html>
