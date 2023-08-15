<%-- 
    Document   : techMan
    Created on : Oct 23, 2022, 7:13:51 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TechMan Dashboard</title>
    </head>
    <body>
    <center>
        <h1> <a href="index.htm">Index</a></h1>
        <hr>
        <h1>TechMAN</h1>

        <hr>
        <h1> ${uploadsDirectoryStatus} </h1>
        <a href='createUploadDirectory.htm'><h1>Create Upload Directory</h1> </a>
        <h1> ${status} </h1>
        <a href='checkMySqlConnection.htm'><h1>Check MySql Connection</h1> </a>

        <br>
        <h1> ${pet4uDatabaseCreationResult} </h1>
        <a href='createPet4u_DB.htm'><h1>Create Pet4U MySQL DATABASE ON My Server</h1> </a>
        <hr>
        <a href='createSalesDatabaseTable.htm'><h1>Create Sales Database Table</h1> </a>
        <hr>
        <h1> ${salesDatabaseTableResult} </h1>
        <a href='createSalesDatabaseTableX.htm'><h1>Create ---NEW--- Sales Database Table</h1> </a>
        <a href='deleteSalesDatabaseTableX.htm'><h1>DELETE ---NEW--- Sales Database Table</h1> </a>
        <hr>
        <h1> ${notesDatabaseTableResult} </h1>
        <a href='createNotesDatabaseTable.htm'><h1>Create Notes Database Table</h1> </a>
        <a href='deleteNotesDatabaseTable.htm'><h1>Delete Notes Database Table</h1> </a>
        <hr>

        <h1> ${suppliersAndOrdersDatabaseTableResult} </h1>
        <a href='createSuppliersAndOrdersDatabaseTable.htm'><h1>Create Suppliers And Orders Database Table</h1> </a>
        <a href='deleteSuppliersAndOrdersDatabaseTable.htm'><h1>Delete Suppliers And Orders Database Table</h1> </a>
        <hr>
        <hr>
        <hr>
        <h1> ${camelotItemsOfOurInterestDatabaseTablesCreationResult} </h1>
        <a href='createCamelotItemsOfOurInterestDatabaseTables.htm'><h1>Create Camelot Items Of Our Interest Database Tables</h1> </a>
        <br>
        <h1> ${camelotItemsOfOurInterestDatabaseTablesDeletionResult} </h1>
        <a href='deleteCamelotItemsOfOurInterestDatabaseTables.htm'><h1>Delete Camelot Items Of Our Interest Database Tables</h1> </a>
        <br>
        <hr>
        <h1> ${inventoryTableCreationResult} </h1>
        <a href='createInventoryDatabaseTable.htm'><h1>Create Inventory Database Tables</h1> </a>
        <br>
        <h1> ${inventoryTableDeletionResult} </h1>
        <a href='deleteInventoryDatabaseTable.htm'><h1>Delete Inventory Database Tables</h1> </a>
        <br>
        <br>
        <hr>
        <h1> ${deliveryTablesCreationResult} </h1>
        <a href='createDeliveryDatabaseTables.htm'><h1>Create Delivery Database Tables</h1> </a>
        <br>
        <h1> ${deliveryTablesDeletionResult} </h1>
        <a href='deleteDeliveryDatabaseTables.htm'><h1>Delete Delivery Database Tables</h1> </a>

        <hr>
        <h1> ${bestBeforeTableCreationResult} </h1>
        <a href='createBestBeforeDatabaseTables.htm'><h1>Create Best_Before Database Tables</h1> </a>
        <br>
        <h1> ${bestBeforeTableDeletionResult} </h1>
        <a href='deleteBestBeforeDatabaseTables.htm'><h1>Delete Best_Before Database Tables</h1> </a>
        <br>


        <hr>
        <h1> ${supplierTableCreationResult} </h1>
        <a href='createBestBeforeDatabaseTables.htm'><h1>Create Best_Before Database Tables</h1> </a>
        <br>
        <h1> ${bestBeforeTableDeletionResult} </h1>
        <a href='deleteBestBeforeDatabaseTables.htm'><h1>Delete Best_Before Database Tables</h1> </a>
        <br>




    </center>
</body>
</html>
