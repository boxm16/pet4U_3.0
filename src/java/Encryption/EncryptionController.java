package Encryption;

import java.time.LocalDateTime;
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
        } else if (password.equals("mixelo") || password.equals("1111")) {
            session.setAttribute("user", "identified");
            session.setAttribute("userName", "me");
            return "/pet4u/pet4uDashboard";
        } else if (password.equals("bilobi")) {
            session.setAttribute("user", "identified");
            session.setAttribute("userName", "Vasilis");
            return "vasilisIndex";
        } else if (password.equals("pela")) {
            session.setAttribute("user", "identified");
            session.setAttribute("userName", "Pelagia");
            return "/camelot/camelotDashboard";
        } else {
            session.setAttribute("user", "unidentified");
            session.setAttribute("userName", "unidentified");
            return "errorPage";
        }
    }

    @RequestMapping(value = "encryptedIndexMe", method = RequestMethod.GET)
    public String encryptedIndexMe(HttpSession session) {
        System.out.println("User Me Identified : " + LocalDateTime.now());
        session.setAttribute("user", "identified");
        session.setAttribute("userName", "me");
        return "adminIndex";
    }

    @RequestMapping(value = "encryptedIndexLefteris", method = RequestMethod.GET)
    public String encryptedIndexLefteris(HttpSession session) {
        System.out.println("User Lefteris Identified : " + LocalDateTime.now());
        session.setAttribute("user", "identified");
        session.setAttribute("userName", "Lefteris");
        return "redirect:index.htm";
    }

    @RequestMapping(value = "encryptedIndexVasilis", method = RequestMethod.GET)
    public String encryptedIndexVasilis(HttpSession session) {
        System.out.println("User Vasilis Identified : " + LocalDateTime.now());
        session.setAttribute("user", "identified");
        session.setAttribute("userName", "Vasilis");
        return "vasilisIndex";
    }

    @RequestMapping(value = "encryptedIndexStavros", method = RequestMethod.GET)
    public String encryptedIndexStavros(HttpSession session) {
        System.out.println("User Stavros Identified : " + LocalDateTime.now());
        session.setAttribute("user", "identified");
        session.setAttribute("userName", "Stavros");
        return "redirect:index.htm";
    }

    @RequestMapping(value = "adminIndex", method = RequestMethod.GET)
    public String adminIndex(HttpSession session, ModelMap modelMap, HttpServletRequest req) {

        String userName = (String) session.getAttribute("userName");

        if (userName == null) {
            return "authorization";
        }
        if (userName.equals("Vasilis")) {
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

    @RequestMapping(value = "goForAuthorization", method = RequestMethod.GET)
    public String goForAuthorization() {

        return "authorization";
    }
}
