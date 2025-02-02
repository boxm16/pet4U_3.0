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

    @RequestMapping(value = "goForCamelotPositionChanign")
    public String goForCamelotPositionChanign(@RequestParam(name = "itemId") String itemId,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
        ArrayList<String> camelotPickingPositions = camelotItemDao.getCamelotPickingPositions();
        model.addAttribute("itemId", itemId);
        model.addAttribute("camelotPickingPositions", camelotPickingPositions);
        return "/camelot/camelotItem/camelotItemPositionChangingServant";
    }
}
