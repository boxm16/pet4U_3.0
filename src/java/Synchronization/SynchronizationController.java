/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Synchronization;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SynchronizationController {

    //------------------here is part for position update
    @RequestMapping(value = "synchronizePositions")
    public String synchronizePositions(@RequestParam(name = "code") String code, ModelMap model) {
        System.out.println("CODEEEE" + code);

        return "redirect:camelotItemsWithPossitionDifference.htm";
    }

}
