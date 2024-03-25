/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Eksagoges;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EksagogesController {

    @RequestMapping(value = "sixMonthsEksagoges")
    public String deliveryDemo(ModelMap modelMap) {

        modelMap.addAttribute("ar", "");
        return "eksagoges/sixMonthsEksagoges";

    }

}
