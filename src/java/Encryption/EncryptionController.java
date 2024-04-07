package Encryption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EncryptionController {

    @RequestMapping(value = "encryptedIndexMe", method = RequestMethod.GET)
    public String encryptedIndexMe(HttpSession session) {
        session.setAttribute("user", "identified");
        session.setAttribute("userName", "me");
        return "redirect:index.htm";
    }

    @RequestMapping(value = "encryptedIndexLefteris", method = RequestMethod.GET)
    public String encryptedIndexLefteris(HttpSession session) {
        session.setAttribute("user", "identified");
        session.setAttribute("userName", "Lefteris");
        return "redirect:index.htm";
    }

    @RequestMapping(value = "adminIndex", method = RequestMethod.GET)
    public String adminIndex(HttpSession session, ModelMap modelMap, HttpServletRequest req) {

        String ipAddress = req.getRemoteAddr();
        System.out.println("Request IP Address: " + ipAddress);

        if (ipAddress.equals("192.168.0.141")||ipAddress.contains("0:0:0:0:0:0:0:1")) {
            session.setAttribute("user", "identified");
            session.setAttribute("userName", "super");
            return "adminIndex";
        }
        String user = (String) session.getAttribute("user");

        System.out.println("Super User Status:" + user);
        if (user == null) {
            modelMap.addAttribute("message", "You are not authorized for this paged");
            return "errorPage";
        } else if (user.equals(
                "identified")) {
            return "adminIndex";
        } else {
            modelMap.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }

    }
}
