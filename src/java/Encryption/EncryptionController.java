package Encryption;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EncryptionController {

    @RequestMapping(value = "encryptedIndex", method = RequestMethod.GET)
    public String deltioApostolis(HttpSession session) {
        session.setAttribute("superUser", "identified");
        
        return "redirect:index.htm";
        
    }
}
