package Analitica;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnaliticaController {

    @RequestMapping(value = "/itemAnalysis", method = RequestMethod.GET)
    public String itemSalesAnalysis(@RequestParam String code, ModelMap model) {
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> itemSnapshots = pet4uItemsDao.getItemSnapshots(code);
        model.addAttribute("itemCode", code);
        model.addAttribute("itemSnapshots", itemSnapshots);

        return "analitica/itemAnalysis";
    }
}
