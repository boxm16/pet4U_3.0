package Endo;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EndoController {

    @RequestMapping(value = "deltioApostolis", method = RequestMethod.GET)
    public String deltioApostolis(ModelMap modelMap) {
        Endo endo = new Endo();
        Item item = new Item();
        String code = "DDD";
        item.setCode(code);
        item.setDescription("ADSDSDAS");
        item.setQuantity("888");
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        items.put(code, item);
        endo.setItems(items);
        endo.setId(35);

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = pet4uItemsDao.getPet4UItemsRowByRow();

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);
        modelMap.addAttribute("endo", endo);

        return "endo/deltioApostolis";

    }

    @RequestMapping(value = "/saveEndo", method = RequestMethod.POST)
    public String save(ModelMap modelMap, @ModelAttribute("endo") Endo endo) {

        System.out.println("ID:" + endo.getId());
        System.out.println("Date:" + endo.getDate());
        System.out.println("TYPE:" + endo.getType());
        System.out.println("Sender:" + endo.getSender());
        System.out.println("Receiver:" + endo.getReceiver());
        LinkedHashMap<String, Item> items = endo.getItems();

        for (Map.Entry<String, Item> itemsEntry : items.entrySet()) {
            System.out.println(itemsEntry.getValue().getCode());
            System.out.println(itemsEntry.getValue().getDescription());
            System.out.println(itemsEntry.getValue().getQuantity());

        }
        modelMap.addAttribute("endo", endo);
        return "endo/deltioApostolis";
    }

}
