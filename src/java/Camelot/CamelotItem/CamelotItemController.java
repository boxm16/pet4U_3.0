package Camelot.CamelotItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotItemController {

    @Autowired
    CamelotItemDao camelotItemDao;

    @RequestMapping(value = "goForCamelotPositionChanging_Α")
    public String goForCamelotPositionChanging_Α(@RequestParam(name = "itemCode") String itemCode,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
        model.addAttribute("itemCode", itemCode);

        return "/camelot/camelotItem/camelotItemPositionChangingServant_Α";
    }

    @RequestMapping(value = "confirmCamelotItemPositionChanging")
    public String goForCamelotPositionChanging_Α(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "pickingPositionName") String pickingPositionName,
            ModelMap model, HttpSession session) {

        pickingPositionName = pickingPositionName.trim();
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
        ArrayList<String> camelotPickingPositions = camelotItemDao.getCamelotPickingPositions();
        if (!camelotPickingPositions.contains(pickingPositionName.trim())) {

            model.addAttribute("itemCode", itemCode);
            model.addAttribute("camelotPickingPositions", camelotPickingPositions);
            return "/camelot/camelotItem/camelotItemPositionChangingServant_Β";
        }
        model.addAttribute("itemCode", itemCode);
        model.addAttribute("pickingPositionName", pickingPositionName);
        return "/camelot/camelotItem/camelotItemPositionChangingConfirmation";
    }

    @RequestMapping(value = "changeCamelotItemPosition")
    public String changeCamelotItemPosition(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "pickingPositionName") String pickingPositionName,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }

        CamelotItemPositionChangingSapApiClient changer = new CamelotItemPositionChangingSapApiClient();
        String result = changer.change(itemCode, pickingPositionName);
        if (result.equals("DONE")) {
            return "redirect:getCamelotItemFromSapHanaView.htm?altercode=" + itemCode;
        } else {
            model.addAttribute("message", result);
            return "erroPage";
        }

    }

    //you may delete this method, it is unneccessary anymore
    @RequestMapping(value = "goForCamelotPositionChanging_B")
    public String goForCamelotPositionChanging_B(@RequestParam(name = "itemCode") String itemCode,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
        ArrayList<String> camelotPickingPositions = camelotItemDao.getCamelotPickingPositions();
        model.addAttribute("itemCode", itemCode);
        model.addAttribute("camelotPickingPositions", camelotPickingPositions);
        return "/camelot/camelotItem/camelotItemPositionChangingServant_B";
    }
    //-----------------OLD CAMELOT POSITION CHANGINH---------------------

    @RequestMapping(value = "goForCamelotPositionChanging_X")
    public String goForCamelotPositionChanging_X(@RequestParam(name = "itemId") String itemId,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || (!userName.equals("me") && !userName.equals("Lefteris") && !userName.equals("Pelagia"))) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
        ArrayList<String> camelotItemPositions = camelotItemDao.getCamelotItemPositions(itemId);
        LinkedHashMap<Integer, String> camelotPickingPositionsXA = camelotItemDao.getCamelotPickingPositionsXA();
        LinkedHashMap<Integer, String> camelotPickingPositionsXB = camelotItemDao.getCamelotPickingPositionsXB();
        model.addAttribute("itemId", itemId);

        model.addAttribute("camelotItemPositions", camelotItemPositions);
        model.addAttribute("camelotPickingPositionsXA", camelotPickingPositionsXA);
        model.addAttribute("camelotPickingPositionsXB", camelotPickingPositionsXB);

        return "/camelot/camelotItem/camelotItemPositionChangingServant_X";
    }

    @RequestMapping(value = "confirmCamelotItemPositionChangingX")
    public String confirmCamelotItemPositionChangingX(@RequestParam(name = "itemId") String itemId,
            @RequestParam(name = "pickingPositionIdXA") String pickingPositionIdXA,
            @RequestParam(name = "pickingPositionIdXB") String pickingPositionIdXB,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || (!userName.equals("me") && !userName.equals("Lefteris") && !userName.equals("Pelagia"))) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
        model.addAttribute("pickingPositionIdXA", pickingPositionIdXA);
        model.addAttribute("pickingPositionIdXB", pickingPositionIdXB);

        System.out.println("itemId: " + itemId + ". POSITION: " + pickingPositionIdXA + ":" + pickingPositionIdXB);

        String result = camelotItemDao.updateCamelotItemPosition(itemId, pickingPositionIdXA, pickingPositionIdXB);

        if (result.equals("DONE")) {
            return "redirect:findCamelotItemByItemId.htm?itemId=" + itemId;
        } else {
            model.addAttribute("message", result);
            return "erroPage";
        }

    }
}
