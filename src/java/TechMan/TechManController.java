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
        return "techMan/techManDashboard";
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

    @RequestMapping(value = "/deleteSales_1_2022_DatabaseTable", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteSales_2_2022_DatabaseTable", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteNotesDatabaseTable", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteCamelotItemsOfOurInterestDatabaseTables", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteWeightCoefficinetDatabaseTable", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteInventoryDatabaseTable", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteDeliveryDatabaseTables", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteBestBeforeDatabaseTables", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteSalesDatabaseTableX", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteSuppliersDatabaseTable", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deleteStockManagementDatabaseTables", method = RequestMethod.GET)
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

    @RequestMapping(value = "/deletePet4uItemStateDatabaseTables", method = RequestMethod.GET)
    public String deletePet4uItemStateDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deletePet4uItemStateDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("pet4uItemStateTableDeletionResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/createSixMonthsSalesByMonthDatabaseTables", method = RequestMethod.GET)
    public String createSixMonthsSalesByMonthDatabaseTables(ModelMap modelMap) {
        String result = techManDao.createSixMonthsSalesByMonthDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("sixMonthsSalesByMonthTableCreationResult", result);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteSixMonthsSalesByMonthDatabaseTables", method = RequestMethod.GET)
    public String deleteSixMonthsSalesByMonthDatabaseTables(ModelMap modelMap) {
        String result = techManDao.deleteSixMonthsSalesByMonthDatabaseTables();
        result = result + "<br>";
        modelMap.addAttribute("sixMonthsSalesByMonthTableDeletionResult", result);
        return "techMan/techManDashboard";
    }
}
