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
        //------------------
        stockPositions.add("Ζ1-1");
        stockPositions.add("Ζ1-2");
        stockPositions.add("Ζ1-3");

        stockPositions.add("Ζ2-1");
        stockPositions.add("Ζ2-2");
        stockPositions.add("Ζ2-3");

        stockPositions.add("Ζ3-1");
        stockPositions.add("Ζ3-2");
        stockPositions.add("Ζ3-3");

        stockPositions.add("Ζ4-1");
        stockPositions.add("Ζ4-2");
        stockPositions.add("Ζ4-3");

        stockPositions.add("Ζ5-1");
        stockPositions.add("Ζ5-2");
        stockPositions.add("Ζ5-3");

        stockPositions.add("Ζ6-1");
        stockPositions.add("Ζ6-2");
        stockPositions.add("Ζ6-3");

        stockPositions.add("Ζ7-1");
        stockPositions.add("Ζ7-2");
        stockPositions.add("Ζ7-3");

        stockPositions.add("Ζ8-1");
        stockPositions.add("Ζ8-2");
        stockPositions.add("Ζ8-3");

        stockPositions.add("Ζ9-1");
        stockPositions.add("Ζ9-2");
        stockPositions.add("Ζ9-3");
//------------------
        stockPositions.add("Η1-1");
        stockPositions.add("Η1-2");
        stockPositions.add("Η1-3");

        stockPositions.add("Η2-1");
        stockPositions.add("Η2-2");
        stockPositions.add("Η2-3");

        stockPositions.add("Η3-1");
        stockPositions.add("Η3-2");
        stockPositions.add("Η3-3");

        stockPositions.add("Η4-1");
        stockPositions.add("Η4-2");
        stockPositions.add("Η4-3");

        stockPositions.add("Η5-1");
        stockPositions.add("Η5-2");
        stockPositions.add("Η5-3");

        stockPositions.add("Η6-1");
        stockPositions.add("Η6-2");
        stockPositions.add("Η6-3");

        stockPositions.add("Η7-1");
        stockPositions.add("Η7-2");
        stockPositions.add("Η7-3");

        stockPositions.add("Η8-1");
        stockPositions.add("Η8-2");
        stockPositions.add("Η8-3");

        stockPositions.add("Η9-1");
        stockPositions.add("Η9-2");
        stockPositions.add("Η9-3");
        //------------------
        stockPositions.add("Θ1-1");
        stockPositions.add("Θ1-2");
        stockPositions.add("Θ1-3");

        stockPositions.add("Θ2-1");
        stockPositions.add("Θ2-2");
        stockPositions.add("Θ2-3");

        stockPositions.add("Θ3-1");
        stockPositions.add("Θ3-2");
        stockPositions.add("Θ3-3");

        stockPositions.add("Θ4-1");
        stockPositions.add("Θ4-2");
        stockPositions.add("Θ4-3");

        stockPositions.add("Θ5-1");
        stockPositions.add("Θ5-2");
        stockPositions.add("Θ5-3");

        stockPositions.add("Θ6-1");
        stockPositions.add("Θ6-2");
        stockPositions.add("Θ6-3");

        stockPositions.add("Θ7-1");
        stockPositions.add("Θ7-2");
        stockPositions.add("Θ7-3");

        stockPositions.add("Θ8-1");
        stockPositions.add("Θ8-2");
        stockPositions.add("Θ8-3");

        stockPositions.add("Θ9-1");
        stockPositions.add("Θ9-2");
        stockPositions.add("Θ9-3");
        //------------------
        stockPositions.add("Ι1-1");
        stockPositions.add("Ι1-2");
        stockPositions.add("Ι1-3");

        stockPositions.add("Ι2-1");
        stockPositions.add("Ι2-2");
        stockPositions.add("Ι2-3");

        stockPositions.add("Ι3-1");
        stockPositions.add("Ι3-2");
        stockPositions.add("Ι3-3");

        stockPositions.add("Ι4-1");
        stockPositions.add("Ι4-2");
        stockPositions.add("Ι4-3");

        stockPositions.add("Ι5-1");
        stockPositions.add("Ι5-2");
        stockPositions.add("Ι5-3");

        stockPositions.add("Ι6-1");
        stockPositions.add("Ι6-2");
        stockPositions.add("Ι6-3");

        stockPositions.add("Ι7-1");
        stockPositions.add("Ι7-2");
        stockPositions.add("Ι7-3");

        stockPositions.add("Ι8-1");
        stockPositions.add("Ι8-2");
        stockPositions.add("Ι8-3");

        stockPositions.add("Ι9-1");
        stockPositions.add("Ι9-2");
        stockPositions.add("Ι9-3");
        //------------------
        stockPositions.add("Κ1-1");
        stockPositions.add("Κ1-2");
        stockPositions.add("Κ1-3");

        stockPositions.add("Κ2-1");
        stockPositions.add("Κ2-2");
        stockPositions.add("Κ2-3");

        stockPositions.add("Κ3-1");
        stockPositions.add("Κ3-2");
        stockPositions.add("Κ3-3");

        stockPositions.add("Κ4-1");
        stockPositions.add("Κ4-2");
        stockPositions.add("Κ4-3");

        stockPositions.add("Κ5-1");
        stockPositions.add("Κ5-2");
        stockPositions.add("Κ5-3");

        stockPositions.add("Κ6-1");
        stockPositions.add("Κ6-2");
        stockPositions.add("Κ6-3");

        stockPositions.add("Κ7-1");
        stockPositions.add("Κ7-2");
        stockPositions.add("Κ7-3");

        stockPositions.add("Κ8-1");
        stockPositions.add("Κ8-2");
        stockPositions.add("Κ8-3");

        stockPositions.add("Κ9-1");
        stockPositions.add("Κ9-2");
        stockPositions.add("Κ9-3");

        //------------------
        stockPositions.add("Λ1-1");
        stockPositions.add("Λ1-2");
        stockPositions.add("Λ1-3");

        stockPositions.add("Λ2-1");
        stockPositions.add("Λ2-2");
        stockPositions.add("Λ2-3");

        stockPositions.add("Λ3-1");
        stockPositions.add("Λ3-2");
        stockPositions.add("Λ3-3");

        stockPositions.add("Λ4-1");
        stockPositions.add("Λ4-2");
        stockPositions.add("Λ4-3");

        stockPositions.add("Λ5-1");
        stockPositions.add("Λ5-2");
        stockPositions.add("Λ5-3");

        stockPositions.add("Λ6-1");
        stockPositions.add("Λ6-2");
        stockPositions.add("Λ6-3");

        stockPositions.add("Λ7-1");
        stockPositions.add("Λ7-2");
        stockPositions.add("Λ7-3");

        stockPositions.add("Λ8-1");
        stockPositions.add("Λ8-2");
        stockPositions.add("Λ8-3");

        stockPositions.add("Λ9-1");
        stockPositions.add("Λ9-2");
        stockPositions.add("Λ9-3");

        //------------------
        stockPositions.add("Ν1-1");
        stockPositions.add("Ν1-2");
        stockPositions.add("Ν1-3");

        stockPositions.add("Ν2-1");
        stockPositions.add("Ν2-2");
        stockPositions.add("Ν2-3");

        stockPositions.add("Ν3-1");
        stockPositions.add("Ν3-2");
        stockPositions.add("Ν3-3");

        stockPositions.add("Ν4-1");
        stockPositions.add("Ν4-2");
        stockPositions.add("Ν4-3");

        stockPositions.add("Ν5-1");
        stockPositions.add("Ν5-2");
        stockPositions.add("Ν5-3");

        stockPositions.add("Ν6-1");
        stockPositions.add("Ν6-2");
        stockPositions.add("Ν6-3");

        stockPositions.add("Ν7-1");
        stockPositions.add("Ν7-2");
        stockPositions.add("Ν7-3");

        stockPositions.add("Ν8-1");
        stockPositions.add("Ν8-2");
        stockPositions.add("Ν8-3");

        stockPositions.add("Ν9-1");
        stockPositions.add("Ν9-2");
        stockPositions.add("Ν9-3");
        
        //------------------
        stockPositions.add("Ξ1-1");
        stockPositions.add("Ξ1-2");
        stockPositions.add("Ξ1-3");

        stockPositions.add("Ξ2-1");
        stockPositions.add("Ξ2-2");
        stockPositions.add("Ξ2-3");

        stockPositions.add("Ξ3-1");
        stockPositions.add("Ξ3-2");
        stockPositions.add("Ξ3-3");

        stockPositions.add("Ξ4-1");
        stockPositions.add("Ξ4-2");
        stockPositions.add("Ξ4-3");

        stockPositions.add("Ξ5-1");
        stockPositions.add("Ξ5-2");
        stockPositions.add("Ξ5-3");

        stockPositions.add("Ξ6-1");
        stockPositions.add("Ξ6-2");
        stockPositions.add("Ξ6-3");

        stockPositions.add("Ξ7-1");
        stockPositions.add("Ξ7-2");
        stockPositions.add("Ξ7-3");

        stockPositions.add("Ξ8-1");
        stockPositions.add("Ξ8-2");
        stockPositions.add("Ξ8-3");

        stockPositions.add("Ξ9-1");
        stockPositions.add("Ξ9-2");
        stockPositions.add("Ξ9-3");

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

    @RequestMapping(value = "camelotStockPositionDeletion", method = RequestMethod.GET)
    public String deleteCamelotStockPosition(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "id") String id,
            ModelMap model) {

        model.addAttribute("id", id);
        model.addAttribute("itemCode", itemCode);
        return "camelotSearch/camelotStockPositionDeletion";
    }

    @RequestMapping(value = "deleteCameltoStockPosition", method = RequestMethod.GET)
    public String deleteCameltoStockPosition(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "id") String id,
            ModelMap model) {

        model.addAttribute("id", id);

        NotesDao notesDao = new NotesDao();
        notesDao.deleteCamelotStockPosition(id);

        return "redirect:camelotStockPositions.htm?itemCode=" + itemCode;
    }

}
