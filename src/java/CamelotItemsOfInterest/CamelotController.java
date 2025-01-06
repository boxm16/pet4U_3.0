package CamelotItemsOfInterest;

import BasicModel.Item;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotController {

    @Autowired
    private CamelotDao camelotDao;

    @RequestMapping(value = "camelotAllItems")
    public String camelotAllItems(ModelMap modelMap) {
        LinkedHashMap<String, Item> camelotAllItems = camelotDao.getCamelotItems();
        modelMap.addAttribute("camelotAllItems", camelotAllItems);
        return "camelot/camelotAllItems";
    }

    @RequestMapping(value = "camelotAllItemsWithPosition")
    public String camelotAllItemsWithPosition(ModelMap modelMap) {
        LinkedHashMap<String, Item> camelotAllItems = camelotDao.getCamelotItems();
        modelMap.addAttribute("camelotAllItems", camelotAllItems);
        return "camelot/camelotAllItemsWithPosition";
    }

}
