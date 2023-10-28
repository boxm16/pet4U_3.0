package Offer;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import Search.SearchDao;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OfferController {
    
    @RequestMapping(value = "/addOffer", method = RequestMethod.POST)
    public String addOffer(@RequestParam String code,
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
        return "redirect:activeOffers.htm";
        //return "redirect:itemAnalysis.htm?code=" + code;
    }
    
    @RequestMapping(value = "/activeOffers")
    public String activeOffers(ModelMap model) {
        
        OfferDao offerDao = new OfferDao();
        ArrayList<Offer> activeOffers = offerDao.getAllActiveOffers();
        
        Pet4uItemsDao pet4UItemDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> allItems = pet4UItemDao.getAllItems();
        
        for (Offer offer : activeOffers) {
            String itemCode = offer.getItemCode();
            Item item = allItems.get(itemCode);
            offer.setItemDescription(item.getDescription());
        }
        model.addAttribute("activeOffers", activeOffers);
        return "offers/activeOffers";
    }
    
    @RequestMapping(value = "/offerStamping")
    public String offerStamping(ModelMap model) {
        
        return "offers/offerStamping";
    }
    
    @RequestMapping(value = "/stampOffer", method = RequestMethod.POST)
    public String stampOffer(@RequestParam String code,
            @RequestParam String title,
            @RequestParam String startDate,
            ModelMap model) {
        if (startDate.isEmpty()) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "START DATE IS MISSING.");
            model.addAttribute("code", code);
            
            return "offers/stampingResult";
        }
        
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(code);
        if (item == null) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "Item Code Not Found");
            model.addAttribute("code", code);
            
            return "offers/stampingResult";
        } else {
            OfferDao offerDao = new OfferDao();
            
            offerDao.addOffer(code, title, startDate);
            model.addAttribute("resultColor", "green");
            model.addAttribute("result", "Good Job");
            model.addAttribute("code", code);
            return "offers/stampingResult";
        }
        
    }
    
}
