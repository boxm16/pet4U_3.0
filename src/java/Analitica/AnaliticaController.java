
package Analitica;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnaliticaController {
    @RequestMapping(value = "/itemAnalysis", method = RequestMethod.GET)
    public String itemSalesAnalysis(@RequestParam String code, ModelMap model) {

        
        
       
        model.addAttribute("item", code);

        return "analitica/itemAnalysis";
    } 
}
