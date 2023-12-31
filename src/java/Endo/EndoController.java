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
        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();

        EndoDao endoDao = new EndoDao();
        // ArrayList<DeliveryItem> pet4UItemsRowByRow = endoDao.getAllPet4UItemsRowByRowWithDeepSearch();
        LinkedHashMap<String, Item> allPet4UItemsWithDeepSearch = endoDao.getAllPet4UItemsWithDeepSearch();
        //  System.out.println("Pet4U Items Were Brought By Deep Search Method. Items count: " + pet4UItemsRowByRow.size());

        for (Map.Entry<String, Item> pet4uAllItemsEntry : pet4uAllItems.entrySet()) {
            Item it = pet4uAllItemsEntry.getValue();
            String code1 = it.getCode();
            Item ite = allPet4UItemsWithDeepSearch.get(code1);
            if (it.getQuantity().equals(ite.getQuantity())) {

            } else {
                System.out.println("SHOUUT:" + code1);
            }
        }

        //  modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);
        modelMap.addAttribute("endo", endo);
        return "endo/deltioApostolis";

    }

    @RequestMapping(value = "/saveEndo", method = RequestMethod.POST)
    public String save(ModelMap modelMap, @ModelAttribute("endo") Endo endo) {

        System.out.println("ID:" + endo.getId());
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
