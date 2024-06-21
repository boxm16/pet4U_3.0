package TESTosteron;

import BasicModel.Item;
import CamelotItemsOfInterest.CamelotDao;
import Notes.NotesDao;
import Pet4uItems.Pet4uItemsDao;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TESTosteronController {

    @RequestMapping(value = "testosteronDashboard")
    public String testosteronDashboard(HttpSession session) {
        String user = (String) session.getAttribute("user");
        System.out.println("Super User Status:" + user);
        if (user == null) {
            return "errorPage";
        } else if (user.equals(
                "identified")) {
            return "testosteron/testosteronDashboard";
        } else {
            return "errorPage";
        }
    }

    @RequestMapping(value = "testViewAndDeepSearch")
    public String testViewAndDeepSearch() {
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();

        TESTosteronDao testosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allPet4UItemsWithDeepSearch = testosteronDao.getAllPet4UItemsWithDeepSearch();
        for (Map.Entry<String, Item> pet4uAllItemsEntry : pet4uAllItems.entrySet()) {
            String codeEx = pet4uAllItemsEntry.getKey();
            Item value1 = pet4uAllItemsEntry.getValue();
            Item v2 = allPet4UItemsWithDeepSearch.remove(codeEx);
            if (v2 == null) {
                System.out.println("SHOUT, NULL IN DEEP SEARCH " + codeEx + "-DESCRIPTION-" + value1.getDescription() + "+POSITION+" + value1.getPosition());

            } else {
                if (!value1.getQuantity().equals(v2.getQuantity())) {
                    System.out.println("QYT " + codeEx + "--" + value1.getDescription() + "++" + value1.getQuantity() + "X" + v2.getQuantity());

                }
            }

        }

        System.out.println("LEFT OVERS: " + allPet4UItemsWithDeepSearch.size());
        for (Map.Entry<String, Item> pet4uAllItemsEntry : allPet4UItemsWithDeepSearch.entrySet()) {
            System.out.println(pet4uAllItemsEntry.getValue().getCode()
                    + "="
                    + pet4uAllItemsEntry.getValue().getDescription()
                    + "-"
                    + pet4uAllItemsEntry.getValue().getPosition()
                    + "+"
                    + pet4uAllItemsEntry.getValue().getPosition());

        }
        System.out.println("TEST COMPLETED: RESULT SEE ABOVE");
        return "testosteron/testResult";
    }

    @RequestMapping(value = "camelotStockPositonsTesting")
    public String camelotStockPositonsTesting(ModelMap modelMap) {
        CamelotDao camelotDao = new CamelotDao();
        LinkedHashMap<String, Item> camelotAllItems = camelotDao.getCamelotItems();

        NotesDao notesDao = new NotesDao();
        int index = 0;
        for (Map.Entry<String, Item> caiEntrySet : camelotAllItems.entrySet()) {
            String result = notesDao.saveCamelotNote(caiEntrySet.getValue().getCode(), "dokimastiko");
            System.out.println(index + " : " + caiEntrySet.getValue().getCode() + " : " + result);
            index++;
        }
        modelMap.addAttribute("camelotAllItems", camelotAllItems);
        return "testosteron/camelotAllItemsNotedTestResult";
    }

    @RequestMapping(value = "showAllDeletedCamelotNotesBatches")
    public String showAllDeletedCamelotNotesBatches(ModelMap modelMap) {
        NotesDao notesDao = new NotesDao();
        LinkedHashMap<String, Integer> deletedCamelotNotesBatches = notesDao.getAllDeletedCamelotNotesBatches();
        modelMap.addAttribute("deletedCamelotNotesBatches", deletedCamelotNotesBatches);
        return "testosteron/allDeletedCamelotNotesBatches";
    }
}
