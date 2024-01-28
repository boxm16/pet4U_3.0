package Endo;

import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EndoControllerX {

    @RequestMapping(value = "endoParalaves", method = RequestMethod.GET)
    public String endoParalaves(ModelMap modelMap) {
     EndoDaoX endoDaoX=new EndoDaoX();
        LinkedHashMap<String, EndoBinder> allEndoBinders = endoDaoX.getAllEndoBinders();
        return "endo/endoParalaves";
    }

}
