package CamelotSearch;

import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import Inventory.InventoryItem;
import Notes.NotesDao;
import java.util.ArrayList;
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
        Item item = notesDao.getCamelotItemForNote(altercode);

        modelMap.addAttribute("item", item);
        modelMap.addAttribute("altercode", altercode);
        return "camelotSearch/camelotNoteServant";
    }

    @RequestMapping(value = "saveCamelotNote", method = RequestMethod.POST)
    public String saveCamelotNote(@RequestParam(name = "altercode") String altercode,
            @RequestParam(name = "note") String note,
            ModelMap model) {

        NotesDao notesDao = new NotesDao();
        String result = notesDao.saveCamelotNote(altercode, note);
        model.addAttribute("result", result);

        return "redirect:camelotNotesCardMode.htm";
        // return "vakulina/notesDisplay";
    }

    @RequestMapping(value = "camelotNotesDisplay")
    public String notesDisplay(ModelMap model) {

        NotesDao notesDao = new NotesDao();
        ArrayList<InventoryItem> notes = notesDao.getAllCamelotNotes();

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItems = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        for (InventoryItem inventoryItem : notes) {
            //     System.out.println("ITETM:" + inventoryItem.getCode());
            String altercode = inventoryItem.getCode();

            Item camelotItem = camelotItems.get(altercode);

            if (camelotItem == null) {
                System.out.println("CamelotItem with altercode " + altercode + "  not present in the lists from microsoft db");
            } else {
                inventoryItem.setCode(camelotItem.getCode());
                inventoryItem.setDescription(camelotItem.getDescription());
                inventoryItem.setPosition(camelotItem.getPosition());
                inventoryItem.setQuantity(camelotItem.getQuantity());
                model.addAttribute("notes", notes);
            }
        }
        return "camelotSearch/camelotNotesDisplay";
    }

    @RequestMapping(value = "camelotNotesCardMode")
    public String camelotNotesCardMode(ModelMap model) {

        NotesDao notesDao = new NotesDao();
        ArrayList<InventoryItem> notes = notesDao.getAllCamelotNotes();

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItems = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        for (InventoryItem inventoryItem : notes) {
            //     System.out.println("ITETM:" + inventoryItem.getCode());
            String altercode = inventoryItem.getCode();

            Item camelotItem = camelotItems.get(altercode);

            if (camelotItem == null) {
                System.out.println("CamelotItem with altercode " + altercode + "  not present in the lists from microsoft db");
            } else {
                inventoryItem.setCode(camelotItem.getCode());
                inventoryItem.setDescription(camelotItem.getDescription());
                inventoryItem.setPosition(camelotItem.getPosition());
                inventoryItem.setQuantity(camelotItem.getQuantity());

            }
        }
        model.addAttribute("notes", notes);
        return "camelotSearch/camelotNotesCardMode";
    }

    @RequestMapping(value = "deleteCamelotNote", method = RequestMethod.GET)
    public String deleteNote(@RequestParam(name = "id") String id) {
        NotesDao notesDao = new NotesDao();
        notesDao.deleteCamelotNote(id);
        return "redirect:camelotNotesDisplay.htm";
    }

    @RequestMapping(value = "deleteCamelotNoteCardMode", method = RequestMethod.GET)
    public String deleteCamelotNoteCardMode(@RequestParam(name = "id") String id) {
        NotesDao notesDao = new NotesDao();
        notesDao.deleteCamelotNote(id);
        return "redirect:camelotNotesCardMode.htm";
    }

    @RequestMapping(value = "deleteAllCamelotNotes", method = RequestMethod.GET)
    public String deleteAllCamelotNotes() {
        NotesDao notesDao = new NotesDao();
        notesDao.deleteAllCamelotNote();
        return "redirect:camelotNotesDisplay.htm";
    }

    @RequestMapping(value = "camelotStockPositions", method = RequestMethod.GET)
    public String camelotStockPositions(@RequestParam(name = "itemCode") String altercode, ModelMap modelMap) {
        Item item = camelotSearchDao.getItemByAltercode(altercode);
        NotesDao notesDao = new NotesDao();
        LinkedHashMap<Integer, String> stockPositions = notesDao.getStockPositions(item);

        modelMap.addAttribute("item", item);
        modelMap.addAttribute("stockPositions", stockPositions);
        return "camelotSearch/camelotStockPositions";
    }

    @RequestMapping(value = "addCamelotStockPosition", method = RequestMethod.POST)
    public String addCamelotStockPosition(@RequestParam(name = "itemCode") String itemCode,
            ModelMap model) {

        ArrayList<String> stockPositions = new ArrayList<>();
        stockPositions.add("Α1-1");

        stockPositions.add("Β1-1");
        stockPositions.add("Β1-2");
        stockPositions.add("Β1-3");

        stockPositions.add("Β2-1");
        stockPositions.add("Β2-2");
        stockPositions.add("Β2-3");

        stockPositions.add("Β3-1");
        stockPositions.add("Β3-2");
        stockPositions.add("Β3-3");

        stockPositions.add("Β4-1");
        stockPositions.add("Β4-2");
        stockPositions.add("Β4-3");

        stockPositions.add("Β5-1");
        stockPositions.add("Β5-2");
        stockPositions.add("Β5-3");

        stockPositions.add("Β6-1");
        stockPositions.add("Β6-2");
        stockPositions.add("Β6-3");

        stockPositions.add("Β7-1");
        stockPositions.add("Β7-2");
        stockPositions.add("Β7-3");

        stockPositions.add("Β8-1");
        stockPositions.add("Β8-2");
        stockPositions.add("Β8-3");

        stockPositions.add("Β9-1");
        stockPositions.add("Β9-2");
        stockPositions.add("Β9-3");
        //------------------
        stockPositions.add("Γ1-1");
        stockPositions.add("Γ1-2");
        stockPositions.add("Γ1-3");

        stockPositions.add("Γ2-1");
        stockPositions.add("Γ2-2");
        stockPositions.add("Γ2-3");

        stockPositions.add("Γ3-1");
        stockPositions.add("Γ3-2");
        stockPositions.add("Γ3-3");

        stockPositions.add("Γ4-1");
        stockPositions.add("Γ4-2");
        stockPositions.add("Γ4-3");

        stockPositions.add("Γ5-1");
        stockPositions.add("Γ5-2");
        stockPositions.add("Γ5-3");

        stockPositions.add("Γ6-1");
        stockPositions.add("Γ6-2");
        stockPositions.add("Γ6-3");

        stockPositions.add("Γ7-1");
        stockPositions.add("Γ7-2");
        stockPositions.add("Γ7-3");

        stockPositions.add("Γ8-1");
        stockPositions.add("Γ8-2");
        stockPositions.add("Γ8-3");

        stockPositions.add("Γ9-1");
        stockPositions.add("Γ9-2");
        stockPositions.add("Γ9-3");
        //------------------
        stockPositions.add("Δ1-1");
        stockPositions.add("Δ1-2");
        stockPositions.add("Δ1-3");

        stockPositions.add("Δ2-1");
        stockPositions.add("Δ2-2");
        stockPositions.add("Δ2-3");

        stockPositions.add("Δ3-1");
        stockPositions.add("Δ3-2");
        stockPositions.add("Δ3-3");

        stockPositions.add("Δ4-1");
        stockPositions.add("Δ4-2");
        stockPositions.add("Δ4-3");

        stockPositions.add("Δ5-1");
        stockPositions.add("Δ5-2");
        stockPositions.add("Δ5-3");

        stockPositions.add("Δ6-1");
        stockPositions.add("Δ6-2");
        stockPositions.add("Δ6-3");

        stockPositions.add("Δ7-1");
        stockPositions.add("Δ7-2");
        stockPositions.add("Δ7-3");

        stockPositions.add("Δ8-1");
        stockPositions.add("Δ8-2");
        stockPositions.add("Δ8-3");

        stockPositions.add("Δ9-1");
        stockPositions.add("Δ9-2");
        stockPositions.add("Δ9-3");
        //------------------
        stockPositions.add("Ε1-1");
        stockPositions.add("Ε1-2");
        stockPositions.add("Ε1-3");

        stockPositions.add("Ε2-1");
        stockPositions.add("Ε2-2");
        stockPositions.add("Ε2-3");

        stockPositions.add("Ε3-1");
        stockPositions.add("Ε3-2");
        stockPositions.add("Ε3-3");

        stockPositions.add("Ε4-1");
        stockPositions.add("Ε4-2");
        stockPositions.add("Ε4-3");

        stockPositions.add("Ε5-1");
        stockPositions.add("Ε5-2");
        stockPositions.add("Ε5-3");

        stockPositions.add("Ε6-1");
        stockPositions.add("Ε6-2");
        stockPositions.add("Ε6-3");

        stockPositions.add("Ε7-1");
        stockPositions.add("Ε7-2");
        stockPositions.add("Ε7-3");

        stockPositions.add("Ε8-1");
        stockPositions.add("Ε8-2");
        stockPositions.add("Ε8-3");

        stockPositions.add("Ε9-1");
        stockPositions.add("Ε9-2");
        stockPositions.add("Ε9-3");

        model.addAttribute("itemCode", itemCode);
        model.addAttribute("stockPositions", stockPositions);

        return "camelotSearch/addCamelotStockPosition";
    }

    @RequestMapping(value = "setCamelotStockPosition", method = RequestMethod.POST)
    public String setCamelotStockPosition(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "position") String position,
            ModelMap model) {

        NotesDao notesDao = new NotesDao();
        String result = notesDao.addCamelotStockPosition(itemCode, position);
        model.addAttribute("result", result);

        return "redirect:camelotStockPositions.htm?itemCode=" + itemCode;
        // return "vakulina/notesDisplay";
    }

    @RequestMapping(value = "camelotStockPositionDeletion", method = RequestMethod.POST)
    public String deleteCamelotStockPosition(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "id") String id,
            ModelMap model) {

        NotesDao notesDao = new NotesDao();
        model.addAttribute("id", id);
        model.addAttribute("itemCode", itemCode);

        return "camelotSearch/camelotStockPositionDeletion";

    }
}
