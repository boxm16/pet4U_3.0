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


        <h1> ${suppliersDatabaseTableResult} </h1>
        <a href='createSuppliersDatabaseTable.htm'><h1>Create Suppliers  Database Table</h1> </a>
        <a href='deleteSuppliersDatabaseTable.htm'><h1>Delete Suppliers  Database Table</h1> </a>
        <hr>

        <hr>
        <h1> ${stockManagementTableCreationResult} </h1>
        <a href='createStockManagementDatabaseTables.htm'><h1>Create Stock Management Database Tables</h1> </a>
        <br>
        <h1> ${stockManagementTableDeletionResult} </h1>
        <a href='deleteStockManagementDatabaseTables.htm'><h1>Delete Stock Management Database Tables</h1> </a>
        <br>

        <hr>
        <h1> ${pet4uItemStateTableCreationResult} </h1>
        <a href='createPet4uItemStateDatabaseTables.htm'><h1>Create Pet4u Item State Database Tables</h1> </a>
        <br>
        <h1> ${pet4uItemStateTableDeletionResult} </h1>
        <a href='deletePet4uItemStateDatabaseTables.htm'><h1>Delete  Pet4u Item State Database Tables</h1> </a>
        <br>

        <hr>
        <h1> ${monthSalesTableCreationResult} </h1>
        <a href='createMonthSalesDatabaseTables.htm'><h1>Create Month Sales Database Tables</h1> </a>
        <br>
        <h1> ${monthSalesTableDeletionResult} </h1>
        <a href='deleteMonthSalesDatabaseTables.htm'><h1>Delete Month Sales Database Tables</h1> </a>
        <br>
        <hr>
        <h1> ${offersTableCreationResult} </h1>
        <a href='createOffersDatabaseTables.htm'><h1>Create Offers Database Tables</h1> </a>
        <br>
        <h1> ${offersTableDeletionResult} </h1>
        <a href='deleteOffersDatabaseTables.htm'><h1>Delete Offers Database Tables</h1> </a>
        <br>
        <hr>
        <h1> ${royalTableCreationResult} </h1>
        <a href='createRoyalDatabaseTables.htm'><h1>Create Royal Database Tables</h1> </a>
        <br>
        <h1> ${royalTableDeletionResult} </h1>
        <a href='deleteRoyalDatabaseTables.htm'><h1>Delete Royal Database Tables</h1> </a>
        <br>

        <hr>
        <h1> ${pet4uStockSnapshotTableCreationResult} </h1>
        <a href='createPet4uStockSnapshotDatabaseTables.htm'><h1>Create Pet4u Stock Snapshot Database Tables</h1> </a>
        <br>
        <h1> ${pet4uStockSnapshotTableDeletionResult} </h1>
        <a href='deletePet4uStockSnapshotDatabaseTables.htm'><h1>Delete Pet4u Stock Snapshot Database Tables</h1> </a>
        <br>


        <hr>
        <h1> ${endotTableCreationResult} </h1>
        <a href='createEndoDatabaseTables.htm'><h1>Create ENDO Database Tables</h1> </a>
        <br>
        <h1> ${endoTableDeletionResult} </h1>
        <a href='deleteEndoDatabaseTables.htm'><h1>Delete ENDO Database Tables</h1> </a>
        <br>

        <hr>
        <h1> ${endoOrderTablesCreationResult} </h1>
        <a href='createEndoOrderDatabaseTables.htm'><h1>Create ENDO ORDER Database Tables</h1> </a>
        <br>
        <h1> ${endoOrderTablesDeletionResult} </h1>
        <a href='deleteEndoOrderDatabaseTables.htm'><h1>Delete ENDO ORDER Database Tables</h1> </a>
        <br>


        <hr>
        <h1> ${endoLockerTablesCreationResult} </h1>
        <a href='createEndoLockerDatabaseTables.htm'><h1>Create ENDO LOCKER Database Tables</h1> </a>
        <br>
        <h1> ${endoLockerTablesDeletionResult} </h1>
        <a href='deleteEndoLockerDatabaseTables.htm'><h1>Delete ENDO LOCKER Database Tables</h1> </a>
        <br>

        <h1> ${camelotNotesDatabaseTableResult} </h1>
        <a href='createCamelotNotesDatabaseTable.htm'><h1>Create Camelot Notes Database Table</h1> </a>
        <a href='deleteCamelotNotesDatabaseTable.htm'><h1>Delete Camelot Notes Database Table</h1> </a>
        <hr>
        
          <h1> ${camelotStockPositionsDatabaseTableResult} </h1>
        <a href='createCamelotStockPositionsDatabaseTable.htm'><h1>Create Camelot Notes Database Table</h1> </a>
        <a href='deleteCamelotStockPositionsDatabaseTable.htm'><h1>Delete Camelot Notes Database Table</h1> </a>
        <hr>







    </center>
</body>
</html>
