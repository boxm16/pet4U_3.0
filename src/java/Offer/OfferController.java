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

    @RequestMapping(value = "/endOfferDashboard", method = RequestMethod.GET)
    public String endOfferDashboard(@RequestParam String id,
            ModelMap model) {
        OfferDao offerDao = new OfferDao();
        Offer offer = offerDao.getOffer(id);

        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(offer.getItemCode());

        model.addAttribute("offer", offer);
        model.addAttribute("item", item);
        return "analitica/endOfferDashboard";
    }

    @RequestMapping(value = "/endOffer", method = RequestMethod.POST)
    public String endOffer(@RequestParam String code, @RequestParam String id, @RequestParam String endDate,
            ModelMap model) {

        if (endDate.isEmpty()) {

            OfferDao offerDao = new OfferDao();
            Offer offer = offerDao.getOffer(id);

            SearchDao searchDao = new SearchDao();
            Item item = searchDao.getItemByAltercode(offer.getItemCode());
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "END DATE IS MISSING.");
            model.addAttribute("offer", offer);
            model.addAttribute("code", code);
            model.addAttribute("item", item);
            return "analitica/endOfferDashboard";
        }
        OfferDao offerDao = new OfferDao();
        String result = offerDao.endOffer(id, endDate);

        return "redirect:analitica/itemAnalysis.htm?item_code=" + code;
    }

}
