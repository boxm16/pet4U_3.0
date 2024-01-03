package Endo;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import TESTosteron.TESTosteronDao;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EndoController {

    @RequestMapping(value = "endoDashboard", method = RequestMethod.GET)
    public String endoDashboard(ModelMap modelMap) {

        EndoDao endoDao = new EndoDao();

        LinkedHashMap<String, Endo> incomingEndos = endoDao.getLastIncomingEndos(7);
        LinkedHashMap<String, Endo> receivingEndos = endoDao.getLastReceivingEndos(7);

        modelMap.addAttribute("incomingEndos", incomingEndos);
        modelMap.addAttribute("receivingEndos", receivingEndos);

        return "endo/endoDashboard";

    }

    @RequestMapping(value = "deltioApostolis", method = RequestMethod.GET)
    public String deltioApostolis(ModelMap modelMap) {

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = pet4uItemsDao.getPet4UItemsRowByRow();

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        return "endo/deltioApostolis";

    }

    @RequestMapping(value = "/saveEndo", method = RequestMethod.POST)
    public String save(ModelMap modelMap, @ModelAttribute("endo") Endo endo) {

        if (endo.getDateString() != null) {
            System.out.println("DA " + endo.getDateString());
            EndoDao endoDao = new EndoDao();
            String result = endoDao.saveDeltioApostolis(endo);

        } else {
            System.out.println("Date String is NULL");
            modelMap.addAttribute("endo", endo);
            modelMap.addAttribute("result", "Date String is NULL");
            return "redirect:endoDashboard.htm";
        }
        modelMap.addAttribute("endo", endo);
        return "endo/endoDashboard";
    }

    @RequestMapping(value = "showDeltioApostolis", method = RequestMethod.GET)
    public String showDeltioApostolis(@RequestParam(name = "id") String id, ModelMap modelMap) {
        System.out.println(id);

        TESTosteronDao testosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allPet4UItemsWithDeepSearch = testosteronDao.getAllPet4UItemsWithDeepSearch();

        EndoDao endoDao = new EndoDao();

        Endo endo = endoDao.getEndo(id, allPet4UItemsWithDeepSearch);

        modelMap.addAttribute("endo", endo);
        return "endo/deltioApostolisDisplay";
    }

}
