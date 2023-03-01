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

    private Boolean uploadsDirectoryExists() {

        File dir = new File(this.basement.getBasementDirectory() + "/Pet4U_Uploads");
        // Tests whether the directory denoted by this abstract pathname exists.
        return dir.exists();
    }
    //----------------------------------------
    @RequestMapping(value = "createPet4u_DB", method = RequestMethod.GET)
    public String createPet4u_DB(ModelMap model) {
        String pet4uDatabaseCreationResult = techManDao.createPet4u_DB();
        model.addAttribute("pet4uDatabaseCreationResult", pet4uDatabaseCreationResult+"<br>");
        return "techMan/techManDashboard";
    }
    
     //---------------------------
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

    @RequestMapping(value = "/createOrdersDatabaseTable", method = RequestMethod.GET)
    public String createOrdersDatabaseTable(ModelMap modelMap) {
        String ordersDatabaseTableResult = techManDao.createOrdersDatabaseTable();

        ordersDatabaseTableResult = ordersDatabaseTableResult + "<br>";
        modelMap.addAttribute("ordersDatabaseTableResult", ordersDatabaseTableResult);
         return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteOrdersDatabaseTable", method = RequestMethod.GET)
    public String deleteOrdersDatabaseTable(ModelMap modelMap) {
        String ordersDatabaseTableResult = techManDao.deleteOrdersDatabaseTable();

        ordersDatabaseTableResult = ordersDatabaseTableResult + "<br>";
        modelMap.addAttribute("ordersDatabaseTableResult", ordersDatabaseTableResult);
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
    @RequestMapping(value = "/createCamelotItemsOfOurInterestDatabaseTable", method = RequestMethod.GET)
    public String createCamelotItemsOfOurInterestDatabaseTable(ModelMap modelMap) {

        String camelotItemsOfOurInterestTableCreationResult = techManDao.createCamelotItemsOfOurInterestTable();
        modelMap.addAttribute("camelotItemsOfOurInterestDatabaseTableCreationResult", camelotItemsOfOurInterestTableCreationResult);
        return "techMan/techManDashboard";
    }

    @RequestMapping(value = "/deleteCamelotItemsOfOurInterestDatabaseTable", method = RequestMethod.GET)
    public String deleteCamelotItemsOfOurInterestDatabaseTable(ModelMap modelMap) {
        String camelotItemsOfOurInterestTableDeletionResult = techManDao.deleteCamelotItemsOfOurInterestTable();
        modelMap.addAttribute("camelotItemsOfOurInterestDatabaseTableDeletionResult", camelotItemsOfOurInterestTableDeletionResult);
       return "techMan/techManDashboard";
    }
}
