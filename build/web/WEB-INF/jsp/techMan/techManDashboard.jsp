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
        <br>
        <h1> ${pet4uDatabaseCreationResult} </h1>
        <a href='createPet4u_DB.htm'><h1>Create Pet4U MySQL DATABASE ON My Server</h1> </a>
        <hr>
        <a href='createSalesDatabaseTable.htm'><h1>Create Sales Database Table</h1> </a>

        <hr>
        <h1 style="color:red"> ${salesDatabaseTableResult} </h1>
        <a href='createSales_1_2022_DatabaseTable.htm'><h1>Create Sales 1-6/2022 Database Table</h1> </a>
        <a href='createSales_2_2022_DatabaseTable.htm'><h1>Create Sales 7-12/2022 Database Table</h1> </a>

        <a href='deleteSales_1_2022_DatabaseTable.htm'><h1>Delete Sales 1-6/2022 Database Table</h1> </a>
        <a href='deleteSales_2_2022_DatabaseTable.htm'><h1>Delete Sales 7-12/2022 Database Table</h1> </a>
        <hr>
        <h1> ${notesDatabaseTableResult} </h1>
        <a href='createNotesDatabaseTable.htm'><h1>Create Notes Database Table</h1> </a>
        <a href='deleteNotesDatabaseTable.htm'><h1>Delete Notes Database Table</h1> </a>
        <hr>

        <h1> ${ordersDatabaseTableResult} </h1>
        <a href='createOrdersDatabaseTable.htm'><h1>Create Orders Database Table</h1> </a>
        <a href='deleteOrdersDatabaseTable.htm'><h1>Delete Orders Database Table</h1> </a>
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
        <br>
        <br>
        <br>
        <hr>
        <h1> ${bestBeforeTableCreationResult} </h1>
        <a href='createBestBeforeDatabaseTables.htm'><h1>Create Best_Before Database Tables</h1> </a>
        <br>
        <h1> ${bestBeforeTableDeletionResult} </h1>
        <a href='deleteBestBeforeDatabaseTables.htm'><h1>Delete Best_Before Database Tables</h1> </a>
        <br>



    </center>
</body>
</html>
