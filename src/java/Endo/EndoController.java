package Endo;

import BasicModel.Item;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
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
        LinkedHashMap<String, Item> items = new LinkedHashMap<>();
        items.put(code, item);
        endo.setItems(items);
        
        return new ModelAndView("endo/deltioApostolis", "endo", endo);
        
    }
    
}
