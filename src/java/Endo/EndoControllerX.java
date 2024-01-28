
package Endo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EndoControllerX {
    
    @RequestMapping(value = "endoParalaves", method = RequestMethod.GET)
    public String endoParalaves(ModelMap modelMap) {

        

        return "endo/endoParalaves";
    }
    
}
