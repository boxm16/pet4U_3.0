package Notes;

import BasicModel.Item;
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

        return "notes/noteServant";
    }
    
     @RequestMapping(value = "saveNote", method = RequestMethod.POST)
    public String saveItemInventory(@RequestParam(name = "altercode") String altercode,
      
         
            @RequestParam(name = "note") String note,
            ModelMap model) {
       
     NotesDao notesDao = new NotesDao();
        String result = notesDao.saveNote(altercode, note);
        model.addAttribute("result", result);
       return "notes/notesDisplay";
    }
}
