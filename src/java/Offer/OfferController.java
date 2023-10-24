package Offer;

import BasicModel.Item;
import Search.SearchDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OfferController {

    @RequestMapping(value = "/addOffer", method = RequestMethod.POST)
    public String offerDashboard(@RequestParam String code,
            @RequestParam String title,
            @RequestParam String startDate,
            ModelMap model) {

        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(code);
        OfferDao offerDao = new OfferDao();
        if (startDate.isEmpty()) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "START DATE IS MISSING.");
            model.addAttribute("code", code);
            model.addAttribute("item", item);
            return "analitica/offerDashboard";
        }

        String result = offerDao.addOffer(code, title, startDate);

        model.addAttribute("resultColor", "green");
        model.addAttribute("result", result);
        model.addAttribute("code", code);
        model.addAttribute("item", item);

        return "analitica/offerDashboard";
    }

}
