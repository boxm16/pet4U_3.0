/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EndoAnalysisController {

    @RequestMapping(value = "endoAnalysis", method = RequestMethod.GET)
    public String endoAnalysis(ModelMap modelMap) {
        EndoAnalysisDao endoAnalysisDao = new EndoAnalysisDao();
        LinkedHashMap<String, EndoApostolis> endoApostolissVaribobis = endoAnalysisDao.getEndoApostolissVaribobis();

        modelMap.addAttribute("endoApostolissVaribobis", endoApostolissVaribobis);
        return "endo/endoAnalysis";
    }
}
