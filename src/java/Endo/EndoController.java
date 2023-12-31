package Endo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EndoController {

    @RequestMapping(value = "deltioApostolis")
    private String deltioApostolis() {
        return "endo/deltioApostolis";
    }

}
