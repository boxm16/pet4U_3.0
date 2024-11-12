package Encryption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EncryptionController {

    @RequestMapping(value = "authorize", method = RequestMethod.POST)
    public String encryptedIndexLefteris(HttpSession session, @RequestParam(name = "password") String password) {
        if (password.equals("per4ito")
                || password.equals("PER4ITO")
                || password.equals("ΠΕΡ4ΙΤΟ")
                || password.equals("περ4ιτο")) {
            session.setAttribute("user", "identified");
            session.setAttribute("userName", "me");
            return "adminIndex";
        } else if (password.equals("vasilis")
                || password.equals("VASILIS")
                || password.equals("ωασιλισ")
                || password.equals("ΩΑΣΙΛΙΣ")) {
            session.setAttribute("user", "identified");
            session.setAttribute("userName", "vasilis");
            return "vasilisIndex";
        } else {
            session.setAttribute("user", "unidentified");
            session.setAttribute("userName", "unidentified");
            return "errorPage";
        }
    }

    @RequestMapping(value = "encryptedIndexMe", method = RequestMethod.GET)
    public String encryptedIndexMe(HttpSession session) {
        session.setAttribute("user", "identified");
        session.setAttribute("userName", "me");
        return "adminIndex";
    }

    @RequestMapping(value = "encryptedIndexLefteris", method = RequestMethod.GET)
    public String encryptedIndexLefteris(HttpSession session) {
        session.setAttribute("user", "identified");
        session.setAttribute("userName", "Lefteris");
        return "redirect:index.htm";
    }

    @RequestMapping(value = "adminIndex", method = RequestMethod.GET)
    public String adminIndex(HttpSession session, ModelMap modelMap, HttpServletRequest req) {

        String userName = (String) session.getAttribute("userName");

        if (userName == null) {
            return "authorization";
        }
        if (userName.equals("vasilis")) {
            return "vasilisIndex";
        }
        if (userName.equals("me")) {
            return "adminIndex";
        }
        return "authorization";
    }

    @RequestMapping(value = "signOut", method = RequestMethod.GET)
    public String signOut(HttpSession session) {
        session.setAttribute("user", null);
        session.setAttribute("userName", null);
        return "index";
    }
}
