package Encryption;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
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
}
