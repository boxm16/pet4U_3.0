package Camelot.CamelotItem;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotItemController {

    @RequestMapping(value = "goForCamelotPositionChanign")
    public String goForCamelotPositionChanign(@RequestParam(name = "itemId") String itemId,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }

        model.addAttribute("itemId", itemId);
        return "/camelot/camelotItem/camelotItemPositionChanignServant";
    }
}
