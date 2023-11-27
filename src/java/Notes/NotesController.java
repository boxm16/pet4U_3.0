package Notes;

import BasicModel.Item;
import Inventory.InventoryDao;
import Inventory.InventoryItem;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController {

    @RequestMapping(value = "getItemForNote", method = RequestMethod.GET)
    public String getItemForNote(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        NotesDao notesDao = new NotesDao();
        Item item = notesDao.getItemForNote(altercode);

        modelMap.addAttribute("item", item);
        modelMap.addAttribute("altercode", altercode);
        return "vakulina/noteServant";

    }

    @RequestMapping(value = "saveNote", method = RequestMethod.POST)
    public String saveItemInventory(@RequestParam(name = "altercode") String altercode,
            @RequestParam(name = "note") String note,
            ModelMap model) {

        NotesDao notesDao = new NotesDao();
        String result = notesDao.saveNote(altercode, note);
        model.addAttribute("result", result);

        return "redirect:notesDisplay.htm";
        // return "vakulina/notesDisplay";
    }

    @RequestMapping(value = "notesDisplay")
    public String notesDisplay(ModelMap model) {

        NotesDao notesDao = new NotesDao();
        ArrayList<InventoryItem> notes = notesDao.getAllNotes();

        InventoryDao inventoryDao = new InventoryDao();
        LinkedHashMap<String, Item> pet4UItems = inventoryDao.getpet4UItemsRowByRow();

        for (InventoryItem inventoryItem : notes) {
            //     System.out.println("ITETM:" + inventoryItem.getCode());
            String altercode = inventoryItem.getCode();

            Item pet4uItem = pet4UItems.get(altercode);

            if (pet4uItem == null) {
                System.out.println("Pet4uItem with altercode " + altercode + "  not present in the lists from microsoft db");
            } else {
                inventoryItem.setCode(pet4uItem.getCode());
                inventoryItem.setDescription(pet4uItem.getDescription());
                inventoryItem.setPosition(pet4uItem.getPosition());
                inventoryItem.setState(pet4uItem.getState());
                model.addAttribute("notes", notes);
            }
        }
        return "vakulina/notesDisplay";
    }
}
