package Camelot.CamelotItem;

import java.util.ArrayList;
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

    @RequestMapping(value = "goForCamelotPositionChanign_Α")
    public String goForCamelotPositionChanign_Α(@RequestParam(name = "itemCode") String itemCode,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
        model.addAttribute("itemCode", itemCode);

        return "/camelot/camelotItem/goForCamelotPositionChanign_Α";
    }

    @RequestMapping(value = "goForCamelotPositionChanign_Β")
    public String goForCamelotPositionChanign_Β(@RequestParam(name = "itemCode") String itemCode,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
        ArrayList<String> camelotPickingPositions = camelotItemDao.getCamelotPickingPositions();
        model.addAttribute("itemCode", itemCode);
        model.addAttribute("camelotPickingPositions", camelotPickingPositions);
        return "/camelot/camelotItem/goForCamelotPositionChanign_Β";
    }
}
