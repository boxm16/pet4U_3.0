/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotItemsOfOurInterest_V_3_1;

import java.util.LinkedHashMap;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotItemsOfOurInterestController {

    @Autowired
    private CamelotItemsOfOurInterestDao camelotItemsOfOurInterestDao;

    @RequestMapping(value = "camelotOrderAlert")
    public String camelotOrderAlert(ModelMap model) {
        TreeMap<String, CamelotItemOfInterest> returnResult = new TreeMap<>();

        LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfInterest = camelotItemsOfOurInterestDao.getCamelotItemsOfOurInterset();

        return "/camelot/camelotOrderAlert";
    }
}
