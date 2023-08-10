package BestBefore;

import BasicModel.Item;
import Inventory.InventoryDao;
import Search.SearchDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BestBeforeController {

    @Autowired
    private BestBeforeDao bestBeforeDao;

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private SearchDao searchDao;

    @RequestMapping(value = "makeBestBeforeStatement", method = RequestMethod.GET)
    public String makeBestBeforeStatement(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        Item item = searchDao.getItemByAltercode(altercode);
        HashMap<LocalDate, BestBeforeStatement> statments = bestBeforeDao.getBestBeforeStatements(altercode);
        modelMap.addAttribute("item", item);
        modelMap.addAttribute("statments", statments);
        modelMap.addAttribute("altercode", altercode);

        return "bestBefore/bestBeforeServant";
    }

    @RequestMapping(value = "saveBestBeforeStatment", method = RequestMethod.POST)
    public String saveBestBeforeStatment(@RequestParam(name = "altercode") String altercode,
            @RequestParam(name = "bestBefore") String bestBefore,
            @RequestParam(name = "alert") String alert,
            @RequestParam(name = "note") String note,
            ModelMap model) {

        BestBeforeStatement bestBeforeStatement = new BestBeforeStatement();
        bestBeforeStatement.setAltercode(altercode);
        bestBeforeStatement.setBestBefore(bestBefore);
        bestBeforeStatement.setNote(note);
        bestBeforeStatement.setAlert(alert);

        String result = bestBeforeDao.saveStatement(bestBeforeStatement);
        model.addAttribute("result", result);
        return "redirect:bestBeforeDashboard.htm";

    }

    @RequestMapping(value = "bestBeforeDashboard", method = RequestMethod.GET)
    public String bestBeforeDashboard(ModelMap modelMap) {
        ArrayList<BestBeforeStatement> bestBeforeStatements = bestBeforeDao.getAllBestBeforeStatements();
        LinkedHashMap<String, Item> pet4UItems = this.inventoryDao.getpet4UItemsRowByRow();
        for (BestBeforeStatement statment : bestBeforeStatements) {
            String altercode = statment.getAltercode();
            Item pet4uItem = pet4UItems.get(altercode);
            if (pet4uItem == null) {
                System.out.println("Pet4uItem  not present in the lists from microsoft db");
            }
            statment.setDescription(pet4uItem.getDescription());
            modelMap.addAttribute("bestBeforeStatements", bestBeforeStatements);
        }
        return "bestBefore/bestBeforeDashboard";
    }

    @RequestMapping(value = "deleteBestBeforeStatement", method = RequestMethod.GET)
    public String deleteBestBeforeStatement(@RequestParam(name = "id") String id) {
        bestBeforeDao.deleteStatement(id);
        return "redirect:bestBeforeDashboard.htm";
    }

    @RequestMapping(value = "editBestBeforeStatement", method = RequestMethod.POST)
    public String editBestBeforeStatement(@RequestParam(name = "id") String id,
            @RequestParam(name = "bestBefore") String bestBefore,
            @RequestParam(name = "alert") String alert) {
        System.out.println("ID:" + id);
        System.out.println("bestBefore:" + bestBefore);
        System.out.println("alert:" + alert);
        String editResult = bestBeforeDao.editStatement(id, bestBefore, alert);
        System.out.println(editResult);
        return "redirect:bestBeforeDashboard.htm";
    }

}
