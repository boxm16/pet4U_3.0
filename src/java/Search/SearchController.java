package Search;

import BasicModel.Item;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private SearchDao searchDao;

    @RequestMapping(value = "searchDashboard")
    public String searchForItem() {

        return "search/searchDashboard";
    }

    @RequestMapping(value = "findItemByAltercode")
    public String findItemByAltercode(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        Item item = searchDao.getItemByAltercode(altercode);
        modelMap.addAttribute("target", altercode);
        modelMap.addAttribute("item", item);
        return "search/singleItemSearchResult";
    }

    @RequestMapping(value = "findItemsByAltercodeMask")
    public String findItemsByAltercodeMask(@RequestParam(name = "altercodeMask") String altercodeMask, ModelMap modelMap) {
        LinkedHashMap<String, Item> items = searchDao.getItemsByAltercodeMask(altercodeMask);
        modelMap.addAttribute("target", altercodeMask);
        modelMap.addAttribute("items", items);
        return "search/multipleItemsSearchResult";
    }

    @RequestMapping(value = "findItemsByADescriptionMask")
    public String findItemsByADescriptionMask(@RequestParam(name = "descriptionMask") String descriptionMask, ModelMap modelMap) {
        LinkedHashMap<String, Item> items = searchDao.getItemsByDescriptionMask(descriptionMask);

        modelMap.addAttribute("target", descriptionMask);
        modelMap.addAttribute("items", items);
        return "search/multipleItemsSearchResult";
    }
    
    
     @RequestMapping(value = "deepSearchForAltercodeMask")
    public String deepSearchForAltercodeMask(@RequestParam(name = "altercodeMask") String altercodeMask, ModelMap modelMap) {
        LinkedHashMap<String, Item> items = searchDao.deepSearchForAltercodeMask(altercodeMask);
        modelMap.addAttribute("target", "<h1 style='background-color:red'>Result From SQL</h1><h1 style='background-color:red'>\"+altercodeMask+\"</h1>");
        modelMap.addAttribute("items", items);
        return "search/multipleItemsSearchResult";
    }

}
