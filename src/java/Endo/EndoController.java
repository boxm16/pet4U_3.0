package Endo;

import BasicModel.Item;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EndoController {

    @RequestMapping(value = "deltioApostolis", method = RequestMethod.GET)
    public ModelAndView deltioApostolis() {
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

        return new ModelAndView("endo/deltioApostolis", "endo", endo);

    }

    @RequestMapping(value = "/saveEndo", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("endo") Endo endo) {

        System.out.println("ID:" + endo.getId());
        LinkedHashMap<String, Item> items = endo.getItems();

        for (Map.Entry<String, Item> itemsEntry : items.entrySet()) {
            System.out.println(itemsEntry.getValue().getCode());
            System.out.println(itemsEntry.getValue().getDescription());
            System.out.println(itemsEntry.getValue().getQuantity());

        }

        return new ModelAndView("endo/deltioApostolis", "endo", endo);
    }

}
