package TechMan;

import Service.Basement;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TechManController {

    @Autowired
    private TechManDao techManDao;

    @Autowired
    private Basement basement;

    @RequestMapping(value = "techMan")
    public String techMan() {
    //    return "techMan/techManDashboard";
            return "index";
    }

    //-----------------------------------------------------------------------
    @RequestMapping(value = "createUploadDirectory", method = RequestMethod.GET)
    public String createUploadDirectory(ModelMap model) {
        Boolean uploadsDirectoryExists = uploadsDirectoryExists();
        if (uploadsDirectoryExists) {
            String status = "Uploads Directroy already exists <br> Directroy Path:" + this.basement.getBasementDirectory() + "/Pet4U_Uploads";
            model.addAttribute("uploadsDirectoryStatus", status);
            return "techMan/techManDashboard";
        }
        //Creating a File object
        File file = new File(this.basement.getBasementDirectory() + "/Pet4U_Uploads");
        //Creating the directory
        boolean bool = file.mkdir();
        System.out.println(bool);
        String status = "Uploads directory could not been created.";
        if (bool) {
            status = "Uploads directroy has just been created.  <br> Directroy Path:" + this.basement.getBasementDirectory() + "/Pet4U_Uploads";
        }
        model.addAttribute("uploadsDirectoryStatus", status);
        return "techMan/techManDashboard";
    }
    //---------------------------------------------------------

    @RequestMapping(value = "checkMySqlConnection")
    public String checkMySqlConnection(ModelMap model) {
        String status = techManDao.getMySqlConnectionStatus();
        model.addAttribute("uploadsDirectoryStatus", status);
        return "techMan/techManDashboard";
    }

    private Boolean uploadsDirectoryExists() {

        File dir = new File(this.basement.getBasementDirectory() + "/Pet4U_Uploads");
        // Tests whether the directory denoted by this abstract pathname exists.
        return dir.exists();
    }

    //----------------------------------------
    @RequestMapping(value = "createPet4u_DB", method = RequestMethod.GET)
    public String createPet4u_DB(ModelMap model) {
        String pet4uDatabaseCreationResult = techManDao.createPet4u_DB();
        model.addAttribute("pet4uDatabaseCreationResult", pet4uDatabaseCreationResult + "<br>");
        return "techMan/techManDashboard";
    }

    //---------------------------
    @RequestMapping(value = "/createSalesDatabaseTable", method = RequestMethod.GET)
    public String createSalesDatabaseTable(ModelMap modelMap) {
        String createSalesDatabaseTableResult = techManDao.createSalesDatabaseTable();
        createSalesDatabaseTableResult = createSalesDatabaseTableResult + "<br>";
        modelMap.addAttribute("salesDatabaseTableResult", createSalesDatabaseTableResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/createSales_1_2022_DatabaseTable", method = RequestMethod.GET)
    public String createSales_1_2022_DatabaseTable(ModelMap modelMap) {
        String createSales_1_2022_DatabaseTableResult = techManDao.createSales_1_2022_DatabaseTable();
        createSales_1_2022_DatabaseTableResult = createSales_1_2022_DatabaseTableResult + "<br>";
        modelMap.addAttribute("salesDatabaseTableResult", createSales_1_2022_DatabaseTableResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteSales_1_2022_DatabaseTable1", method = RequestMethod.GET)
    public String deleteSales_1_2022_DatabaseTable(ModelMap modelMap) {
        String deleteSales_1_2022_DatabaseTableResult = techManDao.deleteSales_1_2022_DatabaseTable();

        deleteSales_1_2022_DatabaseTableResult = deleteSales_1_2022_DatabaseTableResult + "<br>";
        modelMap.addAttribute("salesDatabaseTableResult", deleteSales_1_2022_DatabaseTableResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/createSales_2_2022_DatabaseTable", method = RequestMethod.GET)
    public String createSales_2_2022_DatabaseTable(ModelMap modelMap) {
        String createSales_2_2022_DatabaseTableResult = techManDao.createSales_2_2022_DatabaseTable();
        createSales_2_2022_DatabaseTableResult = createSales_2_2022_DatabaseTableResult + "<br>";
        modelMap.addAttribute("salesDatabaseTableResult", createSales_2_2022_DatabaseTableResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteSales_2_2022_DatabaseTable1", method = RequestMethod.GET)
    public String deleteSales_2_2022_DatabaseTable(ModelMap modelMap) {
        String deleteSales_2_2022_DatabaseTableResult = techManDao.deleteSales_2_2022_DatabaseTable();

        deleteSales_2_2022_DatabaseTableResult = deleteSales_2_2022_DatabaseTableResult + "<br>";
        modelMap.addAttribute("salesDatabaseTableResult", deleteSales_2_2022_DatabaseTableResult);
        return "techMan/techManDashboard";
    }

    //-----------------
    @RequestMapping(value = "/createNotesDatabaseTable", method = RequestMethod.GET)
    public String createNotesDatabaseTable(ModelMap modelMap) {
        String notesDatabaseTableResult = techManDao.createNotesDatabaseTable();

        notesDatabaseTableResult = notesDatabaseTableResult + "<br>";
        modelMap.addAttribute("notesDatabaseTableResult", notesDatabaseTableResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteNotesDatabaseTable1", method = RequestMethod.GET)
    public String deleteNotesDatabaseTable(ModelMap modelMap) {
        String notesDatabaseTableResult = techManDao.deleteNotesDatabaseTable();

        notesDatabaseTableResult = notesDatabaseTableResult + "<br>";
        modelMap.addAttribute("notesDatabaseTableResult", notesDatabaseTableResult);
        return "techMan/techManDashboard";
    }

    //--------------------------------------------------
    @RequestMapping(value = "/createCamelotItemsOfOurInterestDatabaseTables", method = RequestMethod.GET)
    public String createCamelotItemsOfOurInterestDatabaseTables(ModelMap modelMap) {

        String camelotItemsOfOurInterestTableCreationResult = techManDao.createCamelotItemsOfOurInterestTable();
        String camelotItemsOfOurInterestDayRestTableCreationResult = techManDao.createCamelotItemsOfOurInterestDayRestTable();

        modelMap.addAttribute("camelotItemsOfOurInterestDatabaseTablesCreationResult", camelotItemsOfOurInterestTableCreationResult + "<br>" + camelotItemsOfOurInterestDayRestTableCreationResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteCamelotItemsOfOurInterestDatabaseTables1", method = RequestMethod.GET)
    public String deleteCamelotItemsOfOurInterestDatabaseTables(ModelMap modelMap) {
        //  String camelotItemsOfOurInterestTableDeletionResult = techManDao.deleteCamelotItemsOfOurInterestTable();
        String camelotItemsOfOurInterestTableDeletionResult = "";
        String camelotItemsOfOurInterestDayRestTableDeletionResult = techManDao.deleteCamelotItemsOfOurInterestDayRestTable();

        modelMap.addAttribute("camelotItemsOfOurInterestDatabaseTablesDeletionResult", camelotItemsOfOurInterestTableDeletionResult + "<br>" + camelotItemsOfOurInterestDayRestTableDeletionResult);
        return "techMan/techManDashboard";
    }

    //-------------------------------------
    //-----------------
    @RequestMapping(value = "/createWeightCoefficinetDatabaseTable", method = RequestMethod.GET)
    public String createWeightCoefficinetDatabaseTable(ModelMap modelMap) {
        String weightCoefficinetTableCreationResult = techManDao.createWeightCoefficinetDatabaseTable();

        weightCoefficinetTableCreationResult = weightCoefficinetTableCreationResult + "<br>";
        modelMap.addAttribute("weightCoefficinetTableCreationResult", weightCoefficinetTableCreationResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteWeightCoefficinetDatabaseTable1", method = RequestMethod.GET)
    public String deleteWeightCoefficinetDatabaseTable(ModelMap modelMap) {
        String weightCoefficinetTableDeletionResult = techManDao.deleteWeightCoefficinetDatabaseTable();

        weightCoefficinetTableDeletionResult = weightCoefficinetTableDeletionResult + "<br>";
        modelMap.addAttribute("weightCoefficinetTableDeletionResult", weightCoefficinetTableDeletionResult);
        return "techMan/techManDashboard";
    }
    //-------------------------------------
    //-----------------

    @RequestMapping(value = "/createInventoryDatabaseTable", method = RequestMethod.GET)
    public String createInventoryDatabaseTable(ModelMap modelMap) {

        String inventoryTableCreationResult = techManDao.createInventoryDatabaseTable();

        inventoryTableCreationResult = inventoryTableCreationResult + "<br>";
        modelMap.addAttribute("inventoryTableCreationResult", inventoryTableCreationResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteInventoryDatabaseTable1", method = RequestMethod.GET)
    public String deleteInventoryDatabaseTable(ModelMap modelMap) {
        String inventoryTableDeletionResult = techManDao.deleteInventoryDatabaseTable();

        inventoryTableDeletionResult = inventoryTableDeletionResult + "<br>";
        modelMap.addAttribute("inventoryTableDeletionResult", inventoryTableDeletionResult);
        return "techMan/techManDashboard";
    }

    //-------------------------------------
    //-----------------
    @RequestMapping(value = "/createDeliveryDatabaseTables", method = RequestMethod.GET)
    public String createDeliveryDatabaseTable(ModelMap modelMap) {

        String deliveryTitleTableCreationResult = techManDao.createDeliveryTitleDatabaseTable();
        String deliveryDataTableCreationResult = techManDao.createDeliveryDataDatabaseTable();

        String deliveryTablesCreationResult = deliveryTitleTableCreationResult + " : " + deliveryDataTableCreationResult + "<br>";
        modelMap.addAttribute("deliveryTablesCreationResult", deliveryTablesCreationResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteDeliveryDatabaseTables1", method = RequestMethod.GET)
    public String deleteDeliveryDatabaseTable(ModelMap modelMap) {
        String deliveryTitleTableDeletionResult = techManDao.deleteDeliveryTitleDatabaseTable();
        String deliveryDataTableDeletionResult = techManDao.deleteDeliveryDataDatabaseTable();

        String deliveryTablesDeletionResult = deliveryTitleTableDeletionResult + " : " + deliveryDataTableDeletionResult + "<br>";
        modelMap.addAttribute("deliveryTablesDeletionResult", deliveryTablesDeletionResult);
        return "techMan/techManDashboard";
    }

    //-------------------------------------
    //-----------------
    @RequestMapping(value = "/createBestBeforeDatabaseTables", method = RequestMethod.GET)
    public String createBestBeforeDatabaseTables(ModelMap modelMap) {

        String bestBeforeTableCreationResult = techManDao.createBestBeforeDatabaseTable();
        bestBeforeTableCreationResult = bestBeforeTableCreationResult + "<br>";
        modelMap.addAttribute("bestBeforeTableCreationResult", bestBeforeTableCreationResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteBestBeforeDatabaseTables1", method = RequestMethod.GET)
    public String deleteBestBeforeDatabaseTables(ModelMap modelMap) {
        String bestBeforeTableDeletionResult = techManDao.deleteBestBeforeDatabaseTable();

        bestBeforeTableDeletionResult = bestBeforeTableDeletionResult + "<br>";
        modelMap.addAttribute("bestBeforeTableDeletionResult", bestBeforeTableDeletionResult);
        return "techMan/techManDashboard";
    }

    //---------------------------
    @RequestMapping(value = "/createSalesDatabaseTableX", method = RequestMethod.GET)
    public String createSalesDatabaseTableX(ModelMap modelMap) {
        String result = techManDao.createSalesDatabaseTableX();
        result = result + "<br>";
        modelMap.addAttribute("salesDatabaseTableResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteSalesDatabaseTableX1", method = RequestMethod.GET)
    public String deleteSalesDatabaseTableX(ModelMap modelMap) {
        String result = techManDao.deleteInventoryDatabaseTableX();
        result = result + "<br>";
        modelMap.addAttribute("salesDatabaseTableResult", result);
        return "techMan/techManDashboard";
    }

    //---------------------------
    @RequestMapping(value = "/createSuppliersDatabaseTable", method = RequestMethod.GET)
    public String createSuppliersDatabaseTable(ModelMap modelMap) {
        String suppliersDatabaseTableResult = techManDao.createSuppliersDatabaseTable();

        suppliersDatabaseTableResult = suppliersDatabaseTableResult + "<br>";
        modelMap.addAttribute("suppliersDatabaseTableResult", suppliersDatabaseTableResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteSuppliersDatabaseTable1", method = RequestMethod.GET)
    public String deleteSuppliersDatabaseTable(ModelMap modelMap) {
        String suppliersDatabaseTableResult = techManDao.deleteSuppliersDatabaseTable();

        suppliersDatabaseTableResult = suppliersDatabaseTableResult + "<br>";

        modelMap.addAttribute("suppliersDatabaseTableResult", suppliersDatabaseTableResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/createStockManagementDatabaseTables", method = RequestMethod.GET)
    public String createStockManagementDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createStockManagementDatabaseTable();
        result = result + "<br>";
        modelMap.addAttribute("stockManagementTableCreationResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteStockManagementDatabaseTables1", method = RequestMethod.GET)
    public String deleteStockManagementDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deleteStockManagementDatabaseTable();
        result = result + "<br>";
        modelMap.addAttribute("stockManagementTableDeletionResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/createPet4uItemStateDatabaseTables", method = RequestMethod.GET)
    public String createPet4uItemStateDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createPet4uItemStateDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("pet4uItemStateTableCreationResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deletePet4uItemStateDatabaseTables1", method = RequestMethod.GET)
    public String deletePet4uItemStateDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deletePet4uItemStateDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("pet4uItemStateTableDeletionResult", result);
        return "techMan/techManDashboard";
    }
//-----------------------------------------------------------

    @RequestMapping(value = "/createMonthSalesDatabaseTables", method = RequestMethod.GET)
    public String createMonthSalesDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createMonthSalesDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("monthSalesTableCreationResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteMonthSalesDatabaseTables1", method = RequestMethod.GET)
    public String deleteMonthSalesDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deleteMonthSalesDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("monthSalesTableDeletionResult", result);
        return "techMan/techManDashboard";
    }

    //----------------------------------------------------------
    @RequestMapping(value = "/createOffersDatabaseTables", method = RequestMethod.GET)
    public String createOffersDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createOffersDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("offersTableCreationResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteOffersDatabaseTables1", method = RequestMethod.GET)
    public String deleteOffersDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deleteOffersDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("offersTableDeletionResult", result);
        return "techMan/techManDashboard";
    }
    //----------------------------------------------------------

    @RequestMapping(value = "/createRoyalDatabaseTables", method = RequestMethod.GET)
    public String createRoyalDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createRoyalDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("royalTableCreationResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteRoyalDatabaseTables1", method = RequestMethod.GET)
    public String deleteRoyalDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deleteRoyalDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("royalTableDeletionResult", result);
        return "techMan/techManDashboard";
    }

    //----------------------------------------------------------
    @RequestMapping(value = "/createPet4uStockSnapshotDatabaseTables", method = RequestMethod.GET)
    public String createPet4uStockSnapshotDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createPet4uStockSnapshotDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("pet4uStockSnapshotTableCreationResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deletePet4uStockSnapshotDatabaseTables1", method = RequestMethod.GET)
    public String deletePet4uStockSnapshotDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deletePet4uStockSnapshotDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("pet4uStockSnapshotTableDeletionResult", result);
        return "techMan/techManDashboard";
    }

    //------------------
    //----------------------------------------------------------
    @RequestMapping(value = "/createEndoDatabaseTables", method = RequestMethod.GET)
    public String createEndoDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createEndoDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("endotTableCreationResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteEndoDatabaseTables1", method = RequestMethod.GET)
    public String deleteEndoDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deleteEndoDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("endoTableDeletionResult", result);
        return "techMan/techManDashboard";
    }

    //------------------
    //----------------------------------------------------------
    @RequestMapping(value = "/createEndoOrderDatabaseTables", method = RequestMethod.GET)
    public String createEndoOrderDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createEndoOrderTitleDatabaseTables();
        String result1 = techManDao.createEndoOrderDataDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("endoOrderTablesCreationResult", result + " " + result1);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteEndoOrderDatabaseTables", method = RequestMethod.GET)
    public String deleteEndoOrderDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deleteEndoOrderTitleDatabaseTables();
        String result1 = techManDao.deleteEndoOrderDataDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("endoOrderTablesDeletionResult", result + " " + result1);
        return "techMan/techManDashboard";
    }

    //------------------
    //----------------------------------------------------------
    @RequestMapping(value = "/createEndoLockerDatabaseTables", method = RequestMethod.GET)
    public String createEndoLockerDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createEndoLockerTitleDatabaseTables();
        String result1 = techManDao.createEndoLockerDataDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("endoLockerTablesCreationResult", result + " " + result1);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteEndoLockerDatabaseTables", method = RequestMethod.GET)
    public String deleteEndoLockerDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deleteEndoLockerTitleDatabaseTables();
        String result1 = techManDao.deleteEndoLockerDataDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("endoLockerTablesDeletionResult", result + " " + result1);
        return "techMan/techManDashboard";
    }
}
