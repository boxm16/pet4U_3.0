package CamelotSearch;

import BasicModel.Item;
import Notes.NotesDao;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotSearchController {

    @Autowired
    private CamelotSearchDao camelotSearchDao;

    @RequestMapping(value = "camelotSearchDashboard")
    public String searchForItem() {

        return "camelotSearch/camelotSearchDashboard";
    }

    @RequestMapping(value = "findCamelotItemByAltercode")
    public String findCamelotItemByAltercode(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        Item item = camelotSearchDao.getItemByAltercode(altercode);
        modelMap.addAttribute("target", altercode);
        modelMap.addAttribute("item", item);
        return "camelotSearch/singleItemSearchResult";
    }

    @RequestMapping(value = "findCamelotItemsByAltercodeMask")
    public String findCamelotItemsByAltercodeMask(@RequestParam(name = "altercodeMask") String altercodeMask, ModelMap modelMap) {
        LinkedHashMap<String, Item> items = camelotSearchDao.getItemsByAltercodeMask(altercodeMask);
        modelMap.addAttribute("target", altercodeMask);
        modelMap.addAttribute("items", items);
        return "camelotSearch/multipleItemsSearchResult";
    }

    @RequestMapping(value = "findCamelotItemsByADescriptionMask")
    public String findItemsByADescriptionMask(@RequestParam(name = "descriptionMask") String descriptionMask, ModelMap modelMap) {
        LinkedHashMap<String, Item> items = camelotSearchDao.getItemsByDescriptionMask(descriptionMask);

        modelMap.addAttribute("target", descriptionMask);
        modelMap.addAttribute("items", items);
        return "camelotSearch/multipleItemsSearchResult";
    }

    @RequestMapping(value = "getCamelotItemForNote", method = RequestMethod.GET)
    public String getCamelotItemForNote(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        NotesDao notesDao = new NotesDao();
        Item item = notesDao.getItemForNote(altercode);

        modelMap.addAttribute("item", item);
        modelMap.addAttribute("altercode", altercode);
        return "camelotSearch/camelotNoteServant";

    }
}
